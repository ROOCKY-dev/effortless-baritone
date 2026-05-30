/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.networking.PacketListener;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildTooltipPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerCommandPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerPermissionCheckPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSettingsPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotCapturePacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotSharePacket;
import dev.huskuraft.effortless.networking.packets.session.SessionConfigPacket;
import dev.huskuraft.effortless.networking.packets.session.SessionPacket;

public interface AllPacketListener
extends PacketListener {
    public void handle(PlayerCommandPacket var1, Player var2);

    public void handle(PlayerSettingsPacket var1, Player var2);

    public void handle(PlayerBuildPacket var1, Player var2);

    public void handle(PlayerPermissionCheckPacket var1, Player var2);

    public void handle(PlayerBuildTooltipPacket var1, Player var2);

    public void handle(SessionPacket var1, Player var2);

    public void handle(SessionConfigPacket var1, Player var2);

    public void handle(PlayerSnapshotCapturePacket var1, Player var2);

    public void handle(PlayerSnapshotSharePacket var1, Player var2);
}
