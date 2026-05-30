/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.text;

import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;

public class TextWidget
extends AbstractWidget {
    private int color = 0xFFFFFF;
    private Gravity gravity = Gravity.START;

    public TextWidget(Entrance entrance, int x, int y, Text message) {
        super(entrance, x, y, 0, 0, message);
    }

    public TextWidget(Entrance entrance, int x, int y, Text message, Gravity gravity) {
        super(entrance, x, y, 0, 0, message);
        this.gravity = gravity;
    }

    public TextWidget(Entrance entrance, int x, int y, int width, int height, Text message, Gravity gravity) {
        super(entrance, x, y, width, height, message);
        this.gravity = gravity;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    @Override
    public int getWidth() {
        return super.getWidth() == 0 ? this.getTypeface().measureWidth(this.getMessage()) : super.getWidth();
    }

    @Override
    public int getHeight() {
        return this.getTypeface().measureHeight(this.getMessage());
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        if (this.getTypeface().measureWidth(this.getMessage()) > this.getWidth()) {
            switch (this.gravity.ordinal()) {
                case 0: {
                    renderer.renderScrollingText(this.getTypeface(), this.getMessage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
                    break;
                }
                case 2: {
                    renderer.renderScrollingText(this.getTypeface(), this.getMessage(), this.getX() - this.getWidth() / 2, this.getY(), this.getWidth(), this.getHeight());
                    break;
                }
                case 1: {
                    renderer.renderScrollingText(this.getTypeface(), this.getMessage(), this.getX() + this.getWidth() / 2, this.getY(), this.getWidth(), this.getHeight());
                }
            }
            return;
        }
        switch (this.gravity.ordinal()) {
            case 0: {
                renderer.renderTextFromStart(this.getTypeface(), this.getMessage(), this.getX(), this.getY(), this.color, false);
                break;
            }
            case 2: {
                renderer.renderTextFromCenter(this.getTypeface(), this.getMessage(), this.getX(), this.getY(), this.color, false);
                break;
            }
            case 1: {
                renderer.renderTextFromEnd(this.getTypeface(), this.getMessage(), this.getX(), this.getY(), this.color, false);
            }
        }
    }

    public static enum Gravity {
        START,
        END,
        CENTER;

    }
}
