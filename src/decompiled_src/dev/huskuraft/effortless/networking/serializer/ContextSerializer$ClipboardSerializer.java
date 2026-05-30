/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.clipboard.Clipboard;
import dev.huskuraft.effortless.networking.serializer.SnapshotSerializer;

public static class ContextSerializer.ClipboardSerializer
implements NetByteBufSerializer<Clipboard> {
    @Override
    public Clipboard read(NetByteBuf byteBuf) {
        return new Clipboard(byteBuf.readBoolean(), byteBuf.read(new SnapshotSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, Clipboard clipboard) {
        byteBuf.writeBoolean(clipboard.enabled());
        byteBuf.write(clipboard.snapshot(), new SnapshotSerializer());
    }
}
