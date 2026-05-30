/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.schematic.IStaticSchematic;
import baritone.api.schematic.MaskSchematic;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class gb
extends MaskSchematic {
    private final int[][] a;

    public gb(IStaticSchematic iStaticSchematic) {
        super(iStaticSchematic);
        int[][] nArray = new int[iStaticSchematic.widthX()][iStaticSchematic.lengthZ()];
        int n2 = 0;
        for (int i2 = 0; i2 < iStaticSchematic.widthX(); ++i2) {
            for (int i3 = 0; i3 < iStaticSchematic.lengthZ(); ++i3) {
                Object object;
                Object object2;
                block5: {
                    Predicate<BlockState> predicate = blockState -> !(blockState.getBlock() instanceof AirBlock);
                    object2 = iStaticSchematic.getColumn(i2, i3);
                    for (int i4 = ((BlockState[])object2).length - 1; i4 >= 0; --i4) {
                        if (!predicate.test(object2[i4])) continue;
                        object = OptionalInt.of(i4);
                        break block5;
                    }
                    object = object2 = OptionalInt.empty();
                }
                if (((OptionalInt)object).isPresent()) {
                    nArray[i2][i3] = ((OptionalInt)object2).getAsInt();
                    continue;
                }
                ++n2;
                nArray[i2][i3] = Integer.MAX_VALUE;
            }
        }
        if (n2 != 0) {
            System.out.println(n2 + " columns had no block despite being in a map art, letting them be whatever");
        }
        this.a = nArray;
    }

    @Override
    public final boolean partOfMask(int n2, int n3, int n4, BlockState blockState) {
        return n3 >= this.a[n2][n4];
    }
}

