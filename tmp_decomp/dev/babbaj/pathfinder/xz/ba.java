/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.bc;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ba
implements bc {
    private final boolean a;
    private int a = false;

    public ba(int n2) {
        this.a = n2;
    }

    @Override
    public final int a(byte[] byArray, int n2, int n3) {
        int n4;
        n3 = n2 + n3 - 4;
        for (n4 = n2; n4 <= n3; n4 += 4) {
            if ((byArray[n4] & 0xFC) != 72 || (byArray[n4 + 3] & 3) != 1) continue;
            int n5 = (byArray[n4] & 3) << 24 | (byArray[n4 + 1] & 0xFF) << 16 | (byArray[n4 + 2] & 0xFF) << 8 | byArray[n4 + 3] & 0xFC;
            byArray[n4] = (byte)(0x48 | (n5 -= this.a + n4 - n2) >>> 24 & 3);
            byArray[n4 + 1] = (byte)(n5 >>> 16);
            byArray[n4 + 2] = (byte)(n5 >>> 8);
            byArray[n4 + 3] = (byte)(byArray[n4 + 3] & 3 | n5);
        }
        this.a += (n4 -= n2);
        return n4;
    }
}

