/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone;

import baritone.a;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.IElytraProcess;
import baritone.api.process.PathingCommand;
import baritone.ey;
import net.minecraft.core.BlockPos;

public final class eq
extends ey
implements IElytraProcess {
    public eq(a a2) {
        super(a2);
    }

    @Override
    public final void repackChunks() {
        throw new UnsupportedOperationException("Called repackChunks() on NullElytraBehavior");
    }

    @Override
    public final BlockPos currentDestination() {
        return null;
    }

    @Override
    public final void pathTo(BlockPos blockPos) {
        throw new UnsupportedOperationException("Called pathTo() on NullElytraBehavior");
    }

    @Override
    public final void pathTo(Goal goal) {
        throw new UnsupportedOperationException("Called pathTo() on NullElytraBehavior");
    }

    @Override
    public final void resetState() {
    }

    @Override
    public final boolean isActive() {
        return false;
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        throw new UnsupportedOperationException("Called onTick on NullElytraProcess");
    }

    @Override
    public final void onLostControl() {
    }

    @Override
    public final String displayName0() {
        return "NullElytraProcess";
    }

    @Override
    public final boolean isLoaded() {
        return false;
    }

    @Override
    public final boolean isSafeToCancel() {
        return true;
    }
}

