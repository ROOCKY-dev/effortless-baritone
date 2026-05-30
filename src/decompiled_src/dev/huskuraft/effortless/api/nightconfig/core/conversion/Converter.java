/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

public interface Converter<FieldType, ConfigValueType> {
    public FieldType convertToField(ConfigValueType var1);

    public ConfigValueType convertFromField(FieldType var1);
}
