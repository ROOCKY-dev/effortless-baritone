/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.EnumGetMethod;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConfigSpec {
    protected final Config storage;

    public ConfigSpec() {
        this(Config.inMemoryUniversal());
    }

    ConfigSpec(Config storage) {
        this.storage = storage;
    }

    public void define(String path, Object defaultValue) {
        this.define(StringUtils.split(path, '.'), defaultValue);
    }

    public void define(List<String> path, Object defaultValue) {
        this.define(path, defaultValue, (Object o) -> o != null && defaultValue.getClass().isAssignableFrom(o.getClass()));
    }

    public void define(String path, Object defaultValue, Predicate<Object> validator) {
        this.define(StringUtils.split(path, '.'), defaultValue, validator);
    }

    public void define(String path, Supplier<?> defaultValueSupplier, Predicate<Object> validator) {
        this.define(StringUtils.split(path, '.'), defaultValueSupplier, validator);
    }

    public void define(List<String> path, Object defaultValue, Predicate<Object> validator) {
        this.storage.set(path, (Object)new ValueSpec(defaultValue, validator));
    }

    public void define(List<String> path, Supplier<?> defaultValueSupplier, Predicate<Object> validator) {
        this.storage.set(path, (Object)new ValueSpec(defaultValueSupplier, validator));
    }

    public <V> void defineOfClass(String path, V defaultValue, Class<? super V> acceptableValueClass) {
        this.defineOfClass(StringUtils.split(path, '.'), (Supplier<V>)new DumbSupplier(defaultValue), acceptableValueClass);
    }

    public <V> void defineOfClass(String path, Supplier<V> defaultValueSupplier, Class<? super V> acceptableValueClass) {
        this.defineOfClass(StringUtils.split(path, '.'), defaultValueSupplier, acceptableValueClass);
    }

    public <V> void defineOfClass(List<String> path, V defaultValue, Class<? super V> acceptableValueClass) {
        this.defineOfClass(path, (Supplier<V>)new DumbSupplier(defaultValue), acceptableValueClass);
    }

    public <V> void defineOfClass(List<String> path, Supplier<V> defaultValueSupplier, Class<? super V> acceptableValueClass) {
        this.define(path, defaultValueSupplier, (Object o) -> o != null && acceptableValueClass.isAssignableFrom(o.getClass()));
    }

    public void defineInList(String path, Object defaultValue, Collection<?> acceptableValues) {
        this.defineInList(StringUtils.split(path, '.'), defaultValue, acceptableValues);
    }

    public void defineInList(String path, Supplier<?> defaultValueSupplier, Collection<?> acceptableValues) {
        this.defineInList(StringUtils.split(path, '.'), defaultValueSupplier, acceptableValues);
    }

    public void defineInList(List<String> path, Object defaultValue, Collection<?> acceptableValues) {
        this.defineInList(path, new DumbSupplier(defaultValue), acceptableValues);
    }

    public void defineInList(List<String> path, Supplier<?> defaultValueSupplier, Collection<?> acceptableValues) {
        this.define(path, defaultValueSupplier, acceptableValues::contains);
    }

    public <V extends Comparable<? super V>> void defineInRange(String path, V defaultValue, V min, V max) {
        this.defineInRange(StringUtils.split(path, '.'), defaultValue, min, max);
    }

    public <V extends Comparable<? super V>> void defineInRange(String path, Supplier<V> defaultValueSupplier, V min, V max) {
        this.defineInRange(StringUtils.split(path, '.'), defaultValueSupplier, min, max);
    }

    public <V extends Comparable<? super V>> void defineInRange(List<String> path, V defaultValue, V min, V max) {
        this.defineInRange(path, new DumbSupplier(defaultValue), min, max);
    }

    public <V extends Comparable<? super V>> void defineInRange(List<String> path, Supplier<V> defaultValueSupplier, V min, V max) {
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }
        this.define(path, defaultValueSupplier, (Object o) -> {
            if (!(o instanceof Comparable)) {
                return false;
            }
            Comparable c = (Comparable)o;
            try {
                return c.compareTo(min) >= 0 && c.compareTo(max) <= 0;
            }
            catch (ClassCastException ex) {
                return false;
            }
        });
    }

    public void defineList(String path, List<?> defaultValue, Predicate<Object> elementValidator) {
        this.defineList(StringUtils.split(path, '.'), defaultValue, elementValidator);
    }

    public void defineList(String path, Supplier<List<?>> defaultValueSupplier, Predicate<Object> elementValidator) {
        this.defineList(StringUtils.split(path, '.'), defaultValueSupplier, elementValidator);
    }

    public void defineList(List<String> path, List<?> defaultValue, Predicate<Object> elementValidator) {
        this.defineList(path, new DumbSupplier(defaultValue), elementValidator);
    }

    public void defineList(List<String> path, Supplier<List<?>> defaultValueSupplier, Predicate<Object> elementValidator) {
        this.define(path, defaultValueSupplier, (Object o) -> {
            if (!(o instanceof List)) {
                return false;
            }
            List list = (List)o;
            for (Object element : list) {
                if (elementValidator.test(element)) continue;
                return false;
            }
            return true;
        });
    }

    public <T extends Enum<T>> void defineEnum(String path, T defaultValue, EnumGetMethod method) {
        this.defineEnum(StringUtils.split(path, '.'), defaultValue, method);
    }

    public <T extends Enum<T>> void defineEnum(List<String> path, T defaultValue, EnumGetMethod method) {
        this.defineEnum(path, defaultValue.getDeclaringClass(), method, new DumbSupplier(defaultValue));
    }

    public <T extends Enum<T>> void defineEnum(String path, Class<T> enumType, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        this.defineEnum(StringUtils.split(path, '.'), enumType, method, defaultValueSupplier);
    }

    public <T extends Enum<T>> void defineEnum(List<String> path, Class<T> enumType, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        this.define(path, defaultValueSupplier, (Object o) -> method.validate(o, enumType));
    }

    public <T extends Enum<T>> void defineRestrictedEnum(String path, T defaultValue, Collection<T> acceptableValues, EnumGetMethod method) {
        this.defineRestrictedEnum(StringUtils.split(path, '.'), defaultValue, acceptableValues, method);
    }

    public <T extends Enum<T>> void defineRestrictedEnum(List<String> path, T defaultValue, Collection<T> acceptableValues, EnumGetMethod method) {
        this.defineRestrictedEnum(path, defaultValue.getDeclaringClass(), acceptableValues, method, new DumbSupplier(defaultValue));
    }

    public <T extends Enum<T>> void defineRestrictedEnum(String path, Class<T> enumType, Collection<T> acceptableValues, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        this.defineRestrictedEnum(StringUtils.split(path, '.'), enumType, acceptableValues, method, defaultValueSupplier);
    }

    public <T extends Enum<T>> void defineRestrictedEnum(List<String> path, Class<T> enumType, Collection<T> acceptableValues, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        this.define(path, defaultValueSupplier, (Object o) -> method.validate(o, enumType) && acceptableValues.contains(method.get(o, enumType)));
    }

    public void undefine(String path) {
        this.undefine(StringUtils.split(path, '.'));
    }

    public void undefine(List<String> path) {
        this.storage.remove(path);
    }

    public boolean isDefined(String path) {
        return this.isDefined(StringUtils.split(path, '.'));
    }

    public boolean isDefined(List<String> path) {
        return this.storage.contains(path);
    }

    public boolean isCorrect(String path, Object value) {
        return this.isCorrect(StringUtils.split(path, '.'), value);
    }

    public boolean isCorrect(List<String> path, Object value) {
        ValueSpec spec = (ValueSpec)this.storage.getRaw(path);
        return spec.validator.test(value);
    }

    public boolean isCorrect(Config config) {
        return this.isCorrect(config.valueMap(), this.storage.valueMap());
    }

    private boolean isCorrect(Map<String, Object> configMap, Map<String, Object> specMap) {
        Object specValue;
        String key;
        for (Map.Entry<String, Object> specEntry : specMap.entrySet()) {
            key = specEntry.getKey();
            specValue = specEntry.getValue();
            Object configValue = configMap.get(key);
            if (configValue == null) {
                return false;
            }
            if (specValue instanceof Config) {
                if (!(configValue instanceof Config)) {
                    return false;
                }
                if (this.isCorrect(((Config)configValue).valueMap(), ((Config)specValue).valueMap())) continue;
                return false;
            }
            ValueSpec valueSpec = (ValueSpec)specValue;
            if (valueSpec.validator.test(configValue)) continue;
            return false;
        }
        for (Map.Entry<String, Object> configEntry : configMap.entrySet()) {
            key = configEntry.getKey();
            specValue = specMap.get(key);
            if (specValue != null) continue;
            return false;
        }
        return true;
    }

    public Object correct(String path, Object value) {
        return this.correct(StringUtils.split(path, '.'), value);
    }

    public Object correct(List<String> path, Object value) {
        ValueSpec spec = (ValueSpec)this.storage.getRaw(path);
        return spec.validator.test(value) ? value : spec.defaultValueSupplier.get();
    }

    public int correct(Config config) {
        return this.correct(config, (CorrectionAction action, List<String> path, Object incorrectValue, Object correctedValue) -> {});
    }

    public int correct(Config config, CorrectionListener listener) {
        return this.correct(config.valueMap(), this.storage.valueMap(), new ArrayList<String>(), listener, config::createSubConfig);
    }

    private int correct(Map<String, Object> configMap, Map<String, Object> specMap, List<String> parentPath, CorrectionListener listener, Supplier<Config> subConfigSupplier) {
        String key;
        int count = 0;
        for (Map.Entry<String, Object> specEntry : specMap.entrySet()) {
            key = specEntry.getKey();
            Object specValue = specEntry.getValue();
            Object configValue = configMap.get(key);
            if (specValue instanceof Config) {
                if (!(configValue instanceof Config)) {
                    Config newValue = subConfigSupplier.get();
                    configMap.put(key, newValue);
                    CorrectionAction correctionAction = configValue == null ? CorrectionAction.ADD : CorrectionAction.REPLACE;
                    this.handleCorrection(parentPath, key, configValue, newValue, listener, correctionAction);
                    ++count;
                    configValue = newValue;
                }
                parentPath.add(key);
                Map<String, Object> configValueMap = ((Config)configValue).valueMap();
                Map<String, Object> specValueMap = ((Config)specValue).valueMap();
                count += this.correct(configValueMap, specValueMap, parentPath, listener, subConfigSupplier);
                parentPath.remove(parentPath.size() - 1);
                continue;
            }
            ValueSpec valueSpec = (ValueSpec)specValue;
            if (valueSpec.validator.test(configValue)) continue;
            Object newValue = valueSpec.defaultValueSupplier.get();
            configMap.put(key, newValue);
            CorrectionAction correctionAction = configValue == null ? CorrectionAction.ADD : CorrectionAction.REPLACE;
            this.handleCorrection(parentPath, key, configValue, newValue, listener, correctionAction);
            ++count;
        }
        Iterator<Map.Entry<String, Object>> it = configMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> configEntry = it.next();
            key = configEntry.getKey();
            Object configValue = configEntry.getValue();
            Object specValue = specMap.get(key);
            if (specValue != null) continue;
            it.remove();
            this.handleCorrection(parentPath, key, configValue, null, listener, CorrectionAction.REMOVE);
            ++count;
        }
        return count;
    }

    private void handleCorrection(List<String> parentPath, String key, Object value, Object newValue, CorrectionListener listener, CorrectionAction action) {
        parentPath.add(key);
        List<String> valuePath = Collections.unmodifiableList(parentPath);
        listener.onCorrect(action, valuePath, value, newValue);
        parentPath.remove(parentPath.size() - 1);
    }

    private static final class ValueSpec {
        private final Supplier<?> defaultValueSupplier;
        private final Predicate<Object> validator;

        private ValueSpec(Object defaultValue, Predicate<Object> validator) {
            this(new DumbSupplier(Objects.requireNonNull(defaultValue, "The default value must not be null.")), validator);
        }

        private ValueSpec(Supplier<?> defaultValueSupplier, Predicate<Object> validator) {
            this.defaultValueSupplier = Objects.requireNonNull(defaultValueSupplier, "The supplier of the default value must not be null.");
            this.validator = Objects.requireNonNull(validator, "The validator must not be null.");
        }
    }

    private static final class DumbSupplier<T>
    implements Supplier<T> {
        private final T value;

        private DumbSupplier(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return this.value;
        }
    }

    @FunctionalInterface
    public static interface CorrectionListener {
        public void onCorrect(CorrectionAction var1, List<String> var2, Object var3, Object var4);
    }

    public static enum CorrectionAction {
        ADD,
        REPLACE,
        REMOVE;

    }
}
