/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.pattern.Transformer;
import dev.huskuraft.effortless.building.pattern.Transformers;
import dev.huskuraft.effortless.building.pattern.array.ArrayTransformer;
import dev.huskuraft.effortless.building.pattern.mirror.MirrorTransformer;
import dev.huskuraft.effortless.building.pattern.raidal.RadialTransformer;
import dev.huskuraft.effortless.building.pattern.randomize.Chance;
import dev.huskuraft.effortless.building.pattern.randomize.ItemRandomizer;
import dev.huskuraft.effortless.building.pattern.randomize.Randomizer;

public class TransformerSerializer
implements NetByteBufSerializer<Transformer> {
    @Override
    public Transformer read(NetByteBuf byteBuf) {
        return switch (byteBuf.readEnum(Transformers.class)) {
            default -> throw new MatchException(null, null);
            case Transformers.ARRAY -> byteBuf.read(new ArrayTransformerSerializer());
            case Transformers.MIRROR -> byteBuf.read(new MirrorTransformerSerializer());
            case Transformers.RADIAL -> byteBuf.read(new RadialTransformerSerializer());
            case Transformers.RANDOMIZER -> byteBuf.read(new ItemRandomizerSerializer());
        };
    }

    @Override
    public void write(NetByteBuf byteBuf, Transformer transformer) {
        byteBuf.writeEnum(transformer.getType());
        switch (transformer.getType()) {
            case ARRAY: {
                byteBuf.write((ArrayTransformer)transformer, new ArrayTransformerSerializer());
                break;
            }
            case MIRROR: {
                byteBuf.write((MirrorTransformer)transformer, new MirrorTransformerSerializer());
                break;
            }
            case RADIAL: {
                byteBuf.write((RadialTransformer)transformer, new RadialTransformerSerializer());
                break;
            }
            case RANDOMIZER: {
                byteBuf.write((ItemRandomizer)transformer, new ItemRandomizerSerializer());
            }
        }
    }

    static class ArrayTransformerSerializer
    implements NetByteBufSerializer<ArrayTransformer> {
        ArrayTransformerSerializer() {
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

    static class MirrorTransformerSerializer
    implements NetByteBufSerializer<MirrorTransformer> {
        MirrorTransformerSerializer() {
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

    static class RadialTransformerSerializer
    implements NetByteBufSerializer<RadialTransformer> {
        RadialTransformerSerializer() {
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

    static class ItemRandomizerSerializer
    implements NetByteBufSerializer<ItemRandomizer> {
        ItemRandomizerSerializer() {
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
}
