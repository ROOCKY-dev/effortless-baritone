/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector4d;
import java.util.stream.IntStream;

public record Vector4i(int x, int y, int z, int w) {
    public static final Vector4i ZERO = new Vector4i(0, 0, 0, 0);
    public static final Vector4i ONE = new Vector4i(1, 1, 1, 0);
    public static final Vector4i UNIT_X = new Vector4i(1, 0, 0, 0);
    public static final Vector4i UNIT_Y = new Vector4i(0, 1, 0, 0);
    public static final Vector4i UNIT_Z = new Vector4i(0, 0, 1, 0);
    public static final Vector4i UNIT_W = new Vector4i(0, 0, 0, 1);
    public static final Vector4i UNIT_MINUS_X = new Vector4i(-1, 0, 0, 0);
    public static final Vector4i UNIT_MINUS_Y = new Vector4i(0, -1, 0, 0);
    public static final Vector4i UNIT_MINUS_Z = new Vector4i(0, 0, -1, 0);
    public static final Vector4i UNIT_MINUS_W = new Vector4i(0, 0, 0, -1);

    public static Vector4i at(double x, double y, double z, double w) {
        return Vector4i.at((int)MathUtils.floor(x), (int)MathUtils.floor(y), (int)MathUtils.floor(z), (int)MathUtils.floor(w));
    }

    public static Vector4i at(int x, int y, int z, int w) {
        return new Vector4i(x, y, z, w);
    }

    public static Vector4i at(Vector4d vector) {
        return Vector4i.at(vector.x(), vector.y(), vector.z(), vector.w());
    }

    public static Vector4i at(Vector4i vector) {
        return Vector4i.at(vector.x(), vector.y(), vector.z(), vector.w());
    }

    public Vector4i withX(int x) {
        return Vector4i.at(x, this.y, this.z, this.w);
    }

    public Vector4i withY(int y) {
        return Vector4i.at(this.x, y, this.z, this.w);
    }

    public Vector4i withZ(int z) {
        return Vector4i.at(this.x, this.y, z, this.w);
    }

    public Vector4i withW(int w) {
        return Vector4i.at(this.x, this.y, this.z, w);
    }

    public Vector4i add(Vector4i other) {
        return this.add(other.x, other.y, other.z, other.w);
    }

    public Vector4i add(int x, int y, int z, int w) {
        return Vector4i.at(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4i add(Vector4i ... others) {
        int newX = this.x;
        int newY = this.y;
        int newZ = this.z;
        int newW = this.w;
        for (Vector4i other : others) {
            newX += other.x;
            newY += other.y;
            newZ += other.z;
            newW += other.w;
        }
        return Vector4i.at(newX, newY, newZ, newW);
    }

    public Vector4i sub(Vector4i other) {
        return this.sub(other.x, other.y, other.z, other.w);
    }

    public Vector4i sub(int x, int y, int z, int w) {
        return Vector4i.at(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Vector4i sub(Vector4i ... others) {
        int newX = this.x;
        int newY = this.y;
        int newZ = this.z;
        int newW = this.w;
        for (Vector4i other : others) {
            newX -= other.x;
            newY -= other.y;
            newZ -= other.z;
            newW -= other.w;
        }
        return Vector4i.at(newX, newY, newZ, newW);
    }

    public Vector4i mul(Vector4i other) {
        return this.mul(other.x, other.y, other.z, other.w);
    }

    public Vector4i mul(int x, int y, int z, int w) {
        return Vector4i.at(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    public Vector4i mul(Vector4i ... others) {
        int newX = this.x;
        int newY = this.y;
        int newZ = this.z;
        int newW = this.w;
        for (Vector4i other : others) {
            newX *= other.x;
            newY *= other.y;
            newZ *= other.z;
            newW *= other.w;
        }
        return Vector4i.at(newX, newY, newZ, newW);
    }

    public Vector4i mul(int n) {
        return this.mul(n, n, n, n);
    }

    public Vector4i div(Vector4i other) {
        return this.div(other.x, other.y, other.z, other.w);
    }

    public Vector4i div(int x, int y, int z, int w) {
        return Vector4i.at(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public Vector4i div(int n) {
        return this.div(n, n, n, n);
    }

    public Vector4i shr(int x, int y, int z, int w) {
        return Vector4i.at(this.x >> x, this.y >> y, this.z >> z, this.w >> w);
    }

    public Vector4i shr(int n) {
        return this.shr(n, n, n, n);
    }

    public Vector4i shl(int x, int y, int z, int w) {
        return Vector4i.at(this.x << x, this.y << y, this.z << z, this.w << w);
    }

    public Vector4i shl(int n) {
        return this.shl(n, n, n, n);
    }

    public double length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public int lengthSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public double distance(Vector4i other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public int distanceSq(Vector4i other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        int dz = other.z - this.z;
        int dw = other.w - this.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public Vector4i normalize() {
        double len = this.length();
        double x = (double)this.x / len;
        double y = (double)this.y / len;
        double z = (double)this.z / len;
        double w = (double)this.w / len;
        return Vector4i.at(x, y, z, w);
    }

    public double dot(Vector4i other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public boolean containedWithin(Vector4i min, Vector4i max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z && this.w >= min.w;
    }

    public Vector4i floor() {
        return this;
    }

    public Vector4i ceil() {
        return this;
    }

    public Vector4i round() {
        return this;
    }

    public Vector4i abs() {
        return Vector4i.at(MathUtils.abs(this.x), MathUtils.abs(this.y), MathUtils.abs(this.z), MathUtils.abs(this.w));
    }

    public Vector4i getMinimum(Vector4i v2) {
        return new Vector4i(MathUtils.min(this.x, v2.x), MathUtils.min(this.y, v2.y), MathUtils.min(this.z, v2.z), MathUtils.min(this.w, v2.w));
    }

    public Vector4i getMaximum(Vector4i v2) {
        return new Vector4i(MathUtils.max(this.x, v2.x), MathUtils.max(this.y, v2.y), MathUtils.max(this.z, v2.z), MathUtils.max(this.w, v2.w));
    }

    public Vector4d toVector4d() {
        return Vector4d.at(this.x, this.y, this.z, this.w);
    }

    public IntStream stream() {
        return IntStream.of(this.x, this.y, this.z, this.w);
    }

    public int volume() {
        return this.x * this.y * this.z * this.w;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public String toParserString() {
        return this.x + "," + this.y + "," + this.z + "," + this.w;
    }
}
