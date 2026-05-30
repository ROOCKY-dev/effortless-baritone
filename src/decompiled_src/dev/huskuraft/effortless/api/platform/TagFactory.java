/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.tag.ListTag;
import dev.huskuraft.effortless.api.tag.NumericTag;
import dev.huskuraft.effortless.api.tag.RecordTag;
import dev.huskuraft.effortless.api.tag.StringTag;

public interface TagFactory {
    public static TagFactory getInstance() {
        return PlatformLoader.getSingleton(new TagFactory[0]);
    }

    public RecordTag newRecord();

    public ListTag newList();

    public StringTag newLiteral(String var1);

    public NumericTag newPrimitive(boolean var1);

    public NumericTag newPrimitive(byte var1);

    public NumericTag newPrimitive(short var1);

    public NumericTag newPrimitive(int var1);

    public NumericTag newPrimitive(long var1);

    public NumericTag newPrimitive(float var1);

    public NumericTag newPrimitive(double var1);
}
