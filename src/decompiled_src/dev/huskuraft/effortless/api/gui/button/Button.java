/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.gui.button;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.gui.AbstractButton;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.text.Text;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class Button
extends AbstractButton {
    public static final int BUTTON_WIDTH_1 = 72;
    public static final int BUTTON_WIDTH_2 = 150;
    public static final int BUTTON_WIDTH_4 = 306;
    public static final int TAB_WIDTH = 214;
    public static final int DEFAULT_HEIGHT = 20;
    public static final int VERTICAL_PADDING = 4;
    public static final int HORIZONTAL_PADDING = 6;
    public static final int COMPAT_SPACING_H = 4;
    public static final int COMPAT_SPACING_V = 4;
    public static final int PADDINGS = 6;
    private OnPress onPress;

    public Button(Entrance entrance, int x, int y, int width, int height, Text message) {
        super(entrance, x, y, width, height, message, null);
    }

    public Button(Entrance entrance, int x, int y, int width, int height, ResourceLocation icon) {
        super(entrance, x, y, width, height, Text.empty(), icon);
    }

    public Button(Entrance entrance, int x, int y, int width, int height, Text message, ResourceLocation icon) {
        super(entrance, x, y, width, height, message, icon);
    }

    public Button(Entrance entrance, int x, int y, int width, int height, Text message, OnPress onPress) {
        super(entrance, x, y, width, height, message, null);
        this.onPress = onPress;
    }

    public Button(Entrance entrance, int x, int y, int width, int height, ResourceLocation icon, OnPress onPress) {
        super(entrance, x, y, width, height, Text.empty(), icon);
        this.onPress = onPress;
    }

    public Button(Entrance entrance, int x, int y, int width, int height, Text message, ResourceLocation icon, OnPress onPress) {
        super(entrance, x, y, width, height, message, icon);
        this.onPress = onPress;
    }

    public static Builder builder(Entrance entrance, Text text, OnPress onPress) {
        return new Builder(entrance, onPress).setText(text);
    }

    public static Builder builder(Entrance entrance, ResourceLocation icon, OnPress onPress) {
        return new Builder(entrance, onPress).setIcon(icon);
    }

    public static Builder builder(Entrance entrance, Text text, ResourceLocation icon, OnPress onPress) {
        return new Builder(entrance, onPress).setText(text).setIcon(icon);
    }

    @Override
    protected void onPress() {
        if (this.onPress != null) {
            this.onPress.onPress(this);
        }
    }

    public void setOnPressListener(OnPress onPress) {
        this.onPress = onPress;
    }

    public static interface OnPress {
        public void onPress(Button var1);
    }

    public static class Builder {
        private Text text = Text.empty();
        private ResourceLocation icon;
        private final OnPress onPress;
        private final Entrance entrance;
        @Nullable
        private List<Text> tooltip = new ArrayList<Text>();
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Entrance entrance, OnPress onPress) {
            this.entrance = entrance;
            this.onPress = onPress;
        }

        public Builder setText(Text text) {
            this.text = text;
            return this;
        }

        public Builder setIcon(ResourceLocation icon) {
            this.icon = icon;
            return this;
        }

        public Builder setPos(int i, int j) {
            this.x = i;
            this.y = j;
            return this;
        }

        public Builder setWidth(int i) {
            this.width = i;
            return this;
        }

        public Builder setSize(int i, int j) {
            this.width = i;
            this.height = j;
            return this;
        }

        public Builder setBounds(int x, int y, int width, int height) {
            return this.setPos(x, y).setSize(width, height);
        }

        public Builder setBoundsGrid(int width, int height, float row, float col, float size) {
            return this.setBounds((int)((float)width / 2.0f - 144.0f - 9.0f + col * 4.0f * 78.0f), (int)((float)(height - 20 - 8) - row * 24.0f), (int)(72.0f * size * 4.0f + 6.0f * (size * 4.0f - 1.0f)), 20);
        }

        public Builder setBoundsGrid(int x, int y, int width, int height, float row, float col, float size) {
            float innerSize = 1.0f / size;
            float innerWidth = (float)width - (innerSize - 1.0f) * 4.0f - 12.0f;
            float index = col / size;
            float buttonWidth = innerWidth / innerSize;
            return this.setBounds((int)((float)x + index * (buttonWidth + 4.0f) + 6.0f), (int)((float)y + ((float)height - row * 24.0f - 20.0f - 6.0f)), (int)buttonWidth, 20);
        }

        public Builder setTooltip(Text tooltip) {
            this.tooltip = List.of(tooltip);
            return this;
        }

        public Builder setTooltip(List<Text> tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Button build() {
            Button button = new Button(this.entrance, this.x, this.y, this.width, this.height, this.text, this.icon, this.onPress);
            button.setTooltip(this.tooltip);
            return button;
        }
    }
}
