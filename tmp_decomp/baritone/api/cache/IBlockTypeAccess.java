/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.cache;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockTypeAccess {
    public BlockState getBlock(int var1, int var2, int var3);

    default public BlockState getBlock(BlockPos blockPos) {
        return this.getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}

