/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.file.AutosaveCommentedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.CommentedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.GenericBuilder;
import dev.huskuraft.effortless.api.nightconfig.core.file.SimpleCommentedFileConfig;
import java.nio.file.Path;

public final class CommentedFileConfigBuilder
extends GenericBuilder<CommentedConfig, CommentedFileConfig> {
    CommentedFileConfigBuilder(Path file, ConfigFormat<? extends CommentedConfig> format) {
        super(file, format);
    }

    @Override
    protected CommentedFileConfig buildAutosave(FileConfig chain) {
        return new AutosaveCommentedFileConfig((CommentedConfig)this.getConfig(), chain);
    }

    @Override
    protected CommentedFileConfig buildNormal(FileConfig chain) {
        return new SimpleCommentedFileConfig((CommentedConfig)this.getConfig(), chain);
    }
}
