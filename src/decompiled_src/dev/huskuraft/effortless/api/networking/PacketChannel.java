/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.networking.PacketReceiver;
import dev.huskuraft.effortless.api.networking.PacketSender;

public interface PacketChannel
extends PacketSender,
PacketReceiver {
    public int getCompatibilityVersion();

    public ResourceLocation getChannelId();
}
