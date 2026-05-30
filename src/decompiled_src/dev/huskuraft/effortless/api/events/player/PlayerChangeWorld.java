/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.player;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;

@FunctionalInterface
public interface PlayerChangeWorld {
    public void onPlayerChangeWorld(Player var1, World var2, World var3);
}
