/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.api.core.Direction;
import dev.huskuraft.effortless.api.core.InteractionType;
import dev.huskuraft.effortless.api.events.lifecycle.ClientTick;

static class EffortlessClientManager.1 {
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$core$Direction;
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$core$InteractionType;
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$events$lifecycle$ClientTick$Phase;

    static {
        $SwitchMap$dev$huskuraft$effortless$api$events$lifecycle$ClientTick$Phase = new int[ClientTick.Phase.values().length];
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$events$lifecycle$ClientTick$Phase[ClientTick.Phase.START.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$events$lifecycle$ClientTick$Phase[ClientTick.Phase.END.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$dev$huskuraft$effortless$api$core$InteractionType = new int[InteractionType.values().length];
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$InteractionType[InteractionType.ATTACK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$InteractionType[InteractionType.USE_ITEM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$InteractionType[InteractionType.UNKNOWN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$dev$huskuraft$effortless$api$core$Direction = new int[Direction.values().length];
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$Direction[Direction.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$Direction[Direction.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$Direction[Direction.WEST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientManager.1.$SwitchMap$dev$huskuraft$effortless$api$core$Direction[Direction.EAST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
