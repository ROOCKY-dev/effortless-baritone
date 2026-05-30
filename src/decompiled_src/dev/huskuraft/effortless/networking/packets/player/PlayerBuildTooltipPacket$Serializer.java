/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.packets.player;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.operation.ItemSummary;
import dev.huskuraft.effortless.building.operation.OperationTooltip;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildTooltipPacket;
import dev.huskuraft.effortless.networking.serializer.ContextSerializer;

public static class PlayerBuildTooltipPacket.Serializer
implements NetByteBufSerializer<PlayerBuildTooltipPacket> {
    @Override
    public PlayerBuildTooltipPacket read(NetByteBuf byteBuf) {
        return new PlayerBuildTooltipPacket(new OperationTooltip(byteBuf.readEnum(OperationTooltip.Type.class), byteBuf.read(new ContextSerializer()), byteBuf.readMap(buffer1 -> buffer1.readEnum(ItemSummary.class), buffer1 -> buffer1.readList(NetByteBuf::readItemStack))));
    }

    @Override
    public void write(NetByteBuf byteBuf, PlayerBuildTooltipPacket packet) {
        byteBuf.writeEnum(packet.operationTooltip().type());
        byteBuf.write(packet.operationTooltip().context(), new ContextSerializer());
        byteBuf.writeMap(packet.operationTooltip().itemSummary(), NetByteBuf::writeEnum, (buffer1, blockStateMap) -> buffer1.writeList(blockStateMap, NetByteBuf::writeItemStack));
    }
}
