/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.file.AutosaveFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.GenericBuilder;
import java.nio.file.Path;

public class FileConfigBuilder
extends GenericBuilder<Config, FileConfig> {
    FileConfigBuilder(Path file, ConfigFormat<? extends Config> format) {
        super(file, format);
    }

    @Override
    protected FileConfig buildAutosave(FileConfig chain) {
        return new AutosaveFileConfig<FileConfig>(chain);
    }

    @Override
    protected FileConfig buildNormal(FileConfig chain) {
        return chain;
    }
}
