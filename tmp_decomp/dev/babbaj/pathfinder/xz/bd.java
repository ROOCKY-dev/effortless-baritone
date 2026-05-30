/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.bc;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bd
implements bc {
    private static final boolean[] a = new boolean[]{true, true, true, false, true, false, false, false};
    private static final int[] a = new int[]{0, 1, 2, 2, 3, 3, 3, 3};
    private final boolean a;
    private int a = false;
    private int b = 0;

    private static boolean a(byte by2) {
        int n2 = by2 & 0xFF;
        by2 = (byte)n2;
        return n2 == 0 || by2 == 255;
    }

    public bd(int n2) {
        this.a = n2 + 5;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final int a(byte[] var1_1, int var2_2, int var3_3) {
        var4_4 = var2_2 - 1;
        var3_3 = var2_2 + var3_3 - 5;
        for (var5_5 = var2_2; var5_5 <= var3_3; ++var5_5) {
            block5: {
                if ((var1_1[var5_5] & 254) != 232) continue;
                if (((var4_4 = var5_5 - var4_4) & -4) == 0) break block5;
                this.b = 0;
                ** GOTO lbl-1000
            }
            this.b = this.b << var4_4 - 1 & 7;
            if (this.b != 0 && (!bd.a[this.b] || bd.a(var1_1[var5_5 + 4 - bd.a[this.b]]))) {
                var4_4 = var5_5;
            } else lbl-1000:
            // 2 sources

            {
                var4_4 = var5_5;
                if (bd.a(var1_1[var5_5 + 4])) {
                    var6_6 = var1_1[var5_5 + 1] & 255 | (var1_1[var5_5 + 2] & 255) << 8 | (var1_1[var5_5 + 3] & 255) << 16 | (var1_1[var5_5 + 4] & 255) << 24;
                    while (true) {
                        if (this.b == 0 || !bd.a((byte)((var6_6 -= this.a + var5_5 - var2_2) >>> 24 - (var7_7 = bd.a[this.b] << 3)))) break;
                        var6_6 ^= (1 << 32 - var7_7) - 1;
                    }
                    var1_1[var5_5 + 1] = (byte)var6_6;
                    var1_1[var5_5 + 2] = (byte)(var6_6 >>> 8);
                    var1_1[var5_5 + 3] = (byte)(var6_6 >>> 16);
                    var1_1[var5_5 + 4] = (byte)(~((var6_6 >>> 24 & 1) - 1));
                    var5_5 += 4;
                    continue;
                }
            }
            this.b = this.b << 1 | 1;
        }
        this.b = ((var4_4 = var5_5 - var4_4) & -4) != 0 ? 0 : this.b << var4_4 - 1;
        this.a += (var5_5 -= var2_2);
        return var5_5;
    }
}

