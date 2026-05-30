/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.core;

import javax.annotation.Nullable;

public static enum PlayerSkin.Model {
    SLIM("slim"),
    WIDE("default");

    private final String id;

    private PlayerSkin.Model(String id) {
        this.id = id;
    }

    public static PlayerSkin.Model byName(@Nullable String name) {
        if (name == null) {
            return WIDE;
        }
        if (name.equals("slim")) {
            return SLIM;
        }
        return WIDE;
    }

    public String id() {
        return this.id;
    }
}
