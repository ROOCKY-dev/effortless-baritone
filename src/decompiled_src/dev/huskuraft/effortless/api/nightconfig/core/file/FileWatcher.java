/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public final class FileWatcher {
    private static final long SLEEP_TIME_NANOS = 1000L;
    private static volatile FileWatcher DEFAULT_INSTANCE;
    private final Map<Path, WatchedDir> watchedDirs = new ConcurrentHashMap<Path, WatchedDir>();
    private final Map<Path, WatchedFile> watchedFiles = new ConcurrentHashMap<Path, WatchedFile>();
    private final Thread thread = new WatcherThread();
    private final Consumer<Exception> exceptionHandler;
    private volatile boolean run = true;

    public static synchronized FileWatcher defaultInstance() {
        if (DEFAULT_INSTANCE == null || !FileWatcher.DEFAULT_INSTANCE.run) {
            DEFAULT_INSTANCE = new FileWatcher();
        }
        return DEFAULT_INSTANCE;
    }

    public FileWatcher() {
        this(Throwable::printStackTrace);
    }

    public FileWatcher(Consumer<Exception> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        this.thread.start();
    }

    public void addWatch(File file, Runnable changeHandler) throws IOException {
        this.addWatch(file.toPath(), changeHandler);
    }

    public void addWatch(Path file, Runnable changeHandler) throws IOException {
        file = file.toAbsolutePath();
        Path dir = file.getParent();
        WatchedDir watchedDir = this.watchedDirs.computeIfAbsent(dir, k -> new WatchedDir(dir));
        WatchKey watchKey = dir.register(watchedDir.watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        this.watchedFiles.computeIfAbsent(file, k -> new WatchedFile(watchedDir, watchKey, changeHandler));
    }

    public void setWatch(File file, Runnable changeHandler) throws IOException {
        this.setWatch(file.toPath(), changeHandler);
    }

    public void setWatch(Path file, Runnable changeHandler) throws IOException {
        WatchedFile watchedFile = this.watchedFiles.get(file = file.toAbsolutePath());
        if (watchedFile == null) {
            this.addWatch(file, changeHandler);
        } else {
            watchedFile.changeHandler = changeHandler;
        }
    }

    public void removeWatch(File file) {
        this.removeWatch(file.toPath());
    }

    public void removeWatch(Path file) {
        WatchedFile watchedFile;
        file = file.toAbsolutePath();
        Path dir = file.getParent();
        WatchedDir watchedDir = this.watchedDirs.get(dir);
        int remainingChildCount = watchedDir.watchedFileCount.decrementAndGet();
        if (remainingChildCount == 0) {
            this.watchedDirs.remove(dir);
        }
        if ((watchedFile = this.watchedFiles.remove(file)) != null) {
            watchedFile.watchKey.cancel();
        }
    }

    public void stop() throws IOException {
        this.run = false;
    }

    private final class WatcherThread
    extends Thread {
        private WatcherThread() {
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (FileWatcher.this.run) {
                boolean allNull = true;
                Iterator it = FileWatcher.this.watchedDirs.values().iterator();
                block5: while (it.hasNext() && FileWatcher.this.run) {
                    WatchedDir watchedDir = (WatchedDir)it.next();
                    WatchKey key = watchedDir.watchService.poll();
                    if (key == null) continue;
                    allNull = false;
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (!FileWatcher.this.run) break block5;
                        if (event.kind() != StandardWatchEventKinds.ENTRY_MODIFY || event.count() > 1) continue;
                        Path childPath = (Path)event.context();
                        Path filePath = watchedDir.dir.resolve(childPath);
                        WatchedFile watchedFile = (WatchedFile)FileWatcher.this.watchedFiles.get(filePath);
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
            for (WatchedDir watchedDir : FileWatcher.this.watchedDirs.values()) {
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

    private static final class WatchedDir {
        final Path dir;
        final WatchService watchService;
        final AtomicInteger watchedFileCount = new AtomicInteger();

        private WatchedDir(Path dir) {
            this.dir = dir;
            try {
                this.watchService = dir.getFileSystem().newWatchService();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final class WatchedFile {
        final WatchKey watchKey;
        volatile Runnable changeHandler;

        private WatchedFile(WatchedDir watchedDir, WatchKey watchKey, Runnable changeHandler) {
            this.watchKey = watchKey;
            this.changeHandler = changeHandler;
            watchedDir.watchedFileCount.getAndIncrement();
        }
    }
}
