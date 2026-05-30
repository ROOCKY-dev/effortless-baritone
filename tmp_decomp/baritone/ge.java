/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.schematic.AbstractSchematic;
import baritone.api.schematic.IStaticSchematic;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;

public class ge
extends AbstractSchematic
implements IStaticSchematic {
    public BlockState[][][] a;

    public ge() {
    }

    public ge(BlockState[][][] blockStateArray) {
        this.a = blockStateArray;
        boolean bl2 = blockStateArray.length == 0 || blockStateArray[0].length == 0 || blockStateArray[0][0].length == 0;
        this.x = bl2 ? 0 : blockStateArray.length;
        this.z = bl2 ? 0 : blockStateArray[0].length;
        this.y = bl2 ? 0 : blockStateArray[0][0].length;
    }

    @Override
    public BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        return this.a[n2][n4][n3];
    }

    @Override
    public BlockState getDirect(int n2, int n3, int n4) {
        return this.a[n2][n4][n3];
    }

    @Override
    public BlockState[] getColumn(int n2, int n3) {
        return this.a[n2][n3];
    }
}

