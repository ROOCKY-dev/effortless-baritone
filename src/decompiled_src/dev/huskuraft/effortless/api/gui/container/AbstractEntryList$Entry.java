/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.AbstractContainerWidget;
import dev.huskuraft.effortless.api.gui.EntryList;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;

public static abstract class AbstractEntryList.Entry
extends AbstractContainerWidget
implements EntryList.Entry {
    protected AbstractEntryList.Entry(Entrance entrance) {
        super(entrance, 0, 0, 0, 0, Text.empty());
        this.setFocusable(true);
    }

    @Override
    public void onPositionChange(int from, int to) {
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onDeselected() {
    }

    @Deprecated
    public Text getNarration() {
        return Text.empty();
    }

    @Override
    public void render(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        renderer.pushScissor(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        super.render(renderer, mouseX, mouseY, deltaTick);
        renderer.popScissor();
    }

    @Override
    public int getWidth() {
        return MathUtils.min(282, this.getParent().getWidth() - 8);
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        boolean result = super.onMouseClicked(mouseX, mouseY, button);
        boolean mouseOver = this.isMouseOver(mouseX, mouseY);
        if (!result && mouseOver) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
        }
        return result || mouseOver;
    }
}
