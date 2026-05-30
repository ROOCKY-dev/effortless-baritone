/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.player.PlayerPermissionCheckPacket;

public static class PlayerPermissionCheckPacket.Serializer
implements NetByteBufSerializer<PlayerPermissionCheckPacket> {
    @Override
    public PlayerPermissionCheckPacket read(NetByteBuf byteBuf) {
        return new PlayerPermissionCheckPacket(byteBuf.readUUID(), byteBuf.readUUID(), byteBuf.readBoolean());
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerPermissionCheckPacket packet) {
        byteBuf.writeUUID(packet.responseId());
        byteBuf.writeUUID(packet.playerId());
        byteBuf.writeBoolean(packet.granted());
    }
}
