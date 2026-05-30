/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

public class af {
    public static int a(long l2) {
        int n2 = 0;
        do {
            ++n2;
        } while ((l2 >>= 7) != 0L);
        return n2;
    }
}

