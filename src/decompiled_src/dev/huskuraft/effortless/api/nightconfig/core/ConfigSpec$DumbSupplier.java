/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import java.util.function.Supplier;

private static final class ConfigSpec.DumbSupplier<T>
implements Supplier<T> {
    private final T value;

    private ConfigSpec.DumbSupplier(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return this.value;
    }
}
