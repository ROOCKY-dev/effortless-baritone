/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.InventorySnapshot;

public static class ContextSerializer.InventorySnapshotSerializer
implements NetByteBufSerializer<InventorySnapshot> {
    @Override
    public InventorySnapshot read(NetByteBuf byteBuf) {
        return new InventorySnapshot(byteBuf.readList(NetByteBuf::readItemStack), byteBuf.readVarInt(), byteBuf.readVarInt(), byteBuf.readVarInt(), byteBuf.readVarInt(), byteBuf.readVarInt());
    }

    @Override
    public void write(NetByteBuf byteBuf, InventorySnapshot inventorySnapshot) {
        byteBuf.writeList(inventorySnapshot.items(), NetByteBuf::writeItemStack);
        byteBuf.writeVarInt(inventorySnapshot.selected());
        byteBuf.writeVarInt(inventorySnapshot.bagSize());
        byteBuf.writeVarInt(inventorySnapshot.armorSize());
        byteBuf.writeVarInt(inventorySnapshot.offhandSize());
        byteBuf.writeVarInt(inventorySnapshot.hotbarSize());
    }
}
