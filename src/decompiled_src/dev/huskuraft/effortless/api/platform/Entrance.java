/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.events.impl.EventRegistry;
import dev.huskuraft.effortless.api.networking.NetworkChannel;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.platform.Server;
import dev.huskuraft.effortless.api.platform.ServerManager;

public interface Entrance {
    public static Entrance getInstance() {
        return PlatformLoader.getSingleton(new Entrance[0]);
    }

    public String getId();

    public EventRegistry getEventRegistry();

    public NetworkChannel<?> getChannel();

    public ServerManager getServerManager();

    default public Server getServer() {
        return this.getServerManager().getRunningServer();
    }
}
