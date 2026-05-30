/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.pattern.randomize.Chance;
import dev.huskuraft.effortless.building.pattern.randomize.ItemRandomizer;
import dev.huskuraft.effortless.building.pattern.randomize.Randomizer;

static class TransformerSerializer.ItemRandomizerSerializer
implements NetByteBufSerializer<ItemRandomizer> {
    TransformerSerializer.ItemRandomizerSerializer() {
    }

    @Override
    public ItemRandomizer read(NetByteBuf byteBuf) {
        return new ItemRandomizer(byteBuf.readUUID(), byteBuf.readText(), byteBuf.readEnum(Randomizer.Order.class), byteBuf.readEnum(Randomizer.Target.class), byteBuf.readEnum(ItemRandomizer.Source.class), byteBuf.readList(buffer1 -> Chance.of(buffer1.readItem(), buffer1.readVarInt())));
    }

    @Override
    public void write(NetByteBuf byteBuf, ItemRandomizer transformer) {
        byteBuf.writeUUID(transformer.getId());
        byteBuf.writeText(transformer.getName());
        byteBuf.writeEnum(transformer.getOrder());
        byteBuf.writeEnum(transformer.getTarget());
        byteBuf.writeEnum(transformer.getSource());
        byteBuf.writeList(transformer.getChances(), (buf, chance) -> {
            buf.writeItem((Item)chance.content());
            buf.writeVarInt(chance.chance());
        });
    }
}
