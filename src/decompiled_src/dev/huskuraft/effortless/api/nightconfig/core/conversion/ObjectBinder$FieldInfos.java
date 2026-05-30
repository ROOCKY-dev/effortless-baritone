/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.conversion.AnnotationUtils;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Converter;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ObjectBinder;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ReflectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

private static final class ObjectBinder.FieldInfos {
    final Field field;
    final ObjectBinder.BoundConfig boundConfig;
    final Converter<Object, Object> converter;

    ObjectBinder.FieldInfos(Field field, ObjectBinder.BoundConfig boundConfig, Converter<Object, Object> converter) {
        this.field = field;
        this.boundConfig = boundConfig;
        this.converter = converter;
    }

    Object setValue(Object fieldObject, Object value, boolean bypassFinal) {
        if (!bypassFinal && Modifier.isFinal(this.field.getModifiers())) {
            throw new UnsupportedOperationException("Cannot modify the field " + this.field);
        }
        try {
            Object previousValue = this.converter.convertFromField(this.field.get(fieldObject));
            Object newValue = this.converter.convertToField(value);
            AnnotationUtils.checkField(this.field, newValue);
            this.field.set(fieldObject, newValue);
            return previousValue;
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException("Failed to set field " + this.field, e);
        }
    }

    Object removeValue(Object fieldObject, boolean bypassFinal) {
        Object previousValue = this.getValue(fieldObject);
        if (this.field.getType().isPrimitive()) {
            this.setValue(fieldObject, (byte)0, bypassFinal);
        } else {
            this.setValue(fieldObject, null, bypassFinal);
            if (this.boundConfig != null) {
                this.boundConfig.clear();
            }
        }
        return previousValue;
    }

    Object getValue(Object fieldObject) {
        try {
            return this.converter.convertFromField(this.field.get(fieldObject));
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException("Failed to get field " + this.field, e);
        }
    }

    ObjectBinder.BoundConfig getUpdatedConfig(Object fieldObject) {
        this.boundConfig.object = this.getValue(fieldObject);
        return this.boundConfig;
    }

    public String toString() {
        return "FieldInfos{field=" + this.field + ", boundConfig=" + this.boundConfig + '}';
    }
}
