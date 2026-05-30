/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Container;
import dev.huskuraft.effortless.api.core.ItemStack;
import java.util.List;

public interface Inventory
extends Container {
    default public List<ItemStack> getBagItems() {
        return this.getItems().subList(0, this.getBagSize());
    }

    default public List<ItemStack> getArmorItems() {
        return this.getItems().subList(this.getBagSize(), this.getBagSize() + this.getArmorSize());
    }

    default public List<ItemStack> getOffhandItems() {
        return this.getItems().subList(this.getBagSize() + this.getArmorSize(), this.getBagSize() + this.getArmorSize() + this.getOffhandSize());
    }

    default public ItemStack getBagItem(int index) {
        return this.getItem(index);
    }

    default public ItemStack getArmorItem(int index) {
        return this.getItem(index + this.getBagSize());
    }

    default public ItemStack getOffhandItem(int index) {
        return this.getItem(index + this.getBagSize() + this.getArmorSize());
    }

    default public ItemStack getOffhandItem() {
        return this.getOffhandItem(0);
    }

    default public void setOffhandItem(ItemStack itemStack) {
        this.setOffhandItem(0, itemStack);
    }

    default public void setBagItem(int index, ItemStack itemStack) {
        this.setItem(index, itemStack);
    }

    default public void setArmorItem(int index, ItemStack itemStack) {
        this.setItem(index + this.getBagSize(), itemStack);
    }

    default public void setOffhandItem(int index, ItemStack itemStack) {
        this.setItem(index + this.getBagSize() + this.getArmorSize(), itemStack);
    }

    @Override
    default public int getContainerSize() {
        return this.getBagSize() + this.getArmorSize() + this.getOffhandSize();
    }

    public int getBagSize();

    public int getArmorSize();

    public int getOffhandSize();

    public int getHotbarSize();

    public int getSelected();

    default public int getFreeSlotIndex() {
        return this.getBagItems().stream().filter(ItemStack::isEmpty).findFirst().map(this.getBagItems()::indexOf).orElse(-1);
    }

    default public boolean isHotbarSlot(int index) {
        return index >= 0 && index < this.getHotbarSize();
    }

    default public ItemStack getSelectedItem() {
        if (this.isHotbarSlot(this.getSelected())) {
            return this.getBagItem(this.getSelected());
        }
        return ItemStack.empty();
    }

    default public void setSelectedItem(ItemStack itemStack) {
        this.setBagItem(this.getSelected(), itemStack);
    }

    default public List<ItemStack> getHotbarItems() {
        return this.getBagItems().subList(0, this.getHotbarSize());
    }
}
