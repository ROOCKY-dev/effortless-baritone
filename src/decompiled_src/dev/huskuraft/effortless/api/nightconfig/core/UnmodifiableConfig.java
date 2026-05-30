/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.EnumGetMethod;
import dev.huskuraft.effortless.api.nightconfig.core.NullObject;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public interface UnmodifiableConfig {
    default public <T> T get(String path) {
        return this.get(StringUtils.split(path, '.'));
    }

    default public <T> T get(List<String> path) {
        T raw = this.getRaw(path);
        return raw == NullObject.NULL_OBJECT ? null : (T)raw;
    }

    default public <T> T getRaw(String path) {
        return this.getRaw(StringUtils.split(path, '.'));
    }

    public <T> T getRaw(List<String> var1);

    default public <T> Optional<T> getOptional(String path) {
        return this.getOptional(StringUtils.split(path, '.'));
    }

    default public <T> Optional<T> getOptional(List<String> path) {
        return Optional.ofNullable(this.get(path));
    }

    default public <T> T getOrElse(String path, T defaultValue) {
        return this.getOrElse(StringUtils.split(path, '.'), defaultValue);
    }

    default public <T> T getOrElse(List<String> path, T defaultValue) {
        T value = this.getRaw(path);
        return value == null || value == NullObject.NULL_OBJECT ? defaultValue : value;
    }

    default public <T> T getOrElse(List<String> path, Supplier<T> defaultValueSupplier) {
        T value = this.getRaw(path);
        return value == null || value == NullObject.NULL_OBJECT ? defaultValueSupplier.get() : value;
    }

    default public <T> T getOrElse(String path, Supplier<T> defaultValueSupplier) {
        return this.getOrElse(StringUtils.split(path, '.'), defaultValueSupplier);
    }

    default public <T extends Enum<T>> T getEnum(String path, Class<T> enumType, EnumGetMethod method) {
        return this.getEnum(StringUtils.split(path, '.'), enumType, method);
    }

    default public <T extends Enum<T>> T getEnum(String path, Class<T> enumType) {
        return this.getEnum(StringUtils.split(path, '.'), enumType, EnumGetMethod.NAME_IGNORECASE);
    }

    default public <T extends Enum<T>> T getEnum(List<String> path, Class<T> enumType, EnumGetMethod method) {
        T value = this.getRaw(path);
        return method.get(value, enumType);
    }

    default public <T extends Enum<T>> T getEnum(List<String> path, Class<T> enumType) {
        return this.getEnum(path, enumType, EnumGetMethod.NAME_IGNORECASE);
    }

    default public <T extends Enum<T>> Optional<T> getOptionalEnum(String path, Class<T> enumType, EnumGetMethod method) {
        return this.getOptionalEnum(StringUtils.split(path, '.'), enumType, method);
    }

    default public <T extends Enum<T>> Optional<T> getOptionalEnum(String path, Class<T> enumType) {
        return this.getOptionalEnum(path, enumType, EnumGetMethod.NAME_IGNORECASE);
    }

    default public <T extends Enum<T>> Optional<T> getOptionalEnum(List<String> path, Class<T> enumType, EnumGetMethod method) {
        return Optional.ofNullable(this.getEnum(path, enumType, method));
    }

    default public <T extends Enum<T>> Optional<T> getOptionalEnum(List<String> path, Class<T> enumType) {
        return this.getOptionalEnum(path, enumType, EnumGetMethod.NAME_IGNORECASE);
    }

    default public <T extends Enum<T>> T getEnumOrElse(String path, T defaultValue, EnumGetMethod method) {
        return this.getEnumOrElse(StringUtils.split(path, '.'), defaultValue, method);
    }

    default public <T extends Enum<T>> T getEnumOrElse(String path, T defaultValue) {
        return this.getEnumOrElse(path, defaultValue, EnumGetMethod.NAME_IGNORECASE);
    }

    default public <T extends Enum<T>> T getEnumOrElse(List<String> path, T defaultValue, EnumGetMethod method) {
        T value = this.getEnum(path, defaultValue.getDeclaringClass(), method);
        return value == null ? defaultValue : value;
    }

    default public <T extends Enum<T>> T getEnumOrElse(List<String> path, T defaultValue) {
        return this.getEnumOrElse(path, defaultValue, EnumGetMethod.NAME_IGNORECASE);
    }

    default public <T extends Enum<T>> T getEnumOrElse(String path, Class<T> enumType, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        return this.getEnumOrElse(StringUtils.split(path, '.'), enumType, method, defaultValueSupplier);
    }

    default public <T extends Enum<T>> T getEnumOrElse(String path, Class<T> enumType, Supplier<T> defaultValueSupplier) {
        return this.getEnumOrElse(path, enumType, EnumGetMethod.NAME_IGNORECASE, defaultValueSupplier);
    }

    default public <T extends Enum<T>> T getEnumOrElse(List<String> path, Class<T> enumType, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        T value = this.getEnum(path, enumType, method);
        return (T)(value == null ? (Enum)defaultValueSupplier.get() : value);
    }

    default public <T extends Enum<T>> T getEnumOrElse(List<String> path, Class<T> enumType, Supplier<T> defaultValueSupplier) {
        return this.getEnumOrElse(path, enumType, EnumGetMethod.NAME_IGNORECASE, defaultValueSupplier);
    }

    default public int getInt(String path) {
        return ((Number)this.get(path)).intValue();
    }

    default public int getInt(List<String> path) {
        return ((Number)this.getRaw(path)).intValue();
    }

    default public OptionalInt getOptionalInt(String path) {
        return this.getOptionalInt(StringUtils.split(path, '.'));
    }

    default public OptionalInt getOptionalInt(List<String> path) {
        Number n = (Number)this.get(path);
        return n == null ? OptionalInt.empty() : OptionalInt.of(n.intValue());
    }

    default public int getIntOrElse(String path, int defaultValue) {
        return this.getIntOrElse(StringUtils.split(path, '.'), defaultValue);
    }

    default public int getIntOrElse(List<String> path, int defaultValue) {
        Number n = (Number)this.get(path);
        return n == null ? defaultValue : n.intValue();
    }

    default public int getIntOrElse(String path, IntSupplier defaultValueSupplier) {
        return this.getIntOrElse(StringUtils.split(path, '.'), defaultValueSupplier);
    }

    default public int getIntOrElse(List<String> path, IntSupplier defaultValueSupplier) {
        Number n = (Number)this.get(path);
        return n == null ? defaultValueSupplier.getAsInt() : n.intValue();
    }

    default public long getLong(String path) {
        return ((Number)this.getRaw(path)).longValue();
    }

    default public long getLong(List<String> path) {
        return ((Number)this.getRaw(path)).longValue();
    }

    default public OptionalLong getOptionalLong(String path) {
        return this.getOptionalLong(StringUtils.split(path, '.'));
    }

    default public OptionalLong getOptionalLong(List<String> path) {
        Number n = (Number)this.get(path);
        return n == null ? OptionalLong.empty() : OptionalLong.of(n.longValue());
    }

    default public long getLongOrElse(String path, long defaultValue) {
        return this.getLongOrElse(StringUtils.split(path, '.'), defaultValue);
    }

    default public long getLongOrElse(List<String> path, long defaultValue) {
        Number n = (Number)this.get(path);
        return n == null ? defaultValue : n.longValue();
    }

    default public long getLongOrElse(String path, LongSupplier defaultValueSupplier) {
        return this.getLongOrElse(StringUtils.split(path, '.'), defaultValueSupplier);
    }

    default public long getLongOrElse(List<String> path, LongSupplier defaultValueSupplier) {
        Number n = (Number)this.get(path);
        return n == null ? defaultValueSupplier.getAsLong() : n.longValue();
    }

    default public byte getByte(String path) {
        return ((Number)this.getRaw(path)).byteValue();
    }

    default public byte getByte(List<String> path) {
        return ((Number)this.getRaw(path)).byteValue();
    }

    default public byte getByteOrElse(String path, byte defaultValue) {
        return this.getByteOrElse(StringUtils.split(path, '.'), defaultValue);
    }

    default public byte getByteOrElse(List<String> path, byte defaultValue) {
        Number n = (Number)this.get(path);
        return n == null ? defaultValue : n.byteValue();
    }

    default public short getShort(String path) {
        return ((Number)this.getRaw(path)).shortValue();
    }

    default public short getShort(List<String> path) {
        return ((Number)this.getRaw(path)).shortValue();
    }

    default public short getShortOrElse(String path, short defaultValue) {
        return this.getShortOrElse(StringUtils.split(path, '.'), defaultValue);
    }

    default public short getShortOrElse(List<String> path, short defaultValue) {
        Number n = (Number)this.get(path);
        return n == null ? defaultValue : n.shortValue();
    }

    default public char getChar(String path) {
        return (char)this.getInt(path);
    }

    default public char getChar(List<String> path) {
        Object value = this.getRaw(path);
        if (value instanceof Number) {
            return (char)((Number)value).intValue();
        }
        if (value instanceof CharSequence) {
            return ((CharSequence)value).charAt(0);
        }
        return ((Character)value).charValue();
    }

    default public char getCharOrElse(String path, char defaultValue) {
        return this.getCharOrElse(StringUtils.split(path, '.'), defaultValue);
    }

    default public char getCharOrElse(List<String> path, char defaultValue) {
        Object value = this.getRaw(path);
        if (value == null || value == NullObject.NULL_OBJECT) {
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

    default public boolean contains(String path) {
        return this.contains(StringUtils.split(path, '.'));
    }

    public boolean contains(List<String> var1);

    default public boolean isNull(String path) {
        return this.isNull(StringUtils.split(path, '.'));
    }

    default public boolean isNull(List<String> path) {
        return this.getRaw(path) == NullObject.NULL_OBJECT;
    }

    public int size();

    default public boolean isEmpty() {
        return this.size() == 0;
    }

    public Map<String, Object> valueMap();

    public Set<? extends Entry> entrySet();

    public ConfigFormat<?> configFormat();

    default public <T> T apply(String path) {
        return this.get(path);
    }

    default public <T> T apply(List<String> path) {
        return this.get(path);
    }

    public static interface Entry {
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
}
