/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.Vector3f;

public record Quaternionf(float x, float y, float z, float w) {
    public static Quaternionf rotate(Vector3f vector3f, float angle, boolean isAngle) {
        if (isAngle) {
            angle = (float)((double)angle * (Math.PI / 180));
        }
        return new Quaternionf((float)((double)vector3f.x() * Math.sin(angle / 2.0f)), (float)((double)vector3f.y() * Math.sin(angle / 2.0f)), (float)((double)vector3f.z() * Math.sin(angle / 2.0f)), (float)Math.cos(angle / 2.0f));
    }
}
