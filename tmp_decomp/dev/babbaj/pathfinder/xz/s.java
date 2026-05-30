/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.aa;
import dev.babbaj.pathfinder.xz.ad;
import dev.babbaj.pathfinder.xz.ae;
import dev.babbaj.pathfinder.xz.ai;
import dev.babbaj.pathfinder.xz.aj;
import dev.babbaj.pathfinder.xz.d;
import dev.babbaj.pathfinder.xz.f;
import dev.babbaj.pathfinder.xz.m;
import dev.babbaj.pathfinder.xz.w;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class s
extends InputStream {
    private InputStream a;
    private final a a;
    private final int a;
    private final ae a;
    private final aa a;
    private final boolean a;
    private d a;
    private final aj a;
    private boolean b;
    private IOException a;
    private final byte[] a;

    public s(InputStream inputStream, a a2) {
        byte[] byArray = new byte[12];
        new DataInputStream(inputStream).readFully(byArray);
        this(inputStream, -1, true, byArray, a2);
    }

    s(InputStream inputStream, int n2, boolean bl2, byte[] byArray, a a2) {
        this.a = null;
        this.a = new aj();
        this.b = false;
        this.a = null;
        this.a = new byte[1];
        this.a = a2;
        this.a = inputStream;
        this.a = n2;
        this.a = bl2;
        this.a = ad.a(byArray);
        this.a = aa.a(this.a.a);
    }

    @Override
    public final int read() {
        s s2 = this;
        if (s2.read(s2.a, 0, 1) == -1) {
            return -1;
        }
        return this.a[0] & 0xFF;
    }

    @Override
    public final int read(byte[] object, int n2, int n3) {
        int n4;
        block21: {
            if (n2 < 0 || n3 < 0 || n2 + n3 < 0 || n2 + n3 > ((byte[])object).length) {
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
                        try {
                            this.a = new d(this.a, this.a, this.a, this.a, this.a);
                        }
                        catch (m m2) {
                            object = this.a;
                            Object object2 = this.a;
                            Object object3 = new CRC32();
                            ((CRC32)object3).update(0);
                            CheckedInputStream checkedInputStream = new CheckedInputStream((InputStream)object, (Checksum)object3);
                            object = checkedInputStream;
                            if (ad.a(checkedInputStream) != ((ai)object2).d) {
                                throw new f("XZ Block Header or the start of XZ Index is corrupt");
                            }
                            aj aj2 = new aj();
                            for (long i2 = 0L; i2 < ((ai)object2).d; ++i2) {
                                long l2 = ad.a((InputStream)object);
                                long l3 = ad.a((InputStream)object);
                                try {
                                    aj2.a(l2, l3);
                                }
                                catch (w w2) {
                                    throw new f("XZ Index is corrupt");
                                }
                                if (((ai)aj2).a <= ((ai)object2).a && aj2.b <= ((ai)object2).b && aj2.c <= ((ai)object2).c) continue;
                                throw new f("XZ Index is corrupt");
                            }
                            if (((ai)aj2).a != ((ai)object2).a || aj2.b != ((ai)object2).b || aj2.c != ((ai)object2).c || !Arrays.equals(aj2.a.a(), ((aj)object2).a.a())) {
                                throw new f("XZ Index is corrupt");
                            }
                            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
                            object = object2;
                            for (int i3 = (int)(4L - object.a() & 3L); i3 > 0; --i3) {
                                if (dataInputStream.readUnsignedByte() == 0) continue;
                                throw new f("XZ Index is corrupt");
                            }
                            long l4 = ((CRC32)object3).getValue();
                            for (int i4 = 0; i4 < 4; ++i4) {
                                if ((l4 >>> (i4 << 3) & 0xFFL) == (long)dataInputStream.readUnsignedByte()) continue;
                                throw new f("XZ Index is corrupt");
                            }
                            object2 = this;
                            object = new byte[12];
                            new DataInputStream(((s)object2).a).readFully((byte[])object);
                            object3 = ad.b(object);
                            object = object3;
                            if (!(((s)object2).a.a == object.a) || ((ai)((s)object2).a).b() != ((ae)object3).a) {
                                throw new f("XZ Stream Footer does not match Stream Header");
                            }
                            this.b = true;
                            if (n4 > 0) {
                                return n4;
                            }
                            return -1;
                        }
                    }
                    if ((n5 = this.a.read((byte[])object, n2, n3)) > 0) {
                        n4 += n5;
                        n2 += n5;
                        n3 -= n5;
                        continue;
                    }
                    if (n5 != -1) continue;
                    d d2 = this.a;
                    this.a.a((long)d2.a + d2.a.a + (long)d2.a.a, this.a.a);
                    this.a = null;
                }
            }
            catch (IOException iOException) {
                this.a = iOException;
                if (n4 != 0) break block21;
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
        this.a(true);
    }

    public final void a(boolean bl2) {
        if (this.a != null) {
            if (this.a != null) {
                this.a.close();
                this.a = null;
            }
            try {
                if (bl2) {
                    this.a.close();
                }
                return;
            }
            finally {
                this.a = null;
            }
        }
    }
}

