/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.texture;

import dev.huskuraft.effortless.api.texture.SpriteScaling;

public record SpriteScaling.NineSlice(int width, int height, int left, int right, int top, int bottom) implements SpriteScaling
{
    public SpriteScaling.NineSlice(int width, int height) {
        this(width, height, 0, 0, 0, 0);
    }

    public SpriteScaling.NineSlice(int width, int height, int border) {
        this(width, height, border, border, border, border);
    }

    @Override
    public SpriteScaling.Type type() {
        return SpriteScaling.Type.NINE_SLICE;
    }
}
