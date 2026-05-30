/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.input;

import dev.huskuraft.effortless.api.input.Key;
import dev.huskuraft.effortless.api.platform.ClientContentFactory;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface KeyBinding
extends PlatformReference {
    public static KeyBinding of(String name, String category, int code) {
        return ClientContentFactory.getInstance().newKeyBinding(name, category, code);
    }

    public String getName();

    public String getCategory();

    public Key getDefaultKey();

    public Key getKey();

    public boolean consumeClick();

    public boolean isDown();

    default public boolean isKeyDown() {
        return this.getKey().isDown();
    }
}
