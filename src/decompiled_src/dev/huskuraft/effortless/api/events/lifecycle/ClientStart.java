/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.lifecycle;

import dev.huskuraft.effortless.api.platform.Client;

@FunctionalInterface
public interface ClientStart {
    public void onClientStart(Client var1);
}
