/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.core.Direction;
import dev.huskuraft.effortless.api.math.Matrix3f;
import dev.huskuraft.effortless.api.math.Matrix4f;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3f;
import dev.huskuraft.effortless.api.math.Vector4f;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.renderer.RenderUtils;

public interface VertexBuffer
extends PlatformReference {
    public VertexBuffer vertex(double var1, double var3, double var5);

    public VertexBuffer color(int var1, int var2, int var3, int var4);

    public VertexBuffer uv(float var1, float var2);

    public VertexBuffer uv1(int var1, int var2);

    public VertexBuffer uv2(int var1, int var2);

    public VertexBuffer normal(float var1, float var2, float var3);

    public void endVertex();

    default public VertexBuffer color(float red, float green, float blue, float alpha) {
        return this.color((int)(red * 255.0f), (int)(green * 255.0f), (int)(blue * 255.0f), (int)(alpha * 255.0f));
    }

    default public VertexBuffer color(int color) {
        return this.color(RenderUtils.ARGB32.red(color), RenderUtils.ARGB32.green(color), RenderUtils.ARGB32.blue(color), RenderUtils.ARGB32.alpha(color));
    }

    default public VertexBuffer uv1(int overlayUV) {
        return this.uv1(overlayUV & 0xFFFF, overlayUV >> 16 & 0xFFFF);
    }

    default public VertexBuffer uv2(int lightmapUV) {
        return this.uv2(lightmapUV & 0xFFFF, lightmapUV >> 16 & 0xFFFF);
    }

    default public VertexBuffer vertex(Matrix4f matrix, float x, float y, float z) {
        Vector4f vector4f = matrix.mul(new Vector4f(x, y, z, 1.0f));
        return this.vertex(vector4f.x(), vector4f.y(), vector4f.z());
    }

    default public VertexBuffer vertex(Matrix4f matrix, Vector3d vector3d) {
        return this.vertex(matrix, (float)vector3d.x(), (float)vector3d.y(), (float)vector3d.z());
    }

    default public VertexBuffer normal(Matrix3f matrix, float x, float y, float z) {
        Vector3f vector3f = matrix.mul(new Vector3f(x, y, z));
        return this.normal(vector3f.x(), vector3f.y(), vector3f.z());
    }

    default public VertexBuffer normal(Matrix3f matrix, Direction normal) {
        int xOffset = normal != null ? normal.getStepX() : 0;
        int yOffset = normal != null ? normal.getStepY() : 0;
        int zOffset = normal != null ? normal.getStepZ() : 0;
        return this.normal(matrix, xOffset, yOffset, zOffset);
    }
}
