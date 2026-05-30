/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.player;

import dev.huskuraft.effortless.api.core.Player;

@FunctionalInterface
public interface PlayerRespawn {
    public void onPlayerRespawn(Player var1, Player var2, boolean var3);
}
