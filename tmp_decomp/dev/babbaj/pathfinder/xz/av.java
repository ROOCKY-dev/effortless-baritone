/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.au;

public abstract class av
extends au {
    public int a = 0;
    public int b = 0;

    public abstract void a();

    public final int a(short[] sArray, int n2) {
        int n3;
        this.a();
        short s2 = sArray[n2];
        int n4 = (this.a >>> 11) * s2;
        if ((this.b ^ Integer.MIN_VALUE) < (n4 ^ Integer.MIN_VALUE)) {
            this.a = n4;
            sArray[n2] = (short)(s2 + (2048 - s2 >>> 5));
            n3 = 0;
        } else {
            this.a -= n4;
            this.b -= n4;
            short s3 = s2;
            sArray[n2] = (short)(s3 - (s3 >>> 5));
            n3 = 1;
        }
        return n3;
    }

    public final int a(short[] sArray) {
        int n2 = 1;
        while ((n2 = n2 << 1 | this.a(sArray, n2)) < sArray.length) {
        }
        return n2 - sArray.length;
    }

    public final int b(short[] sArray) {
        int n2 = 1;
        int n3 = 0;
        int n4 = 0;
        do {
            int n5 = this.a(sArray, n2);
            n2 = n2 << 1 | n5;
            n4 |= n5 << n3++;
        } while (n2 < sArray.length);
        return n4;
    }
}

