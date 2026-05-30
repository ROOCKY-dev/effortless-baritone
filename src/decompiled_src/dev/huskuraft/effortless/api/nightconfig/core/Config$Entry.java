/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;

public static interface Config.Entry
extends UnmodifiableConfig.Entry {
    public <T> T setValue(Object var1);
}
