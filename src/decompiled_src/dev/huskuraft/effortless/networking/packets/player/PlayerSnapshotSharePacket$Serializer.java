/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotSharePacket;
import dev.huskuraft.effortless.networking.serializer.SnapshotSerializer;

public static class PlayerSnapshotSharePacket.Serializer
implements NetByteBufSerializer<PlayerSnapshotSharePacket> {
    @Override
    public PlayerSnapshotSharePacket read(NetByteBuf byteBuf) {
        return new PlayerSnapshotSharePacket(byteBuf.readUUID(), byteBuf.readUUID(), byteBuf.read(new SnapshotSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerSnapshotSharePacket packet) {
        byteBuf.writeUUID(packet.from());
        byteBuf.writeUUID(packet.to());
        byteBuf.write(packet.snapshot(), new SnapshotSerializer());
    }
}
