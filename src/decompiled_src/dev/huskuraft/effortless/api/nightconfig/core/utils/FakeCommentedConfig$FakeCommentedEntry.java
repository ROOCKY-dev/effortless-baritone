/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.Config;

private static final class FakeCommentedConfig.FakeCommentedEntry
implements CommentedConfig.Entry {
    private final Config.Entry entry;

    private FakeCommentedConfig.FakeCommentedEntry(Config.Entry entry) {
        this.entry = entry;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public String getKey() {
        return this.entry.getKey();
    }

    @Override
    public <T> T getRawValue() {
        return this.entry.getRawValue();
    }

    @Override
    public String setComment(String comment) {
        return null;
    }

    @Override
    public String removeComment() {
        return null;
    }

    @Override
    public <T> T setValue(Object value) {
        return this.entry.setValue(value);
    }
}
