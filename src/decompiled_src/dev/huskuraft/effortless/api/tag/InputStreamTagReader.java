/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.tag.RecordTag;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface InputStreamTagReader {
    public RecordTag readCompressed(InputStream var1) throws IOException;

    default public RecordTag readCompressed(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file);){
            RecordTag recordTag = this.readCompressed(inputStream);
            return recordTag;
        }
    }
}
