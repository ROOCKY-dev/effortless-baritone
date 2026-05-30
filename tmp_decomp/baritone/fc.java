/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 */
package baritone;

import baritone.fb;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public final class fc
implements BlockGetter {
    private final fb a;

    fc(fb fb2) {
        this.a = fb2;
    }

    @Nullable
    public final BlockEntity getBlockEntity(BlockPos blockPos) {
        return null;
    }

    public final BlockState getBlockState(BlockPos blockPos) {
        return this.a.a(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public final FluidState getFluidState(BlockPos blockPos) {
        return this.getBlockState(blockPos).getFluidState();
    }

    public final int getHeight() {
        return this.a.a.getHeight();
    }

    public final int getMinBuildHeight() {
        return this.a.a.getMinBuildHeight();
    }
}

