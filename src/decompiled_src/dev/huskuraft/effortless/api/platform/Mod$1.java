/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.Mod;

static class Mod.1
implements Mod {
    final /* synthetic */ String val$id;
    final /* synthetic */ String val$version;
    final /* synthetic */ String val$description;
    final /* synthetic */ String val$name;

    Mod.1() {
        this.val$id = string;
        this.val$version = string2;
        this.val$description = string3;
        this.val$name = string4;
    }

    @Override
    public String getId() {
        return this.val$id;
    }

    @Override
    public String getVersionStr() {
        return this.val$version;
    }

    @Override
    public String getDescription() {
        return this.val$description;
    }

    @Override
    public String getName() {
        return this.val$name;
    }
}
