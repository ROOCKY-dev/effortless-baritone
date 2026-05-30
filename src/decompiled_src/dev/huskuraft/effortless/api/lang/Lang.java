/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.lang;

import dev.huskuraft.effortless.api.platform.PlatformLoader;

public interface Lang {
    public static Lang getInstance() {
        return PlatformLoader.getSingleton(new Lang[0]);
    }

    public static String asKey(String namespace, String key) {
        return "%s.%s".formatted(namespace, key);
    }

    public static String getKeyDesc(String namespace, String key) {
        return "key.%s.%s".formatted(namespace, key);
    }

    public static boolean hasKey(String key) {
        return Lang.getInstance().has(key);
    }

    public String getOrDefault(String var1);

    public boolean has(String var1);

    public boolean isDefaultRightToLeft();
}
