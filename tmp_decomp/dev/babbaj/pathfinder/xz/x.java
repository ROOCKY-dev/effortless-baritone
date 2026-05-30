/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.f;
import dev.babbaj.pathfinder.xz.s;
import dev.babbaj.pathfinder.xz.v;
import dev.babbaj.pathfinder.xz.w;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class x
extends InputStream {
    private final a a;
    private final int a;
    private InputStream a;
    private s a;
    private final boolean a;
    private boolean b = false;
    private IOException a;
    private final byte[] a = new byte[1];

    public x(InputStream inputStream) {
        this(inputStream, 0);
    }

    private x(InputStream inputStream, byte by2) {
        this(inputStream, '\u0000');
    }

    private x(InputStream inputStream, char c2) {
        this(inputStream, dev.babbaj.pathfinder.xz.a.a());
    }

    private x(InputStream inputStream, a a2) {
        this.a = a2;
        this.a = inputStream;
        this.a = -1;
        this.a = true;
        this.a = new s(inputStream, a2);
    }

    @Override
    public final int read() {
        x x2 = this;
        if (x2.read(x2.a, 0, 1) == -1) {
            return -1;
        }
        return this.a[0] & 0xFF;
    }

    @Override
    public final int read(byte[] byArray, int n2, int n3) {
        int n4;
        block17: {
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
            if (this.b) {
                return -1;
            }
            n4 = 0;
            try {
                while (n3 > 0) {
                    int n5;
                    if (this.a == null) {
                        block16: {
                            x x2 = this;
                            DataInputStream dataInputStream = new DataInputStream(x2.a);
                            byte[] byArray2 = new byte[12];
                            do {
                                if (dataInputStream.read(byArray2, 0, 1) == -1) {
                                    x2.b = true;
                                    break block16;
                                }
                                dataInputStream.readFully(byArray2, 1, 3);
                            } while (byArray2[0] == 0 && byArray2[1] == 0 && byArray2[2] == 0 && byArray2[3] == 0);
                            dataInputStream.readFully(byArray2, 4, 8);
                            try {
                                x2.a = new s(x2.a, x2.a, x2.a, byArray2, x2.a);
                            }
                            catch (v v2) {
                                throw new f("Garbage after a valid XZ Stream");
                            }
                        }
                        if (this.b) {
                            if (n4 == 0) {
                                return -1;
                            }
                            return n4;
                        }
                    }
                    if ((n5 = this.a.read(byArray, n2, n3)) > 0) {
                        n4 += n5;
                        n2 += n5;
                        n3 -= n5;
                        continue;
                    }
                    if (n5 != -1) continue;
                    this.a = null;
                }
            }
            catch (IOException iOException) {
                this.a = iOException;
                if (n4 != 0) break block17;
                throw iOException;
            }
        }
        return n4;
    }

    @Override
    public final int available() {
        if (this.a == null) {
            throw new w("Stream closed");
        }
        if (this.a != null) {
            throw this.a;
        }
        if (this.a == null) {
            return 0;
        }
        return this.a.available();
    }

    @Override
    public final void close() {
        x x2 = this;
        if (x2.a != null) {
            if (x2.a != null) {
                x2.a.a(false);
                x2.a = null;
            }
            try {
                x2.a.close();
                return;
            }
            finally {
                x2.a = null;
            }
        }
    }
}

