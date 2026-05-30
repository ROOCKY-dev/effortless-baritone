/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.ar;
import java.util.Arrays;

public abstract class ao {
    public final short[] a = new short[768];
    private /* synthetic */ ar a$1a701504;

    ao(ar ar2) {
        this.a$1a701504 = ar2;
    }

    final void a() {
        Arrays.fill(this.a, (short)1024);
    }
}

