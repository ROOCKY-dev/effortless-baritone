/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.text;

import dev.huskuraft.effortless.api.text.ChatFormatting;
import javax.annotation.Nullable;

public record Style(@Nullable Integer color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated) {
    public static Style EMPTY = new Style(null, null, null, null, null, null);

    public Style applyFormat(ChatFormatting chatFormatting) {
        Integer color = this.color;
        Boolean bold = this.bold;
        Boolean italic = this.italic;
        Boolean strikethrough = this.strikethrough;
        Boolean underlined = this.underlined;
        Boolean obfuscated = this.obfuscated;
        switch (chatFormatting) {
            case OBFUSCATED: {
                obfuscated = true;
                break;
            }
            case BOLD: {
                bold = true;
                break;
            }
            case STRIKETHROUGH: {
                strikethrough = true;
                break;
            }
            case UNDERLINE: {
                underlined = true;
                break;
            }
            case ITALIC: {
                italic = true;
                break;
            }
            case RESET: {
                return EMPTY;
            }
            default: {
                color = chatFormatting.getColor();
            }
        }
        return new Style(color, bold, italic, underlined, strikethrough, obfuscated);
    }

    public Style applyFormat(ChatFormatting ... chatFormatting) {
        Style style = this;
        for (ChatFormatting style1 : chatFormatting) {
            style = style.applyFormat(style1);
        }
        return style;
    }

    public Style withColor(@Nullable Integer color) {
        return new Style(color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated);
    }

    public Style withColor(ChatFormatting color) {
        return new Style(color.getColor(), this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated);
    }

    public Style withBold(@Nullable Boolean bold) {
        return new Style(this.color, bold, this.italic, this.underlined, this.strikethrough, this.obfuscated);
    }

    public Style withItalic(@Nullable Boolean italic) {
        return new Style(this.color, this.bold, italic, this.underlined, this.strikethrough, this.obfuscated);
    }

    public Style withUnderlined(@Nullable Boolean underlined) {
        return new Style(this.color, this.bold, this.italic, underlined, this.strikethrough, this.obfuscated);
    }

    public Style withStrikethrough(@Nullable Boolean strikethrough) {
        return new Style(this.color, this.bold, this.italic, this.underlined, strikethrough, this.obfuscated);
    }

    public Style withObfuscated(@Nullable Boolean obfuscated) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, obfuscated);
    }
}
