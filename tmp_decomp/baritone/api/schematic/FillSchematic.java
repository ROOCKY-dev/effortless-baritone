/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import baritone.api.schematic.AbstractSchematic;
import baritone.api.utils.BlockOptionalMeta;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;

public class FillSchematic
extends AbstractSchematic {
    private final BlockOptionalMeta bom;

    public FillSchematic(int n2, int n3, int n4, BlockOptionalMeta blockOptionalMeta) {
        super(n2, n3, n4);
        this.bom = blockOptionalMeta;
    }

    public FillSchematic(int n2, int n3, int n4, BlockState blockState) {
        this(n2, n3, n4, new BlockOptionalMeta(blockState.getBlock()));
    }

    public BlockOptionalMeta getBom() {
        return this.bom;
    }

    @Override
    public BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        if (this.bom.matches(blockState)) {
            return blockState;
        }
        for (BlockState blockState2 : list) {
            if (!this.bom.matches(blockState2)) continue;
            return blockState2;
        }
        return this.bom.getAnyBlockState();
    }
}

