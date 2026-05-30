/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.session;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.networking.packets.session.SessionPacket;
import dev.huskuraft.effortless.networking.serializer.SessionSerializer;

public static class SessionPacket.Serializer
implements NetByteBufSerializer<SessionPacket> {
    @Override
    public SessionPacket read(NetByteBuf byteBuf) {
        return new SessionPacket(byteBuf.read(new SessionSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, SessionPacket packet) {
        byteBuf.write(packet.session(), new SessionSerializer());
    }
}
