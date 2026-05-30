/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector2d;
import java.util.stream.IntStream;

public record Vector2i(int x, int y) {
    public static final Vector2i ZERO = new Vector2i(0, 0);
    public static final Vector2i ONE = new Vector2i(1, 1);
    public static final Vector2i UNIT_X = new Vector2i(1, 0);
    public static final Vector2i UNIT_Y = new Vector2i(0, 1);
    public static final Vector2i UNIT_MINUS_X = new Vector2i(-1, 0);
    public static final Vector2i UNIT_MINUS_Y = new Vector2i(0, -1);

    public static Vector2i at(double x, double y) {
        return Vector2i.at((int)MathUtils.floor(x), (int)MathUtils.floor(y));
    }

    public static Vector2i at(int x, int y) {
        return new Vector2i(x, y);
    }

    public Vector2i withX(int x) {
        return Vector2i.at(x, this.y);
    }

    public Vector2i withY(int y) {
        return Vector2i.at(this.x, y);
    }

    public Vector2i add(Vector2i other) {
        return this.add(other.x, other.y);
    }

    public Vector2i add(int x, int y) {
        return Vector2i.at(this.x + x, this.y + y);
    }

    public Vector2i add(Vector2i ... others) {
        int newX = this.x;
        int newY = this.y;
        for (Vector2i other : others) {
            newX += other.x;
            newY += other.y;
        }
        return Vector2i.at(newX, newY);
    }

    public Vector2i sub(Vector2i other) {
        return this.sub(other.x, other.y);
    }

    public Vector2i sub(int x, int y) {
        return Vector2i.at(this.x - x, this.y - y);
    }

    public Vector2i sub(Vector2i ... others) {
        int newX = this.x;
        int newY = this.y;
        for (Vector2i other : others) {
            newX -= other.x;
            newY -= other.y;
        }
        return Vector2i.at(newX, newY);
    }

    public Vector2i mul(Vector2i other) {
        return this.mul(other.x, other.y);
    }

    public Vector2i mul(int x, int y) {
        return Vector2i.at(this.x * x, this.y * y);
    }

    public Vector2i mul(Vector2i ... others) {
        int newX = this.x;
        int newY = this.y;
        for (Vector2i other : others) {
            newX *= other.x;
            newY *= other.y;
        }
        return Vector2i.at(newX, newY);
    }

    public Vector2i mul(int n) {
        return this.mul(n, n);
    }

    public Vector2i div(Vector2i other) {
        return this.div(other.x, other.y);
    }

    public Vector2i div(int x, int y) {
        return Vector2i.at(this.x / x, this.y / y);
    }

    public Vector2i div(int n) {
        return this.div(n, n);
    }

    public Vector2i shr(int x, int y) {
        return Vector2i.at(this.x >> x, this.y >> y);
    }

    public Vector2i shr(int n) {
        return this.shr(n, n);
    }

    public double length() {
        return MathUtils.sqrt(this.lengthSq());
    }

    public int lengthSq() {
        return this.x * this.x + this.y * this.y;
    }

    public double distance(Vector2i other) {
        return MathUtils.sqrt(this.distanceSq(other));
    }

    public int distanceSq(Vector2i other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        return dx * dx + dy * dy;
    }

    public Vector2i normalize() {
        double len = this.length();
        double x = (double)this.x / len;
        double y = (double)this.y / len;
        return Vector2i.at(x, y);
    }

    public int dot(Vector2i other) {
        return this.x * other.x + this.y * other.y;
    }

    public boolean containedWithin(Vector2i min, Vector2i max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y;
    }

    public Vector2i floor() {
        return this;
    }

    public Vector2i ceil() {
        return this;
    }

    public Vector2i round() {
        return this;
    }

    public Vector2i abs() {
        return Vector2i.at(MathUtils.abs(this.x), MathUtils.abs(this.y));
    }

    public Vector2i getMinimum(Vector2i v2) {
        return new Vector2i(MathUtils.min(this.x, v2.x), MathUtils.min(this.y, v2.y));
    }

    public Vector2i getMaximum(Vector2i v2) {
        return new Vector2i(MathUtils.max(this.x, v2.x), MathUtils.max(this.y, v2.y));
    }

    public Vector2d toVector2d() {
        return Vector2d.at(this.x, this.y);
    }

    public IntStream stream() {
        return IntStream.of(this.x, this.y);
    }

    public int volume() {
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
