/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector3i;
import java.util.Comparator;
import java.util.stream.DoubleStream;

public record Vector3d(double x, double y, double z) {
    public static final Vector3d ZERO = new Vector3d(0.0, 0.0, 0.0);
    public static final Vector3d ONE = new Vector3d(1.0, 1.0, 1.0);
    public static final Vector3d UNIT_X = new Vector3d(1.0, 0.0, 0.0);
    public static final Vector3d UNIT_Y = new Vector3d(0.0, 1.0, 0.0);
    public static final Vector3d UNIT_Z = new Vector3d(0.0, 0.0, 1.0);
    public static final Vector3d UNIT_MINUS_X = new Vector3d(-1.0, 0.0, 0.0);
    public static final Vector3d UNIT_MINUS_Y = new Vector3d(0.0, -1.0, 0.0);
    public static final Vector3d UNIT_MINUS_Z = new Vector3d(0.0, 0.0, -1.0);

    public static Comparator<Vector3d> sortByCoordsYzx() {
        return YzxOrderComparator.YZX_ORDER;
    }

    public static Vector3d at(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }

    public Vector3d withX(double x) {
        return Vector3d.at(x, this.y, this.z);
    }

    public Vector3d withY(double y) {
        return Vector3d.at(this.x, y, this.z);
    }

    public Vector3d withZ(double z) {
        return Vector3d.at(this.x, this.y, z);
    }

    public Vector3d add(Vector3d other) {
        return this.add(other.x, other.y, other.z);
    }

    public Vector3d add(double x, double y, double z) {
        return Vector3d.at(this.x + x, this.y + y, this.z + z);
    }

    public Vector3d add(Vector3d ... others) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        for (Vector3d other : others) {
            newX += other.x;
            newY += other.y;
            newZ += other.z;
        }
        return Vector3d.at(newX, newY, newZ);
    }

    public Vector3d sub(Vector3d other) {
        return this.sub(other.x, other.y, other.z);
    }

    public Vector3d sub(double x, double y, double z) {
        return Vector3d.at(this.x - x, this.y - y, this.z - z);
    }

    public Vector3d sub(Vector3d ... others) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        for (Vector3d other : others) {
            newX -= other.x;
            newY -= other.y;
            newZ -= other.z;
        }
        return Vector3d.at(newX, newY, newZ);
    }

    public Vector3d reverse() {
        return this.mul(-1.0);
    }

    public Vector3d mul(Vector3d other) {
        return this.mul(other.x, other.y, other.z);
    }

    public Vector3d mul(double x, double y, double z) {
        return Vector3d.at(this.x * x, this.y * y, this.z * z);
    }

    public Vector3d mul(Vector3d ... others) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        for (Vector3d other : others) {
            newX *= other.x;
            newY *= other.y;
            newZ *= other.z;
        }
        return Vector3d.at(newX, newY, newZ);
    }

    public Vector3d mul(double n) {
        return this.mul(n, n, n);
    }

    public Vector3d div(Vector3d other) {
        return this.div(other.x, other.y, other.z);
    }

    public Vector3d div(double x, double y, double z) {
        return Vector3d.at(this.x / x, this.y / y, this.z / z);
    }

    public Vector3d div(double n) {
        return this.div(n, n, n);
    }

    public double length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public double lengthSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double distance(Vector3d other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public double distanceSq(Vector3d other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double dz = other.z - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public Vector3d normalize() {
        return this.div(this.length());
    }

    public double dot(Vector3d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3d cross(Vector3d other) {
        return new Vector3d(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x);
    }

    public boolean containedWithin(Vector3d min, Vector3d max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }

    public Vector3d clampY(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("minimum cannot be greater than maximum");
        }
        if (this.y < (double)min) {
            return Vector3d.at(this.x, min, this.z);
        }
        if (this.y > (double)max) {
            return Vector3d.at(this.x, max, this.z);
        }
        return this;
    }

    public Vector3d floor() {
        return Vector3d.at(MathUtils.floor(this.x), MathUtils.floor(this.y), MathUtils.floor(this.z));
    }

    public Vector3d ceil() {
        return Vector3d.at(MathUtils.ceil(this.x), MathUtils.ceil(this.y), MathUtils.ceil(this.z));
    }

    public Vector3d round() {
        return Vector3d.at(MathUtils.floor(this.x + 0.5), MathUtils.floor(this.y + 0.5), MathUtils.floor(this.z + 0.5));
    }

    public Vector3d abs() {
        return Vector3d.at(MathUtils.abs(this.x), MathUtils.abs(this.y), MathUtils.abs(this.z));
    }

    public double toPitch() {
        double x = this.x();
        double z = this.z();
        if (x == 0.0 && z == 0.0) {
            return this.y() > 0.0 ? -90.0 : 90.0;
        }
        double x2 = x * x;
        double z2 = z * z;
        double xz = MathUtils.sqrt(x2 + z2);
        return MathUtils.deg(MathUtils.atan(-this.y() / xz));
    }

    public double toYaw() {
        double x = this.x();
        double z = this.z();
        double t = MathUtils.atan2(-x, z);
        double tau = Math.PI * 2;
        return MathUtils.deg((t + tau) % tau);
    }

    public Vector3d getMinimum(Vector3d v2) {
        return new Vector3d(MathUtils.min(this.x, v2.x), MathUtils.min(this.y, v2.y), MathUtils.min(this.z, v2.z));
    }

    public Vector3d getMaximum(Vector3d v2) {
        return new Vector3d(MathUtils.max(this.x, v2.x), MathUtils.max(this.y, v2.y), MathUtils.max(this.z, v2.z));
    }

    public Vector3i toVector3i() {
        return Vector3i.at(this.x, this.y, this.z);
    }

    public DoubleStream stream() {
        return DoubleStream.of(this.x, this.y, this.z);
    }

    public double volume() {
        return this.x * this.y * this.z;
    }

    public Vector3d rotY(double angle) {
        double cos = MathUtils.cos(angle);
        double sin = MathUtils.sin(angle);
        return new Vector3d(this.x * cos + this.z * sin, this.y, this.z * cos - this.x * sin);
    }

    public Vector3d rotX(double angle) {
        double cos = MathUtils.cos(angle);
        double sin = MathUtils.sin(angle);
        return new Vector3d(this.x, this.y * cos - this.z * sin, this.y * sin + this.z * cos);
    }

    public Vector3d rotZ(double angle) {
        double cos = MathUtils.cos(angle);
        double sin = MathUtils.sin(angle);
        return new Vector3d(this.x * cos - this.y * sin, this.y * cos + this.x * sin, this.z);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public String toParserString() {
        return this.x + "," + this.y + "," + this.z;
    }

    private static final class YzxOrderComparator {
        private static final Comparator<Vector3d> YZX_ORDER = Comparator.comparingDouble(Vector3d::y).thenComparingDouble(Vector3d::z).thenComparingDouble(Vector3d::x);

        private YzxOrderComparator() {
        }
    }
}
