/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import java.util.Map;

public static final class UnmodifiableCommentedConfig.CommentNode {
    private final String comment;
    private final Map<String, UnmodifiableCommentedConfig.CommentNode> children;

    public UnmodifiableCommentedConfig.CommentNode(String comment, Map<String, UnmodifiableCommentedConfig.CommentNode> children) {
        if (comment == null && children == null) {
            throw new IllegalArgumentException("There is no point in creating a CommentNode if the comment AND the children are null.");
        }
        this.comment = comment;
        this.children = children;
    }

    public String getComment() {
        return this.comment;
    }

    public Map<String, UnmodifiableCommentedConfig.CommentNode> getChildren() {
        return this.children;
    }
}
