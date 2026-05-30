/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.lifecycle;

import dev.huskuraft.effortless.api.platform.Client;

@FunctionalInterface
public interface ClientTick {
    public void onClientTick(Client var1, Phase var2);

    public static enum Phase {
        START,
        END;

    }
}
