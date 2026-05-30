/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

public interface PacketListener {
    default public boolean shouldPropagateHandlingExceptions() {
        return true;
    }
}
