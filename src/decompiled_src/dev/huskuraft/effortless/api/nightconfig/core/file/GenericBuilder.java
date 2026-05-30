/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.file.AutoreloadFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileNotFoundAction;
import dev.huskuraft.effortless.api.nightconfig.core.file.WriteAsyncFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.WriteSyncFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingMode;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingMode;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

public abstract class GenericBuilder<Base extends Config, Result extends FileConfig> {
    protected final Path file;
    private Base config;
    protected final ConfigFormat<? extends Base> format;
    protected final ConfigWriter writer;
    protected final ConfigParser<? extends Base> parser;
    protected Charset charset = StandardCharsets.UTF_8;
    protected WritingMode writingMode = WritingMode.REPLACE;
    protected ParsingMode parsingMode = ParsingMode.REPLACE;
    protected FileNotFoundAction nefAction = FileNotFoundAction.CREATE_EMPTY;
    protected boolean sync = false;
    protected boolean autosave = false;
    protected boolean autoreload = false;
    protected boolean concurrent = false;
    protected boolean insertionOrder = Config.isInsertionOrderPreserved();
    protected Supplier<Map<String, Object>> mapCreator = null;

    GenericBuilder(Path file, ConfigFormat<? extends Base> format) {
        this.file = file;
        this.format = format;
        this.writer = format.createWriter();
        this.parser = format.createParser();
    }

    public GenericBuilder<Base, Result> charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public GenericBuilder<Base, Result> writingMode(WritingMode writingMode) {
        this.writingMode = writingMode;
        return this;
    }

    public GenericBuilder<Base, Result> parsingMode(ParsingMode parsingMode) {
        this.parsingMode = parsingMode;
        return this;
    }

    public GenericBuilder<Base, Result> onFileNotFound(FileNotFoundAction nefAction) {
        this.nefAction = nefAction;
        return this;
    }

    public GenericBuilder<Base, Result> defaultResource(String resourcePath) {
        return this.onFileNotFound(FileNotFoundAction.copyResource(resourcePath));
    }

    public GenericBuilder<Base, Result> defaultData(File file) {
        return this.onFileNotFound(FileNotFoundAction.copyData(file));
    }

    public GenericBuilder<Base, Result> defaultData(Path file) {
        return this.onFileNotFound(FileNotFoundAction.copyData(file));
    }

    public GenericBuilder<Base, Result> defaultData(URL url) {
        return this.onFileNotFound(FileNotFoundAction.copyData(url));
    }

    public GenericBuilder<Base, Result> sync() {
        this.sync = true;
        return this;
    }

    public GenericBuilder<Base, Result> autosave() {
        this.autosave = true;
        return this;
    }

    public GenericBuilder<Base, Result> autoreload() {
        this.autoreload = true;
        return this;
    }

    public GenericBuilder<Base, Result> concurrent() {
        if (this.config == null) {
            this.config = this.format.createConcurrentConfig();
        }
        this.concurrent = true;
        return this;
    }

    public GenericBuilder<Base, Result> preserveInsertionOrder() {
        this.insertionOrder = true;
        return this;
    }

    public GenericBuilder<Base, Result> backingMapCreator(Supplier<Map<String, Object>> s) {
        this.mapCreator = s;
        return this;
    }

    public Result build() {
        ConfigWrapper fileConfig;
        if (this.sync) {
            fileConfig = new WriteSyncFileConfig<Base>(this.getConfig(), this.file, this.charset, this.writer, this.writingMode, this.parser, this.parsingMode, this.nefAction);
        } else {
            if (this.autoreload) {
                this.concurrent();
            }
            fileConfig = new WriteAsyncFileConfig<Base>(this.getConfig(), this.file, this.charset, this.writer, this.writingMode, this.parser, this.parsingMode, this.nefAction);
        }
        if (this.autoreload) {
            if (Files.notExists(this.file, new LinkOption[0])) {
                try {
                    this.nefAction.run(this.file, this.format);
                }
                catch (IOException e) {
                    throw new WritingException("An exception occured while executing the FileNotFoundAction for file " + this.file, e);
                }
            }
            fileConfig = new AutoreloadFileConfig<WriteSyncFileConfig<Base>>(fileConfig);
        }
        if (this.autosave) {
            return this.buildAutosave((FileConfig)((Object)fileConfig));
        }
        return this.buildNormal((FileConfig)((Object)fileConfig));
    }

    protected abstract Result buildAutosave(FileConfig var1);

    protected abstract Result buildNormal(FileConfig var1);

    protected final Base getConfig() {
        if (this.config == null) {
            if (this.mapCreator == null) {
                this.mapCreator = Config.getDefaultMapCreator(this.concurrent, this.insertionOrder);
            }
            this.config = this.format.createConfig(this.mapCreator);
        }
        return this.config;
    }
}
