/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.texture;

import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.texture.Texture;
import dev.huskuraft.effortless.api.texture.TextureSprite;

public interface TextureFactory {
    public static TextureFactory getInstance() {
        return PlatformLoader.getSingleton(new TextureFactory[0]);
    }

    public Texture getBlockAtlasTexture();

    public TextureSprite getBackgroundTextureSprite();

    public TextureSprite getButtonTextureSprite(boolean var1, boolean var2);

    public TextureSprite getDemoBackgroundTextureSprite();
}
