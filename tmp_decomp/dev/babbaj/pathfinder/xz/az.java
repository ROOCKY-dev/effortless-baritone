/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.bc;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class az
implements bc {
    private static final int[] a = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 6, 6, 0, 0, 7, 7, 4, 4, 0, 0, 4, 4, 0, 0};
    private final boolean a;
    private int a = false;

    public az(int n2) {
        this.a = n2;
    }

    @Override
    public final int a(byte[] byArray, int n2, int n3) {
        int n4;
        n3 = n2 + n3 - 16;
        for (n4 = n2; n4 <= n3; n4 += 16) {
            int n5 = byArray[n4] & 0x1F;
            n5 = a[n5];
            int n6 = 0;
            int n7 = 5;
            while (n6 < 3) {
                if ((n5 >>> n6 & 1) != 0) {
                    int n8 = n7 >>> 3;
                    int n9 = n7 & 7;
                    long l2 = 0L;
                    for (int i2 = 0; i2 < 6; ++i2) {
                        l2 |= ((long)byArray[n4 + n8 + i2] & 0xFFL) << (i2 << 3);
                    }
                    long l3 = l2 >>> n9;
                    if ((l3 >>> 37 & 0xFL) == 5L && (l3 >>> 9 & 7L) == 0L) {
                        int n10 = ((int)(l3 >>> 13 & 0xFFFFFL) | ((int)(l3 >>> 36) & 1) << 20) << 4;
                        n10 = n10 - (this.a + n4 - n2) >>> 4;
                        l3 = l3 & 0xFFFFFFEE00001FFFL | ((long)n10 & 0xFFFFFL) << 13 | ((long)n10 & 0x100000L) << 16;
                        l2 = l2 & (long)((1 << n9) - 1) | l3 << n9;
                        for (n9 = 0; n9 < 6; ++n9) {
                            byArray[n4 + n8 + n9] = (byte)(l2 >>> (n9 << 3));
                        }
                    }
                }
                ++n6;
                n7 += 41;
            }
        }
        this.a += (n4 -= n2);
        return n4;
    }
}

