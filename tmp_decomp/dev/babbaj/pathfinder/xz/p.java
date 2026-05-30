/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.ak;
import dev.babbaj.pathfinder.xz.al;
import dev.babbaj.pathfinder.xz.an;
import dev.babbaj.pathfinder.xz.ao;
import dev.babbaj.pathfinder.xz.ap;
import dev.babbaj.pathfinder.xz.ar;
import dev.babbaj.pathfinder.xz.as;
import dev.babbaj.pathfinder.xz.at;
import dev.babbaj.pathfinder.xz.av;
import dev.babbaj.pathfinder.xz.aw;
import dev.babbaj.pathfinder.xz.f;
import dev.babbaj.pathfinder.xz.w;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class p
extends InputStream {
    private final a a;
    private DataInputStream a;
    private ak a;
    private aw a;
    private ap a;
    private int a;
    private boolean a;
    private boolean b = true;
    private boolean c = true;
    private boolean d = false;
    private IOException a;
    private final byte[] a = new byte[1];

    public static int a(int n2) {
        return 104 + p.b(n2) / 1024;
    }

    private static int b(int n2) {
        if (n2 < 4096 || n2 > 0x7FFFFFF0) {
            throw new IllegalArgumentException("Unsupported dictionary size ".concat(String.valueOf(n2)));
        }
        return n2 + 15 & 0xFFFFFFF0;
    }

    p(InputStream inputStream, int n2, a a2) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        this.a = a2;
        this.a = new DataInputStream(inputStream);
        this.a = new aw();
        this.a = new ak(p.b(n2));
    }

    @Override
    public final int read() {
        p p2 = this;
        if (p2.read(p2.a, 0, 1) == -1) {
            return -1;
        }
        return this.a[0] & 0xFF;
    }

    @Override
    public final int read(byte[] byArray, int n2, int n3) {
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
        if (this.d) {
            return -1;
        }
        try {
            int n4 = 0;
            while (n3 > 0) {
                Object object;
                int n5;
                block49: {
                    int n6;
                    int n7;
                    int n8;
                    Object object2;
                    int n9;
                    block48: {
                        if (this.a == 0) {
                            p p2 = this;
                            n9 = p2.a.readUnsignedByte();
                            if (n9 == 0) {
                                p2.d = true;
                                p2.a();
                            } else {
                                if (n9 >= 224 || n9 == 1) {
                                    p2.c = true;
                                    p2.b = false;
                                    object2 = p2.a;
                                    p2.a.b = 0;
                                    ((ak)object2).c = 0;
                                    ((ak)object2).d = 0;
                                    ((ak)object2).e = 0;
                                    ((ak)object2).a[((ak)object2).a - 1] = 0;
                                } else if (p2.b) {
                                    throw new f();
                                }
                                if (n9 >= 128) {
                                    p2.a = true;
                                    p2.a = (n9 & 0x1F) << 16;
                                    p2.a += p2.a.readUnsignedShort() + 1;
                                    n5 = p2.a.readUnsignedShort() + 1;
                                    if (n9 >= 192) {
                                        p2.c = false;
                                        object2 = p2;
                                        n9 = ((p)object2).a.readUnsignedByte();
                                        if (n9 > 224) {
                                            throw new f();
                                        }
                                        n8 = n9 / 45;
                                        n9 -= n8 * 9 * 5;
                                        if ((n9 -= (n7 = n9 / 9) * 9) + n7 > 4) {
                                            throw new f();
                                        }
                                        ((p)object2).a = new ap(((p)object2).a, ((p)object2).a, n9, n7, n8);
                                    } else {
                                        if (p2.c) {
                                            throw new f();
                                        }
                                        if (n9 >= 160) {
                                            p2.a.a();
                                        }
                                    }
                                    n8 = n5;
                                    DataInputStream dataInputStream = p2.a;
                                    object2 = p2.a;
                                    if (n8 < 5) {
                                        throw new f();
                                    }
                                    if (dataInputStream.readUnsignedByte() != 0) {
                                        throw new f();
                                    }
                                    ((av)object2).b = dataInputStream.readInt();
                                    ((av)object2).a = -1;
                                    ((aw)object2).c = ((aw)object2).a.length - (n8 -= 5);
                                    dataInputStream.readFully(((aw)object2).a, ((aw)object2).c, n8);
                                } else {
                                    if (n9 > 2) {
                                        throw new f();
                                    }
                                    p2.a = false;
                                    p2.a = p2.a.readUnsignedShort() + 1;
                                }
                            }
                            if (this.d) {
                                if (n4 == 0) {
                                    return -1;
                                }
                                return n4;
                            }
                        }
                        n6 = Math.min(this.a, n3);
                        if (this.a) break block48;
                        n5 = n6;
                        DataInputStream dataInputStream = this.a;
                        object = this.a;
                        int n10 = Math.min(((ak)object).a - ((ak)object).c, n5);
                        dataInputStream.readFully(((ak)object).a, ((ak)object).c, n10);
                        ((ak)object).c += n10;
                        if (((ak)object).d < ((ak)object).c) {
                            ((ak)object).d = ((ak)object).c;
                        }
                        break block49;
                    }
                    n9 = n6;
                    object = this.a;
                    ((ak)object).e = ((ak)object).a - ((ak)object).c <= n9 ? ((ak)object).a : ((ak)object).c + n9;
                    object = this.a;
                    object2 = ((ap)object).a;
                    if (((ak)object2).f > 0) {
                        ak ak2 = object2;
                        ak2.a(ak2.g, ((ak)object2).f);
                    }
                    while (true) {
                        int n11;
                        block51: {
                            block53: {
                                block52: {
                                    block50: {
                                        int n12;
                                        int n13;
                                        object2 = ((ap)object).a;
                                        if (!(((ak)object2).c < ((ak)object2).e)) break;
                                        n9 = ((ap)object).a.c & ((al)object).a;
                                        if (((ap)object).a$1a7015fc.a(((al)object).a[((al)object).a.a], n9) == 0) {
                                            object2 = ((ap)object).a;
                                            n9 = ((ar)object2).a.a.c;
                                            n7 = ((ar)object2).a.a.a(0);
                                            Object object3 = object2;
                                            n5 = n7 >> 8 - ((an)object3).a;
                                            n13 = (n9 & ((an)object3).b) << ((an)object3).a;
                                            n9 = n5 + n13;
                                            object3 = ((ar)object2).a[n9];
                                            n7 = 1;
                                            if (((al)((as)object3).a.a).a.a < 7) {
                                                while ((n7 = n7 << 1 | ((as)object3).a.a.a$1a7015fc.a(((ao)object3).a, n7)) < 256) {
                                                }
                                            } else {
                                                n9 = ((as)object3).a.a.a.a(((al)((as)object3).a.a).a[0]);
                                                n5 = 256;
                                                do {
                                                    n13 = (n9 <<= 1) & n5;
                                                    n12 = ((as)object3).a.a.a$1a7015fc.a(((ao)object3).a, n5 + n13 + n7);
                                                    n7 = n7 << 1 | n12;
                                                    n5 &= 0 - n12 ^ ~n13;
                                                } while (n7 < 256);
                                            }
                                            n5 = (byte)n7;
                                            Object object4 = ((as)object3).a.a.a;
                                            ((ak)object4).a[((ak)object4).c++] = n5;
                                            if (((ak)object4).d < ((ak)object4).c) {
                                                ((ak)object4).d = ((ak)object4).c;
                                            }
                                            object4 = ((al)((as)object3).a.a).a;
                                            if (((at)object4).a <= 3) {
                                                ((at)object4).a = 0;
                                                continue;
                                            }
                                            if (((at)object4).a <= 9) {
                                                ((at)object4).a -= 3;
                                                continue;
                                            }
                                            ((at)object4).a -= 6;
                                            continue;
                                        }
                                        if (((ap)object).a$1a7015fc.a(((al)object).a, ((al)object).a.a) != 0) break block50;
                                        object2 = object;
                                        ((al)object2).a.a = ((al)object2).a.a < 7 ? 7 : 10;
                                        ((al)object2).a[3] = ((al)object2).a[2];
                                        ((al)object2).a[2] = ((al)object2).a[1];
                                        ((al)object2).a[1] = ((al)object2).a[0];
                                        n8 = ((ap)object2).a.a(n9);
                                        n5 = n8;
                                        n7 = ((ap)object2).a$1a7015fc.a(((al)object2).c[n5 < 6 ? n5 - 2 : 3]);
                                        if (n7 < 4) {
                                            ((al)object2).a[0] = n7;
                                        } else {
                                            n9 = (n7 >> 1) - 1;
                                            ((al)object2).a[0] = (2 | n7 & 1) << n9;
                                            if (n7 < 14) {
                                                ((al)object2).a[0] = ((al)object2).a[0] | ((ap)object2).a$1a7015fc.b(((al)object2).d[n7 - 4]);
                                            } else {
                                                int n14 = ((al)object2).a[0];
                                                n13 = n9 - 4;
                                                aw aw2 = ((ap)object2).a$1a7015fc;
                                                n12 = 0;
                                                do {
                                                    ((av)aw2).a();
                                                    ((av)aw2).a >>>= 1;
                                                    n9 = aw2.b - ((av)aw2).a >>> 31;
                                                    aw2.b -= ((av)aw2).a & n9 - 1;
                                                    n12 = n12 << 1 | 1 - n9;
                                                } while (--n13 != 0);
                                                ((al)object2).a[0] = n14 | n12 << 4;
                                                ((al)object2).a[0] = ((al)object2).a[0] | ((ap)object2).a$1a7015fc.b(((al)object2).e);
                                            }
                                        }
                                        n11 = n8;
                                        break block51;
                                    }
                                    object2 = object;
                                    if (((ap)object2).a$1a7015fc.a(((al)object2).b, ((al)object2).a.a) != 0) break block52;
                                    if (((ap)object2).a$1a7015fc.a(((al)object2).b[((al)object2).a.a], n9) != 0) break block53;
                                    ((al)object2).a.a = ((al)object2).a.a < 7 ? 9 : 11;
                                    n11 = 1;
                                    break block51;
                                }
                                if (((ap)object2).a$1a7015fc.a(((al)object2).c, ((al)object2).a.a) == 0) {
                                    n8 = ((al)object2).a[1];
                                } else {
                                    if (((ap)object2).a$1a7015fc.a(((al)object2).d, ((al)object2).a.a) == 0) {
                                        n8 = ((al)object2).a[2];
                                    } else {
                                        n8 = ((al)object2).a[3];
                                        ((al)object2).a[3] = ((al)object2).a[2];
                                    }
                                    ((al)object2).a[2] = ((al)object2).a[1];
                                }
                                ((al)object2).a[1] = ((al)object2).a[0];
                                ((al)object2).a[0] = n8;
                            }
                            ((al)object2).a.a = ((al)object2).a.a < 7 ? 8 : 11;
                            n11 = ((ap)object2).b.a(n9);
                        }
                        n5 = n11;
                        ((ap)object).a.a(((al)object).a[0], n5);
                    }
                    ((av)((ap)object).a$1a7015fc).a();
                }
                n5 = n2;
                byte[] byArray2 = byArray;
                object = this.a;
                int n15 = ((ak)object).c - ((ak)object).b;
                if (((ak)object).c == ((ak)object).a) {
                    ((ak)object).c = 0;
                }
                System.arraycopy(((ak)object).a, ((ak)object).b, byArray2, n5, n15);
                ((ak)object).b = ((ak)object).c;
                int n16 = n15;
                n2 += n16;
                n3 -= n16;
                n4 += n16;
                this.a -= n16;
                if (this.a != 0) continue;
                aw aw3 = this.a;
                if (aw3.c == aw3.a.length && aw3.b == 0 && !(this.a.f > 0)) continue;
                throw new f();
            }
            return n4;
        }
        catch (IOException iOException) {
            this.a = iOException;
            throw iOException;
        }
    }

    @Override
    public final int available() {
        if (this.a == null) {
            throw new w("Stream closed");
        }
        if (this.a != null) {
            throw this.a;
        }
        if (this.a) {
            return this.a;
        }
        return Math.min(this.a, this.a.available());
    }

    private void a() {
        if (this.a != null) {
            this.a = null;
            this.a = null;
        }
    }

    @Override
    public final void close() {
        if (this.a != null) {
            this.a();
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

