/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Config.1
implements UnmodifiableConfig {
    Config.1() {
    }

    @Override
    public <T> T getRaw(List<String> path) {
        return Config.this.getRaw(path);
    }

    @Override
    public boolean contains(List<String> path) {
        return Config.this.contains(path);
    }

    @Override
    public int size() {
        return Config.this.size();
    }

    @Override
    public Map<String, Object> valueMap() {
        return Collections.unmodifiableMap(Config.this.valueMap());
    }

    @Override
    public Set<? extends UnmodifiableConfig.Entry> entrySet() {
        return Config.this.entrySet();
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return Config.this.configFormat();
    }
}
