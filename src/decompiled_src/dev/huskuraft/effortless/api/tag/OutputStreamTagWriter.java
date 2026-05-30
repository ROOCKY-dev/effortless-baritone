/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.tag.RecordTag;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface OutputStreamTagWriter {
    public void writeCompressed(OutputStream var1, RecordTag var2) throws IOException;

    default public void writeCompressed(File file, RecordTag record) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file);){
            this.writeCompressed(outputStream, record);
        }
    }
}
