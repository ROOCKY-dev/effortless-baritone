/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.AxisDirection;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3i;
import java.util.Arrays;
import java.util.Comparator;

public enum Direction {
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vector3i(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vector3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vector3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vector3i(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vector3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vector3i(1, 0, 0));

    private static final Direction[] BY_3D_DATA;
    private static final Direction[] BY_2D_DATA;
    private final int data3d;
    private final int oppositeIndex;
    private final int data2d;
    private final String name;
    private final Axis axis;
    private final AxisDirection axisDirection;
    private final Vector3i normal;

    private Direction(int data3d, int oppositeIndex, int data2d, String name, AxisDirection axisDirection, Axis axis, Vector3i normal) {
        this.data3d = data3d;
        this.data2d = data2d;
        this.oppositeIndex = oppositeIndex;
        this.name = name;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.normal = normal;
    }

    public static Direction from3DDataValue(int i) {
        return BY_3D_DATA[MathUtils.abs(i % BY_3D_DATA.length)];
    }

    public static Direction from2DDataValue(int i) {
        return BY_2D_DATA[MathUtils.abs(i % BY_2D_DATA.length)];
    }

    public static Direction fromYRot(double angle) {
        return Direction.from2DDataValue((int)Math.floor(angle / 90.0 + 0.5) & 3);
    }

    public static Direction getNearest(Vector3d vector3d) {
        return Direction.getNearest(vector3d.x(), vector3d.y(), vector3d.z());
    }

    public static Direction getNearest(double d, double e, double f) {
        return Direction.getNearest((float)d, (float)e, (float)f);
    }

    public static Direction getNearest(float f, float g, float h) {
        Direction direction = NORTH;
        float i = Float.MIN_VALUE;
        for (Direction direction2 : Direction.values()) {
            float j = f * (float)direction2.normal.x() + g * (float)direction2.normal.y() + h * (float)direction2.normal.z();
            if (!(j > i)) continue;
            i = j;
            direction = direction2;
        }
        return direction;
    }

    public static Direction get(AxisDirection axisDirection, Axis axis) {
        for (Direction direction : Direction.values()) {
            if (direction.getAxisDirection() != axisDirection || direction.getAxis() != axis) continue;
            return direction;
        }
        throw new IllegalArgumentException("No such direction: " + String.valueOf((Object)axisDirection) + " " + String.valueOf((Object)axis));
    }

    public int get3DDataValue() {
        return this.data3d;
    }

    public int get2DDataValue() {
        return this.data2d;
    }

    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public Direction getOpposite() {
        return Direction.from3DDataValue(this.oppositeIndex);
    }

    public Direction getClockWise(Axis axis) {
        return switch (axis) {
            case Axis.X -> {
                if (this != WEST && this != EAST) {
                    yield this.getClockWiseX();
                }
                yield this;
            }
            case Axis.Z -> {
                if (this != NORTH && this != SOUTH) {
                    yield this.getClockWiseZ();
                }
                yield this;
            }
            case Axis.Y -> {
                if (this != UP && this != DOWN) {
                    yield this.getClockWise();
                }
                yield this;
            }
            default -> throw new IncompatibleClassChangeError();
        };
    }

    public Direction getCounterClockWise(Axis axis) {
        return switch (axis) {
            case Axis.X -> {
                if (this != WEST && this != EAST) {
                    yield this.getCounterClockWiseX();
                }
                yield this;
            }
            case Axis.Z -> {
                if (this != NORTH && this != SOUTH) {
                    yield this.getCounterClockWiseZ();
                }
                yield this;
            }
            case Axis.Y -> {
                if (this != UP && this != DOWN) {
                    yield this.getCounterClockWise();
                }
                yield this;
            }
            default -> throw new IncompatibleClassChangeError();
        };
    }

    public Direction getClockWise() {
        return switch (this.ordinal()) {
            case 2 -> EAST;
            case 3 -> WEST;
            case 4 -> NORTH;
            case 5 -> SOUTH;
            default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + String.valueOf((Object)this));
        };
    }

    private Direction getClockWiseX() {
        return switch (this.ordinal()) {
            case 0 -> SOUTH;
            case 1 -> NORTH;
            case 2 -> DOWN;
            case 3 -> UP;
            default -> throw new IllegalStateException("Unable to get X-rotated facing of " + String.valueOf((Object)this));
        };
    }

    private Direction getCounterClockWiseX() {
        return switch (this.ordinal()) {
            case 0 -> NORTH;
            case 1 -> SOUTH;
            case 2 -> UP;
            case 3 -> DOWN;
            default -> throw new IllegalStateException("Unable to get X-rotated facing of " + String.valueOf((Object)this));
        };
    }

    private Direction getClockWiseZ() {
        return switch (this.ordinal()) {
            case 0 -> WEST;
            case 1 -> EAST;
            case 4 -> UP;
            case 5 -> DOWN;
            default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + String.valueOf((Object)this));
        };
    }

    private Direction getCounterClockWiseZ() {
        return switch (this.ordinal()) {
            case 0 -> EAST;
            case 1 -> WEST;
            case 4 -> DOWN;
            case 5 -> UP;
            default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + String.valueOf((Object)this));
        };
    }

    public Direction getCounterClockWise() {
        return switch (this.ordinal()) {
            case 2 -> WEST;
            case 3 -> EAST;
            case 4 -> SOUTH;
            case 5 -> NORTH;
            default -> throw new IllegalStateException("Unable to get CCW facing of " + String.valueOf((Object)this));
        };
    }

    @Deprecated
    public float toYRot() {
        return (this.data2d & 3) * 90;
    }

    public int getStepX() {
        return this.normal.x();
    }

    public int getStepY() {
        return this.normal.y();
    }

    public int getStepZ() {
        return this.normal.z();
    }

    public Vector3d step() {
        return new Vector3d(this.getStepX(), this.getStepY(), this.getStepZ());
    }

    public String getName() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public String toString() {
        return this.name;
    }

    public Vector3i getNormal() {
        return this.normal;
    }

    static {
        BY_3D_DATA = (Direction[])Arrays.stream(Direction.values()).sorted(Comparator.comparingInt(direction -> direction.data3d)).toArray(Direction[]::new);
        BY_2D_DATA = (Direction[])Arrays.stream(Direction.values()).filter(direction -> direction.getAxis().isHorizontal()).sorted(Comparator.comparingInt(direction -> direction.data2d)).toArray(Direction[]::new);
    }
}
