/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

public enum NewlineStyle {
    UNIX('\n'),
    WINDOWS('\r', '\n');

    public final char[] chars;

    private NewlineStyle(char ... chars) {
        this.chars = chars;
    }

    public static NewlineStyle system() {
        String systemNewline = System.getProperty("line.separator");
        if (systemNewline.equals("\n")) {
            return UNIX;
        }
        if (systemNewline.equals("\r\n")) {
            return WINDOWS;
        }
        throw new IllegalArgumentException("Unknown system line separator '" + systemNewline + "'. The NewlineStyle enum only supports LF and CRLF.");
    }
}
