/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.utils;

public class Rotation {
    private final float yaw;
    private final float pitch;

    public Rotation(float f2, float f3) {
        this.yaw = f2;
        this.pitch = f3;
        if (Float.isInfinite(f2) || Float.isNaN(f2) || Float.isInfinite(f3) || Float.isNaN(f3)) {
            throw new IllegalStateException(f2 + " " + f3);
        }
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Rotation add(Rotation rotation) {
        return new Rotation(this.yaw + rotation.yaw, this.pitch + rotation.pitch);
    }

    public Rotation subtract(Rotation rotation) {
        return new Rotation(this.yaw - rotation.yaw, this.pitch - rotation.pitch);
    }

    public Rotation clamp() {
        return new Rotation(this.yaw, Rotation.clampPitch(this.pitch));
    }

    public Rotation normalize() {
        return new Rotation(Rotation.normalizeYaw(this.yaw), this.pitch);
    }

    public Rotation normalizeAndClamp() {
        return new Rotation(Rotation.normalizeYaw(this.yaw), Rotation.clampPitch(this.pitch));
    }

    public Rotation withPitch(float f2) {
        return new Rotation(this.yaw, f2);
    }

    public boolean isReallyCloseTo(Rotation rotation) {
        return this.yawIsReallyClose(rotation) && (double)Math.abs(this.pitch - rotation.pitch) < 0.01;
    }

    public boolean yawIsReallyClose(Rotation rotation) {
        float f2 = Math.abs(Rotation.normalizeYaw(this.yaw) - Rotation.normalizeYaw(rotation.yaw));
        return (double)f2 < 0.01 || (double)f2 > 359.99;
    }

    public static float clampPitch(float f2) {
        return Math.max(-90.0f, Math.min(90.0f, f2));
    }

    public static float normalizeYaw(float f2) {
        float f3;
        f2 %= 360.0f;
        if (f3 < -180.0f) {
            f2 += 360.0f;
        }
        if (f2 > 180.0f) {
            f2 -= 360.0f;
        }
        return f2;
    }

    public String toString() {
        return "Yaw: " + this.yaw + ", Pitch: " + this.pitch;
    }
}

