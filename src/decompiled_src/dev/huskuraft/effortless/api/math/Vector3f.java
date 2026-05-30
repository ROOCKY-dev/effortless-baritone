/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Quaternionf;
import dev.huskuraft.effortless.api.math.Vector3i;
import java.util.Comparator;
import java.util.stream.DoubleStream;

public record Vector3f(float x, float y, float z) {
    public static final Vector3f ZERO = new Vector3f(0.0f, 0.0f, 0.0f);
    public static final Vector3f ONE = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final Vector3f UNIT_X = new Vector3f(1.0f, 0.0f, 0.0f);
    public static final Vector3f UNIT_Y = new Vector3f(0.0f, 1.0f, 0.0f);
    public static final Vector3f UNIT_Z = new Vector3f(0.0f, 0.0f, 1.0f);
    public static final Vector3f UNIT_MINUS_X = new Vector3f(-1.0f, 0.0f, 0.0f);
    public static final Vector3f UNIT_MINUS_Y = new Vector3f(0.0f, -1.0f, 0.0f);
    public static final Vector3f UNIT_MINUS_Z = new Vector3f(0.0f, 0.0f, -1.0f);
    public static final Vector3f XN = new Vector3f(-1.0f, 0.0f, 0.0f);
    public static final Vector3f XP = new Vector3f(1.0f, 0.0f, 0.0f);
    public static final Vector3f YN = new Vector3f(0.0f, -1.0f, 0.0f);
    public static final Vector3f YP = new Vector3f(0.0f, 1.0f, 0.0f);
    public static final Vector3f ZN = new Vector3f(0.0f, 0.0f, -1.0f);
    public static final Vector3f ZP = new Vector3f(0.0f, 0.0f, 1.0f);

    public static Comparator<Vector3f> sortByCoordsYzx() {
        return YzxOrderComparator.YZX_ORDER;
    }

    public static Vector3f at(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    public static Vector3f at(double x, double y, double z) {
        return new Vector3f((float)x, (float)y, (float)z);
    }

    public Vector3f withX(float x) {
        return Vector3f.at(x, this.y, this.z);
    }

    public Vector3f withY(float y) {
        return Vector3f.at(this.x, y, this.z);
    }

    public Vector3f withZ(float z) {
        return Vector3f.at(this.x, this.y, z);
    }

    public Vector3f add(Vector3f other) {
        return this.add(other.x, other.y, other.z);
    }

    public Vector3f add(float x, float y, float z) {
        return Vector3f.at(this.x + x, this.y + y, this.z + z);
    }

    public Vector3f add(Vector3f ... others) {
        float newX = this.x;
        float newY = this.y;
        float newZ = this.z;
        for (Vector3f other : others) {
            newX += other.x;
            newY += other.y;
            newZ += other.z;
        }
        return Vector3f.at(newX, newY, newZ);
    }

    public Vector3f sub(Vector3f other) {
        return this.sub(other.x, other.y, other.z);
    }

    public Vector3f sub(float x, float y, float z) {
        return Vector3f.at(this.x - x, this.y - y, this.z - z);
    }

    public Vector3f sub(Vector3f ... others) {
        float newX = this.x;
        float newY = this.y;
        float newZ = this.z;
        for (Vector3f other : others) {
            newX -= other.x;
            newY -= other.y;
            newZ -= other.z;
        }
        return Vector3f.at(newX, newY, newZ);
    }

    public Vector3f reverse() {
        return this.mul(-1.0f);
    }

    public Vector3f mul(Vector3f other) {
        return this.mul(other.x, other.y, other.z);
    }

    public Vector3f mul(float x, float y, float z) {
        return Vector3f.at(this.x * x, this.y * y, this.z * z);
    }

    public Vector3f mul(Vector3f ... others) {
        float newX = this.x;
        float newY = this.y;
        float newZ = this.z;
        for (Vector3f other : others) {
            newX *= other.x;
            newY *= other.y;
            newZ *= other.z;
        }
        return Vector3f.at(newX, newY, newZ);
    }

    public Vector3f mul(float n) {
        return this.mul(n, n, n);
    }

    public Vector3f div(Vector3f other) {
        return this.div(other.x, other.y, other.z);
    }

    public Vector3f div(float x, float y, float z) {
        return Vector3f.at(this.x / x, this.y / y, this.z / z);
    }

    public Vector3f div(float n) {
        return this.div(n, n, n);
    }

    public float length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public float lengthSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public float distance(Vector3f other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public float distanceSq(Vector3f other) {
        float dx = other.x - this.x;
        float dy = other.y - this.y;
        float dz = other.z - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public Vector3f normalize() {
        return this.div(this.length());
    }

    public float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3f cross(Vector3f other) {
        return new Vector3f(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x);
    }

    public boolean containedWithin(Vector3f min, Vector3f max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }

    public Vector3f clampY(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("minimum cannot be greater than maximum");
        }
        if (this.y < (float)min) {
            return Vector3f.at(this.x, min, this.z);
        }
        if (this.y > (float)max) {
            return Vector3f.at(this.x, max, this.z);
        }
        return this;
    }

    public Vector3f floor() {
        return Vector3f.at(MathUtils.floor(this.x), MathUtils.floor(this.y), MathUtils.floor(this.z));
    }

    public Vector3f ceil() {
        return Vector3f.at(MathUtils.ceil(this.x), MathUtils.ceil(this.y), MathUtils.ceil(this.z));
    }

    public Vector3f round() {
        return Vector3f.at(MathUtils.floor((double)this.x + 0.5), MathUtils.floor((double)this.y + 0.5), MathUtils.floor((double)this.z + 0.5));
    }

    public Vector3f abs() {
        return Vector3f.at(MathUtils.abs(this.x), MathUtils.abs(this.y), MathUtils.abs(this.z));
    }

    public double toPitch() {
        float x = this.x();
        float z = this.z();
        if (x == 0.0f && z == 0.0f) {
            return this.y() > 0.0f ? -90.0 : 90.0;
        }
        float x2 = x * x;
        float z2 = z * z;
        float xz = MathUtils.sqrt(x2 + z2);
        return MathUtils.deg(MathUtils.atan(-this.y() / xz));
    }

    public double toYaw() {
        float x = this.x();
        float z = this.z();
        double t = MathUtils.atan2(-x, z);
        double tau = Math.PI * 2;
        return MathUtils.deg((t + tau) % tau);
    }

    public Vector3f getMinimum(Vector3f v2) {
        return new Vector3f(MathUtils.min(this.x, v2.x), MathUtils.min(this.y, v2.y), MathUtils.min(this.z, v2.z));
    }

    public Vector3f getMaximum(Vector3f v2) {
        return new Vector3f(MathUtils.max(this.x, v2.x), MathUtils.max(this.y, v2.y), MathUtils.max(this.z, v2.z));
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

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public String toParserString() {
        return this.x + "," + this.y + "," + this.z;
    }

    public Quaternionf rotationDegrees(float ang) {
        return Quaternionf.rotate(this, ang, true);
    }

    private static final class YzxOrderComparator {
        private static final Comparator<Vector3f> YZX_ORDER = Comparator.comparingDouble(Vector3f::y).thenComparingDouble(Vector3f::z).thenComparingDouble(Vector3f::x);

        private YzxOrderComparator() {
        }
    }
}
