/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.impl;

import dev.huskuraft.effortless.api.events.Event;
import dev.huskuraft.effortless.api.events.EventHolder;
import dev.huskuraft.effortless.api.events.lifecycle.ServerStarted;
import dev.huskuraft.effortless.api.events.lifecycle.ServerStarting;
import dev.huskuraft.effortless.api.events.lifecycle.ServerStopped;
import dev.huskuraft.effortless.api.events.lifecycle.ServerStopping;
import dev.huskuraft.effortless.api.events.networking.RegisterNetwork;
import dev.huskuraft.effortless.api.events.player.PlayerChangeWorld;
import dev.huskuraft.effortless.api.events.player.PlayerLoggedIn;
import dev.huskuraft.effortless.api.events.player.PlayerLoggedOut;
import dev.huskuraft.effortless.api.events.player.PlayerRespawn;

public class EventRegistry
extends EventHolder {
    public Event<RegisterNetwork> getRegisterNetworkEvent() {
        return this.get(new RegisterNetwork[0]);
    }

    public Event<PlayerChangeWorld> getPlayerChangeWorldEvent() {
        return this.get(new PlayerChangeWorld[0]);
    }

    public Event<PlayerRespawn> getPlayerRespawnEvent() {
        return this.get(new PlayerRespawn[0]);
    }

    public Event<PlayerLoggedIn> getPlayerLoggedInEvent() {
        return this.get(new PlayerLoggedIn[0]);
    }

    public Event<PlayerLoggedOut> getPlayerLoggedOutEvent() {
        return this.get(new PlayerLoggedOut[0]);
    }

    public Event<ServerStarting> getServerStartingEvent() {
        return this.get(new ServerStarting[0]);
    }

    public Event<ServerStarted> getServerStartedEvent() {
        return this.get(new ServerStarted[0]);
    }

    public Event<ServerStopping> getServerStoppingEvent() {
        return this.get(new ServerStopping[0]);
    }

    public Event<ServerStopped> getServerStoppedEvent() {
        return this.get(new ServerStopped[0]);
    }
}
