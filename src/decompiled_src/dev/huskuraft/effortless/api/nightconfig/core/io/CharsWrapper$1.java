/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import java.util.Iterator;
import java.util.NoSuchElementException;

class CharsWrapper.1
implements Iterator<Character> {
    private int index;

    CharsWrapper.1() {
        this.index = CharsWrapper.this.offset;
    }

    @Override
    public boolean hasNext() {
        return this.index < CharsWrapper.this.limit;
    }

    @Override
    public Character next() {
        if (this.index >= CharsWrapper.this.limit) {
            throw new NoSuchElementException("Index beyond limit: " + this.index);
        }
        return Character.valueOf(CharsWrapper.this.chars[this.index++]);
    }
}
