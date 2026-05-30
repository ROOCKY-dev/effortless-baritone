/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;

public interface CharacterInput {
    public int read();

    public char readChar();

    default public int readAndSkip(char[] toSkip) {
        int c;
        while ((c = this.read()) != -1 && Utils.arrayContains(toSkip, (char)c)) {
        }
        return c;
    }

    default public char readCharAndSkip(char[] toSkip) {
        char c;
        while (Utils.arrayContains(toSkip, c = this.readChar())) {
        }
        return c;
    }

    default public CharsWrapper read(int n) {
        int next;
        CharsWrapper.Builder builder = new CharsWrapper.Builder(n);
        for (int i = 0; i < n && (next = this.read()) != -1; ++i) {
            builder.append((char)next);
        }
        return builder.build();
    }

    default public CharsWrapper readChars(int n) {
        char[] chars = new char[n];
        for (int i = 0; i < n; ++i) {
            int next = this.read();
            if (next == -1) {
                throw ParsingException.notEnoughData();
            }
            chars[i] = (char)next;
        }
        return new CharsWrapper(chars);
    }

    public CharsWrapper readUntil(char[] var1);

    public CharsWrapper readCharsUntil(char[] var1);

    public int peek();

    public int peek(int var1);

    public char peekChar();

    public char peekChar(int var1);

    public void skipPeeks();

    public void pushBack(char var1);
}
