/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import baritone.api.schematic.ISchematic;
import baritone.api.schematic.MaskSchematic;
import baritone.api.utils.BlockOptionalMetaLookup;
import net.minecraft.world.level.block.state.BlockState;

public class ReplaceSchematic
extends MaskSchematic {
    private final BlockOptionalMetaLookup filter;
    private final Boolean[][][] cache;

    public ReplaceSchematic(ISchematic iSchematic, BlockOptionalMetaLookup blockOptionalMetaLookup) {
        super(iSchematic);
        this.filter = blockOptionalMetaLookup;
        this.cache = new Boolean[this.widthX()][this.heightY()][this.lengthZ()];
    }

    @Override
    public void reset() {
        for (int i2 = 0; i2 < this.cache.length; ++i2) {
            for (int i3 = 0; i3 < this.cache[0].length; ++i3) {
                for (int i4 = 0; i4 < this.cache[0][0].length; ++i4) {
                    this.cache[i2][i3][i4] = null;
                }
            }
        }
    }

    @Override
    protected boolean partOfMask(int n2, int n3, int n4, BlockState blockState) {
        if (this.cache[n2][n3][n4] == null) {
            this.cache[n2][n3][n4] = this.filter.has(blockState);
        }
        return this.cache[n2][n3][n4];
    }
}

