/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;

public static interface CommentedConfig.Entry
extends Config.Entry,
UnmodifiableCommentedConfig.Entry {
    public String setComment(String var1);

    public String removeComment();
}
