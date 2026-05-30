/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.an;
import dev.babbaj.pathfinder.xz.ap;
import dev.babbaj.pathfinder.xz.as;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ar
extends an {
    public final as[] a;
    public final /* synthetic */ ap a;

    ar(ap ap2, int n2, int n3) {
        this.a = ap2;
        super(ap2, n2, n3);
        this.a = new as[1 << n2 + n3];
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = new as(this, 0);
        }
    }

    final void a() {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2].a();
        }
    }
}

