/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.platform.OperatingSystem;

public final class PlatformUtils {
    public static OperatingSystem getOS() {
        return ContentFactory.getInstance().getOperatingSystem();
    }

    public static boolean isWindows() {
        return PlatformUtils.getOS() == OperatingSystem.WINDOWS;
    }

    public static boolean isMacOS() {
        return PlatformUtils.getOS() == OperatingSystem.MACOS;
    }
}
