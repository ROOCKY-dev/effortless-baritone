/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.text;

import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.gui.tooltip.TooltipHelper;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;
import java.util.List;

public class MessageTextWidget
extends AbstractWidget {
    private int color = 0xFFFFFF;
    private final List<Text> texts;
    private Gravity gravity = Gravity.START;

    public MessageTextWidget(Entrance entrance, int x, int y, int width, int height, Text message, Gravity gravity) {
        super(entrance, x, y, width, height, message);
        this.texts = TooltipHelper.wrapLines(this.getTypeface(), message, width);
        this.gravity = gravity;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public int getTextWidth() {
        return super.getWidth() == 0 ? this.getTypeface().measureWidth(this.getMessage()) : super.getWidth();
    }

    public int getTextHeight() {
        return this.texts.stream().mapToInt(this.getTypeface()::measureHeight).sum();
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        int newY = this.getY() + (this.getHeight() - this.getTextHeight()) / 2;
        for (Text text : this.texts) {
            int textWidth = this.getTypeface().measureWidth(text);
            int newX = this.getX() + (this.getWidth() - textWidth) / 2;
            renderer.renderText(this.getTypeface(), text, newX, newY, this.color, false);
            newY += this.getTypeface().measureHeight(text);
        }
    }

    @Override
    public void renderWidgetBackground(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        renderer.renderGradientRect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), -16777216, -16777216);
    }

    public static enum Gravity {
        START,
        END,
        CENTER;

    }
}
