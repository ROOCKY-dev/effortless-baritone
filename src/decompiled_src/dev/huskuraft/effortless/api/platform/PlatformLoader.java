/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.LoaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class PlatformLoader<S>
implements Iterable<Loader<S>> {
    private static final String PREFIX = "META-INF/services/";
    private static final Map<Class<?>, Object> INSTANCES = Collections.synchronizedMap(new HashMap());
    private final Class<S> service;
    private final ClassLoader loader;

    private PlatformLoader(Class<S> svc, ClassLoader cl) {
        this.service = Objects.requireNonNull(svc, "Service interface cannot be null");
        this.loader = cl == null ? ClassLoader.getSystemClassLoader() : cl;
    }

    public static <S> PlatformLoader<S> load(Class<S> service, ClassLoader loader) {
        return new PlatformLoader<S>(service, loader);
    }

    public static <S> PlatformLoader<S> load(Class<S> service) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return PlatformLoader.load(service, cl);
    }

    public static <S> PlatformLoader<S> loadInstalled(Class<S> service) {
        ClassLoader prev = null;
        for (ClassLoader cl = ClassLoader.getSystemClassLoader(); cl != null; cl = cl.getParent()) {
            prev = cl;
        }
        return PlatformLoader.load(service, prev);
    }

    public static <S> S getSingleton(Class<S> service) {
        if (!INSTANCES.containsKey(service)) {
            INSTANCES.put(service, PlatformLoader.load(service).get());
        }
        return service.cast(INSTANCES.get(service));
    }

    public static <S> S getSingleton(S ... typeGetter) {
        return (S)PlatformLoader.getSingleton(typeGetter.getClass().getComponentType());
    }

    @Override
    public Iterator<Loader<S>> iterator() {
        return new LazyIterator<S>(this.service, this.loader);
    }

    public Stream<Loader<S>> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.iterator(), 0), false);
    }

    public Loader<S> findFirst() {
        return this.stream().filter(Loader::isPresent).findFirst().orElseGet(() -> this.stream().filter(Loader::isCompatible).findFirst().orElseThrow());
    }

    public S get() {
        return this.findFirst().get();
    }

    public String toString() {
        return "PlatformLoader[" + this.service.getName() + "]";
    }

    private static class LazyIterator<S>
    implements Iterator<Loader<S>> {
        private final Class<S> clazz;
        private final ClassLoader loader;
        private Enumeration<URL> configs = null;
        private Iterator<String> pending = null;
        private String nextClassName = null;

        private LazyIterator(Class<S> clazz, ClassLoader loader) {
            this.clazz = clazz;
            this.loader = loader;
        }

        private static int parseLine(Class<?> service, URL u, BufferedReader r, int lc, List<String> names) throws IOException, ServiceConfigurationError {
            int n;
            String ln = r.readLine();
            if (ln == null) {
                return -1;
            }
            int ci = ln.indexOf(35);
            if (ci >= 0) {
                ln = ln.substring(0, ci);
            }
            if ((n = (ln = ln.trim()).length()) != 0) {
                int cp;
                if (ln.indexOf(32) >= 0 || ln.indexOf(9) >= 0) {
                    LazyIterator.fail(service, u, lc, "Illegal configuration-file syntax");
                }
                if (!Character.isJavaIdentifierStart(cp = ln.codePointAt(0))) {
                    LazyIterator.fail(service, u, lc, "Illegal provider-class name: " + ln);
                }
                for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                    cp = ln.codePointAt(i);
                    if (Character.isJavaIdentifierPart(cp) || cp == 46) continue;
                    LazyIterator.fail(service, u, lc, "Illegal provider-class name: " + ln);
                }
                if (!names.contains(ln)) {
                    names.add(ln);
                }
            }
            return lc + 1;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static Iterator<String> parse(Class<?> service, URL url) throws ServiceConfigurationError {
            InputStream inputStream = null;
            BufferedReader reader = null;
            ArrayList<String> names = new ArrayList<String>();
            try {
                inputStream = url.openStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                int lc = 1;
                while ((lc = LazyIterator.parseLine(service, url, reader, lc, names)) >= 0) {
                }
            }
            catch (IOException x) {
                LazyIterator.fail(service, "Error reading configuration file", x);
            }
            finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                catch (IOException y) {
                    LazyIterator.fail(service, "Error closing configuration file", y);
                }
            }
            return names.iterator();
        }

        private static void fail(Class<?> service, String msg, Throwable cause) throws ServiceConfigurationError {
            throw new ServiceConfigurationError(service.getName() + ": " + msg, cause);
        }

        private static void fail(Class<?> service, String msg) throws ServiceConfigurationError {
            throw new ServiceConfigurationError(service.getName() + ": " + msg);
        }

        private static void fail(Class<?> service, URL u, int line, String msg) throws ServiceConfigurationError {
            LazyIterator.fail(service, String.valueOf(u) + ":" + line + ": " + msg);
        }

        @Override
        public boolean hasNext() {
            if (this.nextClassName != null) {
                return true;
            }
            if (this.configs == null) {
                try {
                    String fullName = PlatformLoader.PREFIX + this.clazz.getName();
                    this.configs = this.loader == null ? ClassLoader.getSystemResources(fullName) : this.loader.getResources(fullName);
                }
                catch (IOException x) {
                    LazyIterator.fail(this.clazz, "Error locating configuration files", x);
                }
            }
            while (this.pending == null || !this.pending.hasNext()) {
                if (!this.configs.hasMoreElements()) {
                    return false;
                }
                this.pending = LazyIterator.parse(this.clazz, this.configs.nextElement());
            }
            this.nextClassName = this.pending.next();
            return true;
        }

        @Override
        public Loader<S> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            String className = this.nextClassName;
            this.nextClassName = null;
            return new Loader<S>(this.clazz, className, this.loader);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public record Loader<S>(Class<S> clazz, String className, ClassLoader loader) {
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
                    if (Loader.getLoaderTypeByThread() == LoaderType.FABRIC) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.QUILT -> {
                    if (Loader.getLoaderTypeByThread() == LoaderType.QUILT) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.FORGE -> {
                    if (Loader.getLoaderTypeByThread() == LoaderType.FORGE) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.NEO_FORGE -> {
                    if (Loader.getLoaderTypeByThread() == LoaderType.NEO_FORGE) {
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
                    if (Loader.getLoaderTypeByThread() == LoaderType.FABRIC || Loader.getLoaderTypeByThread() == LoaderType.QUILT) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.QUILT -> {
                    if (Loader.getLoaderTypeByThread() == LoaderType.QUILT) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.FORGE -> {
                    if (Loader.getLoaderTypeByThread() == LoaderType.FORGE) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.NEO_FORGE -> {
                    if (Loader.getLoaderTypeByThread() == LoaderType.NEO_FORGE) {
                        yield true;
                    }
                    yield false;
                }
                case LoaderType.VANILLA -> true;
            };
        }

        public S get() {
            if (!this.isPresent() && !this.isCompatible()) {
                throw new ServiceConfigurationError("Cannot find " + String.valueOf((Object)this.getLoaderTypeByName()) + " class " + this.clazz.getName() + " using loader " + String.valueOf((Object)Loader.getLoaderTypeByThread()));
            }
            try {
                return this.create();
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new ServiceConfigurationError("Cannot load " + String.valueOf((Object)this.getLoaderTypeByName()) + " class " + this.clazz.getName() + " using loader " + String.valueOf((Object)Loader.getLoaderTypeByThread()));
            }
        }
    }
}
