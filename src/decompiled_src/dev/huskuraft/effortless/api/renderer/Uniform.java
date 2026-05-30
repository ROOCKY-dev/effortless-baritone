/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.math.Matrix3f;
import dev.huskuraft.effortless.api.math.Matrix4f;
import dev.huskuraft.effortless.api.math.Vector3f;
import dev.huskuraft.effortless.api.math.Vector4f;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface Uniform
extends PlatformReference {
    public void set(float var1);

    public void set(float var1, float var2);

    public void set(float var1, float var2, float var3);

    public void set(float var1, float var2, float var3, float var4);

    public void setSafe(float var1, float var2, float var3, float var4);

    public void setSafe(int var1, int var2, int var3, int var4);

    public void set(int var1);

    public void set(int var1, int var2);

    public void set(int var1, int var2, int var3);

    public void set(int var1, int var2, int var3, int var4);

    public void set(float[] var1);

    default public void set(Vector3f vector) {
        this.set(vector.x(), vector.y(), vector.z());
    }

    default public void set(Vector4f vector) {
        this.set(vector.x(), vector.y(), vector.z(), vector.w());
    }

    public void setMatrix22(float var1, float var2, float var3, float var4);

    public void setMatrix23(float var1, float var2, float var3, float var4, float var5, float var6);

    public void setMatrix24(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    public void setMatrix32(float var1, float var2, float var3, float var4, float var5, float var6);

    public void setMatrix33(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public void setMatrix34(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12);

    public void setMatrix42(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    public void setMatrix43(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12);

    public void setMatrix44(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16);

    default public void set(Matrix4f matrix) {
        this.setMatrix44(matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(), matrix.m10(), matrix.m11(), matrix.m12(), matrix.m13(), matrix.m20(), matrix.m21(), matrix.m22(), matrix.m23(), matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33());
    }

    default public void set(Matrix3f matrix) {
        this.setMatrix33(matrix.m00(), matrix.m01(), matrix.m02(), matrix.m10(), matrix.m11(), matrix.m12(), matrix.m20(), matrix.m21(), matrix.m22());
    }
}
