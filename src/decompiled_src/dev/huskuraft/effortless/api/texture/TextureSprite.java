/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.texture;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.texture.SpriteScaling;

public interface TextureSprite {
    public ResourceLocation name();

    public ResourceLocation texture();

    public int width();

    public int height();

    public int x();

    public int y();

    public float u0();

    public float u1();

    public float v0();

    public float v1();

    default public float u(float u) {
        return this.u0() + (this.u1() - this.u0()) * u;
    }

    default public float v(float v) {
        return this.v0() + (this.v1() - this.v0()) * v;
    }

    default public float uOffset(float offset) {
        return (offset - this.u0()) / (this.u1() - this.u0());
    }

    default public float vOffset(float offset) {
        return (offset - this.v0()) / (this.v1() - this.v0());
    }

    public SpriteScaling scaling();
}
