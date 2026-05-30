/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.AbstractCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

final class SimpleCommentedConfig
extends AbstractCommentedConfig {
    private final ConfigFormat<?> configFormat;

    SimpleCommentedConfig(ConfigFormat<?> configFormat, boolean concurrent) {
        super(concurrent ? new ConcurrentHashMap() : new HashMap());
        this.configFormat = configFormat;
    }

    SimpleCommentedConfig(Map<String, Object> valueMap, ConfigFormat<?> configFormat) {
        super(valueMap);
        this.configFormat = configFormat;
    }

    SimpleCommentedConfig(Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> configFormat) {
        super(mapCreator);
        this.configFormat = configFormat;
    }

    SimpleCommentedConfig(UnmodifiableConfig toCopy, ConfigFormat<?> configFormat, boolean concurrent) {
        super(toCopy, concurrent);
        this.configFormat = configFormat;
    }

    public SimpleCommentedConfig(UnmodifiableConfig toCopy, Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> configFormat) {
        super(toCopy, mapCreator);
        this.configFormat = configFormat;
    }

    SimpleCommentedConfig(UnmodifiableCommentedConfig toCopy, ConfigFormat<?> configFormat, boolean concurrent) {
        super(toCopy, concurrent);
        this.configFormat = configFormat;
    }

    public SimpleCommentedConfig(UnmodifiableCommentedConfig toCopy, Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> configFormat) {
        super(toCopy, mapCreator);
        this.configFormat = configFormat;
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return this.configFormat;
    }

    @Override
    public SimpleCommentedConfig createSubConfig() {
        return new SimpleCommentedConfig(this.mapCreator, this.configFormat);
    }

    @Override
    public AbstractCommentedConfig clone() {
        return new SimpleCommentedConfig((UnmodifiableCommentedConfig)this, (Supplier<Map<String, Object>>)this.mapCreator, this.configFormat);
    }
}
