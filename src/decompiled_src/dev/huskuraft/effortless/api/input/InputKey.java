/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.input;

public record InputKey(int key, int scanCode, int action, int modifiers) {
    public boolean isShiftPressed() {
        return (this.modifiers & 1) != 0;
    }

    public boolean isControlPressed() {
        return (this.modifiers & 2) != 0;
    }

    public boolean isAltPressed() {
        return (this.modifiers & 4) != 0;
    }

    public boolean isSuperPressed() {
        return (this.modifiers & 8) != 0;
    }

    public boolean isCapsLockPressed() {
        return (this.modifiers & 0x10) != 0;
    }

    public boolean isNumLockPressed() {
        return (this.modifiers & 0x20) != 0;
    }

    public static enum Type {
        PRESS(1),
        RELEASE(0),
        REPEAT(2);

        public final int value;

        private Type(int value) {
            this.value = value;
        }
    }
}
