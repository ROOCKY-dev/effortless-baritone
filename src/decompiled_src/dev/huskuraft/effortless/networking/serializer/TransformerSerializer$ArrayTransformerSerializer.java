/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.pattern.array.ArrayTransformer;

static class TransformerSerializer.ArrayTransformerSerializer
implements NetByteBufSerializer<ArrayTransformer> {
    TransformerSerializer.ArrayTransformerSerializer() {
    }

    @Override
    public ArrayTransformer read(NetByteBuf byteBuf) {
        return new ArrayTransformer(byteBuf.readUUID(), byteBuf.readText(), byteBuf.readVector3i(), byteBuf.readVarInt());
    }

    @Override
    public void write(NetByteBuf byteBuf, ArrayTransformer transformer) {
        byteBuf.writeUUID(transformer.getId());
        byteBuf.writeText(transformer.getName());
        byteBuf.writeVector3i(transformer.offset());
        byteBuf.writeVarInt(transformer.count());
    }
}
