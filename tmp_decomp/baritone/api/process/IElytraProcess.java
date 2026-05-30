/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone.api.process;

import baritone.api.pathing.goals.Goal;
import baritone.api.process.IBaritoneProcess;
import net.minecraft.core.BlockPos;

public interface IElytraProcess
extends IBaritoneProcess {
    public void repackChunks();

    public BlockPos currentDestination();

    public void pathTo(BlockPos var1);

    public void pathTo(Goal var1);

    public void resetState();

    public boolean isLoaded();

    public boolean isSafeToCancel();
}

