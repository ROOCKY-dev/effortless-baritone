/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.building.SingleCommand;

static class EffortlessNetworkChannel.1 {
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$building$SingleCommand;

    static {
        $SwitchMap$dev$huskuraft$effortless$building$SingleCommand = new int[SingleCommand.values().length];
        try {
            EffortlessNetworkChannel.1.$SwitchMap$dev$huskuraft$effortless$building$SingleCommand[SingleCommand.REDO.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessNetworkChannel.1.$SwitchMap$dev$huskuraft$effortless$building$SingleCommand[SingleCommand.UNDO.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
