/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.gui.Typeface;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.api.texture.TextureFactory;

public abstract class AbstractButton
extends AbstractWidget {
    private final ResourceLocation icon;

    protected AbstractButton(Entrance entrance, int x, int y, int width, int height, Text message, ResourceLocation icon) {
        super(entrance, x, y, width, height, message);
        this.icon = icon;
        this.focusable = true;
    }

    protected abstract void onPress();

    public void onClick(double d, double e) {
        this.onPress();
    }

    public void onRelease(double d, double e) {
    }

    public void onDrag(double d, double e, double f, double g) {
    }

    protected boolean isClickable(double d, double e) {
        return this.isMouseOver(d, e);
    }

    @Override
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.isActive() && this.isVisible()) {
            if (keyCode != 257 && keyCode != 32 && keyCode != 335) {
                return false;
            }
            this.playDownSound();
            this.onPress();
            return true;
        }
        return false;
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        this.renderButtonBackground(renderer, mouseX, mouseY, deltaTick);
        if (this.icon == null) {
            this.renderScrollingString(renderer, this.getTypeface(), 2, (this.isActive() ? 0xFFFFFF : 0xA0A0A0) | (int)MathUtils.ceil(this.getAlpha() * 255.0f) << 24);
        } else {
            this.renderIconScrollingString(renderer, this.getTypeface(), 2, (this.isActive() ? 0xFFFFFF : 0xA0A0A0) | (int)MathUtils.ceil(this.getAlpha() * 255.0f) << 24);
            if (!this.isActive()) {
                renderer.setRsShaderColor(0.72f, 0.72f, 0.72f, 0.75f);
                this.renderIcon(renderer);
                renderer.setRsShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            } else {
                this.renderIcon(renderer);
            }
        }
    }

    public void renderButtonBackground(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        renderer.renderSprite(TextureFactory.getInstance().getButtonTextureSprite(this.isActive(), this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    protected void renderScrollingString(Renderer renderer, Typeface typeface, int padding, int color) {
        int left = this.getX() + padding;
        int right = this.getX() + this.getWidth() - padding;
        Text message = this.isActive() ? this.getMessage() : this.getMessage().withStyle(ChatFormatting.RESET);
        renderer.renderScrollingText(typeface, message, left, this.getY() + (this.getHeight() - this.getTypeface().measureHeight(message)) / 2 + 1, right, this.getY() + this.getHeight(), color);
    }

    protected void renderIcon(Renderer renderer) {
        float left = (float)(-(Math.min(this.getTypeface().measureWidth(this.getMessage()), this.getWidth() - 18) + 18)) / 2.0f + (float)this.getWidth() / 2.0f + (float)this.getX() + 1.0f;
        int top = this.getTop() + 2;
        renderer.renderTexture(this.icon, (int)left, top, 16, 16, 0.0f, 0.0f, 16, 16, 16, 16);
    }

    protected void renderIconScrollingString(Renderer renderer, Typeface typeface, int padding, int color) {
        int left = this.getX() + padding + 18;
        int right = this.getX() + this.getWidth() - padding;
        Text message = this.isActive() ? this.getMessage() : this.getMessage().withStyle(ChatFormatting.RESET);
        renderer.renderScrollingText(typeface, message, left, this.getY() + (this.getHeight() - this.getTypeface().measureHeight(message)) / 2 + 1, right, this.getY() + this.getHeight(), color);
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        boolean clickable;
        if (super.onMouseClicked(mouseX, mouseY, button) && this.isActive() && this.isVisible() && this.isMouseKeyValid(button) && (clickable = this.isClickable(mouseX, mouseY))) {
            this.playDownSound();
            this.onClick(mouseX, mouseY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isMouseKeyValid(button)) {
            this.onDrag(mouseX, mouseY, deltaX, deltaY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseReleased(double mouseX, double mouseY, int button) {
        if (this.isMouseKeyValid(button)) {
            this.onRelease(mouseX, mouseY);
            return true;
        }
        return false;
    }

    protected boolean isMouseKeyValid(int i) {
        return i == 0;
    }

    public void playDownSound() {
        this.getEntrance().getClient().getSoundManager().playButtonClickSound();
    }
}
