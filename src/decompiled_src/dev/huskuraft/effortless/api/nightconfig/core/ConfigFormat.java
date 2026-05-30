/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.InMemoryFormat;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.utils.WriterSupplier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

public interface ConfigFormat<C extends Config> {
    public ConfigWriter createWriter();

    public ConfigParser<C> createParser();

    default public C createConfig() {
        return this.createConfig(Config.getDefaultMapCreator(false));
    }

    default public C createConcurrentConfig() {
        return this.createConfig(Config.getDefaultMapCreator(true));
    }

    public C createConfig(Supplier<Map<String, Object>> var1);

    public boolean supportsComments();

    default public boolean supportsType(Class<?> type) {
        return InMemoryFormat.DEFAULT_PREDICATE.test(type);
    }

    default public boolean isInMemory() {
        return false;
    }

    default public void initEmptyFile(Path f) throws IOException {
        this.initEmptyFile(() -> Files.newBufferedWriter(f, new OpenOption[0]));
    }

    default public void initEmptyFile(File f) throws IOException {
        this.initEmptyFile(f.toPath());
    }

    default public void initEmptyFile(WriterSupplier ws) throws IOException {
    }
}
