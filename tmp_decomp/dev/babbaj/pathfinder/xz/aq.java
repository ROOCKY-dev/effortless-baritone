/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.am;
import dev.babbaj.pathfinder.xz.ap;

public final class aq
extends am {
    private /* synthetic */ ap a;

    private aq(ap ap2) {
        this.a = ap2;
        super(ap2);
    }

    public final int a(int n2) {
        if (this.a.a$1a7015fc.a(((am)this).a, 0) == 0) {
            return this.a.a$1a7015fc.a(((am)this).a[n2]) + 2;
        }
        if (this.a.a$1a7015fc.a(((am)this).a, 1) == 0) {
            return this.a.a$1a7015fc.a(this.b[n2]) + 2 + 8;
        }
        return this.a.a$1a7015fc.a(this.b) + 2 + 8 + 8;
    }

    /* synthetic */ aq(ap ap2, byte by2) {
        this(ap2);
    }
}

