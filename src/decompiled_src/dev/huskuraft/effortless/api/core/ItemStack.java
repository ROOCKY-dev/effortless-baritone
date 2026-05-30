/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockItem;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.Items;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.StatTypes;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.text.Text;
import java.util.List;

public interface ItemStack
extends PlatformReference {
    public static ItemStack empty() {
        return ContentFactory.getInstance().newItemStack();
    }

    public static ItemStack of(Item item, int count) {
        return ContentFactory.getInstance().newItemStack(item, count);
    }

    public List<Text> getTooltips(Player var1, TooltipType var2);

    public Item getItem();

    public int getCount();

    public void setCount(int var1);

    public Text getHoverName();

    public ItemStack copy();

    default public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    default public void increase(int count) {
        this.setCount(this.getCount() + count);
    }

    default public void decrease(int count) {
        this.setCount(this.getCount() - count);
    }

    default public boolean isEmpty() {
        return this == ItemStack.empty() || this.getItem().equals(Items.AIR.item()) || this.getCount() <= 0;
    }

    default public boolean isAir() {
        return this.getItem().equals(Items.AIR.item());
    }

    default public boolean isBlock() {
        return this.getItem() instanceof BlockItem;
    }

    default public boolean isDamaged() {
        return this.isDamageableItem() && this.getDamageValue() > 0;
    }

    default public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isDamageableItem() || !this.isDamaged());
    }

    public int getDamageValue();

    public void setDamageValue(int var1);

    public int getMaxDamage();

    default public int getDurabilityLeft() {
        return this.getMaxDamage() - this.getDamageValue();
    }

    public boolean isDamageableItem();

    default public boolean damage(int damage) {
        if (this.isDamageableItem()) {
            this.setDamageValue(Math.min(this.getDamageValue() + damage, this.getMaxDamage()));
            return true;
        }
        return false;
    }

    default public void mineBlock(World world, Player player, BlockPosition blockPosition, BlockState blockState) {
        if (this.getItem().mineBlock(world, player, blockPosition, blockState, this)) {
            player.awardStat(StatTypes.ITEM_USED.get(this.getItem()));
        }
    }

    default public Text getName() {
        return this.getItem().getName(this);
    }

    default public ItemStack withCount(int count) {
        ItemStack itemStack = this.copy();
        itemStack.setCount(count);
        return itemStack;
    }

    public static enum TooltipType {
        NORMAL,
        NORMAL_CREATIVE,
        ADVANCED,
        ADVANCED_CREATIVE;

    }
}
