/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.CheckedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.InMemoryFormat;
import dev.huskuraft.effortless.api.nightconfig.core.SimpleConfig;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public interface Config
extends UnmodifiableConfig {
    default public <T> T set(String path, Object value) {
        return this.set(StringUtils.split(path, '.'), value);
    }

    public <T> T set(List<String> var1, Object var2);

    public boolean add(List<String> var1, Object var2);

    default public boolean add(String path, Object value) {
        return this.add(StringUtils.split(path, '.'), value);
    }

    default public void addAll(UnmodifiableConfig config) {
        for (UnmodifiableConfig.Entry entry : config.entrySet()) {
            Object value;
            List<String> key = Collections.singletonList(entry.getKey());
            boolean existed = !this.add(key, value = entry.getRawValue());
            if (existed && !(value instanceof UnmodifiableConfig)) continue;
        }
    }

    default public void putAll(UnmodifiableConfig config) {
        this.valueMap().putAll(config.valueMap());
    }

    default public <T> T remove(String path) {
        return this.remove(StringUtils.split(path, '.'));
    }

    public <T> T remove(List<String> var1);

    default public void removeAll(UnmodifiableConfig config) {
        this.valueMap().keySet().removeAll(config.valueMap().keySet());
    }

    public void clear();

    default public UnmodifiableConfig unmodifiable() {
        return new UnmodifiableConfig(){

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
        };
    }

    default public Config checked() {
        return new CheckedConfig(this);
    }

    @Override
    public Map<String, Object> valueMap();

    public Set<? extends Entry> entrySet();

    public Config createSubConfig();

    default public void update(String path, Object value) {
        this.set(path, value);
    }

    default public void update(List<String> path, Object value) {
        this.set(path, value);
    }

    public static Config of(ConfigFormat<? extends Config> format) {
        return new SimpleConfig(format, false);
    }

    public static Config of(Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> format) {
        return new SimpleConfig(mapCreator, format);
    }

    public static Config ofConcurrent(ConfigFormat<? extends Config> format) {
        return new SimpleConfig(format, true);
    }

    public static Config inMemory() {
        return InMemoryFormat.defaultInstance().createConfig();
    }

    public static Config inMemoryUniversal() {
        return InMemoryFormat.withUniversalSupport().createConfig();
    }

    public static Config inMemoryConcurrent() {
        return InMemoryFormat.defaultInstance().createConcurrentConfig();
    }

    public static Config inMemoryUniversalConcurrent() {
        return InMemoryFormat.withUniversalSupport().createConcurrentConfig();
    }

    public static Config wrap(Map<String, Object> map, ConfigFormat<?> format) {
        return new SimpleConfig(map, format);
    }

    public static Config copy(UnmodifiableConfig config) {
        return new SimpleConfig(config, config.configFormat(), false);
    }

    public static Config copy(UnmodifiableConfig config, Supplier<Map<String, Object>> mapCreator) {
        return new SimpleConfig(config, mapCreator, config.configFormat());
    }

    public static Config copy(UnmodifiableConfig config, ConfigFormat<?> format) {
        return new SimpleConfig(config, format, false);
    }

    public static Config copy(UnmodifiableConfig config, Supplier<Map<String, Object>> mapCreator, ConfigFormat<?> format) {
        return new SimpleConfig(config, mapCreator, format);
    }

    public static Config concurrentCopy(UnmodifiableConfig config) {
        return new SimpleConfig(config, config.configFormat(), true);
    }

    public static Config concurrentCopy(UnmodifiableConfig config, ConfigFormat<?> format) {
        return new SimpleConfig(config, format, true);
    }

    public static boolean isInsertionOrderPreserved() {
        String prop = System.getProperty("nightconfig.preserveInsertionOrder");
        return prop != null && (prop.equals("true") || prop.equals("1"));
    }

    public static void setInsertionOrderPreserved(boolean orderPreserved) {
        System.setProperty("nightconfig.preserveInsertionOrder", orderPreserved ? "true" : "false");
    }

    public static <T> Supplier<Map<String, T>> getDefaultMapCreator(boolean concurrent, boolean insertionOrderPreserved) {
        if (insertionOrderPreserved) {
            return concurrent ? () -> Collections.synchronizedMap(new LinkedHashMap()) : LinkedHashMap::new;
        }
        return concurrent ? ConcurrentHashMap::new : HashMap::new;
    }

    public static <T> Supplier<Map<String, T>> getDefaultMapCreator(boolean concurrent) {
        return Config.getDefaultMapCreator(concurrent, Config.isInsertionOrderPreserved());
    }

    public static interface Entry
    extends UnmodifiableConfig.Entry {
        public <T> T setValue(Object var1);
    }
}
