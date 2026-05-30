/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.Vector3f;
import java.nio.FloatBuffer;

public record Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
    public Matrix3f(FloatBuffer buffer) {
        this(buffer.get(0), buffer.get(1), buffer.get(2), buffer.get(3), buffer.get(4), buffer.get(5), buffer.get(6), buffer.get(7), buffer.get(8));
    }

    public Vector3f mul(Vector3f vector) {
        return new Vector3f(Math.fma(this.m00(), vector.x(), Math.fma(this.m10(), vector.y(), this.m20() * vector.z())), Math.fma(this.m01(), vector.x(), Math.fma(this.m11(), vector.y(), this.m21() * vector.z())), Math.fma(this.m02(), vector.x(), Math.fma(this.m12(), vector.y(), this.m22() * vector.z())));
    }

    public void write(FloatBuffer buffer) {
        buffer.put(new float[]{this.m00(), this.m01(), this.m02(), this.m10(), this.m11(), this.m12(), this.m20(), this.m21(), this.m22()});
    }
}
