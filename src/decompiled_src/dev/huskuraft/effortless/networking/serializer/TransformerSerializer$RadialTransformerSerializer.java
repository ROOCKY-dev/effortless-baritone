/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.pattern.raidal.RadialTransformer;

static class TransformerSerializer.RadialTransformerSerializer
implements NetByteBufSerializer<RadialTransformer> {
    TransformerSerializer.RadialTransformerSerializer() {
    }

    @Override
    public RadialTransformer read(NetByteBuf byteBuf) {
        return new RadialTransformer(byteBuf.readUUID(), byteBuf.readText(), byteBuf.readVector3d(), byteBuf.readEnum(Axis.class), byteBuf.readVarInt(), byteBuf.readVarInt(), byteBuf.readVarInt());
    }

    @Override
    public void write(NetByteBuf byteBuf, RadialTransformer transformer) {
        byteBuf.writeUUID(transformer.getId());
        byteBuf.writeText(transformer.getName());
        byteBuf.writeVector3d(transformer.position());
        byteBuf.writeEnum(transformer.axis());
        byteBuf.writeVarInt(transformer.slices());
        byteBuf.writeVarInt(transformer.radius());
        byteBuf.writeVarInt(transformer.length());
    }
}
