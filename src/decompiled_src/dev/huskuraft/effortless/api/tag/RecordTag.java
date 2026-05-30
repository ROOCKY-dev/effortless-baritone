/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.tag;

import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.math.Vector2d;
import dev.huskuraft.effortless.api.math.Vector2i;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3i;
import dev.huskuraft.effortless.api.platform.TagFactory;
import dev.huskuraft.effortless.api.tag.ListTag;
import dev.huskuraft.effortless.api.tag.NumericTag;
import dev.huskuraft.effortless.api.tag.StringTag;
import dev.huskuraft.effortless.api.tag.Tag;
import dev.huskuraft.effortless.api.tag.TagDecoder;
import dev.huskuraft.effortless.api.tag.TagEncoder;
import dev.huskuraft.effortless.api.text.Text;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface RecordTag
extends Tag {
    public static RecordTag newRecord() {
        return TagFactory.getInstance().newRecord();
    }

    public static RecordTag of(Map<String, Tag> tags) {
        RecordTag tag = TagFactory.getInstance().newRecord();
        tags.forEach(tag::putTag);
        return tag;
    }

    public Set<String> keySet();

    public Tag getTag(String var1);

    public Tag putTag(String var1, Tag var2);

    public void remove(String var1);

    default public Map<String, Tag> getTags() {
        return this.keySet().stream().collect(Collectors.toMap(Function.identity(), this::getTag));
    }

    default public String getString(String key) {
        return this.getTag(key).asString().getAsString();
    }

    default public void putString(String key, String value) {
        this.putTag(key, StringTag.of(value));
    }

    default public boolean getBoolean(String key) {
        return this.getTag(key).asPrimitive().getAsByte() != 0;
    }

    default public boolean getBooleanOrElse(String key, boolean defaultValue) {
        try {
            return this.getBoolean(key);
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    default public void putBoolean(String key, boolean value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public byte getByte(String key) {
        return this.getTag(key).asPrimitive().getAsByte();
    }

    default public void putByte(String key, byte value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public short getShort(String key) {
        return this.getTag(key).asPrimitive().getAsShort();
    }

    default public void putShort(String key, short value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public int getInt(String key) {
        return this.getTag(key).asPrimitive().getAsInt();
    }

    default public int getIntOrElse(String key, int defaultValue) {
        try {
            return this.getInt(key);
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    default public void putInt(String key, int value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public long getLong(String key) {
        return this.getTag(key).asPrimitive().getAsLong();
    }

    default public void putLong(String key, long value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public float getFloat(String key) {
        return this.getTag(key).asPrimitive().getAsFloat();
    }

    default public void putFloat(String key, float value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public double getDouble(String key) {
        return this.getTag(key).asPrimitive().getAsDouble();
    }

    default public void putDouble(String key, double value) {
        this.putTag(key, NumericTag.of(value));
    }

    default public boolean[] getBooleanArray(String key) {
        throw new NotImplementedException("getBooleanArray is not implemented yet");
    }

    default public void putBooleanArray(String key, boolean[] value) {
        throw new NotImplementedException("putBooleanArray is not implemented yet");
    }

    default public byte[] getByteArray(String key) {
        throw new NotImplementedException("getByteArray is not implemented yet");
    }

    default public void putByteArray(String key, byte[] value) {
        throw new NotImplementedException("putByteArray is not implemented yet");
    }

    default public short[] getShortArray(String key) {
        throw new NotImplementedException("getShortArray is not implemented yet");
    }

    default public void putShortArray(String key, short[] value) {
        throw new NotImplementedException("putShortArray is not implemented yet");
    }

    default public int[] getIntArray(String key) {
        throw new NotImplementedException("getIntArray is not implemented yet");
    }

    default public void putIntArray(String key, int[] value) {
        throw new NotImplementedException("putIntArray is not implemented yet");
    }

    default public long[] getLongArray(String key) {
        throw new NotImplementedException("getLongArray is not implemented yet");
    }

    default public void putLongArray(String key, long[] value) {
        throw new NotImplementedException("putLongArray is not implemented yet");
    }

    default public float[] getFloatArray(String key) {
        throw new NotImplementedException("getFloatArray is not implemented yet");
    }

    default public void putFloatArray(String key, float[] value) {
        throw new NotImplementedException("putFloatArray is not implemented yet");
    }

    default public double[] getDoubleArray(String key) {
        throw new NotImplementedException("getDoubleArray is not implemented yet");
    }

    default public void putDoubleArray(String key, double[] value) {
        throw new NotImplementedException("putDoubleArray is not implemented yet");
    }

    default public <T> T getTag(String key, TagDecoder<T> decoder) {
        return decoder.decode(this.getTag(key), true);
    }

    default public <T> void putTag(String key, T value, TagEncoder<T> encoder) {
        this.putTag(key, encoder.encode(value));
    }

    default public <T> List<T> getList(String key, TagDecoder<T> reader) {
        return this.getTag(key).asList().stream().map(reader::decode).toList();
    }

    default public <T> void putList(String key, Collection<T> collection, TagEncoder<T> writer) {
        this.putTag(key, ListTag.of(collection.stream().map(writer::encode).toList()));
    }

    default public <T extends Enum<T>> T getEnum(String key, Class<T> clazz) {
        try {
            ResourceLocation id = ResourceLocation.decompose(this.getString(key));
            return Enum.valueOf(clazz, id.getPath().toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    default public <T extends Enum<T>> void putEnum(String key, Enum<T> value) {
        ResourceLocation id = ResourceLocation.of("effortless", value.name().toLowerCase(Locale.ROOT));
        this.putString(key, id.toString());
    }

    default public ResourceLocation getResource(String key) {
        try {
            return ResourceLocation.decompose(this.getString(key));
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    default public void putResource(String key, ResourceLocation value) {
        this.putString(key, value.toString());
    }

    default public UUID getUUID(String key) {
        try {
            return UUID.fromString(this.getString(key));
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    default public void putUUID(String key, UUID value) {
        this.putString(key, value.toString());
    }

    default public Item getItem(String key) {
        try {
            return Item.fromId(Objects.requireNonNull(this.getResource(key)));
        }
        catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    default public void putItem(String key, Item value) {
        this.putResource(key, value.getId());
    }

    default public Vector3d getVector3d(String key) {
        try {
            List<Double> positions = this.getList(key, tag1 -> tag1.asPrimitive().getAsDouble());
            return new Vector3d(positions.get(0), positions.get(1), positions.get(2));
        }
        catch (Exception e) {
            return null;
        }
    }

    default public void putVector3d(String key, Vector3d value) {
        this.putList(key, List.of(Double.valueOf(value.x()), Double.valueOf(value.y()), Double.valueOf(value.z())), NumericTag::of);
    }

    default public Vector3i getVector3i(String key) {
        try {
            List<Integer> positions = this.getList(key, tag1 -> tag1.asPrimitive().getAsInt());
            return new Vector3i(positions.get(0), positions.get(1), positions.get(2));
        }
        catch (Exception e) {
            return null;
        }
    }

    default public void putVector3i(String key, Vector3i value) {
        this.putList(key, List.of(Integer.valueOf(value.x()), Integer.valueOf(value.y()), Integer.valueOf(value.z())), NumericTag::of);
    }

    default public Vector2d getVector2d(String key) {
        try {
            List<Double> positions = this.getList(key, tag1 -> tag1.asPrimitive().getAsDouble());
            return new Vector2d(positions.get(0), positions.get(1));
        }
        catch (Exception e) {
            return null;
        }
    }

    default public void putVector2d(String key, Vector2d value) {
        this.putList(key, List.of(Double.valueOf(value.x()), Double.valueOf(value.y())), NumericTag::of);
    }

    default public Vector2i getVector2i(String key) {
        try {
            List<Integer> positions = this.getList(key, tag1 -> tag1.asPrimitive().getAsInt());
            return new Vector2i(positions.get(0), positions.get(1));
        }
        catch (Exception e) {
            return null;
        }
    }

    default public void putVector2i(String key, Vector2i value) {
        this.putList(key, List.of(Integer.valueOf(value.x()), Integer.valueOf(value.y())), NumericTag::of);
    }

    default public Text getText(String key) {
        return Text.text(this.getTag(key).asString().getAsString());
    }

    default public void putText(String key, Text value) {
        this.putString(key, value.getString());
    }

    public static class NotImplementedException
    extends RuntimeException {
        NotImplementedException(String message) {
            super(message);
        }
    }
}
