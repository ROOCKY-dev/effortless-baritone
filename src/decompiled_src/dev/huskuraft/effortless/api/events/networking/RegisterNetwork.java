/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.networking;

import dev.huskuraft.effortless.api.networking.NetworkRegistry;

@FunctionalInterface
public interface RegisterNetwork {
    public void onRegisterNetwork(NetworkRegistry var1);
}
