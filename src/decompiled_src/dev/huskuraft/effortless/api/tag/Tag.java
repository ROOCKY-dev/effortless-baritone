/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.tag.ListTag;
import dev.huskuraft.effortless.api.tag.NumericTag;
import dev.huskuraft.effortless.api.tag.RecordTag;
import dev.huskuraft.effortless.api.tag.StringTag;

public interface Tag
extends PlatformReference {
    public static final byte TAG_END = 0;
    public static final byte TAG_BYTE = 1;
    public static final byte TAG_SHORT = 2;
    public static final byte TAG_INT = 3;
    public static final byte TAG_LONG = 4;
    public static final byte TAG_FLOAT = 5;
    public static final byte TAG_DOUBLE = 6;
    public static final byte TAG_BYTE_ARRAY = 7;
    public static final byte TAG_STRING = 8;
    public static final byte TAG_LIST = 9;
    public static final byte TAG_COMPOUND = 10;
    public static final byte TAG_INT_ARRAY = 11;
    public static final byte TAG_LONG_ARRAY = 12;
    public static final byte TAG_ANY_NUMERIC = 99;

    default public RecordTag asRecord() {
        return (RecordTag)this;
    }

    default public ListTag asList() {
        return (ListTag)this;
    }

    default public StringTag asString() {
        return (StringTag)this;
    }

    default public NumericTag asPrimitive() {
        return (NumericTag)this;
    }

    public byte getId();

    public String getAsString();
}
