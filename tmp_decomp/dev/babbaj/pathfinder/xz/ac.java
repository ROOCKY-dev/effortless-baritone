/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.aa;
import java.security.MessageDigest;

public final class ac
extends aa {
    private final MessageDigest a;

    public ac() {
        ((aa)this).a = 32;
        ((aa)this).a = "SHA-256";
        this.a = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public final void a(byte[] byArray, int n2, int n3) {
        this.a.update(byArray, n2, n3);
    }

    @Override
    public final byte[] a() {
        byte[] byArray = this.a.digest();
        this.a.reset();
        return byArray;
    }
}

