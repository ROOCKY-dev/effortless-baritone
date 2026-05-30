/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.core.BlockEntity;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.Direction;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.gui.Typeface;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Matrix3f;
import dev.huskuraft.effortless.api.math.Matrix4f;
import dev.huskuraft.effortless.api.math.Quaternionf;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector4f;
import dev.huskuraft.effortless.api.platform.ClientEntrance;
import dev.huskuraft.effortless.api.renderer.BufferSource;
import dev.huskuraft.effortless.api.renderer.Camera;
import dev.huskuraft.effortless.api.renderer.MatrixStack;
import dev.huskuraft.effortless.api.renderer.OverlayTexture;
import dev.huskuraft.effortless.api.renderer.RenderLayer;
import dev.huskuraft.effortless.api.renderer.RenderLayers;
import dev.huskuraft.effortless.api.renderer.ScreenRect;
import dev.huskuraft.effortless.api.renderer.VertexBuffer;
import dev.huskuraft.effortless.api.renderer.Window;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.api.texture.SpriteScaling;
import dev.huskuraft.effortless.api.texture.TextureSprite;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public abstract class Renderer {
    private final ScissorStack scissorStack = new ScissorStack();

    public int optionColor(float alpha) {
        return new Color(0.0f, 0.0f, 0.0f, 0.95f * alpha).getRGB();
    }

    public final Window getWindow() {
        return ClientEntrance.getInstance().getClient().getWindow();
    }

    public final Camera getCamera() {
        return ClientEntrance.getInstance().getClient().getCamera();
    }

    public abstract MatrixStack matrixStack();

    public abstract BufferSource bufferSource();

    public final void pushPose() {
        this.matrixStack().push();
    }

    public final void popPose() {
        this.matrixStack().pop();
    }

    public final Matrix4f lastMatrixPose() {
        return this.matrixStack().last().pose();
    }

    public final Matrix3f lastMatrixNormal() {
        return this.matrixStack().last().normal();
    }

    public final void translate(Vector3d vector) {
        this.translate(vector.x(), vector.y(), vector.z());
    }

    public final void translate(double x, double y, double z) {
        this.translate((float)x, (float)y, (float)z);
    }

    public final void translate(float x, float y, float z) {
        this.matrixStack().translate(x, y, z);
    }

    public final void scale(double n) {
        this.scale(n, n, n);
    }

    public final void scale(Vector3d vector) {
        this.scale(vector.x(), vector.y(), vector.z());
    }

    public final void scale(double x, double y, double z) {
        this.scale((float)x, (float)y, (float)z);
    }

    public final void scale(float x, float y, float z) {
        this.matrixStack().scale(x, y, z);
    }

    public final void rotate(Quaternionf quaternion) {
        this.matrixStack().rotate(quaternion);
    }

    public final void rotate(Quaternionf quaternion, float x, float y, float z) {
    }

    public final void multiply(Matrix4f matrix) {
        this.matrixStack().multiply(matrix);
    }

    protected abstract void enableScissor(int var1, int var2, int var3, int var4);

    protected abstract void disableScissor();

    public final void pushScissor(int x, int y, int width, int height) {
        Matrix4f pose = this.lastMatrixPose();
        Vector4f p1 = pose.mul(new Vector4f(x, y, 0.0f, 1.0f));
        Vector4f p2 = pose.mul(new Vector4f(x + width, y + height, 0.0f, 1.0f));
        this.applyScissor(this.scissorStack.push(new ScreenRect((int)p1.x(), (int)p1.y(), (int)(p2.x() - p1.x()), (int)(p2.y() - p1.y()))));
    }

    public final void popScissor() {
        this.applyScissor(this.scissorStack.pop());
    }

    private void applyScissor(ScreenRect rect) {
        if (rect == null) {
            this.disableScissor();
        } else {
            int height = this.getWindow().getHeight();
            double scale = this.getWindow().getGuiScaledFactor();
            double d1 = (double)rect.left() * scale;
            double d2 = (double)height - (double)rect.bottom() * scale;
            double d3 = (double)rect.width() * scale;
            double d4 = (double)rect.height() * scale;
            this.enableScissor((int)d1, (int)d2, Math.max(0, (int)d3), Math.max(0, (int)d4));
        }
    }

    public abstract void setRsShaderColor(float var1, float var2, float var3, float var4);

    public VertexBuffer vertexBuffer(RenderLayer renderLayer) {
        return this.bufferSource().getBuffer(renderLayer);
    }

    public abstract void flush();

    public final void renderHLine(int x1, int x2, int y, int color) {
        this.renderHLine(RenderLayers.GUI, x1, x2, y, color);
    }

    public final void renderHLine(RenderLayer renderLayer, int x1, int x2, int y, int color) {
        if (x2 < x1) {
            int i = x1;
            x1 = x2;
            x2 = i;
        }
        this.renderRect(renderLayer, x1, y, x2 + 1, y + 1, color);
    }

    public final void renderVLine(int x, int y1, int y2, int color) {
        this.renderVLine(RenderLayers.GUI, x, y1, y2, color);
    }

    public final void renderVLine(RenderLayer renderLayer, int x, int y1, int y2, int color) {
        if (y2 < y1) {
            int i = y1;
            y1 = y2;
            y2 = i;
        }
        this.renderRect(renderLayer, x, y1 + 1, x + 1, y2, color);
    }

    public final void renderLine(RenderLayer renderLayer, Vector3d v1, Vector3d v2, int uv2, int color) {
        VertexBuffer buffer = this.vertexBuffer(renderLayer);
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, v1).uv(1.0f, 1.0f).uv2(uv2).color(color).uv1(OverlayTexture.NO_OVERLAY).endVertex();
        buffer.vertex(pose, v2).uv(1.0f, 1.0f).uv2(uv2).color(color).uv1(OverlayTexture.NO_OVERLAY).endVertex();
    }

    public final void renderBorder(int x, int y, int width, int height, int color) {
        this.renderRect(x, y, x + width, y + 1, color);
        this.renderRect(x, y + height - 1, x + width, y + height, color);
        this.renderRect(x, y + 1, x + 1, y + height - 1, color);
        this.renderRect(x + width - 1, y + 1, x + width, y + height - 1, color);
    }

    public final void renderRect(int x1, int y1, int x2, int y2, int color) {
        this.renderRect(RenderLayers.GUI, x1, y1, x2, y2, color, 0);
    }

    public final void renderRect(int x1, int y1, int x2, int y2, int color, int z) {
        this.renderRect(RenderLayers.GUI, x1, y1, x2, y2, color, z);
    }

    public final void renderRect(RenderLayer renderLayer, int x1, int y1, int x2, int y2, int color) {
        this.renderRect(renderLayer, x1, y1, x2, y2, color, 0);
    }

    public final void renderRect(RenderLayer renderLayer, int x1, int y1, int x2, int y2, int color, int z) {
        int i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        VertexBuffer buffer = this.vertexBuffer(renderLayer);
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, x1, y1, z).color(color).endVertex();
        buffer.vertex(pose, x1, y2, z).color(color).endVertex();
        buffer.vertex(pose, x2, y2, z).color(color).endVertex();
        buffer.vertex(pose, x2, y1, z).color(color).endVertex();
        this.flush();
    }

    public final void renderGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
        this.renderGradientRect(RenderLayers.GUI, x1, y1, x2, y2, color1, color2, 0);
    }

    public final void renderGradientRect(int x1, int y1, int x2, int y2, int color1, int color2, int z) {
        this.renderGradientRect(RenderLayers.GUI, x1, y1, x2, y2, color1, color2, z);
    }

    public final void renderGradientRect(RenderLayer renderLayer, int x1, int y1, int x2, int y2, int color1, int color2) {
        this.renderGradientRect(renderLayer, x1, y1, x2, y2, color1, color2, 0);
    }

    public final void renderGradientRect(RenderLayer renderLayer, int x1, int y1, int x2, int y2, int color1, int color2, int z) {
        VertexBuffer buffer = this.vertexBuffer(renderLayer);
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, x1, y1, z).color(color1).endVertex();
        buffer.vertex(pose, x1, y2, z).color(color2).endVertex();
        buffer.vertex(pose, x2, y2, z).color(color2).endVertex();
        buffer.vertex(pose, x2, y1, z).color(color1).endVertex();
        this.flush();
    }

    public final void renderQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int offset, int color) {
        this.renderQuad(RenderLayers.GUI, x1, y1, x2, y2, x3, y3, x4, y4, offset, color);
    }

    public final void renderQuad(RenderLayer renderLayer, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int offset, int color) {
        VertexBuffer buffer = this.vertexBuffer(renderLayer);
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, x1, y1, offset).color(color).endVertex();
        buffer.vertex(pose, x2, y2, offset).color(color).endVertex();
        buffer.vertex(pose, x3, y3, offset).color(color).endVertex();
        buffer.vertex(pose, x4, y4, offset).color(color).endVertex();
    }

    public final void renderQuad(RenderLayer renderLayer, Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, int uv2, int color, Direction normal) {
        this.renderQuadUV(renderLayer, v1, v2, v3, v4, 0.0f, 0.0f, 1.0f, 1.0f, uv2, color, normal);
    }

    public final void renderQuadUV(RenderLayer renderLayer, Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, float minU, float minV, float maxU, float maxV, int uv2, int color, Direction normal) {
        VertexBuffer buffer = this.vertexBuffer(renderLayer);
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, v1).color(color).uv(minU, minV).uv1(OverlayTexture.NO_OVERLAY).uv2(uv2).normal(this.lastMatrixNormal(), normal).endVertex();
        buffer.vertex(pose, v2).color(color).uv(maxU, minV).uv1(OverlayTexture.NO_OVERLAY).uv2(uv2).normal(this.lastMatrixNormal(), normal).endVertex();
        buffer.vertex(pose, v3).color(color).uv(maxU, maxV).uv1(OverlayTexture.NO_OVERLAY).uv2(uv2).normal(this.lastMatrixNormal(), normal).endVertex();
        buffer.vertex(pose, v4).color(color).uv(minU, maxV).uv1(OverlayTexture.NO_OVERLAY).uv2(uv2).normal(this.lastMatrixNormal(), normal).endVertex();
    }

    protected abstract int renderTextInternal(Typeface var1, Text var2, int var3, int var4, int var5, int var6, boolean var7, boolean var8, int var9);

    public int renderText(Typeface typeface, Text text, int x, int y, int color, int backgroundColor, boolean shadow, boolean seeThrough, int lightMap) {
        int width = this.renderTextInternal(typeface, text, x, y, color, backgroundColor, shadow, seeThrough, lightMap);
        this.flush();
        return width;
    }

    public final int renderText(Typeface typeface, String string, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, Text.text(string), x, y, color, 0, shadow, false, 0xF000F0);
    }

    public final int renderText(Typeface typeface, Text text, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, text, x, y, color, 0, shadow, false, 0xF000F0);
    }

    public final int renderTextFromStart(Typeface typeface, String string, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, string, x, y, color, shadow);
    }

    public final int renderTextFromStart(Typeface typeface, Text text, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, text, x, y, color, shadow);
    }

    public final int renderTextFromCenter(Typeface typeface, String string, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, string, x - typeface.measureWidth(string) / 2, y, color, shadow);
    }

    public final int renderTextFromCenter(Typeface typeface, Text text, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, text, x - typeface.measureWidth(text) / 2, y, color, shadow);
    }

    public final int renderTextFromEnd(Typeface typeface, String string, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, string, x - typeface.measureWidth(string), y, color, shadow);
    }

    public final int renderTextFromEnd(Typeface typeface, Text text, int x, int y, int color, boolean shadow) {
        return this.renderText(typeface, text, x - typeface.measureWidth(text), y, color, shadow);
    }

    public final void renderScrollingText(Typeface typeface, Text text, int x1, int y1, int width, int height) {
        this.renderScrollingText(typeface, text, x1, y1, x1 + width, y1 + height, -1);
    }

    public final void renderScrollingText(Typeface typeface, Text text, int x1, int y1, int x2, int y2, int color) {
        int textWidth = typeface.measureWidth(text);
        int containerHeight = (y1 + y2 - 9) / 2 + 1;
        int containerWidth = x2 - x1;
        if (textWidth > containerWidth) {
            int padding = 0;
            int extraWidth = textWidth - containerWidth + padding;
            float speed = 2.0f;
            double sec = (double)System.currentTimeMillis() / 1000.0 * (double)speed;
            double e = MathUtils.max(MathUtils.min((double)extraWidth * 0.5, 30.0), 3.0);
            double f = MathUtils.sin(1.5707963267948966 * MathUtils.cos(Math.PI * 2 * sec / e)) / 2.0 + 0.5;
            double offset = MathUtils.lerp(f, 0.0, (double)extraWidth);
            this.flush();
            this.pushScissor(x1 - 1, y1, x2 + 1 - (x1 - 1), y2 - y1);
            this.renderText(typeface, text, (int)Math.round((double)x1 - offset), y1, color, true);
            this.popScissor();
        } else {
            this.renderTextFromCenter(typeface, text, (x1 + x2) / 2, y1, color, true);
        }
    }

    public final void renderTexture(ResourceLocation location, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        this.renderTexture(location, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public final void renderTexture(ResourceLocation location, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        this.renderTexture(location, x1, x2, y1, y2, blitOffset, uOffset / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, vOffset / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
        this.flush();
    }

    private void renderTexture(ResourceLocation location, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        VertexBuffer buffer = this.vertexBuffer(RenderLayers.texture(location, false, false));
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, x1, y1, blitOffset).uv(minU, minV).endVertex();
        buffer.vertex(pose, x1, y2, blitOffset).uv(minU, maxV).endVertex();
        buffer.vertex(pose, x2, y2, blitOffset).uv(maxU, maxV).endVertex();
        buffer.vertex(pose, x2, y1, blitOffset).uv(maxU, minV).endVertex();
    }

    private void renderTexture(ResourceLocation location, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV, float red, float green, float blue, float alpha) {
        VertexBuffer buffer = this.vertexBuffer(RenderLayers.colorTexture(location, false, false));
        Matrix4f pose = this.lastMatrixPose();
        buffer.vertex(pose, x1, y1, blitOffset).color(red, green, blue, alpha).uv(minU, minV).endVertex();
        buffer.vertex(pose, x1, y2, blitOffset).color(red, green, blue, alpha).uv(minU, maxV).endVertex();
        buffer.vertex(pose, x2, y2, blitOffset).color(red, green, blue, alpha).uv(maxU, maxV).endVertex();
        buffer.vertex(pose, x2, y1, blitOffset).color(red, green, blue, alpha).uv(maxU, minV).endVertex();
    }

    public void renderPanelBackgroundTexture(int x, int y, float uOffset, float vOffset, int uWidth, int vHeight) {
    }

    public void renderSprite(TextureSprite sprite, int x, int y, int width, int height) {
        this.renderSprite(sprite, x, y, 0, width, height);
    }

    public void renderSprite(TextureSprite sprite, int x, int y, int blitOffset, int width, int height) {
        SpriteScaling spriteScaling = sprite.scaling();
        if (spriteScaling instanceof SpriteScaling.Stretch) {
            SpriteScaling.Stretch stretch = (SpriteScaling.Stretch)spriteScaling;
            this.renderSpriteInner(sprite, x, y, blitOffset, width, height);
        } else {
            spriteScaling = sprite.scaling();
            if (spriteScaling instanceof SpriteScaling.Tile) {
                SpriteScaling.Tile tile = (SpriteScaling.Tile)spriteScaling;
                this.renderTiledSprite(sprite, x, y, blitOffset, width, height, 0, 0, tile.width(), tile.height(), tile.width(), tile.height());
            } else {
                spriteScaling = sprite.scaling();
                if (spriteScaling instanceof SpriteScaling.NineSlice) {
                    SpriteScaling.NineSlice nineSlice = (SpriteScaling.NineSlice)spriteScaling;
                    this.renderNineSlicedSprite(sprite, nineSlice, x, y, blitOffset, width, height);
                }
            }
        }
    }

    public void renderSprite(TextureSprite sprite, int sliceWidth, int sliceHeight, int i, int j, int x, int y, int width, int height) {
        this.renderSprite(sprite, sliceWidth, sliceHeight, i, j, x, y, 0, width, height);
    }

    public void renderSprite(TextureSprite sprite, int sliceWidth, int sliceHeight, int i, int j, int x, int y, int blitOffset, int width, int height) {
        SpriteScaling spriteScaling = sprite.scaling();
        if (spriteScaling instanceof SpriteScaling.Stretch) {
            SpriteScaling.Stretch stretch = (SpriteScaling.Stretch)spriteScaling;
            this.renderSpriteInner(sprite, sliceWidth, sliceHeight, i, j, x, y, blitOffset, width, height);
        } else {
            this.renderSpriteInner(sprite, x, y, blitOffset, width, height);
        }
    }

    private void renderSpriteInner(TextureSprite sprite, int sliceWidth, int sliceHeight, int i, int j, int x, int y, int blitOffset, int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }
        this.renderTexture(sprite.texture(), x, x + width, y, y + height, blitOffset, sprite.u((float)i / (float)sliceWidth), sprite.u((float)(i + width) / (float)sliceWidth), sprite.v((float)j / (float)sliceHeight), sprite.v((float)(j + height) / (float)sliceHeight));
    }

    private void renderSpriteInner(TextureSprite sprite, int x, int y, int blitOffset, int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }
        this.renderTexture(sprite.texture(), x, x + width, y, y + height, blitOffset, sprite.u0(), sprite.u1(), sprite.v0(), sprite.v1());
    }

    private void renderNineSlicedSprite(TextureSprite sprite, SpriteScaling.NineSlice nineSlice, int x, int y, int blitOffset, int width, int height) {
        int i = Math.min(nineSlice.left(), width / 2);
        int j = Math.min(nineSlice.right(), width / 2);
        int k = Math.min(nineSlice.top(), height / 2);
        int l = Math.min(nineSlice.bottom(), height / 2);
        if (width == nineSlice.width() && height == nineSlice.height()) {
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), 0, 0, x, y, blitOffset, width, height);
        } else if (height == nineSlice.height()) {
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), 0, 0, x, y, blitOffset, i, height);
            this.renderTiledSprite(sprite, x + i, y, blitOffset, width - j - i, height, i, 0, nineSlice.width() - j - i, nineSlice.height(), nineSlice.width(), nineSlice.height());
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), nineSlice.width() - j, 0, x + width - j, y, blitOffset, j, height);
        } else if (width == nineSlice.width()) {
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), 0, 0, x, y, blitOffset, width, k);
            this.renderTiledSprite(sprite, x, y + k, blitOffset, width, height - l - k, 0, k, nineSlice.width(), nineSlice.height() - l - k, nineSlice.width(), nineSlice.height());
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), 0, nineSlice.height() - l, x, y + height - l, blitOffset, width, l);
        } else {
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), 0, 0, x, y, blitOffset, i, k);
            this.renderTiledSprite(sprite, x + i, y, blitOffset, width - j - i, k, i, 0, nineSlice.width() - j - i, k, nineSlice.width(), nineSlice.height());
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), nineSlice.width() - j, 0, x + width - j, y, blitOffset, j, k);
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), 0, nineSlice.height() - l, x, y + height - l, blitOffset, i, l);
            this.renderTiledSprite(sprite, x + i, y + height - l, blitOffset, width - j - i, l, i, nineSlice.height() - l, nineSlice.width() - j - i, l, nineSlice.width(), nineSlice.height());
            this.renderSpriteInner(sprite, nineSlice.width(), nineSlice.height(), nineSlice.width() - j, nineSlice.height() - l, x + width - j, y + height - l, blitOffset, j, l);
            this.renderTiledSprite(sprite, x, y + k, blitOffset, i, height - l - k, 0, k, i, nineSlice.height() - l - k, nineSlice.width(), nineSlice.height());
            this.renderTiledSprite(sprite, x + i, y + k, blitOffset, width - j - i, height - l - k, i, k, nineSlice.width() - j - i, nineSlice.height() - l - k, nineSlice.width(), nineSlice.height());
            this.renderTiledSprite(sprite, x + width - j, y + k, blitOffset, i, height - l - k, nineSlice.width() - j, k, j, nineSlice.height() - l - k, nineSlice.width(), nineSlice.height());
        }
    }

    private void renderTiledSprite(TextureSprite sprite, int x, int y, int blitOffset, int width, int height, int i, int j, int spriteWidth, int spriteHeight, int nineSliceWidth, int nineSliceHeight) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (spriteWidth <= 0 || spriteHeight <= 0) {
            throw new IllegalArgumentException("Tiled sprite texture size must be positive, got " + spriteWidth + "x" + spriteHeight);
        }
        for (int k = 0; k < width; k += spriteWidth) {
            int l = Math.min(spriteWidth, width - k);
            for (int m = 0; m < height; m += spriteHeight) {
                int n = Math.min(spriteHeight, height - m);
                this.renderSpriteInner(sprite, nineSliceWidth, nineSliceHeight, i, j, x + k, y + m, blitOffset, l, n);
            }
        }
    }

    public abstract void renderItem(ItemStack var1, int var2, int var3);

    public final void renderItem(Typeface typeface, ItemStack stack, int x, int y, @Nullable Text text) {
        this.renderItem(stack, x + 1, y + 1);
        this.pushPose();
        this.translate(0.0f, 0.0f, 200.0f);
        this.renderText(typeface, text, x + 19 - 2 - typeface.measureWidth(text), y + 6 + 3, 0xFFFFFF, true);
        this.popPose();
    }

    public abstract void renderTooltip(Typeface var1, List<Text> var2, int var3, int var4);

    public abstract void renderBlockState(RenderLayer var1, World var2, BlockPosition var3, BlockState var4);

    public abstract void renderBlockEntity(RenderLayer var1, World var2, BlockPosition var3, BlockEntity var4);

    private static class ScissorStack {
        private final Deque<ScreenRect> stack = new ArrayDeque<ScreenRect>();

        private ScissorStack() {
        }

        public ScreenRect push(ScreenRect pScissor) {
            ScreenRect rect = this.stack.peekLast();
            if (rect != null) {
                ScreenRect rect1 = Objects.requireNonNullElse(pScissor.intersection(rect), ScreenRect.empty());
                this.stack.addLast(rect1);
                return rect1;
            }
            this.stack.addLast(pScissor);
            return pScissor;
        }

        @Nullable
        public ScreenRect pop() {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException("Scissor stack underflow");
            }
            this.stack.removeLast();
            return this.stack.peekLast();
        }
    }
}
