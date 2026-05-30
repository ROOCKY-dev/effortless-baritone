/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import baritone.api.schematic.AbstractSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.mask.Mask;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MaskSchematic
extends AbstractSchematic {
    private final ISchematic schematic;

    public MaskSchematic(ISchematic iSchematic) {
        super(iSchematic.widthX(), iSchematic.heightY(), iSchematic.lengthZ());
        this.schematic = iSchematic;
    }

    protected abstract boolean partOfMask(int var1, int var2, int var3, BlockState var4);

    @Override
    public boolean inSchematic(int n2, int n3, int n4, BlockState blockState) {
        return this.schematic.inSchematic(n2, n3, n4, blockState) && this.partOfMask(n2, n3, n4, blockState);
    }

    @Override
    public BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        return this.schematic.desiredState(n2, n3, n4, blockState, list);
    }

    public static MaskSchematic create(ISchematic iSchematic, final Mask mask) {
        return new MaskSchematic(iSchematic){

            @Override
            protected boolean partOfMask(int n2, int n3, int n4, BlockState blockState) {
                return mask.partOfMask(n2, n3, n4, blockState);
            }
        };
    }
}

