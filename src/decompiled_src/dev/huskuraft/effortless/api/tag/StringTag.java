/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.platform.TagFactory;
import dev.huskuraft.effortless.api.tag.Tag;
import java.util.Locale;

public interface StringTag
extends Tag {
    public static StringTag of(String value) {
        return TagFactory.getInstance().newLiteral(value);
    }

    public static <T extends Enum<T>> StringTag of(Enum<T> value) {
        return StringTag.of(ResourceLocation.of("effortless", value.name().toLowerCase(Locale.ROOT)).toString());
    }

    default public String getString() {
        return this.getAsString();
    }

    default public <T extends Enum<T>> T getAsEnum(Class<T> clazz) {
        try {
            ResourceLocation id = ResourceLocation.decompose(this.getString());
            return Enum.valueOf(clazz, id.getPath().toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
