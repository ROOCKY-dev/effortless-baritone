/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigSpec;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

private static final class ConfigSpec.ValueSpec {
    private final Supplier<?> defaultValueSupplier;
    private final Predicate<Object> validator;

    private ConfigSpec.ValueSpec(Object defaultValue, Predicate<Object> validator) {
        this(new ConfigSpec.DumbSupplier(Objects.requireNonNull(defaultValue, "The default value must not be null."), null), validator);
    }

    private ConfigSpec.ValueSpec(Supplier<?> defaultValueSupplier, Predicate<Object> validator) {
        this.defaultValueSupplier = Objects.requireNonNull(defaultValueSupplier, "The supplier of the default value must not be null.");
        this.validator = Objects.requireNonNull(validator, "The validator must not be null.");
    }

    static /* synthetic */ Predicate access$300(ConfigSpec.ValueSpec x0) {
        return x0.validator;
    }

    static /* synthetic */ Supplier access$400(ConfigSpec.ValueSpec x0) {
        return x0.defaultValueSupplier;
    }
}
