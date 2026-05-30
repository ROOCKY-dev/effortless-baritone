/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class CharsWrapper
implements CharSequence,
Cloneable,
Iterable<Character> {
    final char[] chars;
    final int offset;
    final int limit;

    public CharsWrapper(char ... chars) {
        this(chars, 0, chars.length);
    }

    public CharsWrapper(char[] chars, int offset, int limit) {
        if (limit < offset) {
            throw new IllegalArgumentException("limit must be bigger than offset");
        }
        this.chars = Objects.requireNonNull(chars, "chars must not be null");
        this.offset = offset;
        this.limit = limit;
    }

    public CharsWrapper(String str) {
        this(str, 0, str.length());
    }

    public CharsWrapper(String str, int begin, int end) {
        this.offset = 0;
        this.limit = end - begin;
        this.chars = new char[this.limit];
        str.getChars(begin, end, this.chars, 0);
    }

    public CharsWrapper(CharSequence csq) {
        this(csq, 0, csq.length());
    }

    public CharsWrapper(CharSequence csq, int begin, int end) {
        this.offset = 0;
        this.limit = end - begin;
        this.chars = new char[this.limit];
        for (int i = begin; i < end; ++i) {
            this.chars[i - begin] = csq.charAt(i);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.limit == this.offset;
    }

    @Override
    public int length() {
        return this.limit - this.offset;
    }

    @Override
    public char charAt(int index) {
        return this.chars[this.offset + index];
    }

    public char get(int index) {
        return this.chars[this.offset + index];
    }

    public void set(int index, char ch) {
        this.chars[this.offset + index] = ch;
    }

    public void replaceAll(char ch, char replacement) {
        for (int i = this.offset; i < this.limit; ++i) {
            if (this.chars[i] != ch) continue;
            this.chars[i] = replacement;
        }
    }

    public boolean contains(char c) {
        return this.indexOf(c) != -1;
    }

    public int indexOf(char c) {
        for (int i = this.offset; i < this.limit; ++i) {
            if (this.chars[i] != c) continue;
            return i - this.offset;
        }
        return -1;
    }

    public int indexOfFirst(char ... ch) {
        for (int i = this.offset; i < this.limit; ++i) {
            if (!Utils.arrayContains(ch, this.chars[i])) continue;
            return i - this.offset;
        }
        return -1;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharsWrapper)) {
            return false;
        }
        CharsWrapper other = (CharsWrapper)obj;
        int l = other.length();
        if (this.length() != l) {
            return false;
        }
        for (int i = 0; i < l; ++i) {
            char c = this.chars[this.offset + i];
            char co = other.chars[other.offset + i];
            if (c == co) continue;
            return false;
        }
        return true;
    }

    public boolean equalsIgnoreCase(CharSequence cs) {
        if (cs == this) {
            return true;
        }
        if (cs == null || cs.length() != this.length()) {
            return false;
        }
        for (int i = 0; i < this.limit; ++i) {
            char u2;
            char u1 = Character.toUpperCase(this.chars[this.offset + i]);
            if (u1 == (u2 = Character.toUpperCase(cs.charAt(i)))) continue;
            return false;
        }
        return true;
    }

    public boolean contentEquals(CharSequence cs) {
        int l = this.length();
        if (cs == null || cs.length() != l) {
            return false;
        }
        for (int i = 0; i < l; ++i) {
            if (this.chars[this.offset + i] == cs.charAt(i)) continue;
            return false;
        }
        return true;
    }

    public boolean contentEquals(char[] array) {
        int l = this.length();
        if (array == null || array.length != l) {
            return false;
        }
        for (int i = 0; i < l; ++i) {
            if (this.chars[this.offset + i] == array[i]) continue;
            return false;
        }
        return true;
    }

    public boolean startsWith(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int l = cs.length();
        if (l > this.length()) {
            return false;
        }
        for (int i = 0; i < l; ++i) {
            if (this.chars[this.offset + i] == cs.charAt(i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public CharsWrapper subSequence(int start, int end) {
        return new CharsWrapper(Arrays.copyOfRange(this.chars, start + this.offset, end + this.offset));
    }

    public CharsWrapper subView(int start, int end) {
        return new CharsWrapper(this.chars, start + this.offset, end + this.offset);
    }

    public CharsWrapper subView(int start) {
        return new CharsWrapper(this.chars, start + this.offset, this.limit);
    }

    public CharsWrapper trimmedView() {
        int offset;
        int limit = this.limit;
        for (offset = this.offset; offset < limit && this.chars[offset] <= ' '; ++offset) {
        }
        while (limit > offset && this.chars[limit - 1] <= ' ') {
            --limit;
        }
        return new CharsWrapper(this.chars, offset, limit);
    }

    @Override
    public String toString() {
        return new String(this.chars, this.offset, this.length());
    }

    public int hashCode() {
        int h = 0;
        for (int i = this.offset; i < this.limit; ++i) {
            h = 31 * h + this.chars[i];
        }
        return h;
    }

    public CharsWrapper clone() {
        return new CharsWrapper(Arrays.copyOf(this.chars, this.chars.length));
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>(){
            private int index;
            {
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
        };
    }

    public static final class Builder
    extends Writer
    implements CharacterOutput {
        private static final char[] NULL = new char[]{'n', 'u', 'l', 'l'};
        private char[] data;
        private int cursor = 0;

        public Builder(int initialCapacity) {
            this.data = new char[Math.min(2, initialCapacity)];
        }

        private void ensureCapacity(int capacity) {
            if (this.data.length < capacity) {
                int newCapacity = Math.max(capacity, this.data.length * 2);
                this.data = Arrays.copyOf(this.data, newCapacity);
            }
        }

        @Override
        public Builder append(char c) {
            this.write(c);
            return this;
        }

        @Override
        public Builder append(CharSequence csq) {
            if (csq == null) {
                return this.append(NULL);
            }
            if (csq instanceof String) {
                return this.append((String)csq);
            }
            return this.append(csq, 0, csq.length());
        }

        @Override
        public Builder append(CharSequence csq, int start, int end) {
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

        public Builder append(char ... chars) {
            this.write(chars);
            return this;
        }

        public Builder append(char[] chars, int begin, int end) {
            int length = end - begin;
            this.write(chars, begin, length);
            return this;
        }

        public Builder append(String str) {
            this.write(str);
            return this;
        }

        public Builder append(String str, int begin, int end) {
            int length = end - begin;
            this.write(str, begin, length);
            return this;
        }

        public Builder append(CharsWrapper cw) {
            this.write(cw);
            return this;
        }

        public Builder append(Object o) {
            if (o == null) {
                return this.append(NULL);
            }
            return this.append(o.toString());
        }

        public Builder append(Object ... objects) {
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
}
