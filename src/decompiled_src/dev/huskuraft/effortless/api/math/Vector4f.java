/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import java.util.stream.DoubleStream;

public record Vector4f(float x, float y, float z, float w) {
    public static final Vector4f ZERO = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4f ONE = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Vector4f UNIT_X = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4f UNIT_Y = new Vector4f(0.0f, 1.0f, 0.0f, 0.0f);
    public static final Vector4f UNIT_Z = new Vector4f(0.0f, 0.0f, 1.0f, 0.0f);
    public static final Vector4f UNIT_W = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Vector4f UNIT_MINUS_X = new Vector4f(-1.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4f UNIT_MINUS_Y = new Vector4f(0.0f, -1.0f, 0.0f, 0.0f);
    public static final Vector4f UNIT_MINUS_Z = new Vector4f(0.0f, 0.0f, -1.0f, 0.0f);
    public static final Vector4f UNIT_MINUS_W = new Vector4f(0.0f, 0.0f, 0.0f, -1.0f);

    public static Vector4f at(double x, double y, double z, double w) {
        return new Vector4f((float)x, (float)y, (float)z, (float)w);
    }

    public static Vector4f at(float x, float y, float z, float w) {
        return new Vector4f(x, y, z, w);
    }

    public Vector4f withX(float x) {
        return Vector4f.at(x, this.y, this.z, this.w);
    }

    public Vector4f withY(float y) {
        return Vector4f.at(this.x, y, this.z, this.w);
    }

    public Vector4f withZ(float z) {
        return Vector4f.at(this.x, this.y, z, this.w);
    }

    public Vector4f withW(float w) {
        return Vector4f.at(this.x, this.y, this.z, w);
    }

    public Vector4f add(Vector4f other) {
        return this.add(other.x, other.y, other.z, other.w);
    }

    public Vector4f add(float x, float y, float z, float w) {
        return Vector4f.at(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4f add(Vector4f ... others) {
        float newX = this.x;
        float newY = this.y;
        float newZ = this.z;
        float newW = this.w;
        for (Vector4f other : others) {
            newX += other.x;
            newY += other.y;
            newZ += other.z;
            newW += other.w;
        }
        return Vector4f.at(newX, newY, newZ, newW);
    }

    public Vector4f sub(Vector4f other) {
        return this.sub(other.x, other.y, other.z, other.w);
    }

    public Vector4f sub(float x, float y, float z, float w) {
        return Vector4f.at(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Vector4f sub(Vector4f ... others) {
        float newX = this.x;
        float newY = this.y;
        float newZ = this.z;
        float newW = this.w;
        for (Vector4f other : others) {
            newX -= other.x;
            newY -= other.y;
            newZ -= other.z;
            newW -= other.w;
        }
        return Vector4f.at(newX, newY, newZ, newW);
    }

    public Vector4f reverse() {
        return this.mul(-1.0f);
    }

    public Vector4f mul(Vector4f other) {
        return this.mul(other.x, other.y, other.z, other.w);
    }

    public Vector4f mul(float x, float y, float z, float w) {
        return Vector4f.at(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    public Vector4f mul(Vector4f ... others) {
        float newX = this.x;
        float newY = this.y;
        float newZ = this.z;
        float newW = this.w;
        for (Vector4f other : others) {
            newX *= other.x;
            newY *= other.y;
            newZ *= other.z;
            newW *= other.w;
        }
        return Vector4f.at(newX, newY, newZ, newW);
    }

    public Vector4f mul(float n) {
        return this.mul(n, n, n, n);
    }

    public Vector4f div(Vector4f other) {
        return this.div(other.x, other.y, other.z, other.w);
    }

    public Vector4f div(float x, float y, float z, float w) {
        return Vector4f.at(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public Vector4f div(float n) {
        return this.div(n, n, n, n);
    }

    public float length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public float lengthSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public float distance(Vector4f other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public float distanceSq(Vector4f other) {
        float dx = other.x - this.x;
        float dy = other.y - this.y;
        float dz = other.z - this.z;
        float dw = other.w - this.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public Vector4f normalize() {
        return this.div(this.length());
    }

    public float dot(Vector4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public boolean containedWithin(Vector4f min, Vector4f max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z && this.w >= min.w && this.w <= max.w;
    }

    public Vector4f floor() {
        return Vector4f.at(MathUtils.floor(this.x), MathUtils.floor(this.y), MathUtils.floor(this.z), MathUtils.floor(this.w));
    }

    public Vector4f ceil() {
        return Vector4f.at(MathUtils.ceil(this.x), MathUtils.ceil(this.y), MathUtils.ceil(this.z), MathUtils.ceil(this.w));
    }

    public Vector4f round() {
        return Vector4f.at(MathUtils.floor((double)this.x + 0.5), MathUtils.floor((double)this.y + 0.5), MathUtils.floor((double)this.z + 0.5), MathUtils.floor((double)this.w + 0.5));
    }

    public Vector4f abs() {
        return Vector4f.at(MathUtils.abs(this.x), MathUtils.abs(this.y), MathUtils.abs(this.z), MathUtils.abs(this.w));
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
