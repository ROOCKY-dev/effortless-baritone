/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

public enum IndentStyle {
    TABS('\t'),
    SPACES_2(' ', ' '),
    SPACES_4(' ', ' ', ' ', ' '),
    SPACES_8(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
    NONE(new char[0]);

    public final char[] chars;

    private IndentStyle(char ... chars) {
        this.chars = chars;
    }
}
