/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockEntity;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.core.fluid.BucketCollectable;
import dev.huskuraft.effortless.api.core.fluid.LiquidPlaceable;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.List;
import javax.annotation.Nullable;

public interface Block
extends PlatformReference {
    public BlockState getDefaultBlockState();

    public BlockState getBlockState(Player var1, BlockInteraction var2);

    public BucketCollectable getBucketCollectable();

    public LiquidPlaceable getLiquidPlaceable();

    public void destroy(World var1, Player var2, BlockPosition var3, BlockState var4);

    public void destroyStart(World var1, Player var2, BlockPosition var3, BlockState var4, BlockEntity var5, ItemStack var6);

    public void destroyEnd(World var1, Player var2, BlockPosition var3, BlockState var4, BlockEntity var5, ItemStack var6);

    public void place(World var1, Player var2, BlockPosition var3, BlockState var4, ItemStack var5);

    public Item asItem();

    public BlockEntity getEntity(BlockPosition var1, BlockState var2);

    public List<ItemStack> getDrops(World var1, Player var2, BlockPosition var3, BlockState var4, @Nullable BlockEntity var5, ItemStack var6);
}
