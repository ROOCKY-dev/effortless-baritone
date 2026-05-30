/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Block;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.InteractionResult;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.Registry;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.platform.RegistryFactory;
import dev.huskuraft.effortless.api.text.Text;
import java.util.Optional;

public interface Item
extends PlatformReference {
    public static final Registry<Item> REGISTRY = RegistryFactory.getInstance().getRegistry(new Item[0]);

    public static Item fromId(ResourceLocation id) {
        return ContentFactory.getInstance().newItem(id);
    }

    public static Optional<Item> fromIdOptional(ResourceLocation id) {
        return ContentFactory.getInstance().newOptionalItem(id);
    }

    public ItemStack getDefaultStack();

    public Block getBlock();

    public ResourceLocation getId();

    public InteractionResult useOnBlock(Player var1, BlockInteraction var2);

    public boolean isCorrectToolForDrops(BlockState var1);

    public int getMaxStackSize();

    public int getMaxDamage();

    public boolean mineBlock(World var1, Player var2, BlockPosition var3, BlockState var4, ItemStack var5);

    public Text getName(ItemStack var1);

    default public Text getName() {
        return this.getName(this.getDefaultStack());
    }

    default public boolean isCorrectToolForDropsNoThrows(BlockState blockState) {
        try {
            return this.isCorrectToolForDrops(blockState);
        }
        catch (Throwable t) {
            return false;
        }
    }
}
