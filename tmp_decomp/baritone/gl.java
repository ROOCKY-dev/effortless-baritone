/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package baritone;

import baritone.ge;
import baritone.gm;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public final class gl
extends ge {
    public gl(CompoundTag object) {
        int n2;
        Object object2;
        int n3;
        Object object32;
        this.x = object.getInt("Width");
        this.y = object.getInt("Height");
        this.z = object.getInt("Length");
        this.a = new BlockState[this.x][this.z][this.y];
        Int2ObjectArrayMap int2ObjectArrayMap = new Int2ObjectArrayMap();
        CompoundTag compoundTag = object.getCompound("Palette");
        for (Object object32 : compoundTag.getAllKeys()) {
            n3 = compoundTag.getInt((String)object32);
            a a2 = baritone.gl$a.a((String)object32);
            if (a2 == null) {
                throw new IllegalArgumentException("Unable to parse palette tag");
            }
            object2 = a2.a();
            if (object2 == null) {
                throw new IllegalArgumentException("Unable to deserialize palette tag");
            }
            int2ObjectArrayMap.put(n3, object2);
        }
        Object object4 = object.getByteArray("BlockData");
        object32 = new int[this.x * this.y * this.z];
        n3 = 0;
        for (n2 = 0; n2 < ((Object)object32).length; ++n2) {
            CompoundTag compoundTag2;
            if (n3 >= ((Object)object4).length) {
                throw new IllegalArgumentException("No remaining bytes in BlockData for complete schematic");
            }
            int n4 = n3;
            object = object4;
            int n5 = 0;
            int n6 = 0;
            do {
                compoundTag2 = object[n4++];
                n5 |= (compoundTag2 & 0x7F) << n6++ * 7;
                if (n6 <= 5) continue;
                throw new IllegalArgumentException("VarInt size cannot exceed 5 bytes");
            } while ((compoundTag2 & 0x80) != 0);
            object2 = new gm(n5);
            object32[n2] = object2.a;
            n3 += object2.b;
        }
        for (n2 = 0; n2 < this.y; ++n2) {
            for (int i2 = 0; i2 < this.z; ++i2) {
                for (int i3 = 0; i3 < this.x; ++i3) {
                    int n7 = (n2 * this.z + i2) * this.x + i3;
                    object4 = (BlockState)int2ObjectArrayMap.get((int)object32[n7]);
                    if (object4 == null) {
                        throw new IllegalArgumentException("Invalid Palette Index " + n7);
                    }
                    this.a[i3][i2][n2] = object4;
                }
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class a {
        private static final Pattern a = Pattern.compile("(?<location>(\\w+:)?\\w+)(\\[(?<properties>(\\w+=\\w+,?)+)])?");
        private final ResourceLocation a;
        private final Map<String, String> a;
        private BlockState a;

        private a(ResourceLocation resourceLocation, Map<String, String> map) {
            this.a = resourceLocation;
            this.a = map;
        }

        final BlockState a() {
            if (this.a == null) {
                Block block = (Block)BuiltInRegistries.BLOCK.get(this.a);
                this.a = block.defaultBlockState();
                this.a.keySet().stream().sorted(String::compareTo).forEachOrdered(string -> {
                    if ((block = block.getStateDefinition().getProperty(string)) != null) {
                        Object object = (String)this.a.get(string);
                        string = block;
                        block = this.a;
                        if (!((Optional)(object = string.getValue((String)object))).isPresent()) {
                            throw new IllegalArgumentException("Invalid value for property " + String.valueOf(string));
                        }
                        this.a = (BlockState)block.setValue((Property)string, (Comparable)((Optional)object).get());
                    }
                });
            }
            return this.a;
        }

        static a a(String stringArray) {
            if (!(stringArray = a.matcher((CharSequence)stringArray)).matches()) {
                return null;
            }
            try {
                String string = stringArray.group("location");
                stringArray = stringArray.group("properties");
                string = ResourceLocation.parse((String)string);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                if (stringArray != null) {
                    stringArray = stringArray.split(",");
                    int n2 = stringArray.length;
                    for (int i2 = 0; i2 < n2; ++i2) {
                        String[] stringArray2 = stringArray[i2].split("=");
                        hashMap.put(stringArray2[0], stringArray2[1]);
                    }
                }
                return new a((ResourceLocation)string, hashMap);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }
}

