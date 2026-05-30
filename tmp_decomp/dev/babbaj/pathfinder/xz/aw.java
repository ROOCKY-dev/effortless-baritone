/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.av;
import dev.babbaj.pathfinder.xz.f;

public final class aw
extends av {
    public final byte[] a = dev.babbaj.pathfinder.xz.a.a(65531);
    public int c = this.a.length;

    @Override
    public final void a() {
        if ((((av)this).a & 0xFF000000) == 0) {
            try {
                this.b = this.b << 8 | this.a[this.c++] & 0xFF;
                ((av)this).a <<= 8;
                return;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw new f();
            }
        }
    }
}

