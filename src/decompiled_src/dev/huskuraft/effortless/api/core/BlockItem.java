/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Block;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.InteractionResult;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;

public interface BlockItem
extends Item {
    @Override
    public Block getBlock();

    @Override
    public InteractionResult useOnBlock(Player var1, BlockInteraction var2);

    public InteractionResult placeOnBlock(Player var1, BlockInteraction var2);

    default public boolean setBlockOnly(World world, Player player, BlockInteraction blockInteraction, BlockState blockState) {
        return world.setBlockAndUpdate(blockInteraction.getBlockPosition(), blockState);
    }

    default public boolean updateBlockEntityTag(World world, BlockPosition blockPosition, BlockState blockState, ItemStack itemStack) {
        return true;
    }
}
