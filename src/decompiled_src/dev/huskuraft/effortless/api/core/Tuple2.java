/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Tuple;
import java.util.List;

public record Tuple2<T1, T2>(T1 value1, T2 value2) implements Tuple
{
    public Tuple2<T1, T2> withValue1(T1 value1) {
        return new Tuple2<T1, T2>(value1, this.value2);
    }

    public Tuple2<T1, T2> withValue2(T2 value2) {
        return new Tuple2<T1, T2>(this.value1, value2);
    }

    @Override
    public List<Object> asList() {
        return List.of(this.value1, this.value2);
    }
}
