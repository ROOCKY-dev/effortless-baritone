/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.by;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bz {
    public by[] a;
    public int a = new by[1024];

    public bz() {
        this(0);
    }

    private bz(byte by2) {
    }

    public final void a(by by2) {
        if (this.a >= this.a.length - 1) {
            this.a = Arrays.copyOf(this.a, this.a.length << 1);
        }
        ++this.a;
        by2.d = this.a;
        this.a[this.a] = by2;
        this.b(by2);
    }

    public final void b(by by2) {
        int n2 = by2.d;
        int n3 = n2 >>> 1;
        double d2 = by2.c;
        by by3 = this.a[n3];
        while (n2 > 1 && by3.c > d2) {
            this.a[n2] = by3;
            this.a[n3] = by2;
            by2.d = n3;
            by3.d = n2;
            n2 = n3;
            n3 = n2 >>> 1;
            by3 = this.a[n3];
        }
    }
}

