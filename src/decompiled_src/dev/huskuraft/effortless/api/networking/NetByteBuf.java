/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Registry;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.math.Vector2d;
import dev.huskuraft.effortless.api.math.Vector2i;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3i;
import dev.huskuraft.effortless.api.networking.NetByteBufReader;
import dev.huskuraft.effortless.api.networking.NetByteBufWriter;
import dev.huskuraft.effortless.api.networking.Utf8String;
import dev.huskuraft.effortless.api.networking.VarInt;
import dev.huskuraft.effortless.api.networking.VarLong;
import dev.huskuraft.effortless.api.networking.WrappedByteBuf;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.tag.ListTag;
import dev.huskuraft.effortless.api.tag.NumericTag;
import dev.huskuraft.effortless.api.tag.RecordTag;
import dev.huskuraft.effortless.api.tag.StringTag;
import dev.huskuraft.effortless.api.tag.Tag;
import dev.huskuraft.effortless.api.text.Style;
import dev.huskuraft.effortless.api.text.Text;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class NetByteBuf
extends WrappedByteBuf {
    public NetByteBuf(ByteBuf source) {
        super(source);
    }

    public static NetByteBuf newBuffer() {
        return new NetByteBuf(Unpooled.buffer());
    }

    public <T> T readNullable(NetByteBufReader<T> reader) {
        if (this.readBoolean()) {
            return this.read(reader);
        }
        return null;
    }

    public UUID readUUID() {
        return new UUID(this.readLong(), this.readLong());
    }

    public <T extends Enum<T>> T readEnum(Class<T> clazz) {
        return (T)((Enum[])clazz.getEnumConstants())[this.readVarInt()];
    }

    public String readString() {
        return Utf8String.read(this, 1024);
    }

    public Text readText() {
        return Text.text(this.readString()).withStyle(new Style(this.readNullable(WrappedByteBuf::readInt), this.readNullable(WrappedByteBuf::readBoolean), this.readNullable(WrappedByteBuf::readBoolean), this.readNullable(WrappedByteBuf::readBoolean), this.readNullable(WrappedByteBuf::readBoolean), this.readNullable(WrappedByteBuf::readBoolean))).withSiblings(this.readList(NetByteBuf::readText));
    }

    public int readVarInt() {
        return VarInt.read(this);
    }

    public long readVarLong() {
        return VarLong.read(this);
    }

    public Item readItem() {
        return this.readId(Item.REGISTRY);
    }

    public <T extends PlatformReference> T readId(Registry<T> registry) {
        return registry.byId(this.readVarInt());
    }

    public ItemStack readItemStack() {
        return ItemStack.of(this.readItem(), this.readVarInt());
    }

    public <T> T read(NetByteBufReader<T> reader) {
        return reader.read(this);
    }

    public <T> List<T> readList(NetByteBufReader<T> reader) {
        int i = this.readVarInt();
        ArrayList<T> list = new ArrayList<T>();
        for (int j = 0; j < i; ++j) {
            list.add(this.read(reader));
        }
        return Collections.unmodifiableList(list);
    }

    public <K, V> Map<K, V> readMap(NetByteBufReader<K> keyReader, NetByteBufReader<V> valueReader) {
        int i = this.readVarInt();
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>();
        for (int j = 0; j < i; ++j) {
            map.put(this.read(keyReader), this.read(valueReader));
        }
        return Collections.unmodifiableMap(map);
    }

    public <T> void writeNullable(T value, NetByteBufWriter<T> writer) {
        this.writeBoolean(value != null);
        if (value != null) {
            this.write(value, writer);
        }
    }

    public void writeUUID(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public <T extends Enum<T>> void writeEnum(Enum<T> value) {
        this.writeVarInt(value.ordinal());
    }

    public void writeString(String value) {
        Utf8String.write(this, value, 1024);
    }

    public void writeText(Text value) {
        this.writeString(value.getString());
        this.writeNullable(value.getStyle().color(), WrappedByteBuf::writeInt);
        this.writeNullable(value.getStyle().bold(), WrappedByteBuf::writeBoolean);
        this.writeNullable(value.getStyle().italic(), WrappedByteBuf::writeBoolean);
        this.writeNullable(value.getStyle().underlined(), WrappedByteBuf::writeBoolean);
        this.writeNullable(value.getStyle().strikethrough(), WrappedByteBuf::writeBoolean);
        this.writeNullable(value.getStyle().obfuscated(), WrappedByteBuf::writeBoolean);
        this.writeList(value.getSiblings(), NetByteBuf::writeText);
    }

    public void writeVarInt(int value) {
        VarInt.write(this, value);
    }

    public void writeVarLong(long value) {
        VarLong.write(this, value);
    }

    public void writeItem(Item value) {
        this.writeId(Item.REGISTRY, value);
    }

    public <T extends PlatformReference> void writeId(Registry<T> registry, T value) {
        this.writeVarInt(registry.getId(value));
    }

    public void writeItemStack(ItemStack value) {
        this.writeItem(value.getItem());
        this.writeVarInt(value.getCount());
    }

    public <T> void write(T value, NetByteBufWriter<T> writer) {
        writer.write(this, value);
    }

    public <T> void writeList(Collection<T> collection, NetByteBufWriter<T> writer) {
        this.writeVarInt(collection.size());
        for (T object : collection) {
            this.write(object, writer);
        }
    }

    public <K, V> void writeMap(Map<K, V> map, NetByteBufWriter<K> keyWriter, NetByteBufWriter<V> valueWriter) {
        this.writeVarInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            keyWriter.write(this, entry.getKey());
            valueWriter.write(this, entry.getValue());
        }
    }

    public Vector3d readVector3d() {
        return new Vector3d(this.readDouble(), this.readDouble(), this.readDouble());
    }

    public void writeVector3d(Vector3d vector) {
        this.writeDouble(vector.x());
        this.writeDouble(vector.y());
        this.writeDouble(vector.z());
    }

    public Vector2d readVector2d() {
        return new Vector2d(this.readDouble(), this.readDouble());
    }

    public void writeVector2d(Vector2d vector) {
        this.writeDouble(vector.x());
        this.writeDouble(vector.y());
    }

    public Vector3i readVector3i() {
        return new Vector3i(this.readVarInt(), this.readVarInt(), this.readVarInt());
    }

    public void writeVector3i(Vector3i vector) {
        this.writeVarInt(vector.x());
        this.writeVarInt(vector.y());
        this.writeVarInt(vector.z());
    }

    public Vector2i readVector2i() {
        return new Vector2i(this.readVarInt(), this.readVarInt());
    }

    public void writeVector2i(Vector2i vector) {
        this.writeVarInt(vector.x());
        this.writeVarInt(vector.y());
    }

    public ResourceLocation readResourceLocation() {
        return ResourceLocation.of(this.readString(), this.readString());
    }

    public void writeResourceLocation(ResourceLocation resourceLocation) {
        this.writeString(resourceLocation.getNamespace());
        this.writeString(resourceLocation.getPath());
    }

    public BlockState readBlockState() {
        return this.readId(BlockState.REGISTRY);
    }

    public void writeBlockState(BlockState blockState) {
        this.writeId(BlockState.REGISTRY, blockState);
    }

    public RecordTag readRecordTag() {
        return (RecordTag)this.readTag();
    }

    public void writeRecordTag(RecordTag tag) {
        this.writeTag(tag);
    }

    public Tag readTag() {
        return switch (this.readByte()) {
            case 1 -> NumericTag.of(this.readByte());
            case 2 -> NumericTag.of(this.readShort());
            case 3 -> NumericTag.of(this.readInt());
            case 4 -> NumericTag.of(this.readLong());
            case 5 -> NumericTag.of(this.readFloat());
            case 6 -> NumericTag.of(this.readDouble());
            case 8 -> StringTag.of(this.readString());
            case 9 -> ListTag.of(this.readList(NetByteBuf::readTag));
            case 10 -> RecordTag.of(this.readMap(NetByteBuf::readString, NetByteBuf::readTag));
            default -> throw new IllegalArgumentException("Unsupported tag type!");
        };
    }

    public void writeTag(Tag tag) {
        this.writeByte(tag.getId());
        switch (tag.getId()) {
            case 1: {
                this.writeByte(tag.asPrimitive().getAsByte());
                break;
            }
            case 2: {
                this.writeShort(tag.asPrimitive().getAsShort());
                break;
            }
            case 3: {
                this.writeInt(tag.asPrimitive().getAsInt());
                break;
            }
            case 4: {
                this.writeLong(tag.asPrimitive().getAsLong());
                break;
            }
            case 5: {
                this.writeFloat(tag.asPrimitive().getAsFloat());
                break;
            }
            case 6: {
                this.writeDouble(tag.asPrimitive().getAsDouble());
                break;
            }
            case 8: {
                this.writeString(tag.asString().getString());
                break;
            }
            case 9: {
                this.writeList(tag.asList().stream().toList(), NetByteBuf::writeTag);
                break;
            }
            case 10: {
                this.writeMap(tag.asRecord().getTags(), NetByteBuf::writeString, NetByteBuf::writeTag);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported tag!");
            }
        }
    }
}
