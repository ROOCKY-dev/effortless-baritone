/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileWatcher;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

final class AutoreloadFileConfig<C extends FileConfig>
extends ConfigWrapper<C>
implements FileConfig {
    private final FileWatcher watcher = FileWatcher.defaultInstance();

    AutoreloadFileConfig(C config) {
        super(config);
        try {
            this.watcher.addWatch(config.getFile(), () -> config.load());
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to create the autoreloaded config", e);
        }
    }

    @Override
    public File getFile() {
        return ((FileConfig)this.config).getFile();
    }

    @Override
    public Path getNioPath() {
        return ((FileConfig)this.config).getNioPath();
    }

    @Override
    public void save() {
        ((FileConfig)this.config).save();
    }

    @Override
    public void load() {
        ((FileConfig)this.config).load();
    }

    @Override
    public void close() {
        this.watcher.removeWatch(((FileConfig)this.config).getFile());
        ((FileConfig)this.config).close();
    }
}
