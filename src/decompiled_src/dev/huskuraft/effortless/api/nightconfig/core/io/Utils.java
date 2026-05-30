/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;

public final class Utils {
    private Utils() {
    }

    public static boolean arrayContains(char[] array, char element) {
        for (char c : array) {
            if (c != element) continue;
            return true;
        }
        return false;
    }

    public static int arrayIndexOf(char[] array, char element) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != element) continue;
            return i;
        }
        return -1;
    }

    public static long parseLong(CharsWrapper chars, int base) {
        int offset = chars.offset;
        boolean negative = false;
        char firstChar = chars.charAt(0);
        if (firstChar == '-') {
            negative = true;
            ++offset;
        } else if (firstChar == '+') {
            ++offset;
        }
        long value = 0L;
        long coefficient = 1L;
        char[] array = chars.chars;
        for (int i = chars.limit - 1; i >= offset; --i) {
            int digitValue = Character.digit(array[i], base);
            if (digitValue == -1) {
                throw new ParsingException("Invalid value: " + chars);
            }
            value += (long)digitValue * coefficient;
            coefficient *= (long)base;
        }
        return negative ? -value : value;
    }

    public static int parseInt(CharsWrapper chars, int base) {
        return (int)Utils.parseLong(chars, base);
    }

    public static double parseDouble(CharsWrapper chars) {
        return Double.parseDouble(chars.toString());
    }
}
