/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file;

import dev.huskuraft.effortless.api.file.FileStorage;
import dev.huskuraft.effortless.api.file.FileType;
import dev.huskuraft.effortless.api.tag.Tag;
import dev.huskuraft.effortless.api.tag.TagSerializer;
import java.io.File;
import java.io.IOException;

public abstract class TagElementFileStorage<T>
extends FileStorage<T> {
    private final TagSerializer<T> serializer;

    protected TagElementFileStorage(String fileName, FileType fileType, TagSerializer<T> serializer) {
        super(fileName, fileType);
        this.serializer = serializer;
    }

    @Override
    protected T read(File config) throws IOException {
        return this.serializer.decode((Tag)this.getFileType().getAdapter().read(config), true);
    }

    @Override
    protected void write(File file, T t) throws IOException {
        this.getFileType().getAdapter().write(file, this.serializer.encode(t, true));
    }
}
