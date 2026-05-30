/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.Block;
import dev.huskuraft.effortless.api.core.BlockEntity;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.InteractionResult;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.Registry;
import dev.huskuraft.effortless.api.core.Revolve;
import dev.huskuraft.effortless.api.core.StateHolder;
import dev.huskuraft.effortless.api.core.fluid.Fluid;
import dev.huskuraft.effortless.api.platform.RegistryFactory;
import dev.huskuraft.effortless.api.sound.SoundSet;

public interface BlockState
extends StateHolder {
    public static final Registry<BlockState> REGISTRY = RegistryFactory.getInstance().getRegistry(new BlockState[0]);

    public BlockState mirror(Axis var1);

    public BlockState rotate(Revolve var1);

    public boolean isAir();

    public boolean canBeReplaced(Player var1, BlockInteraction var2);

    public boolean isReplaceable();

    public boolean hasTagFeatureCannotReplace();

    public SoundSet getSoundSet();

    public Block getBlock();

    public boolean canBeReplaced(Fluid var1);

    public InteractionResult use(Player var1, BlockInteraction var2);

    public boolean requiresCorrectToolForDrops();

    default public Item getItem() {
        return this.getBlock().asItem();
    }

    default public BlockEntity getEntity(BlockPosition blockPosition) {
        return this.getBlock().getEntity(blockPosition, this);
    }

    @Deprecated
    public int getRequiredItemCount();
}
