/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.CommentedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.CommentedConfigWrapper;
import java.io.File;
import java.nio.file.Path;

class SimpleCommentedFileConfig
extends CommentedConfigWrapper<CommentedConfig>
implements CommentedFileConfig {
    private final FileConfig fileConfig;

    SimpleCommentedFileConfig(CommentedConfig config, FileConfig fileConfig) {
        super(config);
        this.fileConfig = fileConfig;
    }

    @Override
    public File getFile() {
        return this.fileConfig.getFile();
    }

    @Override
    public Path getNioPath() {
        return this.fileConfig.getNioPath();
    }

    @Override
    public void save() {
        this.fileConfig.save();
    }

    @Override
    public void load() {
        this.fileConfig.load();
    }

    @Override
    public void close() {
        this.fileConfig.close();
    }
}
