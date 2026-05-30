/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Entity;
import dev.huskuraft.effortless.api.core.EquipmentSlot;
import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.core.Inventory;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.PlayerProfile;
import dev.huskuraft.effortless.api.core.Stat;
import dev.huskuraft.effortless.api.text.Text;

public interface Player
extends Entity {
    public PlayerProfile getProfile();

    public Inventory getInventory();

    default public boolean isOperator() {
        return this.getServer().isOperator(this.getProfile());
    }

    default public boolean isSinglePlayerOwner() {
        return this.getServer().isSinglePlayerOwner(this.getProfile());
    }

    default public ItemStack getItemStack(InteractionHand hand) {
        return switch (hand) {
            default -> throw new MatchException(null, null);
            case InteractionHand.MAIN -> this.getItemBySlot(EquipmentSlot.MAINHAND);
            case InteractionHand.OFF -> this.getItemBySlot(EquipmentSlot.OFFHAND);
        };
    }

    default public void setItemStack(InteractionHand hand, ItemStack itemStack) {
        switch (hand) {
            case MAIN: {
                this.getInventory().setSelectedItem(itemStack);
                break;
            }
            case OFF: {
                this.getInventory().setOffhandItem(itemStack);
            }
        }
    }

    default public ItemStack getItemBySlot(EquipmentSlot slot) {
        return switch (slot) {
            default -> throw new MatchException(null, null);
            case EquipmentSlot.MAINHAND -> this.getInventory().getSelectedItem();
            case EquipmentSlot.OFFHAND -> this.getInventory().getOffhandItem();
            case EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD -> this.getInventory().getArmorItems().get(slot.getIndex());
        };
    }

    public void drop(ItemStack var1, boolean var2, boolean var3);

    public void sendMessage(Text var1);

    default public void sendMessage(String message) {
        this.sendMessage(Text.text(message));
    }

    public void sendClientMessage(Text var1, boolean var2);

    default public void sendClientMessage(String message, boolean actionBar) {
        this.sendClientMessage(Text.text(message), actionBar);
    }

    public void swing(InteractionHand var1);

    public void awardStat(Stat<?> var1, int var2);

    default public void awardStat(Stat<?> stat) {
        this.awardStat(stat, 1);
    }

    public void resetStat(Stat<?> var1);
}
