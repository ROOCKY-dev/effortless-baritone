/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface ResourceLocation
extends PlatformReference {
    public static ResourceLocation decompose(String value, String separator) {
        try {
            return ResourceLocation.of(value.split(separator)[0], value.split(separator)[1]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.valueOf(e) + "for value: " + value);
        }
    }

    public static ResourceLocation decompose(String value) {
        return ResourceLocation.decompose(value, ":");
    }

    public static ResourceLocation vanilla(String path) {
        return ResourceLocation.of("minecraft", path);
    }

    public static ResourceLocation of(String namespace, String path) {
        return ContentFactory.getInstance().newResourceLocation(namespace, path);
    }

    public String getNamespace();

    public String getPath();

    default public String getString() {
        return this.getNamespace() + ":" + this.getPath();
    }
}
