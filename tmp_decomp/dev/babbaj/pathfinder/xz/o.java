/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.l;
import dev.babbaj.pathfinder.xz.n;
import dev.babbaj.pathfinder.xz.p;
import dev.babbaj.pathfinder.xz.t;
import java.io.InputStream;

final class o
extends n
implements l {
    private int a;

    o(byte[] byArray) {
        if (byArray.length != 1 || (byArray[0] & 0xFF) > 37) {
            throw new t("Unsupported LZMA2 properties");
        }
        this.a = 2 | byArray[0] & 1;
        this.a <<= (byArray[0] >>> 1) + 11;
    }

    @Override
    public final int a() {
        return p.a(this.a);
    }

    @Override
    public final InputStream a(InputStream inputStream, a a2) {
        return new p(inputStream, this.a, a2);
    }
}

