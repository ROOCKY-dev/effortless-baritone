/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.math.Quaternionf;
import dev.huskuraft.effortless.api.math.Vector3d;

public interface Camera {
    public Vector3d position();

    public Quaternionf rotation();

    public float eyeHeight();
}
