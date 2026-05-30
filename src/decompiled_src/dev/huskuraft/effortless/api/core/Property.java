/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.PropertyValue;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.Collection;
import java.util.Optional;

public interface Property
extends PlatformReference {
    public String getName();

    public String getName(PropertyValue var1);

    public Optional<PropertyValue> getValue(String var1);

    public Collection<PropertyValue> getPossibleValues();

    public Class<?> getValueClass();
}
