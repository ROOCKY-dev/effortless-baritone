/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.NullObject;

public enum EnumGetMethod {
    NAME,
    NAME_IGNORECASE,
    ORDINAL_OR_NAME,
    ORDINAL_OR_NAME_IGNORECASE;


    public boolean isCaseSensitive() {
        return this == NAME || this == ORDINAL_OR_NAME;
    }

    public boolean isOrdinalOk() {
        return this == ORDINAL_OR_NAME || this == ORDINAL_OR_NAME_IGNORECASE;
    }

    public <T extends Enum<T>> T get(Object value, Class<T> enumType) {
        if (value == null || value == NullObject.NULL_OBJECT) {
            return null;
        }
        Class<?> cls = value.getClass();
        if (enumType.isAssignableFrom(cls)) {
            return (T)((Enum)value);
        }
        if (cls == String.class) {
            String name = (String)value;
            if (this.isCaseSensitive()) {
                return Enum.valueOf(enumType, name);
            }
            for (Enum item : (Enum[])enumType.getEnumConstants()) {
                if (!item.name().equalsIgnoreCase(name)) continue;
                return (T)item;
            }
            String enumName = enumType.getCanonicalName();
            throw new IllegalArgumentException("No enum constant " + enumName + "." + name);
        }
        if (cls == Integer.class) {
            if (this.isOrdinalOk()) {
                return (T)((Enum[])enumType.getEnumConstants())[(Integer)value];
            }
            throw new ClassCastException("Cannot convert an Integer to an Enum: disallowed by EnumGetMethod." + (Object)((Object)this));
        }
        String name = cls.getCanonicalName();
        throw new ClassCastException("Cannot convert a value of type " + name + " to an Enum");
    }

    public <T extends Enum<T>> boolean validate(Object value, Class<T> enumType) {
        if (value == null || value == NullObject.NULL_OBJECT) {
            return true;
        }
        Class<?> cls = value.getClass();
        if (enumType.isAssignableFrom(cls)) {
            return true;
        }
        if (cls == String.class) {
            String name = (String)value;
            if (this.isCaseSensitive()) {
                for (Enum item : (Enum[])enumType.getEnumConstants()) {
                    if (!item.name().equals(name)) continue;
                    return true;
                }
            } else {
                for (Enum item : (Enum[])enumType.getEnumConstants()) {
                    if (!item.name().equalsIgnoreCase(name)) continue;
                    return true;
                }
            }
        } else if (cls == Integer.class && this.isOrdinalOk()) {
            int idx = (Integer)value;
            return idx >= 0 && idx < ((Enum[])enumType.getEnumConstants()).length;
        }
        return false;
    }
}
