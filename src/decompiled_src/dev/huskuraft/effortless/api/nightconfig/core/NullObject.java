/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

public final class NullObject {
    public static final NullObject NULL_OBJECT = new NullObject();

    private NullObject() {
    }

    public String toString() {
        return "NULL_OBJECT";
    }

    public boolean equals(Object o) {
        return o == this;
    }

    public int hashCode() {
        return 0;
    }
}
