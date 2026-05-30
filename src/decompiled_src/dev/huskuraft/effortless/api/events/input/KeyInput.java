/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.input;

import dev.huskuraft.effortless.api.input.InputKey;

@FunctionalInterface
public interface KeyInput {
    public void onKeyInput(InputKey var1);
}
