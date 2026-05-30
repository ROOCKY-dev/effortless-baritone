/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder.xz;

import dev.babbaj.pathfinder.xz.ab;
import dev.babbaj.pathfinder.xz.ac;
import dev.babbaj.pathfinder.xz.t;
import dev.babbaj.pathfinder.xz.y;
import dev.babbaj.pathfinder.xz.z;
import java.security.NoSuchAlgorithmException;

public abstract class aa {
    public int a;
    public String a;

    public abstract void a(byte[] var1, int var2, int var3);

    public abstract byte[] a();

    public static aa a(int n2) {
        switch (n2) {
            case 0: {
                return new ab();
            }
            case 1: {
                return new y();
            }
            case 4: {
                return new z();
            }
            case 10: {
                try {
                    return new ac();
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
            }
        }
        throw new t("Unsupported Check ID ".concat(String.valueOf(n2)));
    }
}

