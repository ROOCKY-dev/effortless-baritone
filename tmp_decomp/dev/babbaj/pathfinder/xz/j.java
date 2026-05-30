/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.ah;
import dev.babbaj.pathfinder.xz.w;
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class j
extends InputStream {
    private InputStream a;
    private final ah a;
    private IOException a;
    private final byte[] a = new byte[1];

    public j(InputStream inputStream, int n2) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        this.a = inputStream;
        this.a = new ah(n2);
    }

    @Override
    public final int read() {
        j j2 = this;
        if (j2.read(j2.a, 0, 1) == -1) {
            return -1;
        }
        return this.a[0] & 0xFF;
    }

    @Override
    public final int read(byte[] object, int n2, int n3) {
        int n4;
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
            n3 = this.a.read((byte[])object, n2, n3);
        }
        catch (IOException iOException) {
            this.a = iOException;
            throw iOException;
        }
        if (n3 == -1) {
            return -1;
        }
        byte[] byArray = object;
        object = this.a;
        int n5 = n4 + n3;
        for (n4 = n2; n4 < n5; ++n4) {
            int n6 = n4;
            byArray[n6] = (byte)(byArray[n6] + object.a[object.a + object.b & 0xFF]);
            object.a[object.b-- & 0xFF] = byArray[n4];
        }
        return n3;
    }

    @Override
    public final int available() {
        if (this.a == null) {
            throw new w("Stream closed");
        }
        if (this.a != null) {
            throw this.a;
        }
        return this.a.available();
    }

    @Override
    public final void close() {
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

