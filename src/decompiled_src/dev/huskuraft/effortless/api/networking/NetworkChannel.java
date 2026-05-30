/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.networking.ByteBufSender;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.api.networking.NetworkRegistry;
import dev.huskuraft.effortless.api.networking.Packet;
import dev.huskuraft.effortless.api.networking.PacketChannel;
import dev.huskuraft.effortless.api.networking.PacketListener;
import dev.huskuraft.effortless.api.networking.ResponsiblePacket;
import dev.huskuraft.effortless.api.networking.Side;
import dev.huskuraft.effortless.api.platform.Entrance;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public abstract class NetworkChannel<P extends PacketListener>
implements PacketChannel {
    private final Entrance entrance;
    private final String name;
    private final Side side;
    private final Map<UUID, Consumer<? extends ResponsiblePacket<?>>> responseMap = Collections.synchronizedMap(new HashMap());
    private PacketSet<P> packetSet = new PacketSet(this);
    private ByteBufSender sender;

    protected NetworkChannel(Entrance entrance, String name, Side side) {
        this.entrance = entrance;
        this.name = name;
        this.side = side;
    }

    @Override
    public void sendPacket(Packet packet, Player player) {
        this.sendBuffer(this.createBuffer(packet), player);
    }

    @Override
    public void sendBuffer(ByteBuf byteBuf, Player player) {
        this.sender.sendBuffer(byteBuf, player);
    }

    public void onRegisterNetwork(NetworkRegistry registry) {
        this.sender = registry.register(this.getChannelId(), this.side, this);
    }

    @Override
    public <T extends ResponsiblePacket<?>> void sendPacket(T packet, Consumer<T> callback) {
        this.responseMap.put(packet.responseId(), callback);
        this.sendPacket(packet);
    }

    @Override
    public abstract void receivePacket(Packet var1, Player var2);

    @Override
    public void receiveBuffer(ByteBuf byteBuf, Player player) {
        Packet<P> packet = null;
        try {
            packet = this.createPacket(byteBuf);
            Objects.requireNonNull(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create packet in channel '" + String.valueOf(this.getChannelId()) + "'", e);
        }
        try {
            ResponsiblePacket responsiblePacket;
            Consumer<ResponsiblePacket<?>> callback;
            Packet<P> packet1 = packet;
            this.receivePacket(packet1, player);
            if (packet instanceof ResponsiblePacket && (callback = this.responseMap.remove((responsiblePacket = (ResponsiblePacket)packet).responseId())) != null) {
                callback.accept(responsiblePacket);
            }
        }
        catch (ClassCastException | RejectedExecutionException runtimeException) {
            // empty catch block
        }
    }

    public Packet<P> createPacket(ByteBuf byteBuf) {
        return this.packetSet.createPacket(byteBuf);
    }

    public ByteBuf createBuffer(Packet<P> packet) {
        return this.packetSet.createBuffer(packet);
    }

    public <T extends Packet<P>> void registerPacket(Class<T> clazz, NetByteBufSerializer<T> serializer) {
        try {
            this.packetSet.addPacket(clazz, serializer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterPackets() {
        this.packetSet = new PacketSet(this);
    }

    @Override
    public abstract int getCompatibilityVersion();

    @Override
    public final ResourceLocation getChannelId() {
        return ResourceLocation.of(this.entrance.getId(), this.name);
    }

    private class PacketSet<T extends PacketListener> {
        private final Map<Class<?>, Integer> classToId = new LinkedHashMap();
        private final List<NetByteBufSerializer<? extends Packet<T>>> idToDeserializer = new ArrayList<NetByteBufSerializer<? extends Packet<T>>>();

        private PacketSet(NetworkChannel networkChannel) {
        }

        public <P extends Packet<T>> PacketSet<T> addPacket(Class<P> clazz, NetByteBufSerializer<P> serializer) {
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
}
