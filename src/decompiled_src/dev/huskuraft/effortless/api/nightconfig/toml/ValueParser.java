/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import dev.huskuraft.effortless.api.nightconfig.toml.ArrayParser;
import dev.huskuraft.effortless.api.nightconfig.toml.StringParser;
import dev.huskuraft.effortless.api.nightconfig.toml.TableParser;
import dev.huskuraft.effortless.api.nightconfig.toml.TemporalParser;
import dev.huskuraft.effortless.api.nightconfig.toml.Toml;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlParser;

final class ValueParser {
    private static final char[] END_OF_VALUE = new char[]{'\t', ' ', '\n', '\r', ',', ']', '}'};
    private static final char[] END_OF_VALUE_DATE = new char[]{'\t', '#', '\n', '\r', ',', ']', '}'};
    private static final char[] TRUE_END = new char[]{'r', 'u', 'e'};
    private static final char[] FALSE_END = new char[]{'a', 'l', 's', 'e'};
    private static final char[] ONLY_IN_FP_NUMBER = new char[]{'.', 'e', 'E'};
    private static final char[] FP_INFINITY = new char[]{'i', 'n', 'f'};
    private static final char[] FP_NAN = new char[]{'n', 'a', 'n'};

    static Object parse(CharacterInput input, char firstChar, TomlParser parser) {
        switch (firstChar) {
            case '{': {
                return TableParser.parseInline(input, parser);
            }
            case '[': {
                return ArrayParser.parse(input, parser);
            }
            case '\'': {
                if (input.peek() == 39 && input.peek(1) == 39) {
                    input.skipPeeks();
                    return StringParser.parseMultiLiteral(input, parser);
                }
                return StringParser.parseLiteral(input, parser);
            }
            case '\"': {
                if (input.peek() == 34 && input.peek(1) == 34) {
                    input.skipPeeks();
                    return StringParser.parseMultiBasic(input, parser);
                }
                return StringParser.parseBasic(input, parser);
            }
            case 't': {
                return ValueParser.parseTrue(input);
            }
            case 'f': {
                return ValueParser.parseFalse(input);
            }
            case '+': 
            case '-': {
                input.pushBack(firstChar);
                return ValueParser.parseNumber(input.readUntil(END_OF_VALUE));
            }
        }
        input.pushBack(firstChar);
        CharsWrapper valueChars = input.readUntil(END_OF_VALUE_DATE);
        if (ValueParser.shouldBeTemporal(valueChars)) {
            return TemporalParser.parse(valueChars);
        }
        CharsWrapper trimmed = valueChars.trimmedView();
        if (trimmed.isEmpty()) {
            throw new ParsingException("Invalid value containing only whitespaces");
        }
        return ValueParser.parseNumber(trimmed);
    }

    static Object parse(CharacterInput input, TomlParser parser) {
        return ValueParser.parse(input, Toml.readNonSpaceChar(input, false), parser);
    }

    private static boolean shouldBeTemporal(CharsWrapper valueChars) {
        return valueChars.length() >= 8 && (valueChars.get(2) == ':' || valueChars.get(4) == '-' && valueChars.get(7) == '-');
    }

    private static Number parseNumber(CharsWrapper valueChars) {
        long longValue;
        CharsWrapper remaining;
        char first = (valueChars = ValueParser.simplifyNumber(valueChars)).get(0);
        if (first == '-') {
            remaining = valueChars.subView(1);
            if (remaining.contentEquals(FP_INFINITY)) {
                return Double.NEGATIVE_INFINITY;
            }
        } else {
            remaining = first == '+' ? valueChars.subView(1) : valueChars;
        }
        if (remaining.contentEquals(FP_INFINITY)) {
            return Double.POSITIVE_INFINITY;
        }
        if (remaining.contentEquals(FP_NAN)) {
            return Double.NaN;
        }
        CharsWrapper numberChars = valueChars;
        int base = 10;
        if (valueChars.length() > 2) {
            switch (valueChars.subView(0, 2).toString()) {
                case "0x": {
                    base = 16;
                    break;
                }
                case "0b": {
                    base = 2;
                    break;
                }
                case "0o": {
                    base = 8;
                }
            }
            if (base != 10) {
                numberChars = valueChars.subView(2);
            }
        }
        if (base == 10 && valueChars.indexOf('.') != -1) {
            try {
                return Utils.parseDouble(valueChars);
            }
            catch (NumberFormatException ex) {
                throw new ParsingException("Invalid floating-point value: " + valueChars);
            }
        }
        try {
            longValue = Utils.parseLong(numberChars, base);
        }
        catch (NumberFormatException ex) {
            throw new ParsingException("Invalid integer value: " + valueChars);
        }
        int intValue = (int)longValue;
        if ((long)intValue == longValue) {
            return intValue;
        }
        return longValue;
    }

    private static CharsWrapper simplifyNumber(CharsWrapper numberChars) {
        if (numberChars.charAt(0) == '_') {
            throw new ParsingException("Invalid leading underscore in number " + numberChars);
        }
        if (numberChars.charAt(numberChars.length() - 1) == '_') {
            throw new ParsingException("Invalid trailing underscore in number " + numberChars);
        }
        CharsWrapper.Builder builder = new CharsWrapper.Builder(16);
        boolean nextCannotBeUnderscore = false;
        for (char c : numberChars) {
            if (c == '_') {
                if (nextCannotBeUnderscore) {
                    throw new ParsingException("Invalid underscore followed by another one in number " + numberChars);
                }
                nextCannotBeUnderscore = true;
                continue;
            }
            if (nextCannotBeUnderscore) {
                nextCannotBeUnderscore = false;
            }
            builder.append(c);
        }
        return builder.build();
    }

    private static Boolean parseFalse(CharacterInput input) {
        CharsWrapper remaining = input.readUntil(END_OF_VALUE);
        if (!remaining.contentEquals(FALSE_END)) {
            throw new ParsingException("Invalid value f" + remaining + " - Expected the boolean value false.");
        }
        return false;
    }

    private static Boolean parseTrue(CharacterInput input) {
        CharsWrapper remaining = input.readUntil(END_OF_VALUE);
        if (!remaining.contentEquals(TRUE_END)) {
            throw new ParsingException("Invalid value t" + remaining + " - Expected the boolean value true.");
        }
        return true;
    }

    private ValueParser() {
    }
}
