/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.utils.UnmodifiableConfigWrapper;
import java.util.List;
import java.util.Set;

public abstract class ConfigWrapper<C extends Config>
extends UnmodifiableConfigWrapper<C>
implements Config {
    protected ConfigWrapper(C config) {
        super(config);
    }

    @Override
    public Set<? extends Config.Entry> entrySet() {
        return ((Config)this.config).entrySet();
    }

    @Override
    public <T> T set(List<String> path, Object value) {
        return ((Config)this.config).set(path, value);
    }

    @Override
    public boolean add(List<String> path, Object value) {
        return ((Config)this.config).add(path, value);
    }

    @Override
    public <T> T remove(List<String> path) {
        return ((Config)this.config).remove(path);
    }

    @Override
    public void clear() {
        ((Config)this.config).clear();
    }

    @Override
    public Config createSubConfig() {
        return ((Config)this.config).createSubConfig();
    }

    public String toString() {
        return this.getClass().getSimpleName() + ':' + this.config;
    }
}
