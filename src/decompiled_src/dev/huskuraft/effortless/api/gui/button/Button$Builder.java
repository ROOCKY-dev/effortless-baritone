/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.gui.button;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.gui.button.Button;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.text.Text;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public static class Button.Builder {
    private Text text = Text.empty();
    private ResourceLocation icon;
    private final Button.OnPress onPress;
    private final Entrance entrance;
    @Nullable
    private List<Text> tooltip = new ArrayList<Text>();
    private int x;
    private int y;
    private int width = 150;
    private int height = 20;

    public Button.Builder(Entrance entrance, Button.OnPress onPress) {
        this.entrance = entrance;
        this.onPress = onPress;
    }

    public Button.Builder setText(Text text) {
        this.text = text;
        return this;
    }

    public Button.Builder setIcon(ResourceLocation icon) {
        this.icon = icon;
        return this;
    }

    public Button.Builder setPos(int i, int j) {
        this.x = i;
        this.y = j;
        return this;
    }

    public Button.Builder setWidth(int i) {
        this.width = i;
        return this;
    }

    public Button.Builder setSize(int i, int j) {
        this.width = i;
        this.height = j;
        return this;
    }

    public Button.Builder setBounds(int x, int y, int width, int height) {
        return this.setPos(x, y).setSize(width, height);
    }

    public Button.Builder setBoundsGrid(int width, int height, float row, float col, float size) {
        return this.setBounds((int)((float)width / 2.0f - 144.0f - 9.0f + col * 4.0f * 78.0f), (int)((float)(height - 20 - 8) - row * 24.0f), (int)(72.0f * size * 4.0f + 6.0f * (size * 4.0f - 1.0f)), 20);
    }

    public Button.Builder setBoundsGrid(int x, int y, int width, int height, float row, float col, float size) {
        float innerSize = 1.0f / size;
        float innerWidth = (float)width - (innerSize - 1.0f) * 4.0f - 12.0f;
        float index = col / size;
        float buttonWidth = innerWidth / innerSize;
        return this.setBounds((int)((float)x + index * (buttonWidth + 4.0f) + 6.0f), (int)((float)y + ((float)height - row * 24.0f - 20.0f - 6.0f)), (int)buttonWidth, 20);
    }

    public Button.Builder setTooltip(Text tooltip) {
        this.tooltip = List.of(tooltip);
        return this;
    }

    public Button.Builder setTooltip(List<Text> tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public Button build() {
        Button button = new Button(this.entrance, this.x, this.y, this.width, this.height, this.text, this.icon, this.onPress);
        button.setTooltip(this.tooltip);
        return button;
    }
}
