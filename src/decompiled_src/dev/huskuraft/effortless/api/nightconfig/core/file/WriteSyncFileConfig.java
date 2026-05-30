/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileNotFoundAction;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingMode;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingMode;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;

final class WriteSyncFileConfig<C extends Config>
extends ConfigWrapper<C>
implements FileConfig {
    private final Path nioPath;
    private final Charset charset;
    private boolean closed;
    private final ConfigWriter writer;
    private final WritingMode writingMode;
    private final ConfigParser<?> parser;
    private final FileNotFoundAction nefAction;
    private final ParsingMode parsingMode;
    private volatile boolean currentlyWriting = false;

    WriteSyncFileConfig(C config, Path nioPath, Charset charset, ConfigWriter writer, WritingMode writingMode, ConfigParser<?> parser, ParsingMode parsingMode, FileNotFoundAction nefAction) {
        super(config);
        this.nioPath = nioPath;
        this.charset = charset;
        this.writer = writer;
        this.parser = parser;
        this.parsingMode = parsingMode;
        this.nefAction = nefAction;
        this.writingMode = writingMode;
    }

    @Override
    public File getFile() {
        return this.nioPath.toFile();
    }

    @Override
    public Path getNioPath() {
        return this.nioPath;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void save() {
        WriteSyncFileConfig writeSyncFileConfig = this;
        synchronized (writeSyncFileConfig) {
            if (this.closed) {
                throw new IllegalStateException("Cannot save a closed FileConfig");
            }
            this.currentlyWriting = true;
            this.writer.write(this.config, this.nioPath, this.writingMode, this.charset);
            this.currentlyWriting = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void load() {
        if (!this.currentlyWriting) {
            WriteSyncFileConfig writeSyncFileConfig = this;
            synchronized (writeSyncFileConfig) {
                if (this.closed) {
                    throw new IllegalStateException("Cannot (re)load a closed FileConfig");
                }
                this.parser.parse(this.nioPath, (Config)this.config, this.parsingMode, this.nefAction);
            }
        }
    }

    @Override
    public void close() {
        this.closed = true;
    }
}
