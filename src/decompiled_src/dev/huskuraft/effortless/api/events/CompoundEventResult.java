/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.EventResult;

public class CompoundEventResult<T> {
    private static final CompoundEventResult<?> PASS = new CompoundEventResult<Object>(EventResult.pass(), null);
    private final EventResult result;
    private final T object;

    private CompoundEventResult(EventResult result, T object) {
        this.result = result;
        this.object = object;
    }

    public static <T> CompoundEventResult<T> pass() {
        return PASS;
    }

    public static <T> CompoundEventResult<T> interrupt(Boolean value, T object) {
        return new CompoundEventResult<T>(EventResult.interrupt(value), object);
    }

    public static <T> CompoundEventResult<T> interruptTrue(T object) {
        return new CompoundEventResult<T>(EventResult.interruptTrue(), object);
    }

    public static <T> CompoundEventResult<T> interruptDefault(T object) {
        return new CompoundEventResult<T>(EventResult.interruptDefault(), object);
    }

    public static <T> CompoundEventResult<T> interruptFalse(T object) {
        return new CompoundEventResult<T>(EventResult.interruptFalse(), object);
    }

    public boolean interruptsFurtherEvaluation() {
        return this.result.interruptsFurtherEvaluation();
    }

    public Boolean value() {
        return this.result.value();
    }

    public boolean isEmpty() {
        return this.result.isEmpty();
    }

    public boolean isPresent() {
        return this.result.isPresent();
    }

    public boolean isTrue() {
        return this.result.isTrue();
    }

    public boolean isFalse() {
        return this.result.isFalse();
    }

    public EventResult result() {
        return this.result;
    }

    public T object() {
        return this.object;
    }
}
