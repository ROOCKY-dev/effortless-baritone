/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import java.util.Map;
import java.util.Objects;

protected static class AbstractConfig.EntryWrapper
implements Config.Entry {
    protected final Map.Entry<String, Object> mapEntry;

    public AbstractConfig.EntryWrapper(Map.Entry<String, Object> mapEntry) {
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
        if (obj instanceof AbstractConfig.EntryWrapper) {
            AbstractConfig.EntryWrapper other = (AbstractConfig.EntryWrapper)obj;
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
