/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;

final class StringWriter {
    private static final char[] ESCAPED_B = new char[]{'\\', 'b'};
    private static final char[] ESCAPED_F = new char[]{'\\', 'f'};
    private static final char[] ESCAPED_N = new char[]{'\\', 'n'};
    private static final char[] ESCAPED_R = new char[]{'\\', 'r'};
    private static final char[] ESCAPED_T = new char[]{'\\', 't'};
    private static final char[] ESCAPED_BACKSLASH = new char[]{'\\', '\\'};
    private static final char[] ESCAPED_QUOTE = new char[]{'\\', '\"'};

    static void writeBasic(CharSequence csq, CharacterOutput output) {
        output.write('\"');
        int l = csq.length();
        for (int i = 0; i < l; ++i) {
            StringWriter.writeBasicChar(csq.charAt(i), output);
        }
        output.write('\"');
    }

    static void writeLiteral(String str, CharacterOutput output) {
        output.write('\'');
        output.write(str);
        output.write('\'');
    }

    private static void writeBasicChar(char c, CharacterOutput output) {
        switch (c) {
            case '\\': {
                output.write(ESCAPED_BACKSLASH);
                break;
            }
            case '\"': {
                output.write(ESCAPED_QUOTE);
                break;
            }
            case '\b': {
                output.write(ESCAPED_B);
                break;
            }
            case '\f': {
                output.write(ESCAPED_F);
                break;
            }
            case '\n': {
                output.write(ESCAPED_N);
                break;
            }
            case '\r': {
                output.write(ESCAPED_R);
                break;
            }
            case '\t': {
                output.write(ESCAPED_T);
                break;
            }
            default: {
                output.write(c);
            }
        }
    }

    private StringWriter() {
    }
}
