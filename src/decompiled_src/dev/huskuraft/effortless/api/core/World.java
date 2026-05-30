/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockEntity;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.DimensionType;
import dev.huskuraft.effortless.api.core.FluidState;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.ResourceKey;
import dev.huskuraft.effortless.api.core.WorldBorder;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.UUID;

public interface World
extends PlatformReference {
    public Player getPlayer(UUID var1);

    public BlockState getBlockState(BlockPosition var1);

    public FluidState getFluidState(BlockPosition var1);

    public BlockEntity getBlockEntity(BlockPosition var1);

    public boolean setBlock(BlockPosition var1, BlockState var2, int var3, int var4);

    default public boolean setBlock(BlockPosition blockPosition, BlockState blockState, int flags) {
        return this.setBlock(blockPosition, blockState, flags, 512);
    }

    default public boolean setBlockAndUpdate(BlockPosition blockPosition, BlockState blockState) {
        return this.setBlock(blockPosition, blockState, 3);
    }

    default public boolean removeBlock(BlockPosition blockPosition, boolean moving) {
        return this.setBlock(blockPosition, this.getFluidState(blockPosition).createLegacyBlock(), 3 | (moving ? 64 : 0));
    }

    public boolean isClient();

    public ResourceKey<World> getDimensionId();

    public DimensionType getDimensionType();

    default public int getMinBuildHeight() {
        return this.getDimensionType().minY();
    }

    default public int getMaxBuildHeight() {
        return this.getDimensionType().minY() + this.getDimensionType().height();
    }

    public WorldBorder getWorldBorder();
}
