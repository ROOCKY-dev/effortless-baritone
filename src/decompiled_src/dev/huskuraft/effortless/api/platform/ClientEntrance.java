/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.events.impl.ClientEventRegistry;
import dev.huskuraft.effortless.api.platform.Client;
import dev.huskuraft.effortless.api.platform.ClientManager;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.platform.ServerManager;

public interface ClientEntrance
extends Entrance {
    public static ClientEntrance getInstance() {
        return PlatformLoader.getSingleton(new ClientEntrance[0]);
    }

    public ClientManager getClientManager();

    @Override
    public ClientEventRegistry getEventRegistry();

    default public Client getClient() {
        return this.getClientManager().getRunningClient();
    }

    @Override
    default public ServerManager getServerManager() {
        throw new UnsupportedOperationException();
    }
}
