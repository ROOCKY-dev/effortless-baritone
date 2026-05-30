/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.tag.Tag;

public interface TagDecoder<T> {
    public T decode(Tag var1);

    default public T validate(T value) {
        return value;
    }

    default public T decode(Tag tag, boolean validate) {
        return validate ? this.validate(this.decode(tag)) : this.decode(tag);
    }
}
