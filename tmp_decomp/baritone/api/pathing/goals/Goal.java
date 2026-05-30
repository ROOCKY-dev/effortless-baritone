/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone.api.pathing.goals;

import net.minecraft.core.BlockPos;

public interface Goal {
    public boolean isInGoal(int var1, int var2, int var3);

    public double heuristic(int var1, int var2, int var3);

    default public boolean isInGoal(BlockPos blockPos) {
        return this.isInGoal(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    default public double heuristic(BlockPos blockPos) {
        return this.heuristic(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    default public double heuristic() {
        return 0.0;
    }
}

