/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.LoaderType;
import java.lang.reflect.InvocationTargetException;
import java.util.ServiceConfigurationError;

public record PlatformLoader.Loader<S>(Class<S> clazz, String className, ClassLoader loader) {
    private static LoaderType getLoaderTypeByThread() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader.getClass().getPackageName().equals("net.fabricmc.loader.impl.launch.knot")) {
            return LoaderType.FABRIC;
        }
        if (loader.getClass().getPackageName().startsWith("org.quiltmc.")) {
            return LoaderType.QUILT;
        }
        if (loader.getClass().getPackageName().equals("cpw.mods.modlauncher")) {
            if (loader.getDefinedPackage("net.neoforged.neoforge.server") != null) {
                return LoaderType.NEO_FORGE;
            }
            return LoaderType.FORGE;
        }
        throw new IllegalStateException("Unknown loader: " + loader.getClass().getPackageName());
    }

    public LoaderType getLoaderTypeByName() {
        if (this.className.contains(".vanilla.")) {
            return LoaderType.VANILLA;
        }
        if (this.className.contains(".fabric.")) {
            return LoaderType.FABRIC;
        }
        if (this.className.contains(".quilt.")) {
            return LoaderType.QUILT;
        }
        if (this.className.contains(".forge.")) {
            return LoaderType.FORGE;
        }
        if (this.className.contains(".neoforge.")) {
            return LoaderType.NEO_FORGE;
        }
        return LoaderType.VANILLA;
    }

    private S create() throws ClassNotFoundException, ClassCastException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> childClass = Class.forName(this.className, false, this.loader);
        if (!this.clazz.isAssignableFrom(childClass)) {
            throw new ClassCastException(this.className + " is not a subtype of " + this.clazz.getName());
        }
        return this.clazz.cast(childClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
    }

    public boolean isPresent() {
        return switch (this.getLoaderTypeByName()) {
            default -> throw new MatchException(null, null);
            case LoaderType.FABRIC -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.FABRIC) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.QUILT -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.QUILT) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.FORGE -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.FORGE) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.NEO_FORGE -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.NEO_FORGE) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.VANILLA -> true;
        };
    }

    public boolean isCompatible() {
        return switch (this.getLoaderTypeByName()) {
            default -> throw new MatchException(null, null);
            case LoaderType.FABRIC -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.FABRIC || PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.QUILT) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.QUILT -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.QUILT) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.FORGE -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.FORGE) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.NEO_FORGE -> {
                if (PlatformLoader.Loader.getLoaderTypeByThread() == LoaderType.NEO_FORGE) {
                    yield true;
                }
                yield false;
            }
            case LoaderType.VANILLA -> true;
        };
    }

    public S get() {
        if (!this.isPresent() && !this.isCompatible()) {
            throw new ServiceConfigurationError("Cannot find " + String.valueOf((Object)this.getLoaderTypeByName()) + " class " + this.clazz.getName() + " using loader " + String.valueOf((Object)PlatformLoader.Loader.getLoaderTypeByThread()));
        }
        try {
            return this.create();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ServiceConfigurationError("Cannot load " + String.valueOf((Object)this.getLoaderTypeByName()) + " class " + this.clazz.getName() + " using loader " + String.valueOf((Object)PlatformLoader.Loader.getLoaderTypeByThread()));
        }
    }
}
