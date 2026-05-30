/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.Player;
import io.netty.buffer.ByteBuf;

public interface ByteBufReceiver {
    public void receiveBuffer(ByteBuf var1, Player var2);
}
