/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.AbstractConfig;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import java.util.Map;
import java.util.function.Supplier;

final class SimpleConfig
extends AbstractConfig {
    private final ConfigFormat<?> configFormat;

    SimpleConfig(ConfigFormat<?> configFormat, boolean concurrent) {
        super(concurrent);
        this.configFormat = configFormat;
    }

    SimpleConfig(Map<String, Object> map, ConfigFormat<?> configFormat) {
        super(map);
        this.configFormat = configFormat;
    }

    SimpleConfig(Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> configFormat) {
        super(mapCreator);
        this.configFormat = configFormat;
    }

    SimpleConfig(UnmodifiableConfig toCopy, ConfigFormat<?> configFormat, boolean concurrent) {
        super(toCopy, concurrent);
        this.configFormat = configFormat;
    }

    SimpleConfig(UnmodifiableConfig toCopy, Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> configFormat) {
        super(toCopy, mapCreator);
        this.configFormat = configFormat;
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return this.configFormat;
    }

    @Override
    public SimpleConfig createSubConfig() {
        return new SimpleConfig(this.mapCreator, this.configFormat);
    }

    @Override
    public SimpleConfig clone() {
        return new SimpleConfig((UnmodifiableConfig)this, this.mapCreator, this.configFormat);
    }
}
