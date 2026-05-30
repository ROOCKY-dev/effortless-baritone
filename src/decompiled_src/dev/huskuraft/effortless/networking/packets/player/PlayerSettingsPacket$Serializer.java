/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.player.PlayerSettingsPacket;

public static class PlayerSettingsPacket.Serializer
implements NetByteBufSerializer<PlayerSettingsPacket> {
    @Override
    public PlayerSettingsPacket read(NetByteBuf byteBuf) {
        return new PlayerSettingsPacket();
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerSettingsPacket packet) {
    }
}
