/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import dev.huskuraft.effortless.api.nightconfig.core.utils.IntDeque;

public abstract class AbstractInput
implements CharacterInput {
    protected final IntDeque deque = new IntDeque();

    protected abstract int directRead();

    protected abstract char directReadChar();

    @Override
    public int read() {
        if (!this.deque.isEmpty()) {
            return this.deque.removeFirst();
        }
        return this.directRead();
    }

    @Override
    public char readChar() {
        if (!this.deque.isEmpty()) {
            int next = this.deque.removeFirst();
            if (next == -1) {
                throw ParsingException.notEnoughData();
            }
            return (char)next;
        }
        return this.directReadChar();
    }

    @Override
    public int peek() {
        if (this.deque.isEmpty()) {
            int read = this.directRead();
            this.deque.addLast(read);
            return read;
        }
        return this.deque.getFirst();
    }

    @Override
    public int peek(int n) {
        int diff = n - this.deque.size();
        if (diff >= 0) {
            for (int i = 0; i <= diff; ++i) {
                int read = this.directRead();
                this.deque.addLast(read);
                if (read != -1) continue;
                return -1;
            }
        }
        return this.deque.get(n);
    }

    @Override
    public char peekChar() {
        int c = this.peek();
        if (c == -1) {
            throw ParsingException.notEnoughData();
        }
        return (char)c;
    }

    @Override
    public char peekChar(int n) {
        int c = this.peek(n);
        if (c == -1) {
            throw ParsingException.notEnoughData();
        }
        return (char)c;
    }

    @Override
    public void skipPeeks() {
        this.deque.clear();
    }

    @Override
    public void pushBack(char c) {
        this.deque.addFirst(c);
    }

    @Override
    public CharsWrapper readUntil(char[] stop) {
        CharsWrapper.Builder builder = new CharsWrapper.Builder(10);
        int c = this.read();
        while (c != -1 && !Utils.arrayContains(stop, (char)c)) {
            builder.append((char)c);
            c = this.read();
        }
        this.deque.addFirst(c);
        return builder.build();
    }

    @Override
    public CharsWrapper readCharsUntil(char[] stop) {
        CharsWrapper.Builder builder = new CharsWrapper.Builder(10);
        char c = this.readChar();
        while (!Utils.arrayContains(stop, c)) {
            builder.append(c);
            c = this.readChar();
        }
        this.deque.addFirst(c);
        return builder.build();
    }

    protected CharsWrapper consumeDeque(char[] array, int offset, boolean mustReadAll) {
        for (int i = 0; i < offset; ++i) {
            int next = this.deque.removeFirst();
            if (next == -1) {
                if (mustReadAll) {
                    throw ParsingException.notEnoughData();
                }
                return new CharsWrapper(array, 0, i);
            }
            array[i] = (char)next;
        }
        return null;
    }
}
