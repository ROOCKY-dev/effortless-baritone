/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.Direction;

public enum PlaneDirection {
    HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Axis[]{Axis.X, Axis.Z}),
    VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Axis[]{Axis.Y});

    private final Direction[] faces;
    private final Axis[] axis;

    private PlaneDirection(Direction[] directions, Axis[] axiss) {
        this.faces = directions;
        this.axis = axiss;
    }
}
