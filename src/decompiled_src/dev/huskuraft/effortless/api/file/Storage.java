/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.file;

import java.util.function.UnaryOperator;

interface Storage<T> {
    default public void update(UnaryOperator<T> operator) {
        this.set(operator.apply(this.get()));
    }

    public T get();

    public void set(T var1);
}
