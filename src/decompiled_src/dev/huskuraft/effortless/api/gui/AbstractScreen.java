/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.AbstractContainerWidget;
import dev.huskuraft.effortless.api.gui.Screen;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;

public abstract class AbstractScreen
extends AbstractContainerWidget
implements Screen {
    protected boolean transparentBackground = true;
    private int screenWidth = 0;
    private int screenHeight = 0;

    protected int getScreenWidth() {
        return this.screenWidth;
    }

    protected void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    protected int getScreenHeight() {
        return this.screenHeight;
    }

    protected void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    protected AbstractScreen(Entrance entrance, int x, int y, int width, int height, Text message) {
        super(entrance, x, y, width, height, message);
        this.focusable = true;
    }

    protected AbstractScreen(Entrance entrance, Text title) {
        super(entrance, title);
    }

    protected AbstractScreen(Entrance entrance) {
        super(entrance);
    }

    @Override
    public Text getScreenTitle() {
        return this.getMessage();
    }

    @Override
    public void init(int width, int height) {
        this.setScreenWidth(width);
        this.setScreenHeight(height);
        this.recreate();
    }

    @Override
    public int getWidth() {
        if (super.getWidth() == 0) {
            return this.getScreenWidth();
        }
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        if (super.getHeight() == 0) {
            return this.getScreenHeight();
        }
        return super.getHeight();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onDestroy() {
        this.clearWidgets();
    }

    @Override
    public void onAttach() {
    }

    @Override
    public void onDetach() {
    }

    @Override
    public void attach() {
        this.getEntrance().getClientManager().pushScreen(this);
        this.onAttach();
    }

    @Override
    public void detach() {
        this.getEntrance().getClientManager().popScreen(this);
        this.onDetach();
    }

    public void detachAll() {
        this.getEntrance().getClientManager().pushScreen(null);
        this.onDetach();
    }

    @Override
    public boolean isPauseGame() {
        return false;
    }

    @Override
    public void renderWidgetBackground(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        if (this.isTransparentBackground() && this.getEntrance().getClient().isLoaded()) {
            renderer.renderGradientRect(0, 0, this.getScreenWidth(), this.getScreenHeight(), -1072689136, -804253680);
        } else {
            renderer.setRsShaderColor(0.25f, 0.25f, 0.25f, 1.0f);
            renderer.renderPanelBackgroundTexture(0, 0, 0.0f, 0.0f, this.getWidth(), this.getHeight());
            renderer.setRsShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            this.detach();
            return true;
        }
        if (keyCode == 258) {
            boolean isShiftDown;
            boolean bl = isShiftDown = !this.getEntrance().getClient().getWindow().isShiftDown();
            if (!this.onFocusMove(isShiftDown)) {
                this.onFocusMove(isShiftDown);
            }
            return false;
        }
        return super.onKeyPressed(keyCode, scanCode, modifiers);
    }

    public boolean isTransparentBackground() {
        return this.transparentBackground;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }
}
