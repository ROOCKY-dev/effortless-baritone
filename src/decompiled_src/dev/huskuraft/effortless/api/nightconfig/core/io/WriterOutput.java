/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import java.io.IOException;
import java.io.Writer;

public final class WriterOutput
implements CharacterOutput {
    private final Writer writer;

    public WriterOutput(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void write(char c) {
        try {
            this.writer.write(c);
        }
        catch (IOException e) {
            throw new WritingException(e);
        }
    }

    @Override
    public void write(char[] chars, int offset, int length) {
        try {
            this.writer.write(chars, offset, length);
        }
        catch (IOException e) {
            throw new WritingException(e);
        }
    }

    @Override
    public void write(String s, int offset, int length) {
        try {
            this.writer.write(s, offset, length);
        }
        catch (IOException e) {
            throw new WritingException(e);
        }
    }
}
