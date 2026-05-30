/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.NetByteBuf;

public interface NetByteBufWriter<T> {
    public void write(NetByteBuf var1, T var2);
}
