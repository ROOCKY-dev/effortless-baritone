/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.EnumGetMethod;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.AdvancedPath;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Conversion;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Converter;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.InvalidValueException;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Path;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.PreserveNotNull;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ReflectionException;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecClassInArray;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecDoubleInRange;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecEnum;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecFloatInRange;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecIntInRange;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecLongInRange;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecNotNull;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecStringInArray;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecStringInRange;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecValidator;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

final class AnnotationUtils {
    private AnnotationUtils() {
    }

    static boolean isEnum(Field annotatedElement) {
        return annotatedElement.getType().isEnum() || annotatedElement.isAnnotationPresent(PreserveNotNull.class);
    }

    static boolean hasPreserveNotNull(AnnotatedElement annotatedElement) {
        return annotatedElement.isAnnotationPresent(PreserveNotNull.class);
    }

    static boolean mustPreserve(Field field, Class<?> fieldClass) {
        return AnnotationUtils.hasPreserveNotNull(field) || AnnotationUtils.hasPreserveNotNull(fieldClass);
    }

    static Converter<Object, Object> getConverter(Field field) {
        Conversion conversion = field.getAnnotation(Conversion.class);
        if (conversion != null) {
            try {
                Constructor<Converter<?, ?>> constructor = conversion.value().getDeclaredConstructor(new Class[0]);
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                return constructor.newInstance(new Object[0]);
            }
            catch (ReflectiveOperationException ex) {
                throw new ReflectionException("Cannot create a converter for field " + field, ex);
            }
        }
        return null;
    }

    static List<String> getPath(Field field) {
        List<String> annotatedPath = AnnotationUtils.getPath((AnnotatedElement)field);
        return annotatedPath == null ? Collections.singletonList(field.getName()) : annotatedPath;
    }

    static List<String> getPath(AnnotatedElement annotatedElement) {
        Path path = annotatedElement.getDeclaredAnnotation(Path.class);
        if (path != null) {
            return StringUtils.split(path.value(), '.');
        }
        AdvancedPath advancedPath = annotatedElement.getDeclaredAnnotation(AdvancedPath.class);
        if (advancedPath != null) {
            return Arrays.asList(advancedPath.value());
        }
        return null;
    }

    static void checkField(Field field, Object value) {
        SpecValidator specValidator;
        SpecEnum specEnum;
        SpecNotNull specNotNull = field.getDeclaredAnnotation(SpecNotNull.class);
        if (specNotNull != null) {
            AnnotationUtils.checkNotNull(field, value);
            return;
        }
        SpecClassInArray specClassInArray = field.getDeclaredAnnotation(SpecClassInArray.class);
        if (specClassInArray != null) {
            AnnotationUtils.checkFieldSpec(field, value, specClassInArray);
            return;
        }
        SpecStringInArray specStringInArray = field.getDeclaredAnnotation(SpecStringInArray.class);
        if (specStringInArray != null) {
            AnnotationUtils.checkFieldSpec(field, value, specStringInArray);
            return;
        }
        SpecStringInRange specStringInRange = field.getDeclaredAnnotation(SpecStringInRange.class);
        if (specStringInRange != null) {
            AnnotationUtils.checkFieldSpec(field, value, specStringInRange);
            return;
        }
        SpecDoubleInRange specDoubleInRange = field.getDeclaredAnnotation(SpecDoubleInRange.class);
        if (specDoubleInRange != null) {
            AnnotationUtils.checkFieldSpec(field, value, specDoubleInRange);
            return;
        }
        SpecFloatInRange specFloatInRange = field.getDeclaredAnnotation(SpecFloatInRange.class);
        if (specFloatInRange != null) {
            AnnotationUtils.checkFieldSpec(field, value, specFloatInRange);
            return;
        }
        SpecLongInRange specLongInRange = field.getDeclaredAnnotation(SpecLongInRange.class);
        if (specLongInRange != null) {
            AnnotationUtils.checkFieldSpec(field, value, specLongInRange);
            return;
        }
        SpecIntInRange specIntInRange = field.getDeclaredAnnotation(SpecIntInRange.class);
        if (specIntInRange != null) {
            AnnotationUtils.checkFieldSpec(field, value, specIntInRange);
        }
        if ((specEnum = field.getDeclaredAnnotation(SpecEnum.class)) != null) {
            AnnotationUtils.checkFieldSpec(field, value, specEnum);
        }
        if ((specValidator = field.getDeclaredAnnotation(SpecValidator.class)) != null) {
            AnnotationUtils.checkFieldSpec(field, value, specValidator);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecValidator spec) {
        Predicate<Object> validatorInstance;
        try {
            Constructor<? extends Predicate<Object>> constructor = spec.value().getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            validatorInstance = constructor.newInstance(new Object[0]);
        }
        catch (ReflectiveOperationException ex) {
            throw new ReflectionException("Cannot create a converter for field " + field, ex);
        }
        if (!validatorInstance.test(value)) {
            throw new InvalidValueException("Invalid value \"%s\" for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecClassInArray spec) {
        AnnotationUtils.checkNotNull(field, value);
        Class<?> valueClass = value.getClass();
        if (spec.strict()) {
            for (Class<?> aClass : spec.value()) {
                if (!aClass.isAssignableFrom(valueClass)) continue;
                return;
            }
        } else {
            for (Class<?> aClass : spec.value()) {
                if (!aClass.equals(valueClass)) continue;
                return;
            }
        }
        throw new InvalidValueException("Invalid value \"%s\" for field %s: it doesn't conform to %s", value, field, spec);
    }

    private static void checkFieldSpec(Field field, Object value, SpecStringInRange spec) {
        AnnotationUtils.checkClass(field, value, String.class);
        String s = (String)value;
        if (s.compareTo(spec.min()) < 0 || s.compareTo(spec.max()) > 0) {
            throw new InvalidValueException("Invalid value \"%s\" for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecEnum spec) {
        EnumGetMethod m = spec.method();
        Class<?> fieldType = field.getType();
        if (!fieldType.isEnum()) {
            throw new InvalidValueException("Field %s is annotated with @SpecEnum but isn't of type enum", field);
        }
        Class<?> t = fieldType;
        if (!m.validate(value, t)) {
            throw new InvalidValueException("Invalid value \"%s\" for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecStringInArray spec) {
        AnnotationUtils.checkClass(field, value, String.class);
        String s = (String)value;
        if (spec.ignoreCase()) {
            for (String acceptable : spec.value()) {
                if (!s.equalsIgnoreCase(acceptable)) continue;
                return;
            }
        } else {
            for (String acceptable : spec.value()) {
                if (!s.equals(acceptable)) continue;
                return;
            }
        }
        throw new InvalidValueException("Invalid value \"%s\" for field %s: it doesn't conform to %s", value, field, spec);
    }

    private static void checkFieldSpec(Field field, Object value, SpecDoubleInRange spec) {
        AnnotationUtils.checkClass(field, value, Double.class);
        double d = (Double)value;
        if (d < spec.min() || d > spec.max()) {
            throw new InvalidValueException("Invalid value %f for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecFloatInRange spec) {
        AnnotationUtils.checkClass(field, value, Float.class);
        float d = ((Float)value).floatValue();
        if (d < spec.min() || d > spec.max()) {
            throw new InvalidValueException("Invalid value %f for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecLongInRange spec) {
        AnnotationUtils.checkClass(field, value, Long.class);
        long d = (Long)value;
        if (d < spec.min() || d > spec.max()) {
            throw new InvalidValueException("Invalid value %d for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkFieldSpec(Field field, Object value, SpecIntInRange spec) {
        AnnotationUtils.checkClass(field, value, Integer.class);
        int d = (Integer)value;
        if (d < spec.min() || d > spec.max()) {
            throw new InvalidValueException("Invalid value %d for field %s: it doesn't conform to %s", value, field, spec);
        }
    }

    private static void checkNotNull(Field field, Object value) {
        if (value == null) {
            throw new InvalidValueException("Invalid null value for field %s", field);
        }
    }

    private static void checkClass(Field field, Object value, Class<?> expectedClass) {
        AnnotationUtils.checkNotNull(field, value);
        Class<?> valueClass = value.getClass();
        if (valueClass != expectedClass) {
            throw new InvalidValueException("Invalid type %s for field %s, expected %s", valueClass, field, expectedClass);
        }
    }
}
