/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileNotFoundAction;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingMode;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingMode;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicBoolean;

final class WriteAsyncFileConfig<C extends Config>
extends ConfigWrapper<C>
implements FileConfig {
    private final Path nioPath;
    private final Charset charset;
    private final AtomicBoolean closed = new AtomicBoolean();
    private AsynchronousFileChannel channel;
    private final Object channelGuard = new Object();
    private final AtomicBoolean currentlyWriting = new AtomicBoolean();
    private final AtomicBoolean mustWriteAgain = new AtomicBoolean();
    private final ConfigWriter writer;
    private final WriteCompletedHandler writeCompletedHandler;
    private final OpenOption[] openOptions;
    private final ConfigParser<?> parser;
    private final FileNotFoundAction nefAction;
    private final ParsingMode parsingMode;

    WriteAsyncFileConfig(C config, Path nioPath, Charset charset, ConfigWriter writer, WritingMode writingMode, ConfigParser<?> parser, ParsingMode parsingMode, FileNotFoundAction nefAction) {
        super(config);
        this.nioPath = nioPath;
        this.charset = charset;
        this.writer = writer;
        this.parser = parser;
        this.parsingMode = parsingMode;
        this.nefAction = nefAction;
        this.openOptions = writingMode == WritingMode.APPEND ? new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE} : new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
        this.writeCompletedHandler = new WriteCompletedHandler();
    }

    @Override
    public File getFile() {
        return this.nioPath.toFile();
    }

    @Override
    public Path getNioPath() {
        return this.nioPath;
    }

    @Override
    public void save() {
        if (this.closed.get()) {
            throw new IllegalStateException("Cannot save a closed FileConfig");
        }
        this.save(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        if (this.closed.compareAndSet(false, true)) {
            Object object = this.channelGuard;
            synchronized (object) {
                while (this.currentlyWriting.get()) {
                    try {
                        this.channelGuard.wait();
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void save(boolean saveLaterIfWriting) {
        boolean canSaveNow = this.currentlyWriting.compareAndSet(false, true);
        if (canSaveNow) {
            CharsWrapper.Builder builder = new CharsWrapper.Builder(512);
            this.writer.write(this.config, builder);
            CharBuffer chars = CharBuffer.wrap(builder.build());
            ByteBuffer buffer = this.charset.encode(chars);
            Object object = this.channelGuard;
            synchronized (object) {
                try {
                    this.channel = AsynchronousFileChannel.open(this.nioPath, this.openOptions);
                    this.channel.write(buffer, this.channel.size(), null, this.writeCompletedHandler);
                }
                catch (IOException e) {
                    this.writeCompletedHandler.failed((Throwable)e, (Object)null);
                }
            }
        }
        if (saveLaterIfWriting) {
            this.mustWriteAgain.set(true);
        }
    }

    @Override
    public void load() {
        if (this.closed.get()) {
            throw new IllegalStateException("Cannot (re)load a closed FileConfig");
        }
        if (!this.currentlyWriting.get()) {
            this.parser.parse(this.nioPath, (Config)this.config, this.parsingMode, this.nefAction);
        }
    }

    private final class WriteCompletedHandler
    implements CompletionHandler<Integer, Object> {
        private WriteCompletedHandler() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void completed(Integer result, Object attachment) {
            WriteAsyncFileConfig.this.currentlyWriting.set(false);
            if (WriteAsyncFileConfig.this.mustWriteAgain.getAndSet(false)) {
                WriteAsyncFileConfig.this.save(false);
            } else {
                Object object = WriteAsyncFileConfig.this.channelGuard;
                synchronized (object) {
                    try {
                        WriteAsyncFileConfig.this.channel.close();
                        WriteAsyncFileConfig.this.channel = null;
                    }
                    catch (IOException e) {
                        this.failed((Throwable)e, (Object)null);
                    }
                    finally {
                        WriteAsyncFileConfig.this.channelGuard.notify();
                    }
                }
            }
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            throw new WritingException("Error while saving the FileConfig to " + WriteAsyncFileConfig.this.nioPath, exc);
        }
    }
}
