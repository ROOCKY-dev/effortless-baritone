/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.Packet;
import dev.huskuraft.effortless.api.networking.PacketListener;
import java.util.UUID;

public interface ResponsiblePacket<T extends PacketListener>
extends Packet<T> {
    public UUID responseId();
}
