/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.Config;

class ConvertedConfig.1
implements Config.Entry {
    final /* synthetic */ Config.Entry val$entry;

    ConvertedConfig.1() {
        this.val$entry = entry;
    }

    public Object setValue(Object value) {
        return ConvertedConfig.this.readConversion.apply(this.val$entry.setValue(ConvertedConfig.this.writeConversion.apply(value)));
    }

    @Override
    public String getKey() {
        return this.val$entry.getKey();
    }

    @Override
    public <T> T getRawValue() {
        return (T)ConvertedConfig.this.readConversion.apply(this.val$entry.getRawValue());
    }
}
