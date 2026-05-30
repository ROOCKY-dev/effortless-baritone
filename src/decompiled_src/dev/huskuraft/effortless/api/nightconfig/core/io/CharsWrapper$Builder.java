/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import java.io.Writer;
import java.util.Arrays;

public static final class CharsWrapper.Builder
extends Writer
implements CharacterOutput {
    private static final char[] NULL = new char[]{'n', 'u', 'l', 'l'};
    private char[] data;
    private int cursor = 0;

    public CharsWrapper.Builder(int initialCapacity) {
        this.data = new char[Math.min(2, initialCapacity)];
    }

    private void ensureCapacity(int capacity) {
        if (this.data.length < capacity) {
            int newCapacity = Math.max(capacity, this.data.length * 2);
            this.data = Arrays.copyOf(this.data, newCapacity);
        }
    }

    @Override
    public CharsWrapper.Builder append(char c) {
        this.write(c);
        return this;
    }

    @Override
    public CharsWrapper.Builder append(CharSequence csq) {
        if (csq == null) {
            return this.append(NULL);
        }
        if (csq instanceof String) {
            return this.append((String)csq);
        }
        return this.append(csq, 0, csq.length());
    }

    @Override
    public CharsWrapper.Builder append(CharSequence csq, int start, int end) {
        if (csq == null) {
            return this.append(NULL, start, end);
        }
        if (csq instanceof String) {
            return this.append((String)csq, start, end);
        }
        int length = end - start;
        int newCursor = this.cursor + length;
        this.ensureCapacity(newCursor);
        for (int i = start; i < end; ++i) {
            this.data[this.cursor + i] = csq.charAt(i);
        }
        this.cursor = newCursor;
        return this;
    }

    public CharsWrapper.Builder append(char ... chars) {
        this.write(chars);
        return this;
    }

    public CharsWrapper.Builder append(char[] chars, int begin, int end) {
        int length = end - begin;
        this.write(chars, begin, length);
        return this;
    }

    public CharsWrapper.Builder append(String str) {
        this.write(str);
        return this;
    }

    public CharsWrapper.Builder append(String str, int begin, int end) {
        int length = end - begin;
        this.write(str, begin, length);
        return this;
    }

    public CharsWrapper.Builder append(CharsWrapper cw) {
        this.write(cw);
        return this;
    }

    public CharsWrapper.Builder append(Object o) {
        if (o == null) {
            return this.append(NULL);
        }
        return this.append(o.toString());
    }

    public CharsWrapper.Builder append(Object ... objects) {
        for (Object o : objects) {
            this.append(o);
        }
        return this;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    @Override
    public void write(int c) {
        this.write((char)c);
    }

    @Override
    public void write(char c) {
        int newCursor = this.cursor + 1;
        this.ensureCapacity(newCursor);
        this.data[this.cursor] = c;
        this.cursor = newCursor;
    }

    @Override
    public void write(char ... cbuf) {
        CharacterOutput.super.write(cbuf);
    }

    @Override
    public void write(char[] chars, int offset, int length) {
        int newCursor = this.cursor + length;
        this.ensureCapacity(newCursor);
        System.arraycopy(chars, offset, this.data, this.cursor, length);
        this.cursor = newCursor;
    }

    @Override
    public void write(String str) {
        CharacterOutput.super.write(str);
    }

    @Override
    public void write(String s, int offset, int length) {
        int end = offset + length;
        int newCursor = this.cursor + length;
        this.ensureCapacity(newCursor);
        s.getChars(offset, end, this.data, this.cursor);
        this.cursor = newCursor;
    }

    @Override
    public void write(CharsWrapper cw) {
        CharacterOutput.super.write(cw);
    }

    public int length() {
        return this.cursor;
    }

    public char[] getChars() {
        return this.data;
    }

    public char get(int index) {
        return this.data[index];
    }

    public void set(int index, char ch) {
        if (index >= this.cursor) {
            throw new IndexOutOfBoundsException("Index must not be larger than the builder's length");
        }
        this.data[index] = ch;
    }

    public void compact() {
        if (this.cursor != this.data.length) {
            this.data = Arrays.copyOf(this.data, this.cursor);
        }
    }

    public CharsWrapper build() {
        return this.build(0);
    }

    public CharsWrapper build(int start) {
        return new CharsWrapper(this.data, start, this.cursor);
    }

    public CharsWrapper build(int start, int end) {
        if (end > this.cursor) {
            throw new IndexOutOfBoundsException("Specified end index is larger than the builder's length!");
        }
        return new CharsWrapper(this.data, start, end);
    }

    public CharsWrapper copyAndBuild() {
        return this.build(0);
    }

    public CharsWrapper copyAndBuild(int start) {
        return new CharsWrapper(Arrays.copyOfRange(this.data, start, this.cursor));
    }

    public CharsWrapper copyAndBuild(int start, int end) {
        if (end > this.cursor) {
            throw new IndexOutOfBoundsException("Specified end index is larger than the builder's length!");
        }
        return new CharsWrapper(Arrays.copyOfRange(this.data, start, end));
    }

    public String toString() {
        return this.toString(0);
    }

    public String toString(int start) {
        return new String(this.data, start, this.cursor - start);
    }

    public String toString(int start, int end) {
        if (end > this.cursor) {
            throw new IndexOutOfBoundsException("Specified end index is larger than the builder's length!");
        }
        return new String(this.data, start, end - start);
    }
}
