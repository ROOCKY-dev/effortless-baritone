/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file;

import java.io.File;
import java.io.IOException;

public abstract class FileAdapter<T> {
    public abstract T read(File var1) throws IOException;

    public abstract void write(File var1, T var2) throws IOException;
}
