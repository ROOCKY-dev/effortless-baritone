/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector2i;
import java.util.stream.DoubleStream;

public record Vector2d(double x, double y) {
    public static final Vector2d ZERO = new Vector2d(0.0, 0.0);
    public static final Vector2d ONE = new Vector2d(1.0, 1.0);
    public static final Vector2d UNIT_X = new Vector2d(1.0, 0.0);
    public static final Vector2d UNIT_Y = new Vector2d(0.0, 1.0);
    public static final Vector2d UNIT_MINUS_X = new Vector2d(-1.0, 0.0);
    public static final Vector2d UNIT_MINUS_Y = new Vector2d(0.0, -1.0);

    public static Vector2d at(double x, double y) {
        return new Vector2d(x, y);
    }

    public Vector2d withX(double x) {
        return Vector2d.at(x, this.y);
    }

    public Vector2d withY(double y) {
        return Vector2d.at(this.x, y);
    }

    public Vector2d add(Vector2d other) {
        return this.add(other.x, other.y);
    }

    public Vector2d add(double x, double y) {
        return Vector2d.at(this.x + x, this.y + y);
    }

    public Vector2d add(Vector2d ... others) {
        double newX = this.x;
        double newY = this.y;
        for (Vector2d other : others) {
            newX += other.x;
            newY += other.y;
        }
        return Vector2d.at(newX, newY);
    }

    public Vector2d sub(Vector2d other) {
        return this.sub(other.x, other.y);
    }

    public Vector2d sub(double x, double y) {
        return Vector2d.at(this.x - x, this.y - y);
    }

    public Vector2d sub(Vector2d ... others) {
        double newX = this.x;
        double newY = this.y;
        for (Vector2d other : others) {
            newX -= other.x;
            newY -= other.y;
        }
        return Vector2d.at(newX, newY);
    }

    public Vector2d mul(Vector2d other) {
        return this.mul(other.x, other.y);
    }

    public Vector2d mul(double x, double y) {
        return Vector2d.at(this.x * x, this.y * y);
    }

    public Vector2d mul(Vector2d ... others) {
        double newX = this.x;
        double newY = this.y;
        for (Vector2d other : others) {
            newX *= other.x;
            newY *= other.y;
        }
        return Vector2d.at(newX, newY);
    }

    public Vector2d mul(double n) {
        return this.mul(n, n);
    }

    public Vector2d div(Vector2d other) {
        return this.div(other.x, other.y);
    }

    public Vector2d div(double x, double y) {
        return Vector2d.at(this.x / x, this.y / y);
    }

    public Vector2d div(double n) {
        return this.div(n, n);
    }

    public double length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public double lengthSq() {
        return this.x * this.x + this.y * this.y;
    }

    public double distance(Vector2d other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public double distanceSq(Vector2d other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return dx * dx + dy * dy;
    }

    public Vector2d normalize() {
        return this.div(this.length());
    }

    public double dot(Vector2d other) {
        return this.x * other.x + this.y * other.y;
    }

    public boolean containedWithin(Vector2d min, Vector2d max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y;
    }

    public Vector2d floor() {
        return Vector2d.at(MathUtils.floor(this.x), MathUtils.floor(this.y));
    }

    public Vector2d ceil() {
        return Vector2d.at(MathUtils.ceil(this.x), MathUtils.ceil(this.y));
    }

    public Vector2d round() {
        return Vector2d.at(MathUtils.floor(this.x + 0.5), MathUtils.floor(this.y + 0.5));
    }

    public Vector2d abs() {
        return Vector2d.at(MathUtils.abs(this.x), MathUtils.abs(this.y));
    }

    public Vector2d getMinimum(Vector2d v2) {
        return new Vector2d(MathUtils.min(this.x, v2.x), MathUtils.min(this.y, v2.y));
    }

    public Vector2d getMaximum(Vector2d v2) {
        return new Vector2d(MathUtils.max(this.x, v2.x), MathUtils.max(this.y, v2.y));
    }

    public Vector2i toVector2i() {
        return Vector2i.at(this.x, this.y);
    }

    public DoubleStream stream() {
        return DoubleStream.of(this.x, this.y);
    }

    public double volume() {
        return this.x * this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public String toParserString() {
        return this.x + "," + this.y;
    }
}
