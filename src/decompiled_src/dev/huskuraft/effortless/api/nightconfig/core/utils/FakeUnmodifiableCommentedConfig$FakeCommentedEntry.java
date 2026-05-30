/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;

private static final class FakeUnmodifiableCommentedConfig.FakeCommentedEntry
implements UnmodifiableCommentedConfig.Entry {
    private final UnmodifiableConfig.Entry entry;

    private FakeUnmodifiableCommentedConfig.FakeCommentedEntry(UnmodifiableConfig.Entry entry) {
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
}
