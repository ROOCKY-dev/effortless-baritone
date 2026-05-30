/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.core.GameMode;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.networking.serializer.ContextSerializer;

public static class ContextSerializer.ExtrasSerializer
implements NetByteBufSerializer<Context.Extras> {
    @Override
    public Context.Extras read(NetByteBuf byteBuf) {
        return new Context.Extras(byteBuf.readResourceLocation(), byteBuf.read(new ContextSerializer.EntityStateSerializer()), byteBuf.readEnum(GameMode.class), byteBuf.readLong(), byteBuf.read(new ContextSerializer.InventorySnapshotSerializer()));
    }

    @Override
    public void write(NetByteBuf byteBuf, Context.Extras extras) {
        byteBuf.writeResourceLocation(extras.dimensionId());
        byteBuf.write(extras.extras(), new ContextSerializer.EntityStateSerializer());
        byteBuf.writeEnum(extras.gameMode());
        byteBuf.writeLong(extras.seed());
        byteBuf.write(extras.inventorySnapshot(), new ContextSerializer.InventorySnapshotSerializer());
    }
}
