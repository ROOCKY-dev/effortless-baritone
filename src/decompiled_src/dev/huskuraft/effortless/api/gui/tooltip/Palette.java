/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.tooltip;

import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Style;

public record Palette(Style primary, Style highlight) {
    public static final Palette BLUE = Palette.ofColors(ChatFormatting.BLUE, ChatFormatting.AQUA);
    public static final Palette GREEN = Palette.ofColors(ChatFormatting.DARK_GREEN, ChatFormatting.GREEN);
    public static final Palette YELLOW = Palette.ofColors(ChatFormatting.GOLD, ChatFormatting.YELLOW);
    public static final Palette RED = Palette.ofColors(ChatFormatting.DARK_RED, ChatFormatting.RED);
    public static final Palette PURPLE = Palette.ofColors(ChatFormatting.DARK_PURPLE, ChatFormatting.LIGHT_PURPLE);
    public static final Palette GRAY = Palette.ofColors(ChatFormatting.DARK_GRAY, ChatFormatting.GRAY);
    public static final Palette ALL_GRAY = Palette.ofColors(ChatFormatting.GRAY, ChatFormatting.GRAY);
    public static final Palette GRAY_AND_BLUE = Palette.ofColors(ChatFormatting.GRAY, ChatFormatting.BLUE);
    public static final Palette GRAY_AND_WHITE = Palette.ofColors(ChatFormatting.GRAY, ChatFormatting.WHITE);
    public static final Palette GRAY_AND_GOLD = Palette.ofColors(ChatFormatting.GRAY, ChatFormatting.GOLD);
    public static final Palette GRAY_AND_RED = Palette.ofColors(ChatFormatting.GRAY, ChatFormatting.RED);

    public static Style styleFromColor(ChatFormatting color) {
        return Style.EMPTY.applyFormat(color);
    }

    public static Style styleFromColor(int hex) {
        return Style.EMPTY.withColor(hex);
    }

    public static Palette ofColors(ChatFormatting primary, ChatFormatting highlight) {
        return new Palette(Palette.styleFromColor(primary), Palette.styleFromColor(highlight));
    }
}
