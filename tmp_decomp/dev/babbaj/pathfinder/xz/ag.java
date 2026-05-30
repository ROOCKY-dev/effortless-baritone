/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class ag {
    public final int a;
    public final byte[] a = new byte[256];
    public int b = 0;

    ag(int n2) {
        if (n2 <= 0 || n2 > 256) {
            throw new IllegalArgumentException();
        }
        this.a = n2;
    }
}

