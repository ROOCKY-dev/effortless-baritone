/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import java.util.Map;

class ObjectBinder.BoundConfig.1
implements Config.Entry {
    final /* synthetic */ Map.Entry val$entry;

    ObjectBinder.BoundConfig.1() {
        this.val$entry = entry;
    }

    @Override
    public <T> T setValue(Object value) {
        return BoundConfig.this.set((String)this.val$entry.getKey(), value);
    }

    @Override
    public String getKey() {
        return (String)this.val$entry.getKey();
    }

    @Override
    public <T> T getRawValue() {
        return (T)this.val$entry.getValue();
    }
}
