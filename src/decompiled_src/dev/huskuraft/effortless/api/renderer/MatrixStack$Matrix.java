/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.math.Matrix3f;
import dev.huskuraft.effortless.api.math.Matrix4f;

public static interface MatrixStack.Matrix {
    public Matrix4f pose();

    public Matrix3f normal();
}
