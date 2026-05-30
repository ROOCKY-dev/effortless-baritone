/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.List;
import java.util.stream.IntStream;

public interface Container
extends PlatformReference {
    public ItemStack getItem(int var1);

    public void setItem(int var1, ItemStack var2);

    public int getContainerSize();

    default public List<ItemStack> getItems() {
        return IntStream.range(0, this.getContainerSize()).mapToObj(this::getItem).toList();
    }

    default public boolean addItem(int index, ItemStack itemStack) {
        List<ItemStack> items = this.getItems();
        for (int indexInContainer = 0; indexInContainer < items.size(); ++indexInContainer) {
            ItemStack itemStackInContainer = items.get(indexInContainer);
            if (!itemStackInContainer.isAir()) continue;
            this.setItem(indexInContainer, itemStack);
            return true;
        }
        return false;
    }

    default public boolean removeItem(int index) {
        this.setItem(index, ItemStack.empty());
        return true;
    }

    default public boolean addItem(ItemStack itemStack) {
        return this.addItem(-1, itemStack);
    }

    default public boolean contains(ItemStack itemStack) {
        return this.getItems().contains(itemStack);
    }
}
