/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.aa;
import dev.babbaj.pathfinder.xz.ac;
import dev.babbaj.pathfinder.xz.ai;
import dev.babbaj.pathfinder.xz.f;
import dev.babbaj.pathfinder.xz.y;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public final class aj
extends ai {
    public aa a;

    public aj() {
        super(new f());
        try {
            this.a = new ac();
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            this.a = new y();
            return;
        }
    }

    @Override
    public final void a(long l2, long l3) {
        super.a(l2, l3);
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(l2);
        byteBuffer.putLong(l3);
        byte[] byArray = byteBuffer.array();
        this.a.a(byArray, 0, byArray.length);
    }
}

