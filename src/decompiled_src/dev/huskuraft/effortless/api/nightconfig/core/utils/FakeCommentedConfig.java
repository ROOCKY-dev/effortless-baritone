/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FakeCommentedConfig
extends ConfigWrapper<Config>
implements CommentedConfig {
    public FakeCommentedConfig(Config config) {
        super(config);
    }

    @Override
    public String getComment(List<String> path) {
        return null;
    }

    @Override
    public boolean containsComment(List<String> path) {
        return false;
    }

    @Override
    public String setComment(List<String> path, String comment) {
        return null;
    }

    @Override
    public String removeComment(List<String> path) {
        return null;
    }

    @Override
    public void clearComments() {
    }

    @Override
    public Map<String, UnmodifiableCommentedConfig.CommentNode> getComments() {
        return Collections.emptyMap();
    }

    @Override
    public void putAllComments(Map<String, UnmodifiableCommentedConfig.CommentNode> comments) {
    }

    @Override
    public void putAllComments(UnmodifiableCommentedConfig commentedConfig) {
    }

    @Override
    public Map<String, String> commentMap() {
        return Collections.emptyMap();
    }

    @Override
    public Set<? extends CommentedConfig.Entry> entrySet() {
        return new TransformingSet<Config.Entry, FakeCommentedEntry>(((Config)this.config).entrySet(), x$0 -> new FakeCommentedEntry((Config.Entry)x$0), o -> null, o -> o);
    }

    @Override
    public CommentedConfig createSubConfig() {
        return CommentedConfig.fake(super.createSubConfig());
    }

    private static final class FakeCommentedEntry
    implements CommentedConfig.Entry {
        private final Config.Entry entry;

        private FakeCommentedEntry(Config.Entry entry) {
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
}
