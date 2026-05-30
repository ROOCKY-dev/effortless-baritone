/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.AbstractConvertedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

abstract class AbstractConvertedCommentedConfig<C extends CommentedConfig>
extends AbstractConvertedConfig<C>
implements CommentedConfig {
    public AbstractConvertedCommentedConfig(C config, Function<Object, Object> readConversion, Function<Object, Object> writeConversion, Predicate<Class<?>> supportPredicate) {
        super(config, readConversion, writeConversion, supportPredicate);
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
    public void clearComments() {
        ((CommentedConfig)this.config).clearComments();
    }

    @Override
    public Map<String, UnmodifiableCommentedConfig.CommentNode> getComments() {
        return ((CommentedConfig)this.config).getComments();
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
    public Map<String, String> commentMap() {
        return ((CommentedConfig)this.config).commentMap();
    }

    @Override
    public Set<? extends CommentedConfig.Entry> entrySet() {
        Function<CommentedConfig.Entry, CommentedConfig.Entry> readTransfo = entry -> new CommentedConfig.Entry(){
            final /* synthetic */ CommentedConfig.Entry val$entry;
            {
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
        };
        return new TransformingSet<CommentedConfig.Entry, CommentedConfig.Entry>(((CommentedConfig)this.config).entrySet(), readTransfo, o -> null, e -> e);
    }

    @Override
    public CommentedConfig createSubConfig() {
        return ((CommentedConfig)this.config).createSubConfig();
    }
}
