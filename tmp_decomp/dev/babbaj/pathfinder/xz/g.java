/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.e;
import java.io.InputStream;

final class g
extends e {
    long a = 0L;

    public g(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public final int read() {
        int n2 = this.in.read();
        if (n2 != -1 && this.a >= 0L) {
            ++this.a;
        }
        return n2;
    }

    @Override
    public final int read(byte[] byArray, int n2, int n3) {
        int n4 = this.in.read(byArray, n2, n3);
        if (n4 > 0 && this.a >= 0L) {
            this.a += (long)n4;
        }
        return n4;
    }
}

