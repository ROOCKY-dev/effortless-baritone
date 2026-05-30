/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.aa;
import java.util.zip.CRC32;

public final class y
extends aa {
    private final CRC32 a = new CRC32();

    public y() {
        ((aa)this).a = 4;
        ((aa)this).a = "CRC32";
    }

    @Override
    public final void a(byte[] byArray, int n2, int n3) {
        this.a.update(byArray, n2, n3);
    }

    @Override
    public final byte[] a() {
        long l2 = this.a.getValue();
        byte[] byArray = new byte[]{(byte)l2, (byte)(l2 >>> 8), (byte)(l2 >>> 16), (byte)(l2 >>> 24)};
        this.a.reset();
        return byArray;
    }
}

