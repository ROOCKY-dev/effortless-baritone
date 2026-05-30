/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.button;

import dev.huskuraft.effortless.api.gui.button.Button;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;

public class Checkbox
extends Button {
    private static final int CHECKBOX_SIZE = 11;
    private boolean isChecked;

    public Checkbox(Entrance entrance, int x, int y, String name, boolean isChecked) {
        super(entrance, x, y, 0, 0, Text.text(name), (Button b) -> {});
        this.isChecked = isChecked;
    }

    @Override
    public int getWidth() {
        return this.getTypeface().measureWidth(this.getMessage()) + 2 + this.getCheckboxSize();
    }

    @Override
    public int getHeight() {
        return this.getCheckboxSize();
    }

    public int getCheckboxSize() {
        return 11;
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        int color = 0xE0E0E0;
        int packedFGColor = 0;
        if (packedFGColor != 0) {
            color = packedFGColor;
        } else if (!this.isActive()) {
            color = 0xA0A0A0;
        }
        if (this.isChecked) {
            renderer.renderTextFromCenter(this.getTypeface(), "x", this.getX() + this.getCheckboxSize() / 2 + 1, this.getY() + 1, 0xE0E0E0, true);
        }
        renderer.renderTextFromStart(this.getTypeface(), this.getMessage(), this.getX() + this.getCheckboxSize() + 2, this.getY() + 2, color, true);
    }

    @Override
    public void onPress() {
        this.isChecked = !this.isChecked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
