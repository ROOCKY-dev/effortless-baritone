/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.NullObject;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public abstract class AbstractConfig
implements Config,
Cloneable {
    protected final Supplier<Map<String, Object>> mapCreator;
    final Map<String, Object> map;

    public AbstractConfig(boolean concurrent) {
        this(AbstractConfig.getDefaultMapCreator(concurrent));
    }

    public AbstractConfig(Supplier<Map<String, Object>> mapCreator) {
        this.mapCreator = mapCreator;
        this.map = mapCreator.get();
    }

    public AbstractConfig(Map<String, Object> map) {
        this.map = map;
        this.mapCreator = AbstractConfig.getDefaultMapCreator(map instanceof ConcurrentMap);
    }

    public AbstractConfig(UnmodifiableConfig toCopy, boolean concurrent) {
        this(toCopy, AbstractConfig.getDefaultMapCreator(concurrent));
    }

    public AbstractConfig(UnmodifiableConfig toCopy, Supplier<Map<String, Object>> mapCreator) {
        this.map = mapCreator.get();
        this.map.putAll(toCopy.valueMap());
        this.mapCreator = mapCreator;
    }

    protected static <T> Supplier<Map<String, T>> getDefaultMapCreator(boolean concurrent) {
        return Config.getDefaultMapCreator(concurrent);
    }

    protected static <T> Supplier<Map<String, T>> getWildcardMapCreator(Supplier<Map<String, Object>> mapCreator) {
        return () -> {
            Map map = (Map)mapCreator.get();
            map.clear();
            return map;
        };
    }

    @Override
    public <T> T getRaw(List<String> path) {
        int lastIndex = path.size() - 1;
        Map<String, Object> parentMap = this.getMap(path.subList(0, lastIndex));
        if (parentMap == null) {
            return null;
        }
        String lastKey = path.get(lastIndex);
        return (T)parentMap.get(lastKey);
    }

    @Override
    public <T> T set(List<String> path, Object value) {
        int lastIndex = path.size() - 1;
        Map<String, Object> parentMap = this.getOrCreateMap(path.subList(0, lastIndex));
        String lastKey = path.get(lastIndex);
        Object nonNull = value == null ? NullObject.NULL_OBJECT : value;
        return (T)parentMap.put(lastKey, nonNull);
    }

    @Override
    public boolean add(List<String> path, Object value) {
        int lastIndex = path.size() - 1;
        Map<String, Object> parentMap = this.getOrCreateMap(path.subList(0, lastIndex));
        String lastKey = path.get(lastIndex);
        Object nonNull = value == null ? NullObject.NULL_OBJECT : value;
        return parentMap.putIfAbsent(lastKey, nonNull) == null;
    }

    @Override
    public <T> T remove(List<String> path) {
        int lastIndex = path.size() - 1;
        Map<String, Object> parentMap = this.getMap(path.subList(0, lastIndex));
        if (parentMap == null) {
            return null;
        }
        String lastKey = path.get(lastIndex);
        return (T)parentMap.remove(lastKey);
    }

    @Override
    public boolean contains(List<String> path) {
        int lastIndex = path.size() - 1;
        Map<String, Object> parentMap = this.getMap(path.subList(0, lastIndex));
        if (parentMap == null) {
            return false;
        }
        String lastKey = path.get(lastIndex);
        return parentMap.containsKey(lastKey);
    }

    @Override
    public boolean isNull(List<String> path) {
        int lastIndex = path.size() - 1;
        Map<String, Object> parentMap = this.getMap(path.subList(0, lastIndex));
        if (parentMap == null) {
            return false;
        }
        String lastKey = path.get(lastIndex);
        Object value = parentMap.get(lastKey);
        return value == NullObject.NULL_OBJECT;
    }

    private Map<String, Object> getOrCreateMap(List<String> path) {
        Map<String, Object> currentMap = this.map;
        for (String currentKey : path) {
            Config config;
            Object currentValue = currentMap.get(currentKey);
            if (currentValue == null) {
                config = this.createSubConfig();
                currentMap.put(currentKey, config);
            } else {
                if (!(currentValue instanceof Config)) {
                    throw new IllegalArgumentException("Cannot add an element to an intermediary value of type: " + currentValue.getClass());
                }
                config = (Config)currentValue;
            }
            currentMap = config.valueMap();
        }
        return currentMap;
    }

    private Map<String, Object> getMap(List<String> path) {
        Map<String, Object> currentMap = this.map;
        for (String key : path) {
            Object value = currentMap.get(key);
            if (!(value instanceof Config)) {
                return null;
            }
            currentMap = ((Config)value).valueMap();
        }
        return currentMap;
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Map<String, Object> valueMap() {
        return this.map;
    }

    @Override
    public Set<? extends Config.Entry> entrySet() {
        return new TransformingSet<Map.Entry, EntryWrapper>(this.map.entrySet(), EntryWrapper::new, o -> null, o -> o);
    }

    public abstract AbstractConfig clone();

    public int hashCode() {
        return this.map.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractConfig)) {
            return false;
        }
        AbstractConfig other = (AbstractConfig)obj;
        return this.map.equals(other.map);
    }

    public String toString() {
        return this.getClass().getSimpleName() + ':' + this.valueMap();
    }

    protected static class EntryWrapper
    implements Config.Entry {
        protected final Map.Entry<String, Object> mapEntry;

        public EntryWrapper(Map.Entry<String, Object> mapEntry) {
            this.mapEntry = mapEntry;
        }

        @Override
        public String getKey() {
            return this.mapEntry.getKey();
        }

        @Override
        public <T> T getRawValue() {
            return (T)this.mapEntry.getValue();
        }

        @Override
        public <T> T setValue(Object value) {
            return (T)this.mapEntry.setValue(value);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof EntryWrapper) {
                EntryWrapper other = (EntryWrapper)obj;
                return Objects.equals(this.getKey(), other.getKey()) && Objects.equals(this.getValue(), other.getValue());
            }
            return false;
        }

        public int hashCode() {
            int result = 1;
            result = 31 * result + Objects.hashCode(this.getKey());
            result = 31 * result + Objects.hashCode(this.getValue());
            return result;
        }
    }
}
