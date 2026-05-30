/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.PlayerProfile;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.platform.PlayerList;

public interface Server
extends PlatformReference {
    public PlayerList getPlayerList();

    public void execute(Runnable var1);

    default public boolean isOperator(PlayerProfile profile) {
        return this.getPlayerList().isOperator(profile);
    }

    public boolean isSinglePlayerOwner(PlayerProfile var1);

    public boolean isDedicatedServer();
}
