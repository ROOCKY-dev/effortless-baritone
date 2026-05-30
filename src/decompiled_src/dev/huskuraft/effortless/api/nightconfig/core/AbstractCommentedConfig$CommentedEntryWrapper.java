/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.AbstractConfig;
import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

protected class AbstractCommentedConfig.CommentedEntryWrapper
extends AbstractConfig.EntryWrapper
implements CommentedConfig.Entry {
    private List<String> path;

    public AbstractCommentedConfig.CommentedEntryWrapper(Map.Entry<String, Object> mapEntry) {
        super(mapEntry);
        this.path = null;
    }

    protected List<String> getPath() {
        if (this.path == null) {
            this.path = Collections.singletonList(this.getKey());
        }
        return this.path;
    }

    @Override
    public String getComment() {
        return AbstractCommentedConfig.this.getComment(this.getPath());
    }

    @Override
    public String setComment(String comment) {
        return AbstractCommentedConfig.this.setComment(this.getPath(), comment);
    }

    @Override
    public String removeComment() {
        return AbstractCommentedConfig.this.removeComment(this.getPath());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractCommentedConfig.CommentedEntryWrapper) {
            AbstractCommentedConfig.CommentedEntryWrapper other = (AbstractCommentedConfig.CommentedEntryWrapper)obj;
            return Objects.equals(this.getKey(), other.getKey()) && Objects.equals(this.getValue(), other.getValue()) && Objects.equals(this.getComment(), other.getComment());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Objects.hashCode(this.getKey());
        result = 31 * result + Objects.hashCode(this.getValue());
        result = 31 * result + Objects.hashCode(this.getComment());
        return result;
    }
}
