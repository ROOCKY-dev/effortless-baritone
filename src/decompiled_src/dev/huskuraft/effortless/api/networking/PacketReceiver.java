/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.networking.ByteBufReceiver;
import dev.huskuraft.effortless.api.networking.Packet;

public interface PacketReceiver
extends ByteBufReceiver {
    public void receivePacket(Packet var1, Player var2);

    default public void receivePacket(Packet packet) {
        this.receivePacket(packet, null);
    }
}
