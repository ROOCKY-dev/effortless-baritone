/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.apache.commons.lang3.Validate
 */
package baritone;

import baritone.api.schematic.CompositeSchematic;
import baritone.api.schematic.IStaticSchematic;
import baritone.ge;
import java.util.Collections;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.Validate;

public final class gj
extends CompositeSchematic
implements IStaticSchematic {
    public gj(CompoundTag object) {
        super(0, 0, 0);
        CompoundTag[] compoundTagArray = object;
        object = this;
        Vec3i vec3i = new Vec3i(gj.b((CompoundTag)compoundTagArray, "x"), gj.b((CompoundTag)compoundTagArray, "y"), gj.b((CompoundTag)compoundTagArray, "z"));
        for (CompoundTag compoundTag : gj.a((CompoundTag)compoundTagArray)) {
            BlockState[][][] blockStateArray;
            CompoundTag compoundTag2;
            Block block;
            Object object2 = compoundTag.getList("BlockStatePalette", 10);
            Object object3 = object2;
            CompoundTag compoundTag3 = new BlockState[object2.size()];
            for (int i2 = 0; i2 < object3.size(); ++i2) {
                block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse((String)((CompoundTag)object3.get(i2)).getString("Name")));
                CompoundTag compoundTag4 = compoundTag2 = ((CompoundTag)object3.get(i2)).getCompound("Properties");
                Block block2 = block;
                BlockState[][][] blockStateArray2 = block2.defaultBlockState();
                for (Object e2 : compoundTag4.getAllKeys()) {
                    Property property = block2.getStateDefinition().getProperty((String)e2);
                    String string = compoundTag4.getString((String)e2);
                    if (property == null) continue;
                    String string2 = string;
                    Property property2 = property;
                    blockStateArray = blockStateArray2;
                    Optional optional = property2.getValue(string2);
                    if (!optional.isPresent()) {
                        throw new IllegalArgumentException("Invalid value for property " + String.valueOf(property2));
                    }
                    blockStateArray2 = (BlockState)blockStateArray.setValue(property2, (Comparable)optional.get());
                }
                compoundTag3[i2] = blockStateArray2;
            }
            CompoundTag compoundTag5 = compoundTag3;
            int n2 = object2.size();
            int n3 = (int)Math.max(2.0, Math.ceil(Math.log(n2) / Math.log(2.0)));
            compoundTag3 = compoundTag.getCompound("Size");
            long l2 = Math.abs(compoundTag3.getInt("x") * compoundTag3.getInt("y") * compoundTag3.getInt("z"));
            object3 = compoundTag.getLongArray("BlockStates");
            object2 = new a(n3, l2, (long[])object3);
            compoundTag2 = object2;
            block = compoundTag5;
            compoundTag5 = vec3i;
            compoundTag3 = compoundTag;
            object3 = object;
            int n4 = gj.a(compoundTag3, "x") - compoundTag5.getX();
            int n5 = gj.a(compoundTag3, "y") - compoundTag5.getY();
            int n6 = gj.a(compoundTag3, "z") - compoundTag5.getZ();
            CompoundTag compoundTag6 = compoundTag3.getCompound("Size");
            int n7 = Math.abs(compoundTag6.getInt("x"));
            int n8 = Math.abs(compoundTag6.getInt("y"));
            int n9 = Math.abs(compoundTag6.getInt("z"));
            blockStateArray = new BlockState[n7][n9][n8];
            int n10 = 0;
            for (int i3 = 0; i3 < n8; ++i3) {
                for (int i4 = 0; i4 < n9; ++i4) {
                    for (int i5 = 0; i5 < n7; ++i5) {
                        int n11;
                        BlockState[] blockStateArray3 = blockStateArray[i5][i4];
                        long l3 = n10;
                        object2 = compoundTag2;
                        Validate.inclusiveBetween((long)0L, (long)(object2.b - 1L), (long)l3);
                        long l4 = l3 * (long)object2.a;
                        int n12 = (int)(l4 >> 6);
                        int n13 = (int)((l3 + 1L) * (long)object2.a - 1L >> 6);
                        int n14 = (int)(l4 & 0x3FL);
                        if (n12 == n13) {
                            n11 = (int)(object2.a[n12] >>> n14 & object2.a);
                        } else {
                            int n15 = 64 - n14;
                            n11 = (int)((object2.a[n12] >>> n14 | object2.a[n13] << n15) & object2.a);
                        }
                        blockStateArray3[i3] = block[n11];
                        ++n10;
                    }
                }
            }
            ((CompositeSchematic)object3).put(new ge(blockStateArray), n4, n5, n6);
        }
    }

    private static CompoundTag[] a(CompoundTag compoundTag) {
        return (CompoundTag[])compoundTag.getCompound("Regions").getAllKeys().stream().map(arg_0 -> ((CompoundTag)compoundTag.getCompound("Regions")).getCompound(arg_0)).toArray(CompoundTag[]::new);
    }

    private static int a(CompoundTag compoundTag, String string) {
        int n2 = compoundTag.getCompound("Position").getInt(string);
        int n3 = compoundTag.getCompound("Size").getInt(string);
        int n4 = n2;
        return Math.min(n4, n4 + n3 + 1);
    }

    private static int b(CompoundTag compoundTagArray, String string) {
        int n2 = Integer.MAX_VALUE;
        for (CompoundTag compoundTag : gj.a((CompoundTag)compoundTagArray)) {
            n2 = Math.min(n2, gj.a(compoundTag, string));
        }
        return n2;
    }

    @Override
    public final BlockState getDirect(int n2, int n3, int n4) {
        return this.desiredState(n2, n3, n4, null, Collections.emptyList());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class a {
        final long[] a;
        final int a;
        final long a;
        final long b;

        public a(int n2, long l2, @Nullable long[] lArray) {
            long l3;
            Validate.inclusiveBetween((long)1L, (long)32L, (long)n2);
            this.b = l2;
            this.a = n2;
            this.a = (1L << n2) - 1L;
            if (lArray != null) {
                this.a = lArray;
                return;
            }
            long l4 = l2 * (long)n2;
            n2 = 1;
            if (64L == 0L) {
                l3 = 0L;
            } else if (l4 == 0L) {
                l3 = 64L;
            } else {
                long l5;
                if (l4 < 0L) {
                    n2 = -1;
                }
                l3 = (l5 = l4 % (64L * (long)n2)) == 0L ? l4 : l4 + 64L * (long)n2 - l5;
            }
            this.a = new long[(int)(l3 / 64L)];
        }
    }
}

