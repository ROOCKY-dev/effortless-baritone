/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.texture;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.texture.TextureSprite;
import java.util.Set;

public interface Texture {
    public ResourceLocation resource();

    public Set<ResourceLocation> sprites();

    public TextureSprite getSprite(ResourceLocation var1);
}
