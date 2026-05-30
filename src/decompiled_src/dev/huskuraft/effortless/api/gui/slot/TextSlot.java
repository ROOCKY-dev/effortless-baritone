/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.slot;

import dev.huskuraft.effortless.api.gui.slot.Slot;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;

public class TextSlot
extends Slot {
    private final Text symbol;
    private final int color;

    public TextSlot(Entrance entrance, int x, int y, int width, int height, Text message) {
        this(entrance, x, y, width, height, message, -1620284308);
    }

    public TextSlot(Entrance entrance, int x, int y, int width, int height, Text message, int color) {
        this(entrance, x, y, width, height, message, null, color);
    }

    public TextSlot(Entrance entrance, int x, int y, int width, int height, Text message, Text symbol) {
        this(entrance, x, y, width, height, message, symbol, -1620284308);
    }

    public TextSlot(Entrance entrance, int x, int y, int width, int height, Text message, Text symbol, int color) {
        super(entrance, x, y, width, height, message);
        this.symbol = symbol == null ? null : Text.text(symbol.getString().substring(0, Math.min(symbol.getString().length(), 1)).toUpperCase());
        this.color = color;
    }

    public Text getSymbol() {
        return this.symbol;
    }

    @Override
    public int getFullWidth() {
        if (this.getMessage() == null) {
            return this.getWidth();
        }
        return Math.max(this.getTypeface().measureWidth(this.getMessage()) + 3, this.getWidth());
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        renderer.pushPose();
        renderer.renderRect(this.getX(), this.getY(), this.getX() + this.getFullWidth(), this.getY() + this.getHeight(), this.color);
        renderer.translate(this.getFullWidth() - this.getWidth(), 0.0f, 0.0f);
        if (this.symbol != null) {
            renderer.pushPose();
            renderer.translate(this.getX(), this.getY(), 0.0f);
            renderer.scale((float)this.getWidth() / 18.0f, (float)this.getHeight() / 18.0f, 0.0f);
            renderer.translate(9.5f, 10.0f, 0.0f);
            renderer.pushPose();
            renderer.scale(18.0f / (float)this.getTypeface().getLineHeight(), 18.0f / (float)this.getTypeface().getLineHeight(), 0.0f);
            renderer.translate(0.0f, (float)(-this.getTypeface().measureHeight(this.getSymbol())) / 2.0f, 0.0f);
            renderer.renderTextFromCenter(this.getTypeface(), this.getSymbol(), 0, 0, -9671572, false);
            renderer.popPose();
            renderer.popPose();
        }
        if (this.getMessage() != null) {
            renderer.pushPose();
            renderer.translate(this.getX(), this.getY(), 0.0f);
            renderer.scale((float)this.getWidth() / 18.0f, (float)this.getHeight() / 18.0f, 0.0f);
            renderer.translate(17.0f, 18.0f, 0.0f);
            renderer.pushPose();
            renderer.translate(0.0f, -this.getTypeface().measureHeight(this.getMessage()), 0.0f);
            renderer.renderTextFromEnd(this.getTypeface(), this.getMessage(), 0, 0, -1, true);
            renderer.popPose();
            renderer.popPose();
        }
        renderer.popPose();
    }
}
