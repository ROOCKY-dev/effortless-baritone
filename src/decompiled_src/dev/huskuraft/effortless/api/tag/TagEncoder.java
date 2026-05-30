/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.tag.Tag;

public interface TagEncoder<T> {
    public Tag encode(T var1);

    default public T validate(T value) {
        return value;
    }

    default public Tag encode(T t, boolean validate) {
        return this.encode(validate ? this.validate(t) : t);
    }
}
