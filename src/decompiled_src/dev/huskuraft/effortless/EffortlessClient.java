/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.auto.service.AutoService
 */
package dev.huskuraft.effortless;

import com.google.auto.service.AutoService;
import dev.huskuraft.effortless.EffortlessClientConfigStorage;
import dev.huskuraft.effortless.EffortlessClientManager;
import dev.huskuraft.effortless.EffortlessClientNetworkChannel;
import dev.huskuraft.effortless.EffortlessClientSessionManager;
import dev.huskuraft.effortless.EffortlessClientStructureBuilder;
import dev.huskuraft.effortless.EffortlessClientTagConfigStorage;
import dev.huskuraft.effortless.api.events.impl.ClientEventRegistry;
import dev.huskuraft.effortless.api.platform.ClientEntrance;
import dev.huskuraft.effortless.api.platform.PlatformLoader;

@AutoService(value={ClientEntrance.class})
public class EffortlessClient
implements ClientEntrance {
    private final ClientEventRegistry eventRegistry = PlatformLoader.getSingleton(ClientEventRegistry.class);
    private final EffortlessClientNetworkChannel channel = new EffortlessClientNetworkChannel(this);
    private final EffortlessClientStructureBuilder structureBuilder = new EffortlessClientStructureBuilder(this);
    private final EffortlessClientManager clientManager = new EffortlessClientManager(this);
    private final EffortlessClientTagConfigStorage tagConfigStorage = new EffortlessClientTagConfigStorage(this);
    private final EffortlessClientConfigStorage configStorage = new EffortlessClientConfigStorage(this);
    private final EffortlessClientSessionManager sessionManager = new EffortlessClientSessionManager(this);

    public static EffortlessClient getInstance() {
        return (EffortlessClient)ClientEntrance.getInstance();
    }

    @Override
    public ClientEventRegistry getEventRegistry() {
        return this.eventRegistry;
    }

    public EffortlessClientNetworkChannel getChannel() {
        return this.channel;
    }

    public EffortlessClientStructureBuilder getStructureBuilder() {
        return this.structureBuilder;
    }

    @Override
    public EffortlessClientManager getClientManager() {
        return this.clientManager;
    }

    @Deprecated
    public EffortlessClientTagConfigStorage getTagConfigStorage() {
        return this.tagConfigStorage;
    }

    public EffortlessClientConfigStorage getConfigStorage() {
        return this.configStorage;
    }

    public EffortlessClientSessionManager getSessionManager() {
        return this.sessionManager;
    }

    @Override
    public String getId() {
        return "effortless";
    }
}
