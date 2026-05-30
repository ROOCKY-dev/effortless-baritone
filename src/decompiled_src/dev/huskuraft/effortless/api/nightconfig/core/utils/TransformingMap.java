/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingCollection;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingMapEntry;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class TransformingMap<K, InternalV, ExternalV>
extends AbstractMap<K, ExternalV> {
    private final Function<? super InternalV, ? extends ExternalV> readTransformation;
    private final Function<? super ExternalV, ? extends InternalV> writeTransformation;
    private final Function<Object, Object> searchTransformation;
    private final Map<K, InternalV> internalMap;

    public TransformingMap(Map<K, InternalV> map, Function<? super InternalV, ? extends ExternalV> readTransformation, Function<? super ExternalV, ? extends InternalV> writeTransformation, Function<Object, Object> searchTransformation) {
        this.internalMap = map;
        this.readTransformation = readTransformation;
        this.writeTransformation = writeTransformation;
        this.searchTransformation = searchTransformation;
    }

    @Override
    public int size() {
        return this.internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.internalMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.internalMap.containsValue(this.searchTransformation.apply(value));
    }

    @Override
    public ExternalV get(Object key) {
        return this.readTransformation.apply(this.internalMap.get(key));
    }

    @Override
    public ExternalV put(K key, ExternalV value) {
        return this.readTransformation.apply(this.internalMap.put(key, this.writeTransformation.apply(value)));
    }

    @Override
    public ExternalV remove(Object key) {
        return this.readTransformation.apply(this.internalMap.remove(key));
    }

    @Override
    public void putAll(Map<? extends K, ? extends ExternalV> m) {
        this.internalMap.putAll(new TransformingMap<K, Object, Object>(m, this.writeTransformation, o -> o, o -> o));
    }

    @Override
    public void clear() {
        this.internalMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.internalMap.keySet();
    }

    @Override
    public Collection<ExternalV> values() {
        return new TransformingCollection<InternalV, ExternalV>(this.internalMap.values(), this.readTransformation, this.writeTransformation, this.searchTransformation);
    }

    @Override
    public Set<Map.Entry<K, ExternalV>> entrySet() {
        Function<Map.Entry, Map.Entry> internalToExternal = internalEntry -> new TransformingMapEntry(internalEntry, this.readTransformation, this.writeTransformation);
        Function<Map.Entry, Map.Entry> externalToInternal = externalEntry -> new TransformingMapEntry(externalEntry, this.writeTransformation, this.readTransformation);
        Function<Object, Object> searchTranformation = o -> {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)o;
                return new TransformingMapEntry(entry, this.readTransformation, this.writeTransformation);
            }
            return o;
        };
        return new TransformingSet<Map.Entry, Map.Entry>(this.internalMap.entrySet(), internalToExternal, externalToInternal, searchTranformation);
    }

    @Override
    public ExternalV getOrDefault(Object key, ExternalV defaultValue) {
        InternalV result = this.internalMap.get(key);
        return result == defaultValue ? defaultValue : this.readTransformation.apply(result);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super ExternalV> action) {
        this.internalMap.forEach((? super K k, ? super V o) -> action.accept((K)k, (ExternalV)this.readTransformation.apply(o)));
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super ExternalV, ? extends ExternalV> function) {
        this.internalMap.replaceAll(this.transform(function));
    }

    @Override
    public ExternalV putIfAbsent(K key, ExternalV value) {
        return this.readTransformation.apply(this.internalMap.putIfAbsent(key, this.writeTransformation.apply(value)));
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.internalMap.remove(key, this.searchTransformation.apply(value));
    }

    @Override
    public boolean replace(K key, ExternalV oldValue, ExternalV newValue) {
        return this.internalMap.replace(key, this.writeTransformation.apply(oldValue), this.writeTransformation.apply(newValue));
    }

    @Override
    public ExternalV replace(K key, ExternalV value) {
        return this.readTransformation.apply(this.internalMap.replace(key, this.writeTransformation.apply(value)));
    }

    @Override
    public ExternalV computeIfAbsent(K key, Function<? super K, ? extends ExternalV> mappingFunction) {
        Function<Object, Object> function = k -> this.writeTransformation.apply(mappingFunction.apply((K)k));
        return this.readTransformation.apply(this.internalMap.computeIfAbsent(key, function));
    }

    @Override
    public ExternalV computeIfPresent(K key, BiFunction<? super K, ? super ExternalV, ? extends ExternalV> remappingFunction) {
        return this.readTransformation.apply(this.internalMap.computeIfPresent((K)key, this.transform(remappingFunction)));
    }

    @Override
    public ExternalV compute(K key, BiFunction<? super K, ? super ExternalV, ? extends ExternalV> remappingFunction) {
        return this.readTransformation.apply(this.internalMap.compute((K)key, this.transform(remappingFunction)));
    }

    @Override
    public ExternalV merge(K key, ExternalV value, BiFunction<? super ExternalV, ? super ExternalV, ? extends ExternalV> remappingFunction) {
        return this.readTransformation.apply(this.internalMap.merge(key, this.writeTransformation.apply(value), this.transform2(remappingFunction)));
    }

    private BiFunction<K, InternalV, InternalV> transform(BiFunction<? super K, ? super ExternalV, ? extends ExternalV> remappingFunction) {
        return (k, internalV) -> this.writeTransformation.apply(remappingFunction.apply((K)k, (ExternalV)this.readTransformation.apply(internalV)));
    }

    private BiFunction<InternalV, InternalV, InternalV> transform2(BiFunction<? super ExternalV, ? super ExternalV, ? extends ExternalV> remappingFunction) {
        return (internalV1, internalV2) -> this.writeTransformation.apply(remappingFunction.apply((ExternalV)this.readTransformation.apply(internalV1), (ExternalV)this.readTransformation.apply(internalV2)));
    }
}
