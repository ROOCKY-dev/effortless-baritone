/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.Registry;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface RegistryFactory {
    public static RegistryFactory getInstance() {
        return PlatformLoader.getSingleton(new RegistryFactory[0]);
    }

    default public <T extends PlatformReference> Registry<T> getRegistry(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        Class<?> clazz = typeGetter.getClass().getComponentType();
        Registry<?> registry = this.getRegistry(clazz);
        if (registry == null) {
            throw new IllegalArgumentException("Unknown registry: " + clazz.getName());
        }
        return registry;
    }

    public <T extends PlatformReference> Registry<T> getRegistry(Class<T> var1);
}
