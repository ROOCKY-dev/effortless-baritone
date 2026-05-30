/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CommentedConfig.1
implements UnmodifiableCommentedConfig {
    CommentedConfig.1() {
    }

    @Override
    public <T> T getRaw(List<String> path) {
        return CommentedConfig.this.getRaw(path);
    }

    @Override
    public String getComment(List<String> path) {
        return CommentedConfig.this.getComment(path);
    }

    @Override
    public boolean contains(List<String> path) {
        return CommentedConfig.this.contains(path);
    }

    @Override
    public boolean containsComment(List<String> path) {
        return CommentedConfig.this.containsComment(path);
    }

    @Override
    public int size() {
        return CommentedConfig.this.size();
    }

    @Override
    public Map<String, Object> valueMap() {
        return Collections.unmodifiableMap(CommentedConfig.this.valueMap());
    }

    @Override
    public Map<String, String> commentMap() {
        return Collections.unmodifiableMap(CommentedConfig.this.commentMap());
    }

    @Override
    public Map<String, UnmodifiableCommentedConfig.CommentNode> getComments() {
        return CommentedConfig.this.getComments();
    }

    @Override
    public Set<? extends UnmodifiableCommentedConfig.Entry> entrySet() {
        return CommentedConfig.this.entrySet();
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return CommentedConfig.this.configFormat();
    }
}
