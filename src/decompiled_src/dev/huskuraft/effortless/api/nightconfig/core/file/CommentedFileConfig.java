/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.file.CheckedCommentedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.CommentedFileConfigBuilder;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FormatDetector;
import dev.huskuraft.effortless.api.nightconfig.core.file.NoFormatFoundException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface CommentedFileConfig
extends CommentedConfig,
FileConfig {
    @Override
    default public CommentedFileConfig checked() {
        return new CheckedCommentedFileConfig(this);
    }

    public static CommentedFileConfig of(File file) {
        return CommentedFileConfig.of(file.toPath());
    }

    public static CommentedFileConfig of(File file, ConfigFormat<? extends CommentedConfig> format) {
        return CommentedFileConfig.of(file.toPath(), format);
    }

    public static CommentedFileConfig of(Path file) {
        ConfigFormat<?> format = FormatDetector.detect(file);
        if (format == null || !format.supportsComments()) {
            throw new NoFormatFoundException("No suitable format for " + file.getFileName());
        }
        return CommentedFileConfig.of(file, format);
    }

    public static CommentedFileConfig of(Path file, ConfigFormat<? extends CommentedConfig> format) {
        return (CommentedFileConfig)CommentedFileConfig.builder(file, format).build();
    }

    public static CommentedFileConfig of(String filePath) {
        return CommentedFileConfig.of(Paths.get(filePath, new String[0]));
    }

    public static CommentedFileConfig of(String filePath, ConfigFormat<? extends CommentedConfig> format) {
        return CommentedFileConfig.of(Paths.get(filePath, new String[0]), format);
    }

    public static CommentedFileConfig ofConcurrent(File file) {
        return CommentedFileConfig.ofConcurrent(file.toPath());
    }

    public static CommentedFileConfig ofConcurrent(File file, ConfigFormat<? extends CommentedConfig> format) {
        return CommentedFileConfig.ofConcurrent(file.toPath(), format);
    }

    public static CommentedFileConfig ofConcurrent(Path file) {
        return (CommentedFileConfig)CommentedFileConfig.builder(file).concurrent().build();
    }

    public static CommentedFileConfig ofConcurrent(Path file, ConfigFormat<? extends CommentedConfig> format) {
        return (CommentedFileConfig)CommentedFileConfig.builder(file, format).concurrent().build();
    }

    public static CommentedFileConfig ofConcurrent(String filePath, ConfigFormat<? extends CommentedConfig> format) {
        return CommentedFileConfig.ofConcurrent(Paths.get(filePath, new String[0]), format);
    }

    public static CommentedFileConfig ofConcurrent(String filePath) {
        return CommentedFileConfig.ofConcurrent(Paths.get(filePath, new String[0]));
    }

    public static CommentedFileConfigBuilder builder(File file, ConfigFormat<? extends CommentedConfig> format) {
        return CommentedFileConfig.builder(file.toPath(), format);
    }

    public static CommentedFileConfigBuilder builder(File file) {
        return CommentedFileConfig.builder(file.toPath());
    }

    public static CommentedFileConfigBuilder builder(Path file, ConfigFormat<? extends CommentedConfig> format) {
        return new CommentedFileConfigBuilder(file, format);
    }

    public static CommentedFileConfigBuilder builder(Path file) {
        ConfigFormat<?> format = FormatDetector.detect(file);
        if (format == null) {
            throw new NoFormatFoundException("No suitable format for " + file.getFileName());
        }
        if (!format.supportsComments()) {
            throw new NoFormatFoundException("The available format doesn't support comments for " + file.getFileName());
        }
        return CommentedFileConfig.builder(file, format);
    }

    public static CommentedFileConfigBuilder builder(String filePath) {
        return CommentedFileConfig.builder(Paths.get(filePath, new String[0]));
    }

    public static CommentedFileConfigBuilder builder(String filePath, ConfigFormat<? extends CommentedConfig> format) {
        return CommentedFileConfig.builder(Paths.get(filePath, new String[0]), format);
    }
}
