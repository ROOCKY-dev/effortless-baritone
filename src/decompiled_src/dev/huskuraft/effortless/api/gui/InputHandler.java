/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

public interface InputHandler {
    public static final long DOUBLE_CLICK_THRESHOLD_MS = 250L;

    public boolean isMouseOver(double var1, double var3);

    public boolean onMouseMoved(double var1, double var3);

    public boolean onMouseClicked(double var1, double var3, int var5);

    public boolean onMouseReleased(double var1, double var3, int var5);

    public boolean onMouseDragged(double var1, double var3, int var5, double var6, double var8);

    public boolean onMouseScrolled(double var1, double var3, double var5, double var7);

    public boolean onKeyPressed(int var1, int var2, int var3);

    public boolean onKeyReleased(int var1, int var2, int var3);

    public boolean onCharTyped(char var1, int var2);

    public boolean onFocusMove(boolean var1);
}
