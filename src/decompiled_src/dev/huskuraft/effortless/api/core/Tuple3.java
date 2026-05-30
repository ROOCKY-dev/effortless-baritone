/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Tuple;
import java.util.List;

public record Tuple3<T1, T2, T3>(T1 value1, T2 value2, T3 value3) implements Tuple
{
    public Tuple3<T1, T2, T3> withValue1(T1 value1) {
        return new Tuple3<T1, T2, T3>(value1, this.value2, this.value3);
    }

    public Tuple3<T1, T2, T3> withValue2(T2 value2) {
        return new Tuple3<T1, T2, T3>(this.value1, value2, this.value3);
    }

    public Tuple3<T1, T2, T3> withValue3(T3 value3) {
        return new Tuple3<T1, T2, T3>(this.value1, this.value2, value3);
    }

    @Override
    public List<Object> asList() {
        return List.of(this.value1, this.value2, this.value3);
    }
}
