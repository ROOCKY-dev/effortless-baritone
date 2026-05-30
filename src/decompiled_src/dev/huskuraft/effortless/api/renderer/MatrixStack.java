/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.math.Matrix3f;
import dev.huskuraft.effortless.api.math.Matrix4f;
import dev.huskuraft.effortless.api.math.Quaternionf;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface MatrixStack
extends PlatformReference {
    public void push();

    public void pop();

    public Matrix last();

    public void translate(float var1, float var2, float var3);

    public void scale(float var1, float var2, float var3);

    public void rotate(Quaternionf var1);

    public void multiply(Matrix4f var1);

    public void identity();

    public static interface Matrix {
        public Matrix4f pose();

        public Matrix3f normal();
    }
}
