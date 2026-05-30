/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.EnumGetMethod;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.AnnotationUtils;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Converter;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ForceBreakdown;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.InvalidValueException;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ReflectionException;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecEnum;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class ObjectConverter {
    private final boolean bypassTransient;
    private final boolean bypassFinal;

    public ObjectConverter(boolean bypassTransient, boolean bypassFinal) {
        this.bypassTransient = bypassTransient;
        this.bypassFinal = bypassFinal;
    }

    public ObjectConverter() {
        this(false, true);
    }

    public void toConfig(Object o, Config destination) {
        Objects.requireNonNull(o, "The object must not be null.");
        Objects.requireNonNull(destination, "The config must not be null.");
        Class<?> clazz = o.getClass();
        List<String> annotatedPath = AnnotationUtils.getPath(clazz);
        if (annotatedPath != null) {
            destination = (Config)destination.getRaw(annotatedPath);
        }
        this.convertToConfig(o, clazz, destination);
    }

    public void toConfig(Class<?> clazz, Config destination) {
        Objects.requireNonNull(destination, "The config must not be null.");
        List<String> annotatedPath = AnnotationUtils.getPath(clazz);
        if (annotatedPath != null) {
            destination = (Config)destination.getRaw(annotatedPath);
        }
        this.convertToConfig(null, clazz, destination);
    }

    public <C extends Config> C toConfig(Object o, Supplier<C> destinationSupplier) {
        Config destination = (Config)destinationSupplier.get();
        this.toConfig(o, destination);
        return (C)destination;
    }

    public <C extends Config> C toConfig(Class<?> clazz, Supplier<C> destinationSupplier) {
        Config destination = (Config)destinationSupplier.get();
        this.toConfig(clazz, destination);
        return (C)destination;
    }

    public void toObject(UnmodifiableConfig config, Object destination) {
        Objects.requireNonNull(config, "The config must not be null.");
        Objects.requireNonNull(destination, "The object must not be null.");
        Class<?> clazz = destination.getClass();
        List<String> annotatedPath = AnnotationUtils.getPath(clazz);
        if (annotatedPath != null) {
            config = (UnmodifiableConfig)config.getRaw(annotatedPath);
        }
        this.convertToObject(config, destination, clazz);
    }

    public <O> O toObject(UnmodifiableConfig config, Supplier<O> destinationSupplier) {
        O destination = destinationSupplier.get();
        this.toObject(config, destination);
        return destination;
    }

    private void convertToConfig(Object object, Class<?> clazz, Config destination) {
        while (clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                Object value;
                int fieldModifiers = field.getModifiers();
                if (object == null && Modifier.isStatic(fieldModifiers) || !this.bypassTransient && Modifier.isTransient(fieldModifiers)) continue;
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    value = field.get(object);
                }
                catch (IllegalAccessException e) {
                    throw new ReflectionException("Unable to parse the field " + field, e);
                }
                AnnotationUtils.checkField(field, value);
                Converter<Object, Object> converter = AnnotationUtils.getConverter(field);
                if (converter != null) {
                    value = converter.convertFromField(value);
                }
                List<String> path = AnnotationUtils.getPath(field);
                ConfigFormat<?> format = destination.configFormat();
                if (value == null) {
                    destination.set(path, null);
                    continue;
                }
                Class<?> valueType = value.getClass();
                if (Enum.class.isAssignableFrom(valueType)) {
                    if (destination.configFormat().supportsType(Enum.class)) {
                        destination.set(path, value);
                        continue;
                    }
                    destination.set(path, (Object)value.toString());
                    continue;
                }
                if (field.isAnnotationPresent(ForceBreakdown.class) || !format.supportsType(valueType)) {
                    destination.set(path, value);
                    Config converted = destination.createSubConfig();
                    this.convertToConfig(value, valueType, converted);
                    destination.set(path, (Object)converted);
                    continue;
                }
                if (value instanceof Collection) {
                    Collection src = (Collection)value;
                    Class<?> bottomType = this.bottomElementType(src);
                    if (format.supportsType(bottomType)) {
                        destination.set(path, value);
                        continue;
                    }
                    ArrayList<Object> dst = new ArrayList<Object>(src.size());
                    this.convertObjectsToConfigs(src, bottomType, dst, destination);
                    destination.set(path, dst);
                    continue;
                }
                destination.set(path, value);
            }
            clazz = clazz.getSuperclass();
        }
    }

    private void convertToObject(UnmodifiableConfig config, Object object, Class<?> clazz) {
        while (clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                int fieldModifiers = field.getModifiers();
                if (object == null && Modifier.isStatic(fieldModifiers) || !this.bypassFinal && Modifier.isFinal(fieldModifiers)) continue;
                field.setAccessible(true);
                if (!this.bypassTransient && Modifier.isTransient(fieldModifiers)) continue;
                List<String> path = AnnotationUtils.getPath(field);
                Object value = config.get(path);
                Converter<Object, Object> converter = AnnotationUtils.getConverter(field);
                if (converter != null) {
                    value = converter.convertToField(value);
                }
                Class<?> fieldType = field.getType();
                try {
                    if (value instanceof UnmodifiableConfig && !fieldType.isAssignableFrom(value.getClass())) {
                        UnmodifiableConfig cfg = (UnmodifiableConfig)value;
                        Object fieldValue = field.get(object);
                        if (fieldValue == null) {
                            fieldValue = this.createInstance(fieldType);
                            field.set(object, fieldValue);
                            this.convertToObject(cfg, fieldValue, field.getType());
                            continue;
                        }
                        if (AnnotationUtils.mustPreserve(field, clazz)) continue;
                        this.convertToObject(cfg, fieldValue, field.getType());
                        continue;
                    }
                    if (value instanceof Collection && Collection.class.isAssignableFrom(fieldType)) {
                        Collection src = (Collection)value;
                        Class<?> srcBottomType = this.bottomElementType(src);
                        ParameterizedType genericType = (ParameterizedType)field.getGenericType();
                        List<Class<?>> dstTypes = this.elementTypes(genericType);
                        Class<?> dstBottomType = dstTypes.get(dstTypes.size() - 1);
                        if (srcBottomType == null || dstBottomType == null || dstBottomType.isAssignableFrom(srcBottomType)) {
                            AnnotationUtils.checkField(field, value);
                            field.set(object, value);
                            continue;
                        }
                        Collection dst = (ArrayList)field.get(object);
                        if (dst == null) {
                            dst = fieldType == ArrayList.class || fieldType.isInterface() || Modifier.isAbstract(fieldType.getModifiers()) ? new ArrayList(src.size()) : (Collection)this.createInstance(fieldType);
                            field.set(object, dst);
                        }
                        this.convertConfigsToObject(src, dst, dstTypes, 0);
                        AnnotationUtils.checkField(field, dst);
                        continue;
                    }
                    if (value == null && AnnotationUtils.mustPreserve(field, clazz)) {
                        AnnotationUtils.checkField(field, field.get(object));
                        continue;
                    }
                    AnnotationUtils.checkField(field, value);
                    if (field.getType().isEnum()) {
                        Class<?> enumType = field.getType();
                        SpecEnum specEnum = field.getAnnotation(SpecEnum.class);
                        EnumGetMethod method = specEnum == null ? EnumGetMethod.NAME_IGNORECASE : specEnum.method();
                        field.set(object, method.get(value, enumType));
                        continue;
                    }
                    field.set(object, value);
                }
                catch (ReflectiveOperationException ex) {
                    throw new ReflectionException("Unable to work with field " + field, ex);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private Class<?> bottomElementType(ParameterizedType genericType) {
        if (genericType != null && genericType.getActualTypeArguments().length > 0) {
            Type parameter = genericType.getActualTypeArguments()[0];
            if (parameter instanceof ParameterizedType) {
                ParameterizedType genericParameter = (ParameterizedType)parameter;
                Class paramClass = (Class)genericParameter.getRawType();
                if (paramClass.isAssignableFrom(Collection.class)) {
                    return this.bottomElementType(genericParameter);
                }
                return paramClass;
            }
            if (parameter instanceof Class) {
                return (Class)parameter;
            }
        }
        return null;
    }

    private void detectElementTypes(ParameterizedType genericType, List<Class<?>> storage) {
        if (genericType != null && genericType.getActualTypeArguments().length > 0) {
            Type parameter = genericType.getActualTypeArguments()[0];
            if (parameter instanceof ParameterizedType) {
                ParameterizedType genericParameter = (ParameterizedType)parameter;
                Class paramClass = (Class)genericParameter.getRawType();
                storage.add(paramClass);
                if (Collection.class.isAssignableFrom(paramClass)) {
                    this.detectElementTypes(genericParameter, storage);
                }
            } else if (parameter instanceof Class) {
                storage.add((Class)parameter);
            }
        }
    }

    private List<Class<?>> elementTypes(ParameterizedType genericType) {
        ArrayList storage = new ArrayList();
        this.detectElementTypes(genericType, storage);
        return storage;
    }

    private Class<?> bottomElementType(Collection<?> list) {
        for (Object elem : list) {
            if (elem instanceof Collection) {
                return this.bottomElementType((Collection)elem);
            }
            if (elem == null) continue;
            return elem.getClass();
        }
        return null;
    }

    private void convertConfigsToObject(Collection<?> src, Collection<Object> dst, List<Class<?>> dstElementTypes, int currentLevel) {
        Class<?> currentType = dstElementTypes.get(currentLevel);
        for (Object elem : src) {
            if (elem == null) {
                dst.add(null);
                continue;
            }
            if (elem instanceof Collection) {
                Collection subSrc = (Collection)elem;
                Collection<Object> subDst = currentType == ArrayList.class || currentType.isInterface() || Modifier.isAbstract(currentType.getModifiers()) ? new ArrayList<Object>() : (Collection)this.createInstance(currentType);
                this.convertConfigsToObject(subSrc, subDst, dstElementTypes, currentLevel + 1);
                dst.add(subDst);
                continue;
            }
            if (elem instanceof UnmodifiableConfig) {
                Object elementObj = this.createInstance(currentType);
                this.convertToObject((UnmodifiableConfig)elem, elementObj, currentType);
                dst.add(elementObj);
                continue;
            }
            String elemType = elem.getClass().toString();
            throw new InvalidValueException("Unexpected element of type " + elemType + " in collection of objects");
        }
    }

    private void convertObjectsToConfigs(Collection<?> src, Class<?> srcBottomType, Collection<Object> dst, Config parentConfig) {
        for (Object elem : src) {
            if (elem == null) {
                dst.add(null);
                continue;
            }
            if (srcBottomType.isAssignableFrom(elem.getClass())) {
                Config elementConfig = parentConfig.createSubConfig();
                this.convertToConfig(elem, elem.getClass(), elementConfig);
                dst.add(elementConfig);
                continue;
            }
            if (elem instanceof Collection) {
                ArrayList<Object> subList = new ArrayList<Object>();
                this.convertObjectsToConfigs((Collection)elem, srcBottomType, subList, parentConfig);
                subList.trimToSize();
                dst.add(subList);
                continue;
            }
            String elemType = elem.getClass().toString();
            throw new InvalidValueException("Unexpected element of type " + elemType + " in collection of " + srcBottomType);
        }
    }

    private <T> T createInstance(Class<T> tClass) {
        try {
            Constructor<T> ctor = tClass.getDeclaredConstructor(new Class[0]);
            if (!ctor.isAccessible()) {
                ctor.setAccessible(true);
            }
            return ctor.newInstance(new Object[0]);
        }
        catch (ReflectiveOperationException ex) {
            throw new ReflectionException("Unable to create an instance of " + tClass, ex);
        }
    }
}
