/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

public record Range1i(int min, int max) {
    public static final Range1i UNBOUNDED = new Range1i(Integer.MIN_VALUE, Integer.MAX_VALUE);

    public boolean contains(int value) {
        return value >= this.min && value <= this.max;
    }

    public boolean isBelow(int value) {
        return value < this.min;
    }

    public boolean isAbove(int value) {
        return value > this.max;
    }
}
