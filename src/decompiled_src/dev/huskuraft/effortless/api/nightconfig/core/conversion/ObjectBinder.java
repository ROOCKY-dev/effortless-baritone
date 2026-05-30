/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.EnumGetMethod;
import dev.huskuraft.effortless.api.nightconfig.core.InMemoryFormat;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.AnnotationUtils;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.Converter;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ReflectionException;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.SpecEnum;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingMap;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public final class ObjectBinder {
    private final boolean bypassTransient;
    private final boolean bypassFinal;

    public ObjectBinder(boolean bypassTransient, boolean bypassFinal) {
        this.bypassTransient = bypassTransient;
        this.bypassFinal = bypassFinal;
    }

    public ObjectBinder() {
        this(false, true);
    }

    public Config bind(Class<?> clazz) {
        return this.bind(clazz, InMemoryFormat.defaultInstance());
    }

    public Config bind(Class<?> clazz, ConfigFormat<?> configFormat) {
        return this.bind(null, clazz, configFormat);
    }

    public Config bind(Object object) {
        return this.bind(object, InMemoryFormat.defaultInstance());
    }

    public Config bind(Object object, ConfigFormat<?> configFormat) {
        return this.bind(object, object.getClass(), configFormat);
    }

    private Config bind(Object object, Class<?> clazz, ConfigFormat<?> configFormat) {
        BoundConfig boundConfig = this.createBoundConfig(object, clazz, configFormat);
        List<String> annotatedPath = AnnotationUtils.getPath(clazz);
        if (annotatedPath != null) {
            Object parentConfig = configFormat.createConfig();
            parentConfig.set(annotatedPath, (Object)boundConfig);
            return parentConfig;
        }
        return boundConfig;
    }

    private BoundConfig createBoundConfig(Object object, Class<?> clazz, ConfigFormat<?> configFormat) {
        BoundConfig boundConfig = new BoundConfig(object, configFormat, this.bypassFinal);
        for (Field field : clazz.getDeclaredFields()) {
            FieldInfos fieldInfos;
            int fieldModifiers = field.getModifiers();
            if (object == null && Modifier.isStatic(fieldModifiers) || !this.bypassTransient && Modifier.isTransient(fieldModifiers)) continue;
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            List<String> path = AnnotationUtils.getPath(field);
            Converter<Object, Object> converter = AnnotationUtils.getConverter(field);
            boolean isEnum = Enum.class.isAssignableFrom(field.getType());
            if (converter == null) {
                if (isEnum) {
                    SpecEnum spec = field.getAnnotation(SpecEnum.class);
                    EnumGetMethod method = spec == null ? EnumGetMethod.NAME_IGNORECASE : spec.method();
                    converter = new EnumValueConverter(field.getType(), method);
                } else {
                    converter = NoOpConverter.INSTANCE;
                }
            }
            try {
                Object value = converter.convertFromField(field.get(object));
                if (value == null || isEnum || configFormat.supportsType(value.getClass())) {
                    fieldInfos = new FieldInfos(field, null, converter);
                } else {
                    BoundConfig subConfig = this.createBoundConfig(value, field.getType(), configFormat);
                    fieldInfos = new FieldInfos(field, subConfig, converter);
                }
            }
            catch (IllegalAccessException e) {
                throw new ReflectionException("Failed to bind field " + field, e);
            }
            boundConfig.registerField(fieldInfos, path);
        }
        return boundConfig;
    }

    private static final class BoundConfig
    implements Config {
        private Object object;
        private final Map<String, Object> dataMap;
        private final ConfigFormat<?> configFormat;
        private final boolean bypassFinal;

        private BoundConfig(Object object, Map<String, Object> dataMap, ConfigFormat<?> configFormat, boolean bypassFinal) {
            this.object = object;
            this.dataMap = dataMap;
            this.configFormat = configFormat;
            this.bypassFinal = bypassFinal;
        }

        private BoundConfig(Object object, ConfigFormat<?> configFormat, boolean bypassFinal) {
            this(object, new HashMap<String, Object>(), configFormat, bypassFinal);
        }

        private void registerField(FieldInfos fieldInfos, List<String> path) {
            int lastIndex = path.size() - 1;
            Map<String, Object> currentMap = this.dataMap;
            for (String currentKey : path.subList(0, lastIndex)) {
                BoundConfig config;
                Object currentValue = currentMap.get(currentKey);
                if (currentValue == null) {
                    config = new BoundConfig(null, new HashMap<String, Object>(1), this.configFormat, this.bypassFinal);
                    currentMap.put(currentKey, config);
                } else {
                    if (!(currentValue instanceof BoundConfig)) {
                        throw new IllegalArgumentException("Cannot add an element to an intermediary value of type: " + currentValue.getClass());
                    }
                    config = (BoundConfig)currentValue;
                }
                currentMap = config.dataMap;
            }
            String lastKey = path.get(lastIndex);
            currentMap.put(lastKey, fieldInfos);
        }

        private BoundSearchResult searchInfosOrConfig(List<String> path) {
            int lastIndex = path.size() - 1;
            BoundConfig currentConfig = this;
            for (String key : path.subList(0, lastIndex)) {
                Object v = currentConfig.dataMap.get(key);
                if (v == null) {
                    return null;
                }
                if (v instanceof BoundConfig) {
                    currentConfig = (BoundConfig)v;
                    continue;
                }
                FieldInfos fieldInfos = (FieldInfos)v;
                currentConfig = fieldInfos.getUpdatedConfig(currentConfig.object);
            }
            String lastKey = path.get(lastIndex);
            Object data = currentConfig.dataMap.get(lastKey);
            return new BoundSearchResult(currentConfig, data);
        }

        @Override
        public <T> T getRaw(List<String> path) {
            BoundSearchResult searchResult = this.searchInfosOrConfig(path);
            if (searchResult == null) {
                return null;
            }
            if (searchResult.hasSubConfig()) {
                return (T)searchResult.subConfig;
            }
            return (T)searchResult.fieldInfos.getValue(searchResult.parentConfig.object);
        }

        @Override
        public boolean contains(List<String> path) {
            return this.searchInfosOrConfig(path) != null;
        }

        @Override
        public <T> T set(List<String> path, Object value) {
            BoundSearchResult searchResult = this.searchInfosOrConfig(path);
            if (searchResult == null) {
                throw new UnsupportedOperationException("Cannot add elements to a bound config");
            }
            if (searchResult.hasFieldInfos()) {
                return (T)searchResult.fieldInfos.setValue(searchResult.parentConfig.object, value, this.bypassFinal);
            }
            throw new UnsupportedOperationException("Cannot modify non-field elements of a bound config");
        }

        @Override
        public boolean add(List<String> path, Object value) {
            throw new UnsupportedOperationException("Cannot add elements to a bound config");
        }

        @Override
        public <T> T remove(List<String> path) {
            BoundSearchResult searchResult = this.searchInfosOrConfig(path);
            if (searchResult == null) {
                return null;
            }
            if (searchResult.hasFieldInfos()) {
                return (T)searchResult.fieldInfos.removeValue(searchResult.parentConfig.object, this.bypassFinal);
            }
            Config copy = Config.copy(searchResult.subConfig);
            searchResult.subConfig.clear();
            return (T)copy;
        }

        @Override
        public void clear() {
            for (Map.Entry<String, Object> dataEntry : this.dataMap.entrySet()) {
                Object value = dataEntry.getValue();
                if (value instanceof FieldInfos) {
                    ((FieldInfos)value).removeValue(this.object, this.bypassFinal);
                    continue;
                }
                if (!(value instanceof BoundConfig)) continue;
                ((BoundConfig)value).clear();
            }
            this.dataMap.clear();
        }

        @Override
        public ConfigFormat<?> configFormat() {
            return this.configFormat;
        }

        @Override
        public Config createSubConfig() {
            return new BoundConfig(null, new HashMap<String, Object>(1), this.configFormat, this.bypassFinal);
        }

        @Override
        public Map<String, Object> valueMap() {
            Function<Object, Object> readConversion = o -> {
                if (o instanceof FieldInfos) {
                    FieldInfos fieldInfos = (FieldInfos)o;
                    if (fieldInfos.boundConfig != null) {
                        return fieldInfos.getUpdatedConfig(this.object);
                    }
                    return fieldInfos.getValue(this.object);
                }
                return o;
            };
            return new TransformingMap<String, Object, Object>(this.dataMap, readConversion, o -> o, o -> o);
        }

        @Override
        public Set<? extends Config.Entry> entrySet() {
            Function<Map.Entry, Config.Entry> readTransfo = entry -> new Config.Entry(){
                final /* synthetic */ Map.Entry val$entry;
                {
                    this.val$entry = entry;
                }

                @Override
                public <T> T setValue(Object value) {
                    return this.set((String)this.val$entry.getKey(), value);
                }

                @Override
                public String getKey() {
                    return (String)this.val$entry.getKey();
                }

                @Override
                public <T> T getRawValue() {
                    return (T)this.val$entry.getValue();
                }
            };
            return new TransformingSet<Map.Entry, Config.Entry>(this.dataMap.entrySet(), readTransfo, o -> null, o -> o);
        }

        @Override
        public int size() {
            return this.dataMap.size();
        }

        public String toString() {
            return "BoundConfig{object=" + this.object + ", dataMap=" + this.dataMap + '}';
        }
    }

    private static final class EnumValueConverter<T extends Enum<T>>
    implements Converter<T, Object> {
        private final Class<T> enumType;
        private final EnumGetMethod method;

        EnumValueConverter(Class<T> enumType, EnumGetMethod method) {
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

    private static final class NoOpConverter
    implements Converter<Object, Object> {
        static final NoOpConverter INSTANCE = new NoOpConverter();

        private NoOpConverter() {
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

    private static final class FieldInfos {
        final Field field;
        final BoundConfig boundConfig;
        final Converter<Object, Object> converter;

        FieldInfos(Field field, BoundConfig boundConfig, Converter<Object, Object> converter) {
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

        BoundConfig getUpdatedConfig(Object fieldObject) {
            this.boundConfig.object = this.getValue(fieldObject);
            return this.boundConfig;
        }

        public String toString() {
            return "FieldInfos{field=" + this.field + ", boundConfig=" + this.boundConfig + '}';
        }
    }

    private static final class BoundSearchResult {
        final BoundConfig parentConfig;
        final FieldInfos fieldInfos;
        final BoundConfig subConfig;

        BoundSearchResult(BoundConfig parentConfig, Object data) {
            this.parentConfig = parentConfig;
            if (data instanceof FieldInfos) {
                this.fieldInfos = (FieldInfos)data;
                this.subConfig = this.fieldInfos.boundConfig == null ? null : this.fieldInfos.getUpdatedConfig(parentConfig.object);
            } else {
                this.fieldInfos = null;
                this.subConfig = (BoundConfig)data;
            }
        }

        boolean hasFieldInfos() {
            return this.fieldInfos != null;
        }

        boolean hasSubConfig() {
            return this.subConfig != null;
        }
    }
}
