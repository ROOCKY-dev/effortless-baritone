/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.InteractionResult;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.core.fluid.BucketCollectable;
import dev.huskuraft.effortless.api.core.fluid.Fluid;
import dev.huskuraft.effortless.api.core.fluid.Fluids;
import java.util.Objects;

public interface BucketItem
extends Item {
    public static ItemStack createFilledResult(Player player, ItemStack emptyItemStack, ItemStack filledItemStack, boolean preventDuplicates) {
        boolean isCreative = player.getGameMode().isCreative();
        if (!isCreative) {
            emptyItemStack.decrease(1);
        }
        if (isCreative && preventDuplicates) {
            return emptyItemStack;
        }
        if (emptyItemStack.isEmpty()) {
            return filledItemStack;
        }
        if (!player.getInventory().addItem(filledItemStack)) {
            player.drop(filledItemStack, false, false);
        }
        return emptyItemStack;
    }

    public static ItemStack createFilledResult(Player player, ItemStack emptyItemStack, ItemStack filledItemStack) {
        return BucketItem.createFilledResult(player, emptyItemStack, filledItemStack, true);
    }

    public Fluid getContent();

    public boolean useContent(World var1, Player var2, BlockPosition var3, BlockInteraction var4);

    public void useExtraContent(World var1, Player var2, BlockPosition var3, ItemStack var4);

    default public boolean isEmpty() {
        return this.getContent() == null || Objects.equals(this.getContent().reference(), Fluids.EMPTY.fluid().reference());
    }

    @Override
    default public InteractionResult useOnBlock(Player player, BlockInteraction blockInteraction) {
        ItemStack itemStack = player.getItemStack(blockInteraction.getHand());
        BlockState blockState = player.getWorld().getBlockState(blockInteraction.getBlockPosition());
        Item item = itemStack.getItem();
        if (item instanceof BucketItem) {
            BucketItem bucketItem = (BucketItem)item;
            if (bucketItem.isEmpty()) {
                ItemStack collected;
                BucketCollectable bucketCollectable = blockState.getBlock().getBucketCollectable();
                if (bucketCollectable != null && !(collected = bucketCollectable.pickupBlock(player.getWorld(), player, blockInteraction.getBlockPosition(), blockState)).isEmpty()) {
                    ItemStack result = BucketItem.createFilledResult(player, itemStack, collected);
                    return InteractionResult.SUCCESS;
                }
            } else if ((blockState.getBlock().getLiquidPlaceable() != null || blockState.isAir() || blockState.canBeReplaced(bucketItem.getContent())) && bucketItem.useContent(player.getWorld(), player, blockInteraction.getBlockPosition(), blockInteraction)) {
                bucketItem.useExtraContent(player.getWorld(), player, blockInteraction.getBlockPosition(), itemStack);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}
