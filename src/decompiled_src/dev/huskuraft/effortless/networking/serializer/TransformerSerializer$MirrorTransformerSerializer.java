/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.pattern.mirror.MirrorTransformer;

static class TransformerSerializer.MirrorTransformerSerializer
implements NetByteBufSerializer<MirrorTransformer> {
    TransformerSerializer.MirrorTransformerSerializer() {
    }

    @Override
    public MirrorTransformer read(NetByteBuf byteBuf) {
        return new MirrorTransformer(byteBuf.readUUID(), byteBuf.readText(), byteBuf.readVector3d(), byteBuf.readEnum(Axis.class), byteBuf.readVarInt());
    }

    @Override
    public void write(NetByteBuf byteBuf, MirrorTransformer transformer) {
        byteBuf.writeUUID(transformer.getId());
        byteBuf.writeText(transformer.getName());
        byteBuf.writeVector3d(transformer.position());
        byteBuf.writeEnum(transformer.axis());
        byteBuf.writeVarInt(transformer.size());
    }
}
