/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.AbstractInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import java.io.IOException;
import java.io.Reader;

public final class ReaderInput
extends AbstractInput {
    private final Reader reader;

    public ReaderInput(Reader reader) {
        this.reader = reader;
    }

    @Override
    protected int directRead() {
        try {
            return this.reader.read();
        }
        catch (IOException e) {
            throw ParsingException.readFailed(e);
        }
    }

    @Override
    protected char directReadChar() throws ParsingException {
        int read;
        try {
            read = this.reader.read();
        }
        catch (IOException e) {
            throw ParsingException.readFailed(e);
        }
        if (read == -1) {
            throw ParsingException.notEnoughData();
        }
        return (char)read;
    }

    @Override
    public CharsWrapper read(int n) {
        int nRead;
        char[] array = new char[n];
        int offset = Math.min(this.deque.size(), n);
        CharsWrapper smaller = this.consumeDeque(array, offset, false);
        if (smaller != null) {
            return smaller;
        }
        try {
            nRead = this.reader.read(array, offset, n - offset);
        }
        catch (IOException e) {
            throw ParsingException.readFailed(e);
        }
        return new CharsWrapper(array, 0, offset + nRead);
    }

    @Override
    public CharsWrapper readChars(int n) {
        int nRead;
        char[] array = new char[n];
        int offset = Math.min(this.deque.size(), n);
        this.consumeDeque(array, offset, true);
        int length = n - offset;
        try {
            nRead = this.reader.read(array, offset, length);
        }
        catch (IOException e) {
            throw ParsingException.readFailed(e);
        }
        if (nRead != length) {
            throw ParsingException.notEnoughData();
        }
        return new CharsWrapper(array);
    }
}
