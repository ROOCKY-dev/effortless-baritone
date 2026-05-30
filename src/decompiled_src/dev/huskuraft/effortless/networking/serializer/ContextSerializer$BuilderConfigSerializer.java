/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.config.BuilderConfig;

public static class ContextSerializer.BuilderConfigSerializer
implements NetByteBufSerializer<BuilderConfig> {
    @Override
    public BuilderConfig read(NetByteBuf byteBuf) {
        return new BuilderConfig(byteBuf.readVarInt(), byteBuf.readBoolean());
    }

    @Override
    public void write(NetByteBuf byteBuf, BuilderConfig builderConfig) {
        byteBuf.writeVarInt(builderConfig.reservedToolDurability());
        byteBuf.writeBoolean(builderConfig.passiveMode());
    }
}
