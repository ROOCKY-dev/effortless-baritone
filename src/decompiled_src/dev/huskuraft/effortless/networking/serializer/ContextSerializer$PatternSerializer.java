/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.pattern.Pattern;
import dev.huskuraft.effortless.networking.serializer.TransformerSerializer;

public static class ContextSerializer.PatternSerializer
implements NetByteBufSerializer<Pattern> {
    @Override
    public Pattern read(NetByteBuf byteBuf) {
        return new Pattern(byteBuf.readBoolean(), byteBuf.readList(new TransformerSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, Pattern pattern) {
        byteBuf.writeBoolean(pattern.enabled());
        byteBuf.writeList(pattern.transformers(), new TransformerSerializer());
    }
}
