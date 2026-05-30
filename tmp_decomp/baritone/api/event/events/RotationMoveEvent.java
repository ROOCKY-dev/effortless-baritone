/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.event.events;

import baritone.api.utils.Rotation;

public final class RotationMoveEvent {
    private final Type type;
    private final Rotation original;
    private float yaw;
    private float pitch;

    public RotationMoveEvent(Type type, float f2, float f3) {
        this.type = type;
        this.original = new Rotation(f2, f3);
        this.yaw = f2;
        this.pitch = f3;
    }

    public final Rotation getOriginal() {
        return this.original;
    }

    public final void setYaw(float f2) {
        this.yaw = f2;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setPitch(float f2) {
        this.pitch = f2;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final Type getType() {
        return this.type;
    }

    public static enum Type {
        MOTION_UPDATE,
        JUMP;

    }
}

