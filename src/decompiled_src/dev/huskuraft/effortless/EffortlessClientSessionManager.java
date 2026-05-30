/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.events.lifecycle.ClientTick;
import dev.huskuraft.effortless.api.platform.Client;
import dev.huskuraft.effortless.api.platform.Mod;
import dev.huskuraft.effortless.api.platform.Platform;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.networking.packets.session.SessionConfigPacket;
import dev.huskuraft.effortless.session.Session;
import dev.huskuraft.effortless.session.SessionManager;
import dev.huskuraft.effortless.session.config.ConstraintConfig;
import dev.huskuraft.effortless.session.config.SessionConfig;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

public final class EffortlessClientSessionManager
implements SessionManager {
    private final EffortlessClient entrance;
    private final AtomicReference<Session> serverSession = new AtomicReference();
    private final AtomicReference<Session> clientSession = new AtomicReference();
    private final AtomicReference<Boolean> isPlayerNotified = new AtomicReference<Boolean>(false);
    private final AtomicReference<SessionConfig> serverSessionConfig = new AtomicReference();

    public EffortlessClientSessionManager(EffortlessClient entrance) {
        this.entrance = entrance;
        this.getEntrance().getEventRegistry().getClientTickEvent().register(this::onClientTick);
    }

    private EffortlessClient getEntrance() {
        return this.entrance;
    }

    @Override
    public void onSession(Session session, Player player) {
        this.serverSession.set(session);
        this.isPlayerNotified.set(false);
    }

    @Override
    public void onSessionConfig(SessionConfig sessionConfig, Player player) {
        this.serverSessionConfig.set(sessionConfig);
        this.getEntrance().getStructureBuilder().onSessionConfig(sessionConfig);
    }

    public void updateSessionConfig(SessionConfig sessionConfig) {
        this.getEntrance().getChannel().sendPacket(new SessionConfigPacket(sessionConfig));
    }

    public boolean updateGlobalConfig(ConstraintConfig constraintConfig) {
        SessionConfig serverSessionConfig = this.getServerSessionConfig();
        if (serverSessionConfig == null) {
            return false;
        }
        this.updateSessionConfig(serverSessionConfig.withGlobalConfig(constraintConfig));
        return true;
    }

    public boolean updatePlayerConfig(Map<UUID, ConstraintConfig> playerConfigs) {
        SessionConfig serverSessionConfig = this.getServerSessionConfig();
        if (serverSessionConfig == null) {
            return false;
        }
        this.updateSessionConfig(serverSessionConfig.withPlayerConfig(playerConfigs));
        return true;
    }

    public boolean updatePlayerConfig(UUID id, ConstraintConfig constraintConfig) {
        SessionConfig serverSessionConfig = this.getServerSessionConfig();
        if (serverSessionConfig == null) {
            return false;
        }
        this.updateSessionConfig(serverSessionConfig.withPlayerConfig(id, constraintConfig));
        return true;
    }

    @Override
    public boolean isSessionValid() {
        return this.getSessionStatus() == SessionManager.SessionStatus.SUCCESS;
    }

    @Override
    public SessionManager.SessionStatus getSessionStatus() {
        if (this.serverSession.get() == null && this.clientSession.get() == null) {
            return SessionManager.SessionStatus.MOD_MISSING;
        }
        if (this.serverSession.get() == null || this.serverSessionConfig.get() == null) {
            return SessionManager.SessionStatus.SERVER_MOD_MISSING;
        }
        if (this.clientSession.get() == null) {
            return SessionManager.SessionStatus.CLIENT_MOD_MISSING;
        }
        Mod serverMod = this.serverSession.get().mods().stream().filter(mod -> mod.getId().equals("effortless")).findFirst().orElseThrow();
        Mod clientMod = this.clientSession.get().mods().stream().filter(mod -> mod.getId().equals("effortless")).findFirst().orElseThrow();
        if (!serverMod.getVersionStr().equals(clientMod.getVersionStr())) {
            return SessionManager.SessionStatus.PROTOCOL_NOT_MATCH;
        }
        return SessionManager.SessionStatus.SUCCESS;
    }

    @Override
    public Session getLastSession() {
        Platform platform = Platform.getInstance();
        int protocolVersion = 13;
        return new Session(platform.getLoaderType(), platform.getLoaderVersion(), platform.getGameVersion(), platform.getRunningMods(), protocolVersion);
    }

    @Override
    public SessionConfig getLastSessionConfig() {
        return null;
    }

    public Session getServerSession() {
        return this.serverSession.get();
    }

    @Nullable
    public SessionConfig getServerSessionConfig() {
        return this.serverSessionConfig.get();
    }

    public SessionConfig getServerSessionConfigOrEmpty() {
        return this.serverSessionConfig.get() != null ? this.serverSessionConfig.get() : SessionConfig.EMPTY;
    }

    public void notifyPlayer() {
        Text message;
        Text id = Text.text("[").append(Text.translate("effortless.name")).append(Text.text("] ")).withStyle(ChatFormatting.GRAY);
        switch (this.getSessionStatus()) {
            default: {
                throw new MatchException(null, null);
            }
            case MOD_MISSING: {
                Text text = Text.translate("effortless.session_status.message.mod_missing");
                break;
            }
            case SERVER_MOD_MISSING: {
                Text text = Text.translate("effortless.session_status.message.server_mod_missing");
                break;
            }
            case CLIENT_MOD_MISSING: {
                Text text = Text.translate("effortless.session_status.message.client_mod_missing");
                break;
            }
            case PROTOCOL_NOT_MATCH: {
                Text text = Text.translate("effortless.session_status.message.protocol_not_match", this.serverSession.get().protocolVersion(), this.clientSession.get().protocolVersion());
                break;
            }
            case SUCCESS: {
                Text text = message = Text.translate("effortless.session_status.message.success", this.serverSession.get().loaderType().name());
            }
        }
        if (this.getSessionStatus() != SessionManager.SessionStatus.SUCCESS) {
            this.getEntrance().getClientManager().getRunningClient().getPlayer().sendMessage(id.append(message));
        }
    }

    public void onClientTick(Client client, ClientTick.Phase phase) {
        if (phase == ClientTick.Phase.END) {
            return;
        }
        if (this.getEntrance().getClient() == null || this.getEntrance().getClient().getPlayer() == null) {
            this.serverSession.set(null);
            this.serverSessionConfig.set(null);
            this.isPlayerNotified.set(false);
            return;
        }
        Player player = this.getEntrance().getClient().getPlayer();
        if (this.clientSession.get() == null) {
            this.clientSession.set(this.getLastSession());
        }
        if (this.getEntrance().getStructureBuilder().getContext(player).isDisabled()) {
            return;
        }
        if (!this.isPlayerNotified.compareAndSet(false, true)) {
            return;
        }
        this.notifyPlayer();
    }
}
