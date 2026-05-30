/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.utils;

import java.util.Objects;

public final class Pair<A, B> {
    private final A a;
    private final B b;

    public Pair(A a2, B b2) {
        this.a = a2;
        this.b = b2;
    }

    public final A first() {
        return this.a;
    }

    public final B second() {
        return this.b;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != Pair.class) {
            return false;
        }
        object = (Pair)object;
        return Objects.equals(this.a, ((Pair)object).a) && Objects.equals(this.b, ((Pair)object).b);
    }

    public final int hashCode() {
        return 31 * Objects.hashCode(this.a) + Objects.hashCode(this.b);
    }
}

