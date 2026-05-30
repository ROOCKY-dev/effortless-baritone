/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseFireBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package baritone.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public final class VecUtils {
    private VecUtils() {
    }

    public static Vec3 calculateBlockCenter(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        if ((level = blockState.getCollisionShape((BlockGetter)level, blockPos)).isEmpty()) {
            return VecUtils.getBlockPosCenter(blockPos);
        }
        double d2 = (level.min(Direction.Axis.X) + level.max(Direction.Axis.X)) / 2.0;
        double d3 = (level.min(Direction.Axis.Y) + level.max(Direction.Axis.Y)) / 2.0;
        double d4 = (level.min(Direction.Axis.Z) + level.max(Direction.Axis.Z)) / 2.0;
        if (Double.isNaN(d2) || Double.isNaN(d3) || Double.isNaN(d4)) {
            throw new IllegalStateException(String.valueOf(blockState) + " " + String.valueOf(blockPos) + " " + String.valueOf(level));
        }
        if (blockState.getBlock() instanceof BaseFireBlock) {
            d3 = 0.0;
        }
        return new Vec3((double)blockPos.getX() + d2, (double)blockPos.getY() + d3, (double)blockPos.getZ() + d4);
    }

    public static Vec3 getBlockPosCenter(BlockPos blockPos) {
        return new Vec3((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
    }

    public static double distanceToCenter(BlockPos blockPos, double d2, double d3, double d4) {
        double d5 = (double)blockPos.getX() + 0.5 - d2;
        double d6 = (double)blockPos.getY() + 0.5 - d3;
        double d7 = (double)blockPos.getZ() + 0.5 - d4;
        double d8 = d5;
        double d9 = d6;
        double d10 = d7;
        return Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
    }

    public static double entityDistanceToCenter(Entity entity, BlockPos blockPos) {
        return VecUtils.distanceToCenter(blockPos, entity.position().x, entity.position().y, entity.position().z);
    }

    public static double entityFlatDistanceToCenter(Entity entity, BlockPos blockPos) {
        return VecUtils.distanceToCenter(blockPos, entity.position().x, (double)blockPos.getY() + 0.5, entity.position().z);
    }
}

