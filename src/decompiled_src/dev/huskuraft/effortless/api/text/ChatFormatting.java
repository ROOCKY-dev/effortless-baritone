/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum ChatFormatting {
    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 0xAA0000),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 0xAA00AA),
    GOLD("GOLD", '6', 6, 0xFFAA00),
    GRAY("GRAY", '7', 7, 0xAAAAAA),
    DARK_GRAY("DARK_GRAY", '8', 8, 0x555555),
    BLUE("BLUE", '9', 9, 0x5555FF),
    GREEN("GREEN", 'a', 10, 0x55FF55),
    AQUA("AQUA", 'b', 11, 0x55FFFF),
    RED("RED", 'c', 12, 0xFF5555),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 0xFF55FF),
    YELLOW("YELLOW", 'e', 14, 0xFFFF55),
    WHITE("WHITE", 'f', 15, 0xFFFFFF),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1, null);

    public static final char PREFIX_CODE = '\u00a7';
    private static final Pattern STRIP_FORMATTING_PATTERN;
    private static final Map<String, ChatFormatting> FORMATTING_BY_NAME;
    private final String name;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private final int id;
    @Nullable
    private final Integer color;

    private ChatFormatting(String name, @Nullable char code, int id, Integer color) {
        this(name, code, false, id, color);
    }

    private ChatFormatting(String name, char code, boolean isFormat) {
        this(name, code, isFormat, -1, null);
    }

    private ChatFormatting(String name, char code, @Nullable boolean format, int id, Integer color) {
        this.name = name;
        this.code = code;
        this.isFormat = format;
        this.id = id;
        this.color = color;
        this.toString = "\u00a7" + code;
    }

    private static String cleanName(String string) {
        return string.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    @Nullable
    public static String stripFormatting(@Nullable String text) {
        return text == null ? null : STRIP_FORMATTING_PATTERN.matcher(text).replaceAll("");
    }

    @Nullable
    public static ChatFormatting getByName(@Nullable String friendlyName) {
        return friendlyName == null ? null : FORMATTING_BY_NAME.get(ChatFormatting.cleanName(friendlyName));
    }

    @Nullable
    public static ChatFormatting getById(int index) {
        if (index < 0) {
            return RESET;
        }
        for (ChatFormatting textStyle : ChatFormatting.values()) {
            if (textStyle.getId() != index) continue;
            return textStyle;
        }
        return null;
    }

    @Nullable
    public static ChatFormatting getByCode(char code) {
        char c0 = Character.toLowerCase(code);
        for (ChatFormatting textStyle : ChatFormatting.values()) {
            if (textStyle.code != c0) continue;
            return textStyle;
        }
        return null;
    }

    public static Collection<String> getNames(boolean getColor, boolean getFancyStyling) {
        ArrayList<String> list = new ArrayList<String>();
        for (ChatFormatting textStyle : ChatFormatting.values()) {
            if (textStyle.isColor() && !getColor || textStyle.isFormat() && !getFancyStyling) continue;
            list.add(textStyle.getName());
        }
        return list;
    }

    public char getChar() {
        return this.code;
    }

    public int getId() {
        return this.id;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat && this != RESET;
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.toString;
    }

    static {
        STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
        FORMATTING_BY_NAME = Arrays.stream(ChatFormatting.values()).collect(Collectors.toMap(textStyle -> ChatFormatting.cleanName(textStyle.name), textStyle -> textStyle));
    }
}
