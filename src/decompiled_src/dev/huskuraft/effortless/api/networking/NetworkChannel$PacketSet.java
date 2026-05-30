/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.api.networking.NetworkChannel;
import dev.huskuraft.effortless.api.networking.Packet;
import dev.huskuraft.effortless.api.networking.PacketListener;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

private class NetworkChannel.PacketSet<T extends PacketListener> {
    private final Map<Class<?>, Integer> classToId = new LinkedHashMap();
    private final List<NetByteBufSerializer<? extends Packet<T>>> idToDeserializer = new ArrayList<NetByteBufSerializer<? extends Packet<T>>>();

    private NetworkChannel.PacketSet(NetworkChannel networkChannel) {
    }

    public <P extends Packet<T>> NetworkChannel.PacketSet<T> addPacket(Class<P> clazz, NetByteBufSerializer<P> serializer) {
        if (this.classToId.containsKey(clazz)) {
            throw new IllegalArgumentException("Packet " + String.valueOf(clazz) + " is already registered to ID " + String.valueOf(this.classToId.get(clazz)));
        }
        this.classToId.put(clazz, this.idToDeserializer.size());
        this.idToDeserializer.add(serializer);
        return this;
    }

    @Nullable
    public Integer getId(Class<?> clazz) {
        return this.classToId.getOrDefault(clazz, null);
    }

    public ByteBuf createBuffer(Packet<T> packet) {
        Integer id = this.getId(packet.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Packet " + String.valueOf(packet.getClass()) + " is not registered");
        }
        ByteBuf buffer = Unpooled.buffer();
        NetByteBufSerializer<Packet<Packet<T>>> serializer = this.idToDeserializer.get(this.getId(packet.getClass()));
        buffer.writeInt(id.intValue());
        serializer.write(new NetByteBuf(buffer), packet);
        return buffer;
    }

    @Nullable
    public Packet<?> createPacket(ByteBuf byteBuf) {
        int id = byteBuf.readInt();
        NetByteBufSerializer<Packet<T>> serializer = this.idToDeserializer.get(id);
        if (serializer != null) {
            return (Packet)serializer.read(new NetByteBuf(byteBuf));
        }
        return null;
    }
}
