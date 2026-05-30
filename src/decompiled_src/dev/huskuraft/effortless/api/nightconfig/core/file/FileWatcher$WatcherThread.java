/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.file.FileWatcher;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Iterator;
import java.util.concurrent.locks.LockSupport;

private final class FileWatcher.WatcherThread
extends Thread {
    private FileWatcher.WatcherThread() {
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (FileWatcher.this.run) {
            boolean allNull = true;
            Iterator it = FileWatcher.this.watchedDirs.values().iterator();
            block5: while (it.hasNext() && FileWatcher.this.run) {
                FileWatcher.WatchedDir watchedDir = (FileWatcher.WatchedDir)it.next();
                WatchKey key = watchedDir.watchService.poll();
                if (key == null) continue;
                allNull = false;
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (!FileWatcher.this.run) break block5;
                    if (event.kind() != StandardWatchEventKinds.ENTRY_MODIFY || event.count() > 1) continue;
                    Path childPath = (Path)event.context();
                    Path filePath = watchedDir.dir.resolve(childPath);
                    FileWatcher.WatchedFile watchedFile = (FileWatcher.WatchedFile)FileWatcher.this.watchedFiles.get(filePath);
                    if (watchedFile == null) continue;
                    try {
                        watchedFile.changeHandler.run();
                    }
                    catch (Exception e) {
                        FileWatcher.this.exceptionHandler.accept(e);
                    }
                }
                key.reset();
            }
            if (!allNull) continue;
            LockSupport.parkNanos(1000L);
        }
        for (FileWatcher.WatchedDir watchedDir : FileWatcher.this.watchedDirs.values()) {
            try {
                watchedDir.watchService.close();
            }
            catch (IOException e) {
                FileWatcher.this.exceptionHandler.accept(e);
            }
        }
        FileWatcher.this.watchedDirs.clear();
        FileWatcher.this.watchedFiles.clear();
    }
}
