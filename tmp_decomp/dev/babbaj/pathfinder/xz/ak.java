/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.f;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ak {
    public final byte[] a;
    public final int a;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;

    public ak(int n2) {
        this.a = n2;
        this.a = dev.babbaj.pathfinder.xz.a.a(this.a);
    }

    public final int a(int n2) {
        int n3 = this.c - n2 - 1;
        if (n2 >= this.c) {
            n3 += this.a;
        }
        return this.a[n3] & 0xFF;
    }

    public final void a(int n2, int n3) {
        int n4;
        if (n2 < 0 || n2 >= this.d) {
            throw new f();
        }
        int n5 = Math.min(this.e - this.c, n3);
        this.f = n3 - n5;
        this.g = n2;
        n3 = this.c - n2 - 1;
        if (n3 < 0) {
            assert (this.d == this.a);
            n4 = Math.min(this.a - (n3 += this.a), n5);
            assert (n4 <= n2 + 1);
            System.arraycopy(this.a, n3, this.a, this.c, n4);
            this.c += n4;
            n3 = 0;
            if ((n5 -= n4) == 0) {
                return;
            }
        }
        assert (n3 < this.c);
        assert (n5 > 0);
        do {
            n4 = Math.min(n5, this.c - n3);
            System.arraycopy(this.a, n3, this.a, this.c, n4);
            this.c += n4;
        } while ((n5 -= n4) > 0);
        if (this.d < this.c) {
            this.d = this.c;
        }
    }
}

