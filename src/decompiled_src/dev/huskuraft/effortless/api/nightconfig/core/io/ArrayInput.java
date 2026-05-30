/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.AbstractInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;

public final class ArrayInput
extends AbstractInput {
    private final char[] chars;
    private final int limit;
    private int cursor;

    public ArrayInput(CharsWrapper chars) {
        this(chars.chars, chars.offset, chars.limit);
    }

    public ArrayInput(char[] chars) {
        this(chars, 0, chars.length);
    }

    public ArrayInput(char[] chars, int offset, int limit) {
        this.chars = chars;
        this.cursor = offset;
        this.limit = limit;
    }

    @Override
    protected int directRead() {
        if (this.cursor >= this.limit) {
            return -1;
        }
        return this.chars[this.cursor++];
    }

    @Override
    protected char directReadChar() throws ParsingException {
        if (this.cursor >= this.limit) {
            throw ParsingException.notEnoughData();
        }
        return this.chars[this.cursor++];
    }

    @Override
    public CharsWrapper read(int n) {
        int offset;
        int size = Math.min(n, this.limit - this.cursor + this.deque.size());
        char[] array = new char[size];
        CharsWrapper smaller = this.consumeDeque(array, offset = Math.min(this.deque.size(), size), false);
        if (smaller != null) {
            return smaller;
        }
        System.arraycopy(this.chars, this.cursor, array, offset, size - offset);
        this.cursor += size;
        return new CharsWrapper(array);
    }

    @Override
    public CharsWrapper readChars(int n) {
        if (this.limit - this.cursor + this.deque.size() < n) {
            throw ParsingException.notEnoughData();
        }
        int offset = Math.min(this.deque.size(), n);
        char[] array = new char[n];
        this.consumeDeque(array, offset, true);
        System.arraycopy(this.chars, this.cursor, array, offset, n - offset);
        this.cursor += n;
        return new CharsWrapper(array);
    }
}
