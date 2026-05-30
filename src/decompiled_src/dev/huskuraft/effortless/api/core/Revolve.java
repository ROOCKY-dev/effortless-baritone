/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.Direction;

public enum Revolve {
    NONE("none"),
    CLOCKWISE_90("clockwise_90"),
    CLOCKWISE_180("180"),
    COUNTERCLOCKWISE_90("counterclockwise_90");

    private final String id;

    private Revolve(String string2) {
        this.id = string2;
    }

    public Revolve getRotated(Revolve revolve) {
        return switch (revolve.ordinal()) {
            case 2 -> {
                switch (this.ordinal()) {
                    default: {
                        throw new MatchException(null, null);
                    }
                    case 0: {
                        yield CLOCKWISE_180;
                    }
                    case 1: {
                        yield COUNTERCLOCKWISE_90;
                    }
                    case 2: {
                        yield NONE;
                    }
                    case 3: 
                }
                yield CLOCKWISE_90;
            }
            case 3 -> {
                switch (this.ordinal()) {
                    default: {
                        throw new MatchException(null, null);
                    }
                    case 0: {
                        yield COUNTERCLOCKWISE_90;
                    }
                    case 1: {
                        yield NONE;
                    }
                    case 2: {
                        yield CLOCKWISE_90;
                    }
                    case 3: 
                }
                yield CLOCKWISE_180;
            }
            case 1 -> {
                switch (this.ordinal()) {
                    default: {
                        throw new MatchException(null, null);
                    }
                    case 0: {
                        yield CLOCKWISE_90;
                    }
                    case 1: {
                        yield CLOCKWISE_180;
                    }
                    case 2: {
                        yield COUNTERCLOCKWISE_90;
                    }
                    case 3: 
                }
                yield NONE;
            }
            default -> this;
        };
    }

    public Direction rotate(Direction direction) {
        if (direction.getAxis() == Axis.Y) {
            return direction;
        }
        return switch (this.ordinal()) {
            case 1 -> direction.getClockWise();
            case 2 -> direction.getOpposite();
            case 3 -> direction.getCounterClockWise();
            default -> direction;
        };
    }

    public int rotate(int i, int j) {
        return switch (this.ordinal()) {
            case 1 -> (i + j / 4) % j;
            case 2 -> (i + j / 2) % j;
            case 3 -> (i + j * 3 / 4) % j;
            default -> i;
        };
    }

    public String getSerializedName() {
        return this.id;
    }
}
