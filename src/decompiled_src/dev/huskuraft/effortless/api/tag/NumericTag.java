/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.platform.TagFactory;
import dev.huskuraft.effortless.api.tag.Tag;

public interface NumericTag
extends Tag {
    public static NumericTag of(boolean value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public static NumericTag of(byte value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public static NumericTag of(short value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public static NumericTag of(int value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public static NumericTag of(long value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public static NumericTag of(float value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public static NumericTag of(double value) {
        return TagFactory.getInstance().newPrimitive(value);
    }

    public long getAsLong();

    public int getAsInt();

    public short getAsShort();

    public byte getAsByte();

    public double getAsDouble();

    public float getAsFloat();

    public Number getAsNumber();
}
