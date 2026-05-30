/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.tag.TagDecoder;
import dev.huskuraft.effortless.api.tag.TagEncoder;

public interface TagSerializer<T>
extends TagDecoder<T>,
TagEncoder<T> {
    @Override
    default public T validate(T value) {
        return value;
    }
}
