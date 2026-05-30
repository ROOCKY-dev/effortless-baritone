/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

public final class EventResult {
    private static final EventResult TRUE = new EventResult(true, true);
    private static final EventResult STOP = new EventResult(true, null);
    private static final EventResult PASS = new EventResult(false, null);
    private static final EventResult FALSE = new EventResult(true, false);
    private final boolean interruptsFurtherEvaluation;
    private final Boolean value;

    EventResult(boolean interruptsFurtherEvaluation, Boolean value) {
        this.interruptsFurtherEvaluation = interruptsFurtherEvaluation;
        this.value = value;
    }

    public static EventResult pass() {
        return PASS;
    }

    public static EventResult interrupt(Boolean value) {
        if (value == null) {
            return STOP;
        }
        if (value.booleanValue()) {
            return TRUE;
        }
        return FALSE;
    }

    public static EventResult interruptTrue() {
        return TRUE;
    }

    public static EventResult interruptDefault() {
        return STOP;
    }

    public static EventResult interruptFalse() {
        return FALSE;
    }

    public boolean interruptsFurtherEvaluation() {
        return this.interruptsFurtherEvaluation;
    }

    public Boolean value() {
        return this.value;
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public boolean isTrue() {
        return Boolean.TRUE.equals(this.value);
    }

    public boolean isFalse() {
        return Boolean.FALSE.equals(this.value);
    }
}
