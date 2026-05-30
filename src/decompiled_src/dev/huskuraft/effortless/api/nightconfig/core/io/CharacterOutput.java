/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;

public interface CharacterOutput {
    public void write(char var1);

    default public void write(char ... chars) {
        this.write(chars, 0, chars.length);
    }

    public void write(char[] var1, int var2, int var3);

    default public void write(String s) {
        this.write(s, 0, s.length());
    }

    public void write(String var1, int var2, int var3);

    default public void write(CharsWrapper cw) {
        this.write(cw.chars, cw.offset, cw.limit - cw.offset);
    }
}
