/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.Packet
 */
package baritone.api.event.events;

import baritone.api.event.events.type.EventState;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;

public final class PacketEvent {
    private final Connection networkManager;
    private final EventState state;
    private final Packet<?> packet;

    public PacketEvent(Connection connection, EventState eventState, Packet<?> packet) {
        this.networkManager = connection;
        this.state = eventState;
        this.packet = packet;
    }

    public final Connection getNetworkManager() {
        return this.networkManager;
    }

    public final EventState getState() {
        return this.state;
    }

    public final Packet<?> getPacket() {
        return this.packet;
    }

    public final <T extends Packet<?>> T cast() {
        return (T)this.packet;
    }
}

