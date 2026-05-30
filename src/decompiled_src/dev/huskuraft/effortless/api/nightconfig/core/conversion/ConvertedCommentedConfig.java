/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.AbstractConvertedCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConversionTable;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ConvertedCommentedConfig
extends AbstractConvertedCommentedConfig<CommentedConfig> {
    public ConvertedCommentedConfig(CommentedConfig config, ConversionTable readTable, ConversionTable writeTable, Predicate<Class<?>> supportPredicate) {
        this(config, readTable::convert, writeTable::convert, supportPredicate);
    }

    public ConvertedCommentedConfig(CommentedConfig config, Function<Object, Object> readConversion, Function<Object, Object> writeConversion, Predicate<Class<?>> supportPredicate) {
        super(config, readConversion, writeConversion, supportPredicate);
    }
}
