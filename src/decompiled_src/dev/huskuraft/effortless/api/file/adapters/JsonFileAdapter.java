/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file.adapters;

import dev.huskuraft.effortless.api.file.FileAdapter;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import java.io.File;
import java.io.IOException;

public final class JsonFileAdapter
extends FileAdapter<Config> {
    @Override
    public Config read(File file) throws IOException {
        throw new UnsupportedOperationException("JsonParser is not supported");
    }

    @Override
    public void write(File file, Config config) throws IOException {
        throw new UnsupportedOperationException("JsonWriter is not supported");
    }
}
