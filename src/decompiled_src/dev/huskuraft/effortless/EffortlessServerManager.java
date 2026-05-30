/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.Effortless;
import dev.huskuraft.effortless.api.platform.Server;
import dev.huskuraft.effortless.api.platform.ServerManager;
import java.util.concurrent.atomic.AtomicReference;

public final class EffortlessServerManager
implements ServerManager {
    private final Effortless entrance;
    private final AtomicReference<Server> runningServer = new AtomicReference();
    private int interactionCooldown = 0;

    public EffortlessServerManager(Effortless entrance) {
        this.entrance = entrance;
        this.getEntrance().getEventRegistry().getServerStartedEvent().register(this::onServerStarted);
        this.getEntrance().getEventRegistry().getServerStoppedEvent().register(this::onServerStarted);
    }

    private Effortless getEntrance() {
        return this.entrance;
    }

    public void onServerStarted(Server server) {
        this.runningServer.set(server);
    }

    public void onServerStopped(Server server) {
        this.runningServer.set(null);
    }

    @Override
    public Server getRunningServer() {
        return this.runningServer.get();
    }
}
