/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.StateHolder;

public interface FluidState
extends StateHolder {
    public BlockState createLegacyBlock();
}
