/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ConfigWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ObservedMap;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

final class AutosaveFileConfig<C extends FileConfig>
extends ConfigWrapper<C>
implements FileConfig {
    AutosaveFileConfig(C config) {
        super(config);
    }

    @Override
    public <T> T set(List<String> path, Object value) {
        Object result = super.set(path, value);
        this.save();
        return result;
    }

    @Override
    public boolean add(List<String> path, Object value) {
        boolean result = super.add(path, value);
        this.save();
        return result;
    }

    @Override
    public <T> T remove(List<String> path) {
        Object result = super.remove(path);
        this.save();
        return result;
    }

    @Override
    public Map<String, Object> valueMap() {
        return new ObservedMap<String, Object>(super.valueMap(), this::save);
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
        ((FileConfig)this.config).close();
    }
}
