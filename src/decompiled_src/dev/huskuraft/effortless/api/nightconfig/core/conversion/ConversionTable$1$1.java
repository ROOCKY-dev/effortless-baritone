/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;

class ConversionTable.1
implements UnmodifiableConfig.Entry {
    final /* synthetic */ UnmodifiableConfig.Entry val$entry;

    ConversionTable.1() {
        this.val$entry = entry;
    }

    @Override
    public String getKey() {
        return this.val$entry.getKey();
    }

    @Override
    public <T> T getRawValue() {
        return (T)this$0.convert(this.val$entry.getRawValue());
    }
}
