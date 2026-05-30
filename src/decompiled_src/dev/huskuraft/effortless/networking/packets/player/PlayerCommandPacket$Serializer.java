/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.SingleCommand;
import dev.huskuraft.effortless.networking.packets.player.PlayerCommandPacket;

public static class PlayerCommandPacket.Serializer
implements NetByteBufSerializer<PlayerCommandPacket> {
    @Override
    public PlayerCommandPacket read(NetByteBuf byteBuf) {
        return new PlayerCommandPacket(byteBuf.readUUID(), byteBuf.readEnum(SingleCommand.class));
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerCommandPacket packet) {
        byteBuf.writeUUID(packet.responseId());
        byteBuf.writeEnum(packet.action());
    }
}
