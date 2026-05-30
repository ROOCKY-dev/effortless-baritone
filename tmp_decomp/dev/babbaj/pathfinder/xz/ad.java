/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.ae;
import dev.babbaj.pathfinder.xz.af;
import dev.babbaj.pathfinder.xz.f;
import dev.babbaj.pathfinder.xz.t;
import dev.babbaj.pathfinder.xz.u;
import dev.babbaj.pathfinder.xz.v;
import java.io.EOFException;
import java.io.InputStream;
import java.util.zip.CRC32;

public final class ad
extends af {
    public static boolean a(byte[] byArray, int n2, int n3, int n4) {
        CRC32 cRC32 = new CRC32();
        cRC32.update(byArray, n2, n3);
        long l2 = cRC32.getValue();
        for (n2 = 0; n2 < 4; ++n2) {
            if ((byte)(l2 >>> (n2 << 3)) == byArray[n4 + n2]) continue;
            return false;
        }
        return true;
    }

    public static ae a(byte[] byArray) {
        for (int i2 = 0; i2 < u.a.length; ++i2) {
            if (byArray[i2] == u.a[i2]) continue;
            throw new v();
        }
        if (!ad.a(byArray, u.a.length, 2, u.a.length + 2)) {
            throw new f("XZ Stream Header is corrupt");
        }
        try {
            return ad.a(byArray, u.a.length);
        }
        catch (t t2) {
            throw new t("Unsupported options in XZ Stream Header");
        }
    }

    public static ae b(byte[] byArray) {
        ae ae2;
        if (byArray[10] != u.b[0] || byArray[11] != u.b[1]) {
            throw new f("XZ Stream Footer is corrupt");
        }
        if (!ad.a(byArray, 4, 6, 0)) {
            throw new f("XZ Stream Footer is corrupt");
        }
        try {
            ae2 = ad.a(byArray, 8);
        }
        catch (t t2) {
            throw new t("Unsupported options in XZ Stream Footer");
        }
        ae2.a = 0L;
        for (int i2 = 0; i2 < 4; ++i2) {
            ae2.a |= (long)((byArray[i2 + 4] & 0xFF) << (i2 << 3));
        }
        ae2.a = ae2.a + 1L << 2;
        return ae2;
    }

    private static ae a(byte[] byArray, int n2) {
        if (byArray[n2] != 0 || (byArray[n2 + 1] & 0xFF) >= 16) {
            throw new t();
        }
        ae ae2 = new ae();
        new ae().a = byArray[n2 + 1];
        return ae2;
    }

    public static long a(InputStream inputStream) {
        int n2 = inputStream.read();
        if (n2 == -1) {
            throw new EOFException();
        }
        long l2 = n2 & 0x7F;
        int n3 = 0;
        while ((n2 & 0x80) != 0) {
            if (++n3 >= 9) {
                throw new f();
            }
            n2 = inputStream.read();
            if (n2 == -1) {
                throw new EOFException();
            }
            if (n2 == 0) {
                throw new f();
            }
            l2 |= (long)(n2 & 0x7F) << n3 * 7;
        }
        return l2;
    }
}

