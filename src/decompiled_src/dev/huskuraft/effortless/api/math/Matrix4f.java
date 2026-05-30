/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.Vector4f;
import java.nio.FloatBuffer;

public record Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
    public static byte PROPERTY_PERSPECTIVE = 1;
    public static byte PROPERTY_AFFINE = (byte)2;
    public static byte PROPERTY_IDENTITY = (byte)4;
    public static byte PROPERTY_TRANSLATION = (byte)8;
    public static byte PROPERTY_ORTHONORMAL = (byte)16;

    public Matrix4f(FloatBuffer buffer) {
        this(buffer.get(0), buffer.get(1), buffer.get(2), buffer.get(3), buffer.get(4), buffer.get(5), buffer.get(6), buffer.get(7), buffer.get(8), buffer.get(9), buffer.get(10), buffer.get(11), buffer.get(12), buffer.get(13), buffer.get(14), buffer.get(15));
    }

    public int properties() {
        int properties = 0;
        if (this.m03() == 0.0f && this.m13() == 0.0f) {
            if (this.m23() == 0.0f && this.m33() == 1.0f) {
                properties |= PROPERTY_AFFINE;
                if (this.m00() == 1.0f && this.m01() == 0.0f && this.m02() == 0.0f && this.m10() == 0.0f && this.m11() == 1.0f && this.m12() == 0.0f && this.m20() == 0.0f && this.m21() == 0.0f && this.m22() == 1.0f) {
                    properties |= PROPERTY_TRANSLATION | PROPERTY_ORTHONORMAL;
                    if (this.m30() == 0.0f && this.m31() == 0.0f && this.m32() == 0.0f) {
                        properties |= PROPERTY_IDENTITY;
                    }
                }
            } else if (this.m01() == 0.0f && this.m02() == 0.0f && this.m10() == 0.0f && this.m12() == 0.0f && this.m20() == 0.0f && this.m21() == 0.0f && this.m30() == 0.0f && this.m31() == 0.0f && this.m33() == 0.0f) {
                properties |= PROPERTY_PERSPECTIVE;
            }
        }
        return properties;
    }

    public Vector4f mul(Vector4f vector) {
        return (this.properties() & PROPERTY_AFFINE) != 0 ? this.mulAffine(vector) : this.mulGeneric(vector);
    }

    private Vector4f mulAffine(Vector4f vector) {
        return new Vector4f(Math.fma(this.m00(), vector.x(), Math.fma(this.m10(), vector.y(), Math.fma(this.m20(), vector.z(), this.m30() * vector.w()))), Math.fma(this.m01(), vector.x(), Math.fma(this.m11(), vector.y(), Math.fma(this.m21(), vector.z(), this.m31() * vector.w()))), Math.fma(this.m02(), vector.x(), Math.fma(this.m12(), vector.y(), Math.fma(this.m22(), vector.z(), this.m32() * vector.w()))), vector.w());
    }

    private Vector4f mulGeneric(Vector4f vector) {
        return new Vector4f(Math.fma(this.m00(), vector.x(), Math.fma(this.m10(), vector.y(), Math.fma(this.m20(), vector.z(), this.m30() * vector.w()))), Math.fma(this.m01(), vector.x(), Math.fma(this.m11(), vector.y(), Math.fma(this.m21(), vector.z(), this.m31() * vector.w()))), Math.fma(this.m02(), vector.x(), Math.fma(this.m12(), vector.y(), Math.fma(this.m22(), vector.z(), this.m32() * vector.w()))), Math.fma(this.m03(), vector.x(), Math.fma(this.m13(), vector.y(), Math.fma(this.m23(), vector.z(), this.m33() * vector.w()))));
    }

    public void write(FloatBuffer buffer) {
        buffer.put(new float[]{this.m00(), this.m01(), this.m02(), this.m03(), this.m10(), this.m11(), this.m12(), this.m13(), this.m20(), this.m21(), this.m22(), this.m23(), this.m30(), this.m31(), this.m32(), this.m33()});
    }
}
