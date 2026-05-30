/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import baritone.api.schematic.ISchematic;
import net.minecraft.world.level.block.state.BlockState;

public interface IStaticSchematic
extends ISchematic {
    public BlockState getDirect(int var1, int var2, int var3);

    default public BlockState[] getColumn(int n2, int n3) {
        BlockState[] blockStateArray = new BlockState[this.heightY()];
        for (int i2 = 0; i2 < this.heightY(); ++i2) {
            blockStateArray[i2] = this.getDirect(n2, i2, n3);
        }
        return blockStateArray;
    }
}

