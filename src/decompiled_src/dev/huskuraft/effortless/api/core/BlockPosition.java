/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.Direction;
import dev.huskuraft.effortless.api.core.Revolve;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3i;

public record BlockPosition(int x, int y, int z) {
    public static final BlockPosition ZERO = new BlockPosition(0, 0, 0);
    public static final BlockPosition ONE = new BlockPosition(1, 1, 1);

    public static BlockPosition at(double x, double y, double z) {
        return BlockPosition.at((int)MathUtils.floor(x), (int)MathUtils.floor(y), (int)MathUtils.floor(z));
    }

    public static BlockPosition at(int x, int y, int z) {
        return new BlockPosition(x, y, z);
    }

    public static BlockPosition at(Vector3d vector) {
        return BlockPosition.at(vector.x(), vector.y(), vector.z());
    }

    public static BlockPosition at(Vector3i vector) {
        return BlockPosition.at(vector.x(), vector.y(), vector.z());
    }

    public BlockPosition offset(int i, int j, int k) {
        return i == 0 && j == 0 && k == 0 ? this : new BlockPosition(this.x() + i, this.y() + j, this.z() + k);
    }

    public Vector3d getCenter() {
        return this.toVector3d().add(0.5, 0.5, 0.5);
    }

    public BlockPosition offset(Vector3i vector) {
        return this.offset(vector.x(), vector.y(), vector.z());
    }

    public BlockPosition sub(Vector3i vector) {
        return this.offset(-vector.x(), -vector.y(), -vector.z());
    }

    public BlockPosition sub(BlockPosition blockPosition) {
        return this.offset(-blockPosition.x(), -blockPosition.y(), -blockPosition.z());
    }

    public BlockPosition mul(int i) {
        if (i == 1) {
            return this;
        }
        return i == 0 ? ZERO : new BlockPosition(this.x() * i, this.y() * i, this.z() * i);
    }

    public BlockPosition above() {
        return this.relative(Direction.UP);
    }

    public BlockPosition above(int i) {
        return this.relative(Direction.UP, i);
    }

    public BlockPosition below() {
        return this.relative(Direction.DOWN);
    }

    public BlockPosition below(int i) {
        return this.relative(Direction.DOWN, i);
    }

    public BlockPosition north() {
        return this.relative(Direction.NORTH);
    }

    public BlockPosition north(int i) {
        return this.relative(Direction.NORTH, i);
    }

    public BlockPosition south() {
        return this.relative(Direction.SOUTH);
    }

    public BlockPosition south(int i) {
        return this.relative(Direction.SOUTH, i);
    }

    public BlockPosition west() {
        return this.relative(Direction.WEST);
    }

    public BlockPosition west(int i) {
        return this.relative(Direction.WEST, i);
    }

    public BlockPosition east() {
        return this.relative(Direction.EAST);
    }

    public BlockPosition east(int i) {
        return this.relative(Direction.EAST, i);
    }

    public BlockPosition relative(Direction direction) {
        return new BlockPosition(this.x() + direction.getStepX(), this.y() + direction.getStepY(), this.z() + direction.getStepZ());
    }

    public BlockPosition relative(Direction direction, int i) {
        return i == 0 ? this : new BlockPosition(this.x() + direction.getStepX() * i, this.y() + direction.getStepY() * i, this.z() + direction.getStepZ() * i);
    }

    public BlockPosition relative(Axis axis, int i) {
        if (i == 0) {
            return this;
        }
        int j = axis == Axis.X ? i : 0;
        int k = axis == Axis.Y ? i : 0;
        int l = axis == Axis.Z ? i : 0;
        return new BlockPosition(this.x() + j, this.y() + k, this.z() + l);
    }

    public BlockPosition rotate(Revolve revolve) {
        return switch (revolve) {
            case Revolve.CLOCKWISE_90 -> new BlockPosition(-this.z(), this.y(), this.x());
            case Revolve.CLOCKWISE_180 -> new BlockPosition(-this.x(), this.y(), -this.z());
            case Revolve.COUNTERCLOCKWISE_90 -> new BlockPosition(this.z(), this.y(), -this.x());
            default -> this;
        };
    }

    public BlockPosition cross(Vector3i vector) {
        return new BlockPosition(this.y() * vector.z() - this.z() * vector.y(), this.z() * vector.x() - this.x() * vector.z(), this.x() * vector.y() - this.y() * vector.x());
    }

    public int get(Axis axis) {
        return switch (axis) {
            default -> throw new MatchException(null, null);
            case Axis.X -> this.x;
            case Axis.Y -> this.y;
            case Axis.Z -> this.z;
        };
    }

    public BlockPosition add(Vector3i other) {
        return this.add(other.x(), other.y(), other.z());
    }

    public BlockPosition add(BlockPosition blockPosition) {
        return this.add(blockPosition.x(), blockPosition.y(), blockPosition.z());
    }

    public BlockPosition add(int x, int y, int z) {
        return BlockPosition.at(this.x + x, this.y + y, this.z + z);
    }

    public BlockPosition withX(int x) {
        return new BlockPosition(x, this.y, this.z);
    }

    public BlockPosition withY(int y) {
        return new BlockPosition(this.x, y, this.z);
    }

    public BlockPosition withZ(int z) {
        return new BlockPosition(this.x, this.y, z);
    }

    public Vector3d toVector3d() {
        return Vector3d.at(this.x, this.y, this.z);
    }

    public Vector3i toVector3i() {
        return Vector3i.at(this.x, this.y, this.z);
    }

    public int volume() {
        return this.x * this.y * this.z;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
