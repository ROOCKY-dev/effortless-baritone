/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;

class AbstractConvertedCommentedConfig.1
implements CommentedConfig.Entry {
    final /* synthetic */ CommentedConfig.Entry val$entry;

    AbstractConvertedCommentedConfig.1() {
        this.val$entry = entry;
    }

    @Override
    public String getComment() {
        return this.val$entry.getComment();
    }

    @Override
    public String setComment(String comment) {
        return this.val$entry.setComment(comment);
    }

    @Override
    public String removeComment() {
        return this.val$entry.removeComment();
    }

    @Override
    public String getKey() {
        return this.val$entry.getKey();
    }

    @Override
    public <T> T getRawValue() {
        return (T)AbstractConvertedCommentedConfig.this.readConversion.apply(this.val$entry.getRawValue());
    }

    @Override
    public <T> T setValue(Object value) {
        return (T)AbstractConvertedCommentedConfig.this.readConversion.apply(this.val$entry.setValue(AbstractConvertedCommentedConfig.this.writeConversion.apply(value)));
    }
}
