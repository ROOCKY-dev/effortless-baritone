/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.datafix.fixes.ItemIdFix
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.ge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.ItemIdFix;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class gk
extends ge {
    public gk(CompoundTag object) {
        int n2;
        Object object2 = object.getString("Materials");
        if (!((String)object2).equals("Alpha")) {
            throw new IllegalStateException("bad schematic " + (String)object2);
        }
        this.x = object.getInt("Width");
        this.y = object.getInt("Height");
        this.z = object.getInt("Length");
        object2 = object.getByteArray("Blocks");
        byte[] byArray = null;
        if (object.contains("AddBlocks")) {
            byte[] byArray2 = object.getByteArray("AddBlocks");
            object = byArray2;
            byArray = new byte[byArray2.length << 1];
            for (n2 = 0; n2 < ((CompoundTag)object).length; ++n2) {
                byArray[n2 << 1] = (byte)(object[n2] >> 4 & 0xF);
                byArray[(n2 << 1) + 1] = (byte)(object[n2] & 0xF);
            }
        }
        this.a = new BlockState[this.x][this.z][this.y];
        for (int i2 = 0; i2 < this.y; ++i2) {
            for (n2 = 0; n2 < this.z; ++n2) {
                for (int i3 = 0; i3 < this.x; ++i3) {
                    int n3 = (i2 * this.z + n2) * this.x + i3;
                    int n4 = object2[n3] & 0xFF;
                    if (byArray != null) {
                        n4 |= byArray[n3] << 8;
                    }
                    Block block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse((String)ItemIdFix.getItem((int)n4)));
                    this.a[i3][n2][i2] = block.defaultBlockState();
                }
            }
        }
    }
}

