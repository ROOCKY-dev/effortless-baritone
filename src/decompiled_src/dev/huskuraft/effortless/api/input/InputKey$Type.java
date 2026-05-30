/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.input;

public static enum InputKey.Type {
    PRESS(1),
    RELEASE(0),
    REPEAT(2);

    public final int value;

    private InputKey.Type(int value) {
        this.value = value;
    }
}
