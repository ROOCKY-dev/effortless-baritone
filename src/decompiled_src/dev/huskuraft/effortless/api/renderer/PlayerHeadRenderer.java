/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.core.PlayerSkin;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.renderer.Renderer;

public interface PlayerHeadRenderer {
    public static final int SKIN_HEAD_U = 8;
    public static final int SKIN_HEAD_V = 8;
    public static final int SKIN_HEAD_WIDTH = 8;
    public static final int SKIN_HEAD_HEIGHT = 8;
    public static final int SKIN_HAT_U = 40;
    public static final int SKIN_HAT_V = 8;
    public static final int SKIN_HAT_WIDTH = 8;
    public static final int SKIN_HAT_HEIGHT = 8;
    public static final int SKIN_TEX_WIDTH = 64;
    public static final int SKIN_TEX_HEIGHT = 64;

    public static void draw(Renderer renderer, PlayerSkin skin, int x, int y, int size) {
        PlayerHeadRenderer.draw(renderer, skin.texture(), x, y, size);
    }

    public static void draw(Renderer renderer, ResourceLocation texture, int x, int y, int size) {
        PlayerHeadRenderer.draw(renderer, texture, x, y, size, true, false);
    }

    public static void draw(Renderer renderer, ResourceLocation texture, int x, int y, int size, boolean drawHat, boolean upsideDown) {
        if (texture == null) {
            return;
        }
        renderer.renderTexture(texture, x, y, size, size, 8.0f, 8 + (upsideDown ? 8 : 0), 8, 8 * (upsideDown ? -1 : 1), 64, 64);
        if (drawHat) {
            PlayerHeadRenderer.drawHat(renderer, texture, x, y, size, upsideDown);
        }
    }

    public static void drawHat(Renderer renderer, ResourceLocation texture, int x, int y, int size, boolean upsideDown) {
        if (texture == null) {
            return;
        }
        renderer.renderTexture(texture, x, y, size, size, 40.0f, 8 + (upsideDown ? 8 : 0), 8, 8 * (upsideDown ? -1 : 1), 64, 64);
    }
}
