/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file.adapters;

import dev.huskuraft.effortless.api.file.FileAdapter;
import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.tag.RecordTag;
import dev.huskuraft.effortless.api.tag.Tag;
import java.io.File;
import java.io.IOException;

public final class TagFileAdapter
extends FileAdapter<Tag> {
    @Override
    public Tag read(File file) throws IOException {
        return ContentFactory.getInstance().getInputStreamTagReader().readCompressed(file);
    }

    @Override
    public void write(File file, Tag tag) throws IOException {
        ContentFactory.getInstance().getOutputStreamTagWriter().writeCompressed(file, (RecordTag)tag);
    }
}
