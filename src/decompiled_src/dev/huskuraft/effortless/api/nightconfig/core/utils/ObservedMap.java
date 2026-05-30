/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.utils.AbstractObserved;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ObservedEntry;
import dev.huskuraft.effortless.api.nightconfig.core.utils.ObservedSet;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ObservedMap<K, V>
extends AbstractObserved
implements Map<K, V> {
    private final Map<K, V> map;

    public ObservedMap(Map<K, V> map, Runnable callback) {
        super(callback);
        this.map = map;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V result = this.map.put(key, value);
        this.callback.run();
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = this.map.remove(key);
        this.callback.run();
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
        this.callback.run();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.map.replaceAll(function);
        this.callback.run();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V result = this.map.putIfAbsent(key, value);
        if (result != value) {
            this.callback.run();
        }
        return result;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean removed = this.map.remove(key, value);
        if (removed) {
            this.callback.run();
        }
        return removed;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        boolean replaced = this.map.replace(key, oldValue, newValue);
        if (replaced) {
            this.callback.run();
        }
        return replaced;
    }

    @Override
    public V replace(K key, V value) {
        V result = this.map.replace(key, value);
        if (result != value) {
            this.callback.run();
        }
        return result;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V result = this.map.computeIfAbsent((K)key, mappingFunction);
        if (result != null) {
            this.callback.run();
        }
        return result;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V result = this.map.computeIfPresent((K)key, (BiFunction<? super K, ? extends V, ? extends V>)remappingFunction);
        this.callback.run();
        return result;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V result = this.map.compute((K)key, (BiFunction<? super K, ? extends V, ? extends V>)remappingFunction);
        this.callback.run();
        return result;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V result = this.map.merge(key, (V)value, (BiFunction<? extends V, ? extends V, ? extends V>)remappingFunction);
        this.callback.run();
        return result;
    }

    @Override
    public void clear() {
        this.map.clear();
        this.callback.run();
    }

    @Override
    public Set<K> keySet() {
        return new ObservedSet<K>(this.map.keySet(), this.callback);
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Function<Map.Entry, ObservedEntry> readT = e -> new ObservedEntry(e, this.callback);
        Function<ObservedEntry, Map.Entry> writeT = oe -> oe.entry;
        Function<Object, Object> searchT = o -> {
            if (o instanceof ObservedEntry) {
                ObservedEntry observedEntry = (ObservedEntry)o;
                return observedEntry.entry;
            }
            return o;
        };
        TransformingSet<Map.Entry, ObservedEntry> tset = new TransformingSet<Map.Entry, ObservedEntry>(this.map.entrySet(), readT, writeT, searchT);
        return new ObservedSet<Map.Entry<K, V>>(this.map.entrySet(), this.callback);
    }

    @Override
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }
}
