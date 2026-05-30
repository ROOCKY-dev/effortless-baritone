/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.PlayerProfile;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.List;
import java.util.UUID;

public interface PlayerList
extends PlatformReference {
    public List<Player> getPlayers();

    public Player getPlayer(UUID var1);

    public boolean isOperator(PlayerProfile var1);
}
