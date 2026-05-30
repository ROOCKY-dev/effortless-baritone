/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;

public static interface UnmodifiableCommentedConfig.Entry
extends UnmodifiableConfig.Entry {
    public String getComment();
}
