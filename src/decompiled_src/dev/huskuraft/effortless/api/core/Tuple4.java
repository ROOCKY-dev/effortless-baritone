/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Tuple;
import java.util.List;

public record Tuple4<T1, T2, T3, T4>(T1 value1, T2 value2, T3 value3, T4 value4) implements Tuple
{
    public Tuple4<T1, T2, T3, T4> withValue1(T1 value1) {
        return new Tuple4<T1, T2, T3, T4>(value1, this.value2, this.value3, this.value4);
    }

    public Tuple4<T1, T2, T3, T4> withValue2(T2 value2) {
        return new Tuple4<T1, T2, T3, T4>(this.value1, value2, this.value3, this.value4);
    }

    public Tuple4<T1, T2, T3, T4> withValue3(T3 value3) {
        return new Tuple4<T1, T2, T3, T4>(this.value1, this.value2, value3, this.value4);
    }

    public Tuple4<T1, T2, T3, T4> withValue4(T4 value4) {
        return new Tuple4<T1, T2, T3, T4>(this.value1, this.value2, this.value3, value4);
    }

    @Override
    public List<Object> asList() {
        return List.of(this.value1, this.value2, this.value3, this.value4);
    }
}
