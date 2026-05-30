/*
 * Decompiled with CFR 0.152.
 */
package baritone.api;

import baritone.api.IBaritoneProvider;
import baritone.api.Settings;
import baritone.api.utils.SettingsUtil;

public final class BaritoneAPI {
    private static final IBaritoneProvider provider;
    private static final Settings settings;

    public static IBaritoneProvider getProvider() {
        return provider;
    }

    public static Settings getSettings() {
        return settings;
    }

    static {
        settings = new Settings();
        SettingsUtil.readAndApply(settings, "settings.txt");
        try {
            provider = (IBaritoneProvider)Class.forName("baritone.BaritoneProvider").newInstance();
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

