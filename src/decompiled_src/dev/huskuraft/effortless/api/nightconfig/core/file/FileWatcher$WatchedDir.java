/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.concurrent.atomic.AtomicInteger;

private static final class FileWatcher.WatchedDir {
    final Path dir;
    final WatchService watchService;
    final AtomicInteger watchedFileCount = new AtomicInteger();

    private FileWatcher.WatchedDir(Path dir) {
        this.dir = dir;
        try {
            this.watchService = dir.getFileSystem().newWatchService();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
