/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.InputHandler;
import dev.huskuraft.effortless.api.gui.Renderable;

public interface Widget
extends Renderable,
InputHandler {
    public static final int UNSPECIFIC_SIZE = 0;

    public void onTick();

    public void onAnimateTick(float var1);

    public void onCreate();

    public void onReload();

    public void onDestroy();

    public boolean isVisible();

    public void setVisible(boolean var1);

    public boolean isActive();

    public void setActive(boolean var1);

    public boolean isHovered();

    public void setHovered(boolean var1);

    public int getX();

    public void setX(int var1);

    public void moveX(int var1);

    public int getY();

    public void setY(int var1);

    public void moveY(int var1);

    public int getWidth();

    public void setWidth(int var1);

    public int getHeight();

    public void setHeight(int var1);

    default public int getTop() {
        return this.getY();
    }

    default public int getBottom() {
        return this.getY() + this.getHeight();
    }

    default public int getLeft() {
        return this.getX();
    }

    default public int getRight() {
        return this.getX() + this.getWidth();
    }

    default public int getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }

    default public int getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public Widget getParent();

    default public void recreate() {
        this.onDestroy();
        this.onCreate();
        this.onReload();
    }
}
