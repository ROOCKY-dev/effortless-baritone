/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file;

import dev.huskuraft.effortless.api.file.FileType;
import dev.huskuraft.effortless.api.file.Storage;
import dev.huskuraft.effortless.api.platform.Platform;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

abstract class FileStorage<T>
implements Storage<T> {
    private final String fileName;
    private final FileType fileType;
    private T target;

    protected FileStorage(String fileName, FileType fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    protected String getFileName() {
        return this.fileName;
    }

    protected final FileType getFileType() {
        return this.fileType;
    }

    private void read() {
        try {
            File file = this.getTargetFile();
            if (!file.exists()) {
                this.target = this.getDefault();
                this.write();
                return;
            }
            this.target = this.read(file);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().warning("Cannot read config file: " + e.getMessage());
            this.target = this.getDefault();
        }
        this.write();
    }

    private void write() {
        try {
            File file = this.getTargetFile();
            this.write(file, this.target);
        }
        catch (Exception e) {
            Logger.getAnonymousLogger().warning("Cannot save config file: " + e.getMessage());
        }
    }

    protected File getDir() throws IOException {
        File dataDir = Platform.getInstance().getConfigDir().toFile();
        if (!dataDir.exists() && !dataDir.mkdirs()) {
            throw new IOException("Could not create data directory: " + dataDir.getAbsolutePath());
        }
        return dataDir;
    }

    private File getTargetFile() throws IOException {
        return new File(this.getDir(), this.getFileName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void update(UnaryOperator<T> operator) {
        FileStorage fileStorage = this;
        synchronized (fileStorage) {
            this.set(operator.apply(this.get()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public T get() {
        FileStorage fileStorage = this;
        synchronized (fileStorage) {
            if (this.target == null) {
                this.read();
            }
            return this.target;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void set(T config) {
        FileStorage fileStorage = this;
        synchronized (fileStorage) {
            if (Objects.equals(this.target, config)) {
                return;
            }
            this.target = config;
            this.write();
        }
    }

    public abstract T getDefault();

    protected abstract T read(File var1) throws IOException;

    protected abstract void write(File var1, T var2) throws IOException;
}
