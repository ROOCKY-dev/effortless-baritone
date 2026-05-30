/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.NetByteBuf;

public interface NetByteBufReader<T> {
    public T read(NetByteBuf var1);
}
