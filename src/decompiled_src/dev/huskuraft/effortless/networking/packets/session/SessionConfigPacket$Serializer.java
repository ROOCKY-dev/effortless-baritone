/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.session;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.session.SessionConfigPacket;
import dev.huskuraft.effortless.networking.serializer.SessionConfigSerializer;

public static class SessionConfigPacket.Serializer
implements NetByteBufSerializer<SessionConfigPacket> {
    @Override
    public SessionConfigPacket read(NetByteBuf byteBuf) {
        return new SessionConfigPacket(byteBuf.read(new SessionConfigSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, SessionConfigPacket packet) {
        byteBuf.write(packet.sessionConfig(), new SessionConfigSerializer());
    }
}
