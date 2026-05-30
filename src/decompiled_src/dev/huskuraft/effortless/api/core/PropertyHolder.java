/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Property;
import dev.huskuraft.effortless.api.core.PropertyValue;

public record PropertyHolder(Property property, PropertyValue value) {
    public String getAsString() {
        return this.property.getName() + "=" + this.property.getName(this.value);
    }
}
