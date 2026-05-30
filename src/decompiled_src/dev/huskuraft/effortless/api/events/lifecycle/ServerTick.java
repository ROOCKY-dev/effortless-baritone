/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.lifecycle;

import dev.huskuraft.effortless.api.platform.Server;

@FunctionalInterface
public interface ServerTick {
    public void onServerTick(Server var1, Phase var2);

    public static enum Phase {
        START,
        END;

    }
}
