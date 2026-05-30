/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.networking.ByteBufSender;
import dev.huskuraft.effortless.api.networking.Packet;
import dev.huskuraft.effortless.api.networking.ResponsiblePacket;
import java.util.function.Consumer;

public interface PacketSender
extends ByteBufSender {
    public void sendPacket(Packet var1, Player var2);

    default public void sendPacket(Packet packet) {
        this.sendPacket(packet, null);
    }

    default public <T extends ResponsiblePacket<?>> void sendPacket(T packet, Consumer<T> callback) {
        this.sendPacket(packet);
    }
}
