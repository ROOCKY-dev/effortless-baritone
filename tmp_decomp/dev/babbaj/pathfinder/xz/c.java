/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.ax;
import dev.babbaj.pathfinder.xz.ay;
import dev.babbaj.pathfinder.xz.az;
import dev.babbaj.pathfinder.xz.b;
import dev.babbaj.pathfinder.xz.ba;
import dev.babbaj.pathfinder.xz.bb;
import dev.babbaj.pathfinder.xz.bc;
import dev.babbaj.pathfinder.xz.bd;
import dev.babbaj.pathfinder.xz.l;
import dev.babbaj.pathfinder.xz.r;
import dev.babbaj.pathfinder.xz.t;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class c
extends b
implements l {
    private final long a;
    private final int a;

    c(long l2, byte[] byArray) {
        assert (c.a(l2));
        this.a = l2;
        if (byArray.length == 0) {
            this.a = 0;
            return;
        }
        if (byArray.length == 4) {
            int n2 = 0;
            for (int i2 = 0; i2 < 4; ++i2) {
                n2 |= (byArray[i2] & 0xFF) << (i2 << 3);
            }
            this.a = n2;
            return;
        }
        throw new t("Unsupported BCJ filter properties");
    }

    @Override
    public final int a() {
        return r.a();
    }

    @Override
    public final InputStream a(InputStream inputStream, a object) {
        object = null;
        if (this.a == 4L) {
            object = new bd(this.a);
        } else if (this.a == 5L) {
            object = new ba(this.a);
        } else if (this.a == 6L) {
            object = new az(this.a);
        } else if (this.a == 7L) {
            object = new ax(this.a);
        } else if (this.a == 8L) {
            object = new ay(this.a);
        } else if (this.a == 9L) {
            object = new bb(this.a);
        } else assert (false);
        return new r(inputStream, (bc)object);
    }
}

