/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.EquipmentSlot;
import dev.huskuraft.effortless.api.core.InteractionHand;

static class Player.1 {
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$core$InteractionHand;
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot;

    static {
        $SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot = new int[EquipmentSlot.values().length];
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot[EquipmentSlot.MAINHAND.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot[EquipmentSlot.OFFHAND.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot[EquipmentSlot.FEET.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot[EquipmentSlot.LEGS.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot[EquipmentSlot.CHEST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$EquipmentSlot[EquipmentSlot.HEAD.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$dev$huskuraft$effortless$api$core$InteractionHand = new int[InteractionHand.values().length];
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$InteractionHand[InteractionHand.MAIN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.1.$SwitchMap$dev$huskuraft$effortless$api$core$InteractionHand[InteractionHand.OFF.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
