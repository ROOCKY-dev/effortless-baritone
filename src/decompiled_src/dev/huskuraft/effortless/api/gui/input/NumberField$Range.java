/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.input;

private record NumberField.Range(Number min, Number max) {
    public static final NumberField.Range UNBOUNDED = new NumberField.Range(Integer.MIN_VALUE, Integer.MAX_VALUE);

    public boolean contains(int number) {
        return number >= this.min.intValue() && number <= this.max.intValue();
    }

    public boolean contains(double number) {
        return number >= this.min.doubleValue() && number <= this.max.doubleValue();
    }

    public boolean isBelow(Number number) {
        return number.doubleValue() < this.min.doubleValue();
    }

    public boolean isAbove(Number number) {
        return number.doubleValue() > this.max.doubleValue();
    }
}
