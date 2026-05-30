/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ConvertedFormat<C extends Config, F extends ConfigFormat<C>>
implements ConfigFormat<C> {
    private final F initialFormat;
    private final Predicate<Class<?>> supportPredicate;

    public ConvertedFormat(F initialFormat, Predicate<Class<?>> supportPredicate) {
        this.initialFormat = initialFormat;
        this.supportPredicate = supportPredicate;
    }

    @Override
    public ConfigWriter createWriter() {
        return this.initialFormat.createWriter();
    }

    @Override
    public ConfigParser<C> createParser() {
        return this.initialFormat.createParser();
    }

    @Override
    public C createConfig() {
        return this.initialFormat.createConfig();
    }

    @Override
    public C createConcurrentConfig() {
        return this.initialFormat.createConcurrentConfig();
    }

    @Override
    public C createConfig(Supplier<Map<String, Object>> mapCreator) {
        return this.initialFormat.createConfig(mapCreator);
    }

    @Override
    public boolean supportsComments() {
        return this.initialFormat.supportsComments();
    }

    @Override
    public boolean supportsType(Class<?> type) {
        return this.supportPredicate.test(type);
    }
}
