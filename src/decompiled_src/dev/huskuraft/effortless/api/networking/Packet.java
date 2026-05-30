/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.networking.PacketListener;

public interface Packet<T extends PacketListener> {
    public void handle(T var1, Player var2);
}
