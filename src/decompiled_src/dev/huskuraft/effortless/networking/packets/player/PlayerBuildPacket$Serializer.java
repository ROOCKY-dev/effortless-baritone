/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPacket;
import dev.huskuraft.effortless.networking.serializer.ContextSerializer;

public static class PlayerBuildPacket.Serializer
implements NetByteBufSerializer<PlayerBuildPacket> {
    @Override
    public PlayerBuildPacket read(NetByteBuf byteBuf) {
        return new PlayerBuildPacket(byteBuf.readUUID(), byteBuf.read(new ContextSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerBuildPacket packet) {
        byteBuf.writeUUID(packet.playerId());
        byteBuf.write(packet.context(), new ContextSerializer());
    }
}
