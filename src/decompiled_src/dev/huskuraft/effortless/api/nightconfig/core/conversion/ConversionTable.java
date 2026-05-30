/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConvertedCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConvertedCommentedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConvertedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.conversion.ConvertedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.CommentedFileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.file.FileConfig;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingMap;
import dev.huskuraft.effortless.api.nightconfig.core.utils.TransformingSet;
import dev.huskuraft.effortless.api.nightconfig.core.utils.UnmodifiableConfigWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ConversionTable
implements Cloneable {
    private final Map<Class<?>, Function<?, Object>> conversionMap;

    public ConversionTable() {
        this.conversionMap = new HashMap();
    }

    private ConversionTable(ConversionTable toCopy) {
        this.conversionMap = new HashMap(toCopy.conversionMap);
    }

    public <T> void put(Class<T> classToConvert, Function<? super T, Object> conversionFunction) {
        this.conversionMap.put(classToConvert, conversionFunction);
    }

    public void remove(Class<?> classToConvert) {
        this.conversionMap.remove(classToConvert);
    }

    public boolean contains(Class<?> classToConvert) {
        return this.conversionMap.containsKey(classToConvert);
    }

    public Object convert(Object value) {
        Function<Object, Object> conversionFunction = this.getConversionFunction(value);
        if (conversionFunction == null) {
            return value;
        }
        return conversionFunction.apply(value);
    }

    public void convertShallow(Config config) {
        for (Map.Entry<String, Object> configEntry : config.valueMap().entrySet()) {
            Object value = configEntry.getValue();
            Function<Object, Object> conversionFunction = this.getConversionFunction(value);
            if (conversionFunction == null) continue;
            configEntry.setValue(conversionFunction.apply(value));
        }
    }

    public void convertDeep(Config config) {
        for (Map.Entry<String, Object> configEntry : config.valueMap().entrySet()) {
            Object value = configEntry.getValue();
            if (value instanceof Config) {
                this.convertDeep(config);
                continue;
            }
            Function<Object, Object> conversionFunction = this.getConversionFunction(value);
            if (conversionFunction == null) continue;
            configEntry.setValue(conversionFunction.apply(value));
        }
    }

    private Function<Object, Object> getConversionFunction(Object value) {
        if (value == null) {
            return this.conversionMap.get(null);
        }
        Class<?> clazz = value.getClass();
        Function<Object, Object> conversionFunction = this.conversionMap.get(clazz);
        while (conversionFunction == null && (clazz = clazz.getSuperclass()) != null) {
            conversionFunction = this.conversionMap.get(clazz);
        }
        return conversionFunction;
    }

    public ConversionTable chainThen(ConversionTable after) {
        ConversionTable result = new ConversionTable(this);
        for (Map.Entry<Class<?>, Function<?, Object>> entry : result.conversionMap.entrySet()) {
            entry.setValue(entry.getValue().andThen(after::convert));
        }
        for (Map.Entry<Class<?>, Function<?, Object>> entry : after.conversionMap.entrySet()) {
            result.conversionMap.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public UnmodifiableConfig wrap(UnmodifiableConfig config) {
        return new UnmodifiableConfigWrapper<UnmodifiableConfig>(config){

            @Override
            public <T> T getRaw(List<String> path) {
                return (T)ConversionTable.this.convert(this.config.getRaw(path));
            }

            @Override
            public Map<String, Object> valueMap() {
                return new TransformingMap<String, Object, Object>(this.config.valueMap(), v -> ConversionTable.this.convert(v), v -> v, v -> v);
            }

            @Override
            public Set<? extends UnmodifiableConfig.Entry> entrySet() {
                Function<UnmodifiableConfig.Entry, UnmodifiableConfig.Entry> readTransfo = entry -> new UnmodifiableConfig.Entry(){
                    final /* synthetic */ UnmodifiableConfig.Entry val$entry;
                    {
                        this.val$entry = entry;
                    }

                    @Override
                    public String getKey() {
                        return this.val$entry.getKey();
                    }

                    @Override
                    public <T> T getRawValue() {
                        return (T)ConversionTable.this.convert(this.val$entry.getRawValue());
                    }
                };
                return new TransformingSet<UnmodifiableConfig.Entry, UnmodifiableConfig.Entry>(this.config.entrySet(), readTransfo, o -> null, e -> e);
            }

            @Override
            public ConfigFormat<?> configFormat() {
                return this.config.configFormat();
            }
        };
    }

    public Config wrapRead(Config config) {
        return new ConvertedConfig(config, this::convert, v -> v, config.configFormat()::supportsType);
    }

    public CommentedConfig wrapRead(CommentedConfig config) {
        return new ConvertedCommentedConfig(config, this::convert, v -> v, config.configFormat()::supportsType);
    }

    public FileConfig wrapRead(FileConfig config) {
        return new ConvertedFileConfig(config, this::convert, v -> v, config.configFormat()::supportsType);
    }

    public CommentedFileConfig wrapRead(CommentedFileConfig config) {
        return new ConvertedCommentedFileConfig(config, this::convert, v -> v, config.configFormat()::supportsType);
    }

    public Config wrapWrite(Config config, Predicate<Class<?>> supportValueTypePredicate) {
        return new ConvertedConfig(config, v -> v, this::convert, supportValueTypePredicate);
    }

    public CommentedConfig wrapWrite(CommentedConfig config, Predicate<Class<?>> supportValueTypePredicate) {
        return new ConvertedCommentedConfig(config, v -> v, this::convert, supportValueTypePredicate);
    }

    public FileConfig wrapWrite(FileConfig config, Predicate<Class<?>> supportValueTypePredicate) {
        return new ConvertedFileConfig(config, v -> v, this::convert, supportValueTypePredicate);
    }

    public CommentedFileConfig wrapWrite(CommentedFileConfig config, Predicate<Class<?>> supportValueTypePredicate) {
        return new ConvertedCommentedFileConfig(config, v -> v, this::convert, supportValueTypePredicate);
    }

    public ConversionTable clone() {
        return new ConversionTable(this);
    }

    public String toString() {
        return "ConversionTable: " + this.conversionMap;
    }
}
