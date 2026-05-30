/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 */
package baritone.api.event.events;

import baritone.api.event.events.type.EventState;
import net.minecraft.client.multiplayer.ClientLevel;

public final class WorldEvent {
    private final ClientLevel world;
    private final EventState state;

    public WorldEvent(ClientLevel clientLevel, EventState eventState) {
        this.world = clientLevel;
        this.state = eventState;
    }

    public final ClientLevel getWorld() {
        return this.world;
    }

    public final EventState getState() {
        return this.state;
    }
}

