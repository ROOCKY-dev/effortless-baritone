/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.bc;
import dev.babbaj.pathfinder.xz.w;
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class r
extends InputStream {
    private InputStream a;
    private final bc a;
    private final byte[] a;
    private int a;
    private int b;
    private int c = 0;
    private boolean a;
    private IOException a = null;
    private final byte[] b = new byte[1];

    static int a() {
        return 5;
    }

    r(InputStream inputStream, bc bc2) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        assert (bc2 != null);
        this.a = inputStream;
        this.a = bc2;
    }

    @Override
    public int read() {
        r r2 = this;
        if (r2.read(r2.b, 0, 1) == -1) {
            return -1;
        }
        return this.b[0] & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n2, int n3) {
        if (n2 < 0 || n3 < 0 || n2 + n3 < 0 || n2 + n3 > byArray.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 == 0) {
            return 0;
        }
        if (this.a == null) {
            throw new w("Stream closed");
        }
        if (this.a != null) {
            throw this.a;
        }
        try {
            int n4 = 0;
            while (true) {
                int n5 = Math.min(this.b, n3);
                System.arraycopy(this.a, this.a, byArray, n2, n5);
                this.a += n5;
                this.b -= n5;
                n2 += n5;
                n3 -= n5;
                n4 += n5;
                if (this.a + this.b + this.c == 4096) {
                    System.arraycopy(this.a, this.a, this.a, 0, this.b + this.c);
                    this.a = 0;
                }
                if (n3 == 0 || this.a) {
                    if (n4 > 0) {
                        return n4;
                    }
                    return -1;
                }
                assert (this.b == 0);
                n5 = 4096 - (this.a + this.b + this.c);
                if ((n5 = this.a.read(this.a, this.a + this.b + this.c, n5)) == -1) {
                    this.a = true;
                    this.b = this.c;
                    this.c = 0;
                    continue;
                }
                this.c += n5;
                this.b = this.a.a(this.a, this.a, this.c);
                assert (this.b <= this.c);
                this.c -= this.b;
            }
        }
        catch (IOException iOException) {
            this.a = iOException;
            throw iOException;
        }
    }

    @Override
    public int available() {
        if (this.a == null) {
            throw new w("Stream closed");
        }
        if (this.a != null) {
            throw this.a;
        }
        return this.b;
    }

    @Override
    public void close() {
        if (this.a != null) {
            try {
                this.a.close();
                return;
            }
            finally {
                this.a = null;
            }
        }
    }
}

