/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.renderer.RenderUtils;

public class RenderUtils.ARGB32 {
    public RenderUtils.ARGB32(RenderUtils this$0) {
    }

    public static int alpha(int packedColor) {
        return packedColor >>> 24;
    }

    public static int red(int packedColor) {
        return packedColor >> 16 & 0xFF;
    }

    public static int green(int packedColor) {
        return packedColor >> 8 & 0xFF;
    }

    public static int blue(int packedColor) {
        return packedColor & 0xFF;
    }

    public static int color(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
}
