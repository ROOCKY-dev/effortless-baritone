/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

public record Range1d(double low, double high) {
    public static final Range1d UNBOUNDED = new Range1d(Double.MIN_VALUE, Double.MAX_VALUE);

    public boolean contains(double value) {
        return value >= this.low && value <= this.high;
    }

    public boolean isBelow(double value) {
        return value < this.low;
    }

    public boolean isAbove(double value) {
        return value > this.high;
    }
}
