/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Tuple;
import java.util.List;

public record Tuple1<T1>(T1 value1) implements Tuple
{
    public Tuple1<T1> withValue1(T1 value1) {
        return new Tuple1<T1>(value1);
    }

    @Override
    public List<Object> asList() {
        return List.of(this.value1);
    }
}
