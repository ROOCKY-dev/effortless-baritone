/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.AxisDirection;
import dev.huskuraft.effortless.api.text.Text;

public enum Position {
    DISABLED("disabled"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    private Position(String name) {
        this.name = name;
    }

    public Text getDisplayName() {
        return Text.translate("effortless.position.%s".formatted(this.name));
    }

    public AxisDirection getAxis() {
        return switch (this.ordinal()) {
            case 1 -> AxisDirection.NEGATIVE;
            case 2 -> AxisDirection.POSITIVE;
            default -> null;
        };
    }
}
