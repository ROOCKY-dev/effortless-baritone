/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.file.CheckedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfigBuilder;
import dev.huskuraft.effortless.api.nightconfig.core.file.FormatDetector;
import dev.huskuraft.effortless.api.nightconfig.core.file.NoFormatFoundException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface FileConfig
extends Config,
AutoCloseable {
    public File getFile();

    public Path getNioPath();

    public void save();

    public void load();

    @Override
    public void close();

    @Override
    default public FileConfig checked() {
        return new CheckedFileConfig(this);
    }

    public static FileConfig of(File file) {
        return FileConfig.of(file.toPath());
    }

    public static FileConfig of(File file, ConfigFormat<? extends Config> format) {
        return FileConfig.of(file.toPath(), format);
    }

    public static FileConfig of(Path file) {
        ConfigFormat<?> format = FormatDetector.detect(file);
        if (format == null) {
            throw new NoFormatFoundException("No suitable format for " + file.getFileName());
        }
        return FileConfig.of(file, format);
    }

    public static FileConfig of(Path file, ConfigFormat<? extends Config> format) {
        return FileConfig.builder(file, format).build();
    }

    public static FileConfig of(String filePath) {
        return FileConfig.of(Paths.get(filePath, new String[0]));
    }

    public static FileConfig of(String filePath, ConfigFormat<?> format) {
        return FileConfig.of(Paths.get(filePath, new String[0]), format);
    }

    public static FileConfig ofConcurrent(File file) {
        return FileConfig.ofConcurrent(file.toPath());
    }

    public static FileConfig ofConcurrent(File file, ConfigFormat<?> format) {
        return FileConfig.ofConcurrent(file.toPath(), format);
    }

    public static FileConfig ofConcurrent(Path file) {
        return FileConfig.builder(file).concurrent().build();
    }

    public static FileConfig ofConcurrent(Path file, ConfigFormat<?> format) {
        return FileConfig.builder(file, format).concurrent().build();
    }

    public static FileConfig ofConcurrent(String filePath) {
        return FileConfig.ofConcurrent(Paths.get(filePath, new String[0]));
    }

    public static FileConfig ofConcurrent(String filePath, ConfigFormat<?> format) {
        return FileConfig.ofConcurrent(Paths.get(filePath, new String[0]), format);
    }

    public static FileConfigBuilder builder(File file) {
        return FileConfig.builder(file.toPath());
    }

    public static FileConfigBuilder builder(File file, ConfigFormat<?> format) {
        return FileConfig.builder(file.toPath(), format);
    }

    public static FileConfigBuilder builder(Path file) {
        ConfigFormat<?> format = FormatDetector.detect(file);
        if (format == null) {
            throw new NoFormatFoundException("No suitable format for " + file.getFileName());
        }
        return FileConfig.builder(file, format);
    }

    public static FileConfigBuilder builder(Path file, ConfigFormat<?> format) {
        return new FileConfigBuilder(file, (ConfigFormat<? extends Config>)format);
    }

    public static FileConfigBuilder builder(String filePath) {
        return FileConfig.builder(Paths.get(filePath, new String[0]));
    }

    public static FileConfigBuilder builder(String filePath, ConfigFormat<?> format) {
        return FileConfig.builder(Paths.get(filePath, new String[0]), format);
    }
}
