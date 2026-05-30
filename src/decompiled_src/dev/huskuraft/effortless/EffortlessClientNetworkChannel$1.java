/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.building.SingleCommand;

static class EffortlessClientNetworkChannel.1 {
    static final /* synthetic */ int[] $SwitchMap$dev$huskuraft$effortless$building$SingleCommand;

    static {
        $SwitchMap$dev$huskuraft$effortless$building$SingleCommand = new int[SingleCommand.values().length];
        try {
            EffortlessClientNetworkChannel.1.$SwitchMap$dev$huskuraft$effortless$building$SingleCommand[SingleCommand.REDO.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffortlessClientNetworkChannel.1.$SwitchMap$dev$huskuraft$effortless$building$SingleCommand[SingleCommand.UNDO.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
