/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.ap;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class am {
    final short[] a;
    final short[][] a = new short[16][8];
    final short[][] b;
    final short[] b = new short[256];
    private /* synthetic */ ap a$1a7014c6;

    am(ap ap2) {
        this.a$1a7014c6 = ap2;
    }

    final void a() {
        int n2;
        Arrays.fill(this.a, (short)1024);
        for (n2 = 0; n2 < this.a.length; ++n2) {
            Arrays.fill(this.a[n2], (short)1024);
        }
        for (n2 = 0; n2 < this.a.length; ++n2) {
            Arrays.fill(this.b[n2], (short)1024);
        }
        Arrays.fill(this.b, (short)1024);
    }
}

