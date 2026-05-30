/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.aa;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class z
extends aa {
    private static final long[][] a = new long[4][256];
    private long a = -1L;

    public z() {
        ((aa)this).a = 8;
        ((aa)this).a = "CRC64";
    }

    @Override
    public final void a(byte[] byArray, int n2, int n3) {
        n3 = n2 + n3;
        int n4 = n3 - 3;
        while (n2 < n4) {
            int n5 = (int)this.a;
            this.a = a[3][n5 & 0xFF ^ byArray[n2] & 0xFF] ^ a[2][n5 >>> 8 & 0xFF ^ byArray[n2 + 1] & 0xFF] ^ this.a >>> 32 ^ a[1][n5 >>> 16 & 0xFF ^ byArray[n2 + 2] & 0xFF] ^ a[0][n5 >>> 24 ^ byArray[n2 + 3] & 0xFF];
            n2 += 4;
        }
        while (n2 < n3) {
            this.a = a[0][byArray[n2++] & 0xFF ^ (int)this.a & 0xFF] ^ this.a >>> 8;
        }
    }

    @Override
    public final byte[] a() {
        long l2 = this.a ^ 0xFFFFFFFFFFFFFFFFL;
        this.a = -1L;
        byte[] byArray = new byte[8];
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            byArray[i2] = (byte)(l2 >> (i2 << 3));
        }
        return byArray;
    }

    static {
        for (int i2 = 0; i2 < 4; ++i2) {
            for (int i3 = 0; i3 < 256; ++i3) {
                long l2 = i2 == 0 ? (long)i3 : a[i2 - 1][i3];
                for (int i4 = 0; i4 < 8; ++i4) {
                    if ((l2 & 1L) == 1L) {
                        l2 = l2 >>> 1 ^ 0xC96C5795D7870F42L;
                        continue;
                    }
                    l2 >>>= 1;
                }
                z.a[i2][i3] = l2;
            }
        }
    }
}

