/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.texture;

import dev.huskuraft.effortless.api.texture.SpriteScaling;

public record SpriteScaling.Stretch() implements SpriteScaling
{
    @Override
    public SpriteScaling.Type type() {
        return SpriteScaling.Type.STRETCH;
    }
}
