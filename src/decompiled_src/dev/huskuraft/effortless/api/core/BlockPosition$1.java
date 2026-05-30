/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.Revolve;

static class BlockPosition.1 {
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$core$Revolve;
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$api$core$Axis;

    static {
        $SwitchMap$dev$huskuraft$effortless$api$core$Axis = new int[Axis.values().length];
        try {
            BlockPosition.1.$SwitchMap$dev$huskuraft$effortless$api$core$Axis[Axis.X.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            BlockPosition.1.$SwitchMap$dev$huskuraft$effortless$api$core$Axis[Axis.Y.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            BlockPosition.1.$SwitchMap$dev$huskuraft$effortless$api$core$Axis[Axis.Z.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$dev$huskuraft$effortless$api$core$Revolve = new int[Revolve.values().length];
        try {
            BlockPosition.1.$SwitchMap$dev$huskuraft$effortless$api$core$Revolve[Revolve.CLOCKWISE_90.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            BlockPosition.1.$SwitchMap$dev$huskuraft$effortless$api$core$Revolve[Revolve.CLOCKWISE_180.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            BlockPosition.1.$SwitchMap$dev$huskuraft$effortless$api$core$Revolve[Revolve.COUNTERCLOCKWISE_90.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
