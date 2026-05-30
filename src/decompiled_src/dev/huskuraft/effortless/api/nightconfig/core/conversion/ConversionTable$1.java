/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingMap;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import dev.huskuraft.effortless.api.nightconfig.core.utils.UnmodifiableConfigWrapper;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

class ConversionTable.1
extends UnmodifiableConfigWrapper<UnmodifiableConfig> {
    ConversionTable.1(UnmodifiableConfig config) {
        super(config);
    }

    @Override
    public <T> T getRaw(List<String> path) {
        return (T)ConversionTable.this.convert(this.config.getRaw(path));
    }

    @Override
    public Map<String, Object> valueMap() {
        return new TransformingMap<String, Object, Object>(this.config.valueMap(), v -> ConversionTable.this.convert(v), v -> v, v -> v);
    }

    @Override
    public Set<? extends UnmodifiableConfig.Entry> entrySet() {
        Function<UnmodifiableConfig.Entry, UnmodifiableConfig.Entry> readTransfo = entry -> new UnmodifiableConfig.Entry(){
            final /* synthetic */ UnmodifiableConfig.Entry val$entry;
            {
                this.val$entry = entry;
            }

            @Override
            public String getKey() {
                return this.val$entry.getKey();
            }

            @Override
            public <T> T getRawValue() {
                return (T)ConversionTable.this.convert(this.val$entry.getRawValue());
            }
        };
        return new TransformingSet<UnmodifiableConfig.Entry, UnmodifiableConfig.Entry>(this.config.entrySet(), readTransfo, o -> null, e -> e);
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return this.config.configFormat();
    }
}
