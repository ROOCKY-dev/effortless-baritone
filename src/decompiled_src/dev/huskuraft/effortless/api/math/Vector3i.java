/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector3d;
import java.util.Comparator;
import java.util.stream.IntStream;

public record Vector3i(int x, int y, int z) {
    public static final Vector3i ZERO = new Vector3i(0, 0, 0);
    public static final Vector3i ONE = new Vector3i(1, 1, 1);
    public static final Vector3i UNIT_X = new Vector3i(1, 0, 0);
    public static final Vector3i UNIT_Y = new Vector3i(0, 1, 0);
    public static final Vector3i UNIT_Z = new Vector3i(0, 0, 1);
    public static final Vector3i UNIT_MINUS_X = new Vector3i(-1, 0, 0);
    public static final Vector3i UNIT_MINUS_Y = new Vector3i(0, -1, 0);
    public static final Vector3i UNIT_MINUS_Z = new Vector3i(0, 0, -1);

    public static Comparator<Vector3i> sortByCoordsYzx() {
        return YzxOrderComparator.YZX_ORDER;
    }

    public static Vector3i at(double x, double y, double z) {
        return Vector3i.at((int)MathUtils.floor(x), (int)MathUtils.floor(y), (int)MathUtils.floor(z));
    }

    public static Vector3i at(int x, int y, int z) {
        return new Vector3i(x, y, z);
    }

    public static Vector3i at(Vector3d vector) {
        return Vector3i.at(vector.x(), vector.y(), vector.z());
    }

    public static Vector3i at(Vector3i vector) {
        return Vector3i.at(vector.x(), vector.y(), vector.z());
    }

    public Vector3i withX(int x) {
        return Vector3i.at(x, this.y, this.z);
    }

    public Vector3i withY(int y) {
        return Vector3i.at(this.x, y, this.z);
    }

    public Vector3i withZ(int z) {
        return Vector3i.at(this.x, this.y, z);
    }

    public Vector3i add(Vector3i other) {
        return this.add(other.x, other.y, other.z);
    }

    public Vector3i add(int x, int y, int z) {
        return Vector3i.at(this.x + x, this.y + y, this.z + z);
    }

    public Vector3i add(Vector3i ... others) {
        int newX = this.x;
        int newY = this.y;
        int newZ = this.z;
        for (Vector3i other : others) {
            newX += other.x;
            newY += other.y;
            newZ += other.z;
        }
        return Vector3i.at(newX, newY, newZ);
    }

    public Vector3i sub(Vector3i other) {
        return this.sub(other.x, other.y, other.z);
    }

    public Vector3i sub(int x, int y, int z) {
        return Vector3i.at(this.x - x, this.y - y, this.z - z);
    }

    public Vector3i sub(Vector3i ... others) {
        int newX = this.x;
        int newY = this.y;
        int newZ = this.z;
        for (Vector3i other : others) {
            newX -= other.x;
            newY -= other.y;
            newZ -= other.z;
        }
        return Vector3i.at(newX, newY, newZ);
    }

    public Vector3i mul(Vector3i other) {
        return this.mul(other.x, other.y, other.z);
    }

    public Vector3i mul(int x, int y, int z) {
        return Vector3i.at(this.x * x, this.y * y, this.z * z);
    }

    public Vector3i mul(Vector3i ... others) {
        int newX = this.x;
        int newY = this.y;
        int newZ = this.z;
        for (Vector3i other : others) {
            newX *= other.x;
            newY *= other.y;
            newZ *= other.z;
        }
        return Vector3i.at(newX, newY, newZ);
    }

    public Vector3i mul(int n) {
        return this.mul(n, n, n);
    }

    public Vector3i div(Vector3i other) {
        return this.div(other.x, other.y, other.z);
    }

    public Vector3i div(int x, int y, int z) {
        return Vector3i.at(this.x / x, this.y / y, this.z / z);
    }

    public Vector3i div(int n) {
        return this.div(n, n, n);
    }

    public Vector3i shr(int x, int y, int z) {
        return Vector3i.at(this.x >> x, this.y >> y, this.z >> z);
    }

    public Vector3i shr(int n) {
        return this.shr(n, n, n);
    }

    public Vector3i shl(int x, int y, int z) {
        return Vector3i.at(this.x << x, this.y << y, this.z << z);
    }

    public Vector3i shl(int n) {
        return this.shl(n, n, n);
    }

    public double length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public int lengthSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double distance(Vector3i other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public int distanceSq(Vector3i other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        int dz = other.z - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public Vector3i normalize() {
        double len = this.length();
        double x = (double)this.x / len;
        double y = (double)this.y / len;
        double z = (double)this.z / len;
        return Vector3i.at(x, y, z);
    }

    public double dot(Vector3i other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3i cross(Vector3i other) {
        return new Vector3i(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x);
    }

    public boolean containedWithin(Vector3i min, Vector3i max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }

    public Vector3i clampY(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("minimum cannot be greater than maximum");
        }
        if (this.y < min) {
            return Vector3i.at(this.x, min, this.z);
        }
        if (this.y > max) {
            return Vector3i.at(this.x, max, this.z);
        }
        return this;
    }

    public Vector3i floor() {
        return this;
    }

    public Vector3i ceil() {
        return this;
    }

    public Vector3i round() {
        return this;
    }

    public Vector3i abs() {
        return Vector3i.at(MathUtils.abs(this.x), MathUtils.abs(this.y), MathUtils.abs(this.z));
    }

    public double toPitch() {
        double x = this.x();
        double z = this.z();
        if (x == 0.0 && z == 0.0) {
            return this.y() > 0 ? -90.0 : 90.0;
        }
        double x2 = x * x;
        double z2 = z * z;
        double xz = MathUtils.sqrt(x2 + z2);
        return MathUtils.deg(MathUtils.atan((double)(-this.y()) / xz));
    }

    public double toYaw() {
        double x = this.x();
        double z = this.z();
        double t = MathUtils.atan2(-x, z);
        double tau = Math.PI * 2;
        return MathUtils.deg((t + tau) % tau);
    }

    public Vector3i getMinimum(Vector3i v2) {
        return new Vector3i(MathUtils.min(this.x, v2.x), MathUtils.min(this.y, v2.y), MathUtils.min(this.z, v2.z));
    }

    public Vector3i getMaximum(Vector3i v2) {
        return new Vector3i(MathUtils.max(this.x, v2.x), MathUtils.max(this.y, v2.y), MathUtils.max(this.z, v2.z));
    }

    public Vector3d toVector3d() {
        return Vector3d.at(this.x, this.y, this.z);
    }

    public IntStream stream() {
        return IntStream.of(this.x, this.y, this.z);
    }

    public int volume() {
        return this.x * this.y * this.z;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public String toParserString() {
        return this.x + "," + this.y + "," + this.z;
    }

    private static final class YzxOrderComparator {
        private static final Comparator<Vector3i> YZX_ORDER = Comparator.comparingInt(Vector3i::y).thenComparingInt(Vector3i::z).thenComparingInt(Vector3i::x);

        private YzxOrderComparator() {
        }
    }
}
