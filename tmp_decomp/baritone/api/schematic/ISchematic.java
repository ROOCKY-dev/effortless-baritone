/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface ISchematic {
    default public boolean inSchematic(int n2, int n3, int n4, BlockState blockState) {
        return n2 >= 0 && n2 < this.widthX() && n3 >= 0 && n3 < this.heightY() && n4 >= 0 && n4 < this.lengthZ();
    }

    default public int size(Direction.Axis axis) {
        switch (axis) {
            case X: {
                return this.widthX();
            }
            case Y: {
                return this.heightY();
            }
            case Z: {
                return this.lengthZ();
            }
        }
        throw new UnsupportedOperationException(String.valueOf(axis));
    }

    public BlockState desiredState(int var1, int var2, int var3, BlockState var4, List<BlockState> var5);

    default public void reset() {
    }

    public int widthX();

    public int heightY();

    public int lengthZ();
}

