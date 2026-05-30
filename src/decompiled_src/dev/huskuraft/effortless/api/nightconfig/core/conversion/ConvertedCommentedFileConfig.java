/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.conversion.AbstractConvertedCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConversionTable;
import dev.huskuraft.effortless.api.nightconfig.core.file.CommentedFileConfig;
import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ConvertedCommentedFileConfig
extends AbstractConvertedCommentedConfig<CommentedFileConfig>
implements CommentedFileConfig {
    public ConvertedCommentedFileConfig(CommentedFileConfig config, ConversionTable readTable, ConversionTable writeTable, Predicate<Class<?>> supportPredicate) {
        this(config, readTable::convert, writeTable::convert, supportPredicate);
    }

    public ConvertedCommentedFileConfig(CommentedFileConfig config, Function<Object, Object> readConversion, Function<Object, Object> writeConversion, Predicate<Class<?>> supportPredicate) {
        super(config, readConversion, writeConversion, supportPredicate);
    }

    @Override
    public File getFile() {
        return ((CommentedFileConfig)this.config).getFile();
    }

    @Override
    public Path getNioPath() {
        return ((CommentedFileConfig)this.config).getNioPath();
    }

    @Override
    public void save() {
        ((CommentedFileConfig)this.config).save();
    }

    @Override
    public void load() {
        ((CommentedFileConfig)this.config).load();
    }

    @Override
    public void close() {
        ((CommentedFileConfig)this.config).close();
    }
}
