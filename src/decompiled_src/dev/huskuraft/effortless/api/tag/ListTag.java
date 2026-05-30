/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.platform.TagFactory;
import dev.huskuraft.effortless.api.tag.Tag;
import java.util.List;
import java.util.stream.Stream;

public interface ListTag
extends Tag {
    public static ListTag newList() {
        return TagFactory.getInstance().newList();
    }

    public static ListTag of(List<? extends Tag> tags) {
        ListTag tag = TagFactory.getInstance().newList();
        tags.forEach(tag::addTag);
        return tag;
    }

    default public boolean addTag(Tag tag) {
        return this.addTag(this.size(), tag);
    }

    public boolean addTag(int var1, Tag var2);

    public boolean setTag(int var1, Tag var2);

    public Tag getTag(int var1);

    public int size();

    public Stream<Tag> stream();
}
