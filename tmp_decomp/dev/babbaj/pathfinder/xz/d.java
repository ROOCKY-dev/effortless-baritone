/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.aa;
import dev.babbaj.pathfinder.xz.ad;
import dev.babbaj.pathfinder.xz.c;
import dev.babbaj.pathfinder.xz.f;
import dev.babbaj.pathfinder.xz.g;
import dev.babbaj.pathfinder.xz.i;
import dev.babbaj.pathfinder.xz.l;
import dev.babbaj.pathfinder.xz.m;
import dev.babbaj.pathfinder.xz.o;
import dev.babbaj.pathfinder.xz.q;
import dev.babbaj.pathfinder.xz.t;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class d
extends InputStream {
    private final DataInputStream a;
    final g a;
    private InputStream a;
    final aa a;
    private final boolean a;
    private long b;
    private long c = -1L;
    private long d;
    final int a;
    long a;
    private boolean b = false;
    private final byte[] a = new byte[1];

    public d(InputStream inputStream, aa lArray, boolean n2, int n3, a a2) {
        int n4;
        long l2;
        int n5;
        this.a = lArray;
        this.a = n2;
        this.a = new DataInputStream(inputStream);
        n2 = this.a.readUnsignedByte();
        if (n2 == 0) {
            throw new m();
        }
        this.a = 4 * (n2 + 1);
        byte[] byArray = new byte[this.a];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)n2;
        this.a.readFully(byArray2, 1, this.a - 1);
        if (!ad.a(byArray2, 0, this.a - 4, this.a - 4)) {
            throw new f("XZ Block Header is corrupt");
        }
        if ((byArray2[1] & 0x3C) != 0) {
            throw new t("Unsupported options in XZ Block Header");
        }
        n2 = (byArray2[1] & 3) + 1;
        long[] lArray2 = new long[n2];
        byte[][] byArrayArray = new byte[n2][];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray2, 2, this.a - 6);
        try {
            this.d = 0x7FFFFFFFFFFFFFFCL - (long)this.a - (long)lArray.a;
            if ((byArray2[1] & 0x40) != 0) {
                this.c = ad.a(byteArrayInputStream);
                if (this.c == 0L || this.c > this.d) {
                    throw new f();
                }
                this.d = this.c;
            }
            if ((byArray2[1] & 0x80) != 0) {
                this.b = ad.a(byteArrayInputStream);
            }
            for (n5 = 0; n5 < n2; ++n5) {
                lArray2[n5] = ad.a(byteArrayInputStream);
                l2 = ad.a(byteArrayInputStream);
                if (l2 > (long)byteArrayInputStream.available()) {
                    throw new f();
                }
                byArrayArray[n5] = new byte[(int)l2];
                byteArrayInputStream.read(byArrayArray[n5]);
            }
        }
        catch (IOException iOException) {
            throw new f("XZ Block Header is corrupt");
        }
        for (n5 = byteArrayInputStream.available(); n5 > 0; --n5) {
            if (byteArrayInputStream.read() == 0) continue;
            throw new t("Unsupported options in XZ Block Header");
        }
        if (-1L != -1L) {
            n5 = this.a + lArray.a;
            if ((long)n5 >= -1L) {
                throw new f("XZ Index does not match a Block Header");
            }
            l2 = -1L - (long)n5;
            if (l2 > this.d || this.c != -1L && this.c != l2) {
                throw new f("XZ Index does not match a Block Header");
            }
            if (this.b != -1L && this.b != -1L) {
                throw new f("XZ Index does not match a Block Header");
            }
            this.d = l2;
            this.c = l2;
            this.b = -1L;
        }
        l[] lArray3 = new l[lArray2.length];
        for (n4 = 0; n4 < lArray3.length; ++n4) {
            if (lArray2[n4] == 33L) {
                lArray3[n4] = new o(byArrayArray[n4]);
                continue;
            }
            if (lArray2[n4] == 3L) {
                lArray3[n4] = new i(byArrayArray[n4]);
                continue;
            }
            if (dev.babbaj.pathfinder.xz.c.a(lArray2[n4])) {
                lArray3[n4] = new c(lArray2[n4], byArrayArray[n4]);
                continue;
            }
            throw new t("Unknown Filter ID " + lArray2[n4]);
        }
        lArray = lArray3;
        for (n2 = 0; n2 < lArray.length - 1; ++n2) {
            if (lArray[n2].b()) continue;
            throw new t("Unsupported XZ filter chain");
        }
        if (!lArray[lArray.length - 1].c()) {
            throw new t("Unsupported XZ filter chain");
        }
        n2 = 0;
        for (int i2 = 0; i2 < lArray.length; ++i2) {
            if (!lArray[i2].a()) continue;
            ++n2;
        }
        if (n2 > 3) {
            throw new t("Unsupported XZ filter chain");
        }
        if (n3 >= 0) {
            n4 = 0;
            for (int i3 = 0; i3 < lArray3.length; ++i3) {
                n4 += lArray3[i3].a();
            }
            if (n4 > n3) {
                throw new q(n4, n3);
            }
        }
        this.a = new g(inputStream);
        this.a = this.a;
        for (n4 = lArray3.length - 1; n4 >= 0; --n4) {
            this.a = lArray3[n4].a(this.a, a2);
        }
    }

    @Override
    public int read() {
        d d2 = this;
        if (d2.read(d2.a, 0, 1) == -1) {
            return -1;
        }
        return this.a[0] & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n2, int n3) {
        if (this.b) {
            return -1;
        }
        int n4 = this.a.read(byArray, n2, n3);
        if (n4 > 0) {
            if (this.a) {
                this.a.a(byArray, n2, n4);
            }
            this.a += (long)n4;
            long l2 = this.a.a;
            if (l2 < 0L || l2 > this.d || this.a < 0L || this.b != -1L && this.a > this.b) {
                throw new f();
            }
            if (n4 < n3 || this.a == this.b) {
                if (this.a.read() != -1) {
                    throw new f();
                }
                this.a();
                this.b = true;
            }
        } else if (n4 == -1) {
            this.a();
            this.b = true;
        }
        return n4;
    }

    private void a() {
        long l2 = this.a.a;
        if (this.c != -1L && this.c != l2 || this.b != -1L && this.b != this.a) {
            throw new f();
        }
        while ((l2++ & 3L) != 0L) {
            if (this.a.readUnsignedByte() == 0) continue;
            throw new f();
        }
        byte[] byArray = new byte[this.a.a];
        this.a.readFully(byArray);
        if (this.a && !Arrays.equals(this.a.a(), byArray)) {
            throw new f("Integrity check (" + this.a.a + ") does not match");
        }
    }

    @Override
    public int available() {
        return this.a.available();
    }

    @Override
    public void close() {
        block2: {
            try {
                this.a.close();
            }
            catch (IOException iOException) {
                if (c) break block2;
                throw new AssertionError();
            }
        }
        this.a = null;
    }
}

