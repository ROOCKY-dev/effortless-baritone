/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.NullObject;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

public static interface UnmodifiableConfig.Entry {
    public String getKey();

    public <T> T getRawValue();

    default public <T> T getValue() {
        T raw = this.getRawValue();
        return raw == NullObject.NULL_OBJECT ? null : (T)raw;
    }

    default public boolean isNull() {
        return this.getRawValue() == NullObject.NULL_OBJECT;
    }

    default public <T> Optional<T> getOptional() {
        return Optional.ofNullable(this.getValue());
    }

    default public <T> T getOrElse(T defaultValue) {
        T value = this.getRawValue();
        return value == null || value == NullObject.NULL_OBJECT ? defaultValue : value;
    }

    default public int getInt() {
        return ((Number)this.getRawValue()).intValue();
    }

    default public OptionalInt getOptionalInt() {
        Number value = (Number)this.getRawValue();
        return value == null ? OptionalInt.empty() : OptionalInt.of(value.intValue());
    }

    default public int getIntOrElse(int defaultValue) {
        Number value = (Number)this.getRawValue();
        return value == null ? defaultValue : value.intValue();
    }

    default public long getLong() {
        return ((Number)this.getRawValue()).longValue();
    }

    default public OptionalLong getOptionalLong() {
        Number value = (Number)this.getRawValue();
        return value == null ? OptionalLong.empty() : OptionalLong.of(value.longValue());
    }

    default public long getLongOrElse(long defaultValue) {
        Number value = (Number)this.getRawValue();
        return value == null ? defaultValue : value.longValue();
    }

    default public byte getByte() {
        return ((Number)this.getRawValue()).byteValue();
    }

    default public byte getByteOrElse(byte defaultValue) {
        Number value = (Number)this.getRawValue();
        return value == null ? defaultValue : value.byteValue();
    }

    default public short getShort() {
        return ((Number)this.getRawValue()).shortValue();
    }

    default public short getShortOrElse(short defaultValue) {
        Number value = (Number)this.getRawValue();
        return value == null ? defaultValue : value.shortValue();
    }

    default public char getChar() {
        Object value = this.getRawValue();
        if (value instanceof Number) {
            return (char)((Number)value).intValue();
        }
        if (value instanceof CharSequence) {
            return ((CharSequence)value).charAt(0);
        }
        return ((Character)value).charValue();
    }

    default public char getCharOrElse(char defaultValue) {
        Object value = this.getRawValue();
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return (char)((Number)value).intValue();
        }
        if (value instanceof CharSequence) {
            return ((CharSequence)value).charAt(0);
        }
        return ((Character)value).charValue();
    }
}
