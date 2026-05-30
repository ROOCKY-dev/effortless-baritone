/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import dev.huskuraft.effortless.api.nightconfig.toml.Toml;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlParser;

final class StringParser {
    private static final char[] SINGLE_QUOTE = new char[]{'\''};

    static String parseBasic(CharacterInput input, TomlParser parser) {
        char c;
        CharsWrapper.Builder builder = parser.createBuilder();
        boolean escape = false;
        while ((c = input.readChar()) != '\"' || escape) {
            if (escape) {
                builder.write(StringParser.escape(c, input));
                escape = false;
                continue;
            }
            if (c == '\\') {
                escape = true;
                continue;
            }
            builder.write(c);
        }
        return builder.toString();
    }

    static String parseLiteral(CharacterInput input, TomlParser parser) {
        String str = input.readCharsUntil(SINGLE_QUOTE).toString();
        input.readChar();
        return str;
    }

    static String parseMultiBasic(CharacterInput input, TomlParser parser) {
        char c;
        CharsWrapper.Builder builder = parser.createBuilder();
        while ((c = input.readChar()) != '\"' || input.peek() != 34 || input.peek(1) != 34) {
            if (c == '\\') {
                char next = input.readChar();
                if (next == '\n' || next == '\r' && input.peekChar() == '\n' || (next == '\t' || next == ' ') && StringParser.isWhitespace(Toml.readLine(input))) {
                    char nextNonSpace = Toml.readNonSpaceChar(input, true);
                    input.pushBack(nextNonSpace);
                    continue;
                }
                if (next == '\t' || next == ' ') {
                    throw new ParsingException("Invalid escapement: \\" + next);
                }
                builder.write(StringParser.escape(next, input));
                continue;
            }
            builder.write(c);
        }
        input.skipPeeks();
        return StringParser.buildMultilineString(builder);
    }

    static String parseMultiLiteral(CharacterInput input, TomlParser parser) {
        char c;
        CharsWrapper.Builder builder = parser.createBuilder();
        while ((c = input.readChar()) != '\'' || input.peek() != 39 || input.peek(1) != 39) {
            builder.append(c);
        }
        input.skipPeeks();
        return StringParser.buildMultilineString(builder);
    }

    private static String buildMultilineString(CharsWrapper.Builder builder) {
        if (builder.get(0) == '\n') {
            return builder.toString(1);
        }
        if (builder.get(0) == '\r' && builder.get(1) == '\n') {
            return builder.toString(2);
        }
        return builder.toString();
    }

    private static char escape(char c, CharacterInput input) {
        switch (c) {
            case '\"': 
            case '\\': {
                return c;
            }
            case 'b': {
                return '\b';
            }
            case 'f': {
                return '\f';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 't': {
                return '\t';
            }
            case 'u': {
                CharsWrapper chars = input.readChars(4);
                return (char)Utils.parseInt(chars, 16);
            }
            case 'U': {
                CharsWrapper chars = input.readChars(8);
                return (char)Utils.parseInt(chars, 16);
            }
        }
        throw new ParsingException("Invalid escapement: \\" + c);
    }

    private static boolean isWhitespace(CharSequence csq) {
        for (int i = 0; i < csq.length(); ++i) {
            char c = csq.charAt(i);
            if (c == '\t' || c == ' ') continue;
            return false;
        }
        return true;
    }

    private StringParser() {
    }
}
