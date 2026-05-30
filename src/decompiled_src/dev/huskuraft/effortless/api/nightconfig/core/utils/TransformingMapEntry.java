/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

final class TransformingMapEntry<K, InternalV, ExternalV>
implements Map.Entry<K, ExternalV> {
    private final Function<? super InternalV, ? extends ExternalV> readTransformation;
    private final Function<? super ExternalV, ? extends InternalV> writeTransformation;
    private final Map.Entry<K, InternalV> internalEntry;

    TransformingMapEntry(Map.Entry<K, InternalV> internalEntry, Function<? super InternalV, ? extends ExternalV> readTransformation, Function<? super ExternalV, ? extends InternalV> writeTransformation) {
        this.readTransformation = readTransformation;
        this.writeTransformation = writeTransformation;
        this.internalEntry = internalEntry;
    }

    @Override
    public K getKey() {
        return this.internalEntry.getKey();
    }

    @Override
    public ExternalV getValue() {
        return this.readTransformation.apply(this.internalEntry.getValue());
    }

    @Override
    public ExternalV setValue(ExternalV value) {
        return this.readTransformation.apply(this.internalEntry.setValue(this.writeTransformation.apply(value)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry)obj;
        return Objects.equals(this.getKey(), entry.getKey()) && Objects.equals(this.getValue(), entry.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getKey()) ^ Objects.hashCode(this.getValue());
    }
}
