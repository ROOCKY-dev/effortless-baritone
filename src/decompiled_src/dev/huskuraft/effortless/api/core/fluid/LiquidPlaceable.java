/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core.fluid;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.FluidState;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.core.fluid.Fluid;

public interface LiquidPlaceable {
    public boolean canPlaceLiquid(World var1, Player var2, BlockPosition var3, BlockState var4, Fluid var5);

    public boolean placeLiquid(World var1, BlockPosition var2, BlockState var3, FluidState var4);
}
