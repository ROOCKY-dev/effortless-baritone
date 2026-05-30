/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.file.FileWatcher;
import java.nio.file.WatchKey;

private static final class FileWatcher.WatchedFile {
    final WatchKey watchKey;
    volatile Runnable changeHandler;

    private FileWatcher.WatchedFile(FileWatcher.WatchedDir watchedDir, WatchKey watchKey, Runnable changeHandler) {
        this.watchKey = watchKey;
        this.changeHandler = changeHandler;
        watchedDir.watchedFileCount.getAndIncrement();
    }
}
