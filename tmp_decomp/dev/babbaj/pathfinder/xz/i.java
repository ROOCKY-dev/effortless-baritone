/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.a;
import dev.babbaj.pathfinder.xz.h;
import dev.babbaj.pathfinder.xz.j;
import dev.babbaj.pathfinder.xz.l;
import dev.babbaj.pathfinder.xz.t;
import java.io.InputStream;

final class i
extends h
implements l {
    private final int a;

    i(byte[] byArray) {
        if (byArray.length != 1) {
            throw new t("Unsupported Delta filter properties");
        }
        this.a = (byArray[0] & 0xFF) + 1;
    }

    @Override
    public final int a() {
        return 1;
    }

    @Override
    public final InputStream a(InputStream inputStream, a a2) {
        return new j(inputStream, this.a);
    }
}

