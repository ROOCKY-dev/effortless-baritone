/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.at;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class al {
    public final int a;
    public final int[] a;
    public final at a;
    public final short[][] a;
    public final short[] a = new short[12];
    public final short[] b;
    public final short[] c;
    public final short[] d;
    public final short[][] b = new short[12];
    public final short[][] c = new short[12];
    public final short[][] d = new short[12];
    public final short[] e;

    al(int n2) {
        this.b = new short[12][16];
        this.c = new short[4][64];
        this.d = new short[][]{new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32]};
        this.e = new short[16];
        this.a = (1 << n2) - 1;
    }

    void a() {
        int n2;
        this.a[0] = 0;
        this.a[1] = 0;
        this.a[2] = 0;
        this.a[3] = 0;
        this.a.a = 0;
        for (n2 = 0; n2 < this.a.length; ++n2) {
            Arrays.fill(this.a[n2], (short)1024);
        }
        Arrays.fill(this.a, (short)1024);
        Arrays.fill(this.b, (short)1024);
        Arrays.fill(this.c, (short)1024);
        Arrays.fill(this.d, (short)1024);
        for (n2 = 0; n2 < this.b.length; ++n2) {
            Arrays.fill(this.b[n2], (short)1024);
        }
        for (n2 = 0; n2 < this.c.length; ++n2) {
            Arrays.fill(this.c[n2], (short)1024);
        }
        for (n2 = 0; n2 < this.d.length; ++n2) {
            Arrays.fill(this.d[n2], (short)1024);
        }
        Arrays.fill(this.e, (short)1024);
    }
}

