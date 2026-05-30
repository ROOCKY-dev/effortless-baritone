/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ObjectBinder;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingMap;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

private static final class ObjectBinder.BoundConfig
implements Config {
    private Object object;
    private final Map<String, Object> dataMap;
    private final ConfigFormat<?> configFormat;
    private final boolean bypassFinal;

    private ObjectBinder.BoundConfig(Object object, Map<String, Object> dataMap, ConfigFormat<?> configFormat, boolean bypassFinal) {
        this.object = object;
        this.dataMap = dataMap;
        this.configFormat = configFormat;
        this.bypassFinal = bypassFinal;
    }

    private ObjectBinder.BoundConfig(Object object, ConfigFormat<?> configFormat, boolean bypassFinal) {
        this(object, new HashMap<String, Object>(), configFormat, bypassFinal);
    }

    private void registerField(ObjectBinder.FieldInfos fieldInfos, List<String> path) {
        int lastIndex = path.size() - 1;
        Map<String, Object> currentMap = this.dataMap;
        for (String currentKey : path.subList(0, lastIndex)) {
            ObjectBinder.BoundConfig config;
            Object currentValue = currentMap.get(currentKey);
            if (currentValue == null) {
                config = new ObjectBinder.BoundConfig(null, new HashMap<String, Object>(1), this.configFormat, this.bypassFinal);
                currentMap.put(currentKey, config);
            } else {
                if (!(currentValue instanceof ObjectBinder.BoundConfig)) {
                    throw new IllegalArgumentException("Cannot add an element to an intermediary value of type: " + currentValue.getClass());
                }
                config = (ObjectBinder.BoundConfig)currentValue;
            }
            currentMap = config.dataMap;
        }
        String lastKey = path.get(lastIndex);
        currentMap.put(lastKey, fieldInfos);
    }

    private ObjectBinder.BoundSearchResult searchInfosOrConfig(List<String> path) {
        int lastIndex = path.size() - 1;
        ObjectBinder.BoundConfig currentConfig = this;
        for (String key : path.subList(0, lastIndex)) {
            Object v = currentConfig.dataMap.get(key);
            if (v == null) {
                return null;
            }
            if (v instanceof ObjectBinder.BoundConfig) {
                currentConfig = (ObjectBinder.BoundConfig)v;
                continue;
            }
            ObjectBinder.FieldInfos fieldInfos = (ObjectBinder.FieldInfos)v;
            currentConfig = fieldInfos.getUpdatedConfig(currentConfig.object);
        }
        String lastKey = path.get(lastIndex);
        Object data = currentConfig.dataMap.get(lastKey);
        return new ObjectBinder.BoundSearchResult(currentConfig, data);
    }

    @Override
    public <T> T getRaw(List<String> path) {
        ObjectBinder.BoundSearchResult searchResult = this.searchInfosOrConfig(path);
        if (searchResult == null) {
            return null;
        }
        if (searchResult.hasSubConfig()) {
            return (T)searchResult.subConfig;
        }
        return (T)searchResult.fieldInfos.getValue(searchResult.parentConfig.object);
    }

    @Override
    public boolean contains(List<String> path) {
        return this.searchInfosOrConfig(path) != null;
    }

    @Override
    public <T> T set(List<String> path, Object value) {
        ObjectBinder.BoundSearchResult searchResult = this.searchInfosOrConfig(path);
        if (searchResult == null) {
            throw new UnsupportedOperationException("Cannot add elements to a bound config");
        }
        if (searchResult.hasFieldInfos()) {
            return (T)searchResult.fieldInfos.setValue(searchResult.parentConfig.object, value, this.bypassFinal);
        }
        throw new UnsupportedOperationException("Cannot modify non-field elements of a bound config");
    }

    @Override
    public boolean add(List<String> path, Object value) {
        throw new UnsupportedOperationException("Cannot add elements to a bound config");
    }

    @Override
    public <T> T remove(List<String> path) {
        ObjectBinder.BoundSearchResult searchResult = this.searchInfosOrConfig(path);
        if (searchResult == null) {
            return null;
        }
        if (searchResult.hasFieldInfos()) {
            return (T)searchResult.fieldInfos.removeValue(searchResult.parentConfig.object, this.bypassFinal);
        }
        Config copy = Config.copy(searchResult.subConfig);
        searchResult.subConfig.clear();
        return (T)copy;
    }

    @Override
    public void clear() {
        for (Map.Entry<String, Object> dataEntry : this.dataMap.entrySet()) {
            Object value = dataEntry.getValue();
            if (value instanceof ObjectBinder.FieldInfos) {
                ((ObjectBinder.FieldInfos)value).removeValue(this.object, this.bypassFinal);
                continue;
            }
            if (!(value instanceof ObjectBinder.BoundConfig)) continue;
            ((ObjectBinder.BoundConfig)value).clear();
        }
        this.dataMap.clear();
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return this.configFormat;
    }

    @Override
    public Config createSubConfig() {
        return new ObjectBinder.BoundConfig(null, new HashMap<String, Object>(1), this.configFormat, this.bypassFinal);
    }

    @Override
    public Map<String, Object> valueMap() {
        Function<Object, Object> readConversion = o -> {
            if (o instanceof ObjectBinder.FieldInfos) {
                ObjectBinder.FieldInfos fieldInfos = (ObjectBinder.FieldInfos)o;
                if (fieldInfos.boundConfig != null) {
                    return fieldInfos.getUpdatedConfig(this.object);
                }
                return fieldInfos.getValue(this.object);
            }
            return o;
        };
        return new TransformingMap<String, Object, Object>(this.dataMap, readConversion, o -> o, o -> o);
    }

    @Override
    public Set<? extends Config.Entry> entrySet() {
        Function<Map.Entry, Config.Entry> readTransfo = entry -> new Config.Entry(){
            final /* synthetic */ Map.Entry val$entry;
            {
                this.val$entry = entry;
            }

            @Override
            public <T> T setValue(Object value) {
                return this.set((String)this.val$entry.getKey(), value);
            }

            @Override
            public String getKey() {
                return (String)this.val$entry.getKey();
            }

            @Override
            public <T> T getRawValue() {
                return (T)this.val$entry.getValue();
            }
        };
        return new TransformingSet<Map.Entry, Config.Entry>(this.dataMap.entrySet(), readTransfo, o -> null, o -> o);
    }

    @Override
    public int size() {
        return this.dataMap.size();
    }

    public String toString() {
        return "BoundConfig{object=" + this.object + ", dataMap=" + this.dataMap + '}';
    }

    static /* synthetic */ void access$100(ObjectBinder.BoundConfig x0, ObjectBinder.FieldInfos x1, List x2) {
        x0.registerField(x1, x2);
    }

    static /* synthetic */ Object access$200(ObjectBinder.BoundConfig x0) {
        return x0.object;
    }

    static /* synthetic */ Object access$202(ObjectBinder.BoundConfig x0, Object x1) {
        x0.object = x1;
        return x0.object;
    }
}
