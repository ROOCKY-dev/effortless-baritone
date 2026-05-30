/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class UnmodifiableConfigWrapper<C extends UnmodifiableConfig>
implements UnmodifiableConfig {
    protected final C config;

    protected UnmodifiableConfigWrapper(C config) {
        this.config = (UnmodifiableConfig)Objects.requireNonNull(config, "The wrapped config must not be null");
    }

    @Override
    public <T> T getRaw(List<String> path) {
        return this.config.getRaw(path);
    }

    @Override
    public Map<String, Object> valueMap() {
        return this.config.valueMap();
    }

    @Override
    public Set<? extends UnmodifiableConfig.Entry> entrySet() {
        return this.config.entrySet();
    }

    @Override
    public boolean contains(List<String> path) {
        return this.config.contains(path);
    }

    @Override
    public int size() {
        return this.config.size();
    }

    @Override
    public boolean isEmpty() {
        return this.config.isEmpty();
    }

    public boolean equals(Object obj) {
        return this.config.equals(obj);
    }

    public int hashCode() {
        return this.config.hashCode();
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return this.config.configFormat();
    }
}
