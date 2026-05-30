/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.texture;

public sealed interface SpriteScaling {
    public Type type();

    public record NineSlice(int width, int height, int left, int right, int top, int bottom) implements SpriteScaling
    {
        public NineSlice(int width, int height) {
            this(width, height, 0, 0, 0, 0);
        }

        public NineSlice(int width, int height, int border) {
            this(width, height, border, border, border, border);
        }

        @Override
        public Type type() {
            return Type.NINE_SLICE;
        }
    }

    public record Tile(int width, int height) implements SpriteScaling
    {
        @Override
        public Type type() {
            return Type.TILE;
        }
    }

    public record Stretch() implements SpriteScaling
    {
        @Override
        public Type type() {
            return Type.STRETCH;
        }
    }

    public static enum Type {
        STRETCH,
        TILE,
        NINE_SLICE;

    }
}
