/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.Effortless;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.networking.NetworkChannel;
import dev.huskuraft.effortless.api.networking.Packet;
import dev.huskuraft.effortless.api.networking.Side;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.networking.packets.AllPacketListener;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildTooltipPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerCommandPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerPermissionCheckPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSettingsPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotCapturePacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotSharePacket;
import dev.huskuraft.effortless.networking.packets.session.SessionConfigPacket;
import dev.huskuraft.effortless.networking.packets.session.SessionPacket;
import java.util.logging.Logger;

public final class EffortlessNetworkChannel
extends NetworkChannel<AllPacketListener> {
    private static final int COMPATIBILITY_VERSION = 13;
    private final Effortless entrance;
    private final AllPacketListener listener;

    public EffortlessNetworkChannel(Effortless entrance) {
        this(entrance, "default");
    }

    public EffortlessNetworkChannel(Effortless entrance, String name) {
        super(entrance, name, Side.SERVER);
        this.entrance = entrance;
        this.listener = new ServerPacketListener();
        this.registerPacket(SessionPacket.class, new SessionPacket.Serializer());
        this.registerPacket(SessionConfigPacket.class, new SessionConfigPacket.Serializer());
        this.registerPacket(PlayerCommandPacket.class, new PlayerCommandPacket.Serializer());
        this.registerPacket(PlayerSettingsPacket.class, new PlayerSettingsPacket.Serializer());
        this.registerPacket(PlayerBuildPacket.class, new PlayerBuildPacket.Serializer());
        this.registerPacket(PlayerPermissionCheckPacket.class, new PlayerPermissionCheckPacket.Serializer());
        this.registerPacket(PlayerBuildTooltipPacket.class, new PlayerBuildTooltipPacket.Serializer());
        this.registerPacket(PlayerSnapshotCapturePacket.class, new PlayerSnapshotCapturePacket.Serializer());
        this.registerPacket(PlayerSnapshotSharePacket.class, new PlayerSnapshotSharePacket.Serializer());
        this.getEntrance().getEventRegistry().getRegisterNetworkEvent().register(this::onRegisterNetwork);
    }

    public Effortless getEntrance() {
        return this.entrance;
    }

    @Override
    public void receivePacket(Packet packet, Player player) {
        try {
            packet.handle(this.listener, player);
        }
        catch (Exception exception) {
            if (this.listener.shouldPropagateHandlingExceptions()) {
                throw exception;
            }
            Logger.getAnonymousLogger().severe("Failed to handle packet " + String.valueOf(packet) + ", suppressing error" + String.valueOf(exception));
        }
    }

    @Override
    public int getCompatibilityVersion() {
        return 13;
    }

    private class ServerPacketListener
    implements AllPacketListener {
        private ServerPacketListener() {
        }

        @Override
        public void handle(PlayerCommandPacket packet, Player player) {
            EffortlessNetworkChannel.this.getEntrance().getServer().execute(() -> {
                switch (packet.action()) {
                    case REDO: {
                        EffortlessNetworkChannel.this.getEntrance().getStructureBuilder().redo(player);
                        break;
                    }
                    case UNDO: {
                        EffortlessNetworkChannel.this.getEntrance().getStructureBuilder().undo(player);
                    }
                }
            });
        }

        @Override
        public void handle(PlayerSettingsPacket packet, Player player) {
        }

        @Override
        public void handle(PlayerBuildPacket packet, Player player) {
            EffortlessNetworkChannel.this.getEntrance().getServer().execute(() -> EffortlessNetworkChannel.this.getEntrance().getStructureBuilder().onContextReceived(player, packet.context()));
        }

        @Override
        public void handle(PlayerPermissionCheckPacket packet, Player player) {
            EffortlessNetworkChannel.this.getEntrance().getServer().execute(() -> {
                boolean isSinglePlayerOwner = EffortlessNetworkChannel.this.getEntrance().getServerManager().getRunningServer().isSinglePlayerOwner(player.getProfile());
                boolean isOperator = EffortlessNetworkChannel.this.getEntrance().getServerManager().getRunningServer().isOperator(player.getProfile());
                EffortlessNetworkChannel.this.getEntrance().getChannel().sendPacket(new PlayerPermissionCheckPacket(packet.responseId(), packet.playerId(), isSinglePlayerOwner || isOperator), player);
            });
        }

        @Override
        public void handle(PlayerBuildTooltipPacket packet, Player player) {
        }

        @Override
        public void handle(SessionPacket packet, Player player) {
            EffortlessNetworkChannel.this.getEntrance().getServer().execute(() -> EffortlessNetworkChannel.this.getEntrance().getSessionManager().onSession(packet.session(), player));
        }

        @Override
        public void handle(SessionConfigPacket packet, Player player) {
            EffortlessNetworkChannel.this.getEntrance().getServer().execute(() -> EffortlessNetworkChannel.this.getEntrance().getSessionManager().onSessionConfig(packet.sessionConfig(), player));
        }

        @Override
        public void handle(PlayerSnapshotCapturePacket packet, Player player) {
        }

        @Override
        public void handle(PlayerSnapshotSharePacket packet, Player player) {
            EffortlessNetworkChannel.this.getEntrance().getServer().execute(() -> {
                Player fromPlayer = EffortlessNetworkChannel.this.getEntrance().getServer().getPlayerList().getPlayer(packet.from());
                Player toPlayer = EffortlessNetworkChannel.this.getEntrance().getServer().getPlayerList().getPlayer(packet.to());
                if (fromPlayer == null) {
                    return;
                }
                if (toPlayer == null) {
                    fromPlayer.sendMessage(Effortless.getSystemMessage(Text.text("Cannot share this snapshot. Player is offline.")));
                } else {
                    fromPlayer.sendMessage(Effortless.getSystemMessage(Text.text("Snapshot shared to player %s.".formatted(toPlayer.getProfile().getName()))));
                    toPlayer.sendMessage(Effortless.getSystemMessage(Text.text("Player %s shared you a snapshot. Go to clipboard to import it.".formatted(fromPlayer.getProfile().getName()))));
                    EffortlessNetworkChannel.this.getEntrance().getChannel().sendPacket(packet, toPlayer);
                }
            });
        }
    }
}
