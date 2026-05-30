/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Property;
import dev.huskuraft.effortless.api.core.PropertyHolder;
import dev.huskuraft.effortless.api.core.PropertyValue;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface StateHolder
extends PlatformReference {
    public Map<Property, PropertyValue> getPropertiesMap();

    default public List<PropertyHolder> getProperties() {
        return this.getPropertiesMap().entrySet().stream().map(entry -> new PropertyHolder((Property)entry.getKey(), (PropertyValue)entry.getValue())).collect(Collectors.toList());
    }
}
