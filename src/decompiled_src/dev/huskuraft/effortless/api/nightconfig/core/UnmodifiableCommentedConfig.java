/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.FakeUnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UnmodifiableCommentedConfig
extends UnmodifiableConfig {
    default public String getComment(String path) {
        return this.getComment(StringUtils.split(path, '.'));
    }

    public String getComment(List<String> var1);

    default public Optional<String> getOptionalComment(String path) {
        return this.getOptionalComment(StringUtils.split(path, '.'));
    }

    default public Optional<String> getOptionalComment(List<String> path) {
        return Optional.ofNullable(this.getComment(path));
    }

    default public boolean containsComment(String path) {
        return this.containsComment(StringUtils.split(path, '.'));
    }

    public boolean containsComment(List<String> var1);

    public Map<String, String> commentMap();

    default public Map<String, CommentNode> getComments() {
        HashMap<String, CommentNode> map = new HashMap<String, CommentNode>();
        this.getComments(map);
        return map;
    }

    default public void getComments(Map<String, CommentNode> destination) {
        for (Entry entry : this.entrySet()) {
            String key = entry.getKey();
            String comment = entry.getComment();
            Object value = entry.getValue();
            if (comment == null && !(value instanceof UnmodifiableCommentedConfig)) continue;
            Map<String, CommentNode> children = value instanceof UnmodifiableCommentedConfig ? ((UnmodifiableCommentedConfig)value).getComments() : null;
            CommentNode node = new CommentNode(comment, children);
            destination.put(key, node);
        }
    }

    public Set<? extends Entry> entrySet();

    public static UnmodifiableCommentedConfig fake(UnmodifiableConfig config) {
        if (config instanceof UnmodifiableCommentedConfig) {
            return (UnmodifiableCommentedConfig)config;
        }
        return new FakeUnmodifiableCommentedConfig(config);
    }

    public static interface Entry
    extends UnmodifiableConfig.Entry {
        public String getComment();
    }

    public static final class CommentNode {
        private final String comment;
        private final Map<String, CommentNode> children;

        public CommentNode(String comment, Map<String, CommentNode> children) {
            if (comment == null && children == null) {
                throw new IllegalArgumentException("There is no point in creating a CommentNode if the comment AND the children are null.");
            }
            this.comment = comment;
            this.children = children;
        }

        public String getComment() {
            return this.comment;
        }

        public Map<String, CommentNode> getChildren() {
            return this.children;
        }
    }
}
