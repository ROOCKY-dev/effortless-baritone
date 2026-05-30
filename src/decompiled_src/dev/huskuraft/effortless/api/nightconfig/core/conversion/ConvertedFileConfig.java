/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.conversion.AbstractConvertedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConversionTable;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConvertedFileConfig
extends AbstractConvertedConfig<FileConfig>
implements FileConfig {
    public ConvertedFileConfig(FileConfig config, ConversionTable readTable, ConversionTable writeTable, Predicate<Class<?>> supportPredicate) {
        this(config, readTable::convert, writeTable::convert, supportPredicate);
    }

    public ConvertedFileConfig(FileConfig config, Function<Object, Object> readConversion, Function<Object, Object> writeConversion, Predicate<Class<?>> supportPredicate) {
        super(config, readConversion, writeConversion, supportPredicate);
    }

    @Override
    public File getFile() {
        return ((FileConfig)this.config).getFile();
    }

    @Override
    public Path getNioPath() {
        return ((FileConfig)this.config).getNioPath();
    }

    @Override
    public void save() {
        ((FileConfig)this.config).save();
    }

    @Override
    public void load() {
        ((FileConfig)this.config).load();
    }

    @Override
    public void close() {
        ((FileConfig)this.config).close();
    }
}
