/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.AbstractScreen;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.api.texture.TextureFactory;
import dev.huskuraft.effortless.api.texture.TextureSprite;

public abstract class AbstractPanelScreen
extends AbstractScreen {
    public static final int PANEL_WIDTH_64 = 280;
    public static final int PANEL_WIDTH_50 = 224;
    public static final int PANEL_WIDTH_42 = 192;
    public static final int PANEL_HEIGHT_FULL = 238;
    public static final int PANEL_HEIGHT_3_4 = 180;
    public static final int PANEL_HEIGHT_2_4 = 90;
    public static final int PANEL_HEIGHT_1_4 = 45;
    public static final int PANEL_BUTTON_ROW_HEIGHT_1 = 30;
    public static final int PANEL_BUTTON_ROW_HEIGHT_2 = 54;
    public static final int PANEL_BUTTON_ROW_HEIGHT_3 = 78;
    public static final int PANEL_BUTTON_ROW_HEIGHT_4 = 102;
    public static final int PANEL_BUTTON_ROW_HEIGHT_5 = 126;
    public static final int PANEL_BUTTON_ROW_HEIGHT_6 = 150;
    public static final int PANEL_BUTTON_ROW_HEIGHT_1N = 28;
    public static final int PANEL_BUTTON_ROW_HEIGHT_2N = 52;
    public static final int PANEL_BUTTON_ROW_HEIGHT_3N = 76;
    public static final int PANEL_BUTTON_ROW_HEIGHT_4N = 100;
    public static final int PANEL_BUTTON_ROW_HEIGHT_5N = 124;
    public static final int PANEL_BUTTON_ROW_HEIGHT_6N = 148;
    public static final int PADDINGS_H = 6;
    public static final int PADDINGS_V = 6;
    public static final int INNER_PADDINGS_H = 4;
    public static final int INNER_PADDINGS_V = 4;
    public static final int PANEL_TITLE_HEIGHT_1 = 18;
    public static final int PANEL_TITLE_HEIGHT_2 = 24;
    public static final TextureSprite DEMO_BACKGROUND_SPRITE = TextureFactory.getInstance().getDemoBackgroundTextureSprite();
    public static final int MAX_ANIMATION_TICKS = 4;
    protected float animationTicks = 0.0f;
    private boolean detached = false;
    private boolean detachedAll = false;
    public static final int TITLE_COLOR = 0x404040;

    protected AbstractPanelScreen(Entrance entrance, Text title, int width, int height) {
        super(entrance, 0, 0, width, height, title);
    }

    protected AbstractPanelScreen(Entrance entrance, Text title) {
        this(entrance, title, 0, 0);
    }

    protected AbstractPanelScreen(Entrance entrance) {
        this(entrance, Text.empty(), 0, 0);
    }

    @Override
    public void onAnimateTick(float partialTick) {
        this.animationTicks = Math.min(Math.max(this.animationTicks + (float)(this.detached ? -1 : 1) * partialTick, 0.0f), 4.0f);
        if (this.detached && this.animationTicks == 0.0f) {
            if (this.detachedAll) {
                super.detachAll();
            } else {
                super.detach();
            }
        }
    }

    @Override
    public int getX() {
        return this.getScreenWidth() / 2 - this.getWidth() / 2;
    }

    @Override
    public int getY() {
        return this.getScreenHeight() / 2 - this.getHeight() / 2;
    }

    private float getAnimationFactor() {
        float fac = 1.0f - Math.min(this.animationTicks, 4.0f) / 4.0f;
        if (this.detached) {
            return 1.0f - MathUtils.lerp((1.0f - fac) * (1.0f - fac), 1.0f, 0.0f);
        }
        return 1.0f - MathUtils.lerp(fac * fac, 0.0f, 1.0f);
    }

    @Override
    public void init(int width, int height) {
        super.init(width, height);
        if (this.detached) {
            if (this.detachedAll) {
                super.detachAll();
            } else {
                super.detach();
            }
        }
    }

    @Override
    public void detach() {
        this.detached = true;
    }

    @Override
    public void detachAll() {
        this.detached = true;
        this.detachedAll = true;
    }

    @Override
    public boolean isPauseGame() {
        return false;
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        renderer.pushPose();
        renderer.translate((float)this.getX() + (float)this.getWidth() / 2.0f, (float)this.getY() + (float)this.getHeight() / 2.0f, 0.0f);
        renderer.scale(MathUtils.lerp((double)this.getAnimationFactor(), 0.92, 1.0));
        renderer.translate((float)(-this.getX()) - (float)this.getWidth() / 2.0f, (float)(-this.getY()) - (float)this.getHeight() / 2.0f, 0.0f);
        renderer.setRsShaderColor(1.0f, 1.0f, 1.0f, this.getAnimationFactor());
        renderer.renderSprite(DEMO_BACKGROUND_SPRITE, this.getLeft(), this.getTop(), this.getWidth(), this.getHeight());
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        renderer.popPose();
    }
}
