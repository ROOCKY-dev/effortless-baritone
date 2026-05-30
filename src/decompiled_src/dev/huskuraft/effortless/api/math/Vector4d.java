/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import java.util.stream.DoubleStream;

public record Vector4d(double x, double y, double z, double w) {
    public static final Vector4d ZERO = new Vector4d(0.0, 0.0, 0.0, 0.0);
    public static final Vector4d ONE = new Vector4d(1.0, 1.0, 1.0, 1.0);
    public static final Vector4d UNIT_X = new Vector4d(1.0, 0.0, 0.0, 0.0);
    public static final Vector4d UNIT_Y = new Vector4d(0.0, 1.0, 0.0, 0.0);
    public static final Vector4d UNIT_Z = new Vector4d(0.0, 0.0, 1.0, 0.0);
    public static final Vector4d UNIT_W = new Vector4d(0.0, 0.0, 0.0, 1.0);
    public static final Vector4d UNIT_MINUS_X = new Vector4d(-1.0, 0.0, 0.0, 0.0);
    public static final Vector4d UNIT_MINUS_Y = new Vector4d(0.0, -1.0, 0.0, 0.0);
    public static final Vector4d UNIT_MINUS_Z = new Vector4d(0.0, 0.0, -1.0, 0.0);
    public static final Vector4d UNIT_MINUS_W = new Vector4d(0.0, 0.0, 0.0, -1.0);

    public static Vector4d at(double x, double y, double z, double w) {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d withX(double x) {
        return Vector4d.at(x, this.y, this.z, this.w);
    }

    public Vector4d withY(double y) {
        return Vector4d.at(this.x, y, this.z, this.w);
    }

    public Vector4d withZ(double z) {
        return Vector4d.at(this.x, this.y, z, this.w);
    }

    public Vector4d withW(double w) {
        return Vector4d.at(this.x, this.y, this.z, w);
    }

    public Vector4d add(Vector4d other) {
        return this.add(other.x, other.y, other.z, other.w);
    }

    public Vector4d add(double x, double y, double z, double w) {
        return Vector4d.at(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4d add(Vector4d ... others) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        double newW = this.w;
        for (Vector4d other : others) {
            newX += other.x;
            newY += other.y;
            newZ += other.z;
            newW += other.w;
        }
        return Vector4d.at(newX, newY, newZ, newW);
    }

    public Vector4d sub(Vector4d other) {
        return this.sub(other.x, other.y, other.z, other.w);
    }

    public Vector4d sub(double x, double y, double z, double w) {
        return Vector4d.at(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Vector4d sub(Vector4d ... others) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        double newW = this.w;
        for (Vector4d other : others) {
            newX -= other.x;
            newY -= other.y;
            newZ -= other.z;
            newW -= other.w;
        }
        return Vector4d.at(newX, newY, newZ, newW);
    }

    public Vector4d reverse() {
        return this.mul(-1.0);
    }

    public Vector4d mul(Vector4d other) {
        return this.mul(other.x, other.y, other.z, other.w);
    }

    public Vector4d mul(double x, double y, double z, double w) {
        return Vector4d.at(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    public Vector4d mul(Vector4d ... others) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        double newW = this.w;
        for (Vector4d other : others) {
            newX *= other.x;
            newY *= other.y;
            newZ *= other.z;
            newW *= other.w;
        }
        return Vector4d.at(newX, newY, newZ, newW);
    }

    public Vector4d mul(double n) {
        return this.mul(n, n, n, n);
    }

    public Vector4d div(Vector4d other) {
        return this.div(other.x, other.y, other.z, other.w);
    }

    public Vector4d div(double x, double y, double z, double w) {
        return Vector4d.at(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public Vector4d div(double n) {
        return this.div(n, n, n, n);
    }

    public double length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public double lengthSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public double distance(Vector4d other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public double distanceSq(Vector4d other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double dz = other.z - this.z;
        double dw = other.w - this.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public Vector4d normalize() {
        return this.div(this.length());
    }

    public double dot(Vector4d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public boolean containedWithin(Vector4d min, Vector4d max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z && this.w >= min.w && this.w <= max.w;
    }

    public Vector4d floor() {
        return Vector4d.at(MathUtils.floor(this.x), MathUtils.floor(this.y), MathUtils.floor(this.z), MathUtils.floor(this.w));
    }

    public Vector4d ceil() {
        return Vector4d.at(MathUtils.ceil(this.x), MathUtils.ceil(this.y), MathUtils.ceil(this.z), MathUtils.ceil(this.w));
    }

    public Vector4d round() {
        return Vector4d.at(MathUtils.floor(this.x + 0.5), MathUtils.floor(this.y + 0.5), MathUtils.floor(this.z + 0.5), MathUtils.floor(this.w + 0.5));
    }

    public Vector4d abs() {
        return Vector4d.at(MathUtils.abs(this.x), MathUtils.abs(this.y), MathUtils.abs(this.z), MathUtils.abs(this.w));
    }

    public DoubleStream stream() {
        return DoubleStream.of(this.x, this.y, this.z, this.w);
    }

    public double volume() {
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
