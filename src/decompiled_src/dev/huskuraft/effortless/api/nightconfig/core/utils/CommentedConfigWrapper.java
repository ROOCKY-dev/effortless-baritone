/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CommentedConfigWrapper<C extends CommentedConfig>
extends ConfigWrapper<C>
implements CommentedConfig {
    protected CommentedConfigWrapper(C config) {
        super(config);
    }

    @Override
    public String getComment(List<String> path) {
        return ((CommentedConfig)this.config).getComment(path);
    }

    @Override
    public boolean containsComment(List<String> path) {
        return ((CommentedConfig)this.config).containsComment(path);
    }

    @Override
    public String setComment(List<String> path, String comment) {
        return ((CommentedConfig)this.config).setComment(path, comment);
    }

    @Override
    public String removeComment(List<String> path) {
        return ((CommentedConfig)this.config).removeComment(path);
    }

    @Override
    public Map<String, String> commentMap() {
        return ((CommentedConfig)this.config).commentMap();
    }

    @Override
    public Set<? extends CommentedConfig.Entry> entrySet() {
        return ((CommentedConfig)this.config).entrySet();
    }

    @Override
    public void clearComments() {
        ((CommentedConfig)this.config).clearComments();
    }

    @Override
    public void putAllComments(Map<String, UnmodifiableCommentedConfig.CommentNode> comments) {
        ((CommentedConfig)this.config).putAllComments(comments);
    }

    @Override
    public void putAllComments(UnmodifiableCommentedConfig commentedConfig) {
        ((CommentedConfig)this.config).putAllComments(commentedConfig);
    }

    @Override
    public Map<String, UnmodifiableCommentedConfig.CommentNode> getComments() {
        return ((CommentedConfig)this.config).getComments();
    }

    @Override
    public CommentedConfig createSubConfig() {
        return ((CommentedConfig)this.config).createSubConfig();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ':' + this.config;
    }
}
