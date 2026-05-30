/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.player;

import dev.huskuraft.effortless.api.core.Player;

@FunctionalInterface
public interface PlayerLoggedIn {
    public void onPlayerLoggedIn(Player var1);
}
