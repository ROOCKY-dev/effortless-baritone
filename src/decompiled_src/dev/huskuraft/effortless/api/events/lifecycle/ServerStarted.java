/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.lifecycle;

import dev.huskuraft.effortless.api.platform.Server;

@FunctionalInterface
public interface ServerStarted {
    public void onServerStarted(Server var1);
}
