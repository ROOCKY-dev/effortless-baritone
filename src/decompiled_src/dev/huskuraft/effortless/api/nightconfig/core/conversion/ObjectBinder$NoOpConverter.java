/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.conversion.Converter;

private static final class ObjectBinder.NoOpConverter
implements Converter<Object, Object> {
    static final ObjectBinder.NoOpConverter INSTANCE = new ObjectBinder.NoOpConverter();

    private ObjectBinder.NoOpConverter() {
    }

    @Override
    public Object convertToField(Object value) {
        return value;
    }

    @Override
    public Object convertFromField(Object value) {
        return value;
    }
}
