/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file.adapters;

import dev.huskuraft.effortless.api.file.FileAdapter;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingMode;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlParser;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlWriter;
import java.io.File;
import java.io.IOException;

public final class TomlFileAdapter
extends FileAdapter<Config> {
    @Override
    public Config read(File file) throws IOException {
        return new TomlParser().parse(file, (file1, configFormat) -> false);
    }

    @Override
    public void write(File file, Config config) throws IOException {
        new TomlWriter().write((UnmodifiableConfig)config, file, WritingMode.REPLACE);
    }
}
