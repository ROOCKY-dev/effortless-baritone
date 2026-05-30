/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.EnumGetMethod;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Converter;

private static final class ObjectBinder.EnumValueConverter<T extends Enum<T>>
implements Converter<T, Object> {
    private final Class<T> enumType;
    private final EnumGetMethod method;

    ObjectBinder.EnumValueConverter(Class<T> enumType, EnumGetMethod method) {
        this.enumType = enumType;
        this.method = method;
    }

    @Override
    public T convertToField(Object value) {
        return this.method.get(value, this.enumType);
    }

    @Override
    public String convertFromField(T value) {
        return value == null ? null : ((Enum)value).toString();
    }
}
