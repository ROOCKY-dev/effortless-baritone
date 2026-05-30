/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.lifecycle;

import dev.huskuraft.effortless.api.platform.Server;

@FunctionalInterface
public interface ServerStopping {
    public void onServerStopping(Server var1);
}
