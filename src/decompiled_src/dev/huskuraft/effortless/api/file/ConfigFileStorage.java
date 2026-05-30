/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file;

import dev.huskuraft.effortless.api.config.ConfigSerializer;
import dev.huskuraft.effortless.api.file.FileStorage;
import dev.huskuraft.effortless.api.file.FileType;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import java.io.File;
import java.io.IOException;

public abstract class ConfigFileStorage<T>
extends FileStorage<T> {
    private final ConfigSerializer<T> serializer;

    protected ConfigFileStorage(String fileName, FileType fileType, ConfigSerializer<T> serializer) {
        super(fileName, fileType);
        this.serializer = serializer;
    }

    @Override
    protected T read(File config) throws IOException {
        return this.getSerializer().deserialize((Config)this.getFileType().getAdapter().read(config));
    }

    @Override
    protected void write(File file, T t) throws IOException {
        this.getFileType().getAdapter().write(file, this.getSerializer().serialize(t));
    }

    @Override
    public T getDefault() {
        return this.getSerializer().getDefault();
    }

    public ConfigSerializer<T> getSerializer() {
        return this.serializer;
    }

    static {
        Config.setInsertionOrderPreserved(true);
    }
}
