/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotCapturePacket;
import dev.huskuraft.effortless.networking.serializer.SnapshotSerializer;

public static class PlayerSnapshotCapturePacket.Serializer
implements NetByteBufSerializer<PlayerSnapshotCapturePacket> {
    @Override
    public PlayerSnapshotCapturePacket read(NetByteBuf byteBuf) {
        return new PlayerSnapshotCapturePacket(byteBuf.readUUID(), byteBuf.read(new SnapshotSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerSnapshotCapturePacket packet) {
        byteBuf.writeUUID(packet.uuid());
        byteBuf.write(packet.snapshot(), new SnapshotSerializer());
    }
}
