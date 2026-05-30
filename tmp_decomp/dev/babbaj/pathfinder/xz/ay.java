/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.bc;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ay
implements bc {
    private final boolean a;
    private int a = false;

    public ay(int n2) {
        this.a = n2 + 4;
    }

    @Override
    public final int a(byte[] byArray, int n2, int n3) {
        int n4;
        n3 = n2 + n3 - 4;
        for (n4 = n2; n4 <= n3; n4 += 2) {
            if ((byArray[n4 + 1] & 0xF8) != 240 || (byArray[n4 + 3] & 0xF8) != 248) continue;
            int n5 = ((byArray[n4 + 1] & 7) << 19 | (byArray[n4] & 0xFF) << 11 | (byArray[n4 + 3] & 7) << 8 | byArray[n4 + 2] & 0xFF) << 1;
            n5 = n5 - (this.a + n4 - n2) >>> 1;
            byArray[n4 + 1] = (byte)(0xF0 | n5 >>> 19 & 7);
            byArray[n4] = (byte)(n5 >>> 11);
            byArray[n4 + 3] = (byte)(0xF8 | n5 >>> 8 & 7);
            byArray[n4 + 2] = (byte)n5;
            n4 += 2;
        }
        this.a += (n4 -= n2);
        return n4;
    }
}

