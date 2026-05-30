/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleIterator
 *  it.unimi.dsi.fastutil.doubles.DoubleOpenHashSet
 *  net.minecraft.core.BlockPos
 */
package baritone.api.pathing.goals;

import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleOpenHashSet;
import net.minecraft.core.BlockPos;

public class GoalNear
implements Goal,
IGoalRenderPos {
    private final int x;
    private final int y;
    private final int z;
    private final int rangeSq;

    public GoalNear(BlockPos blockPos, int n2) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
        int n3 = n2;
        this.rangeSq = n3 * n3;
    }

    @Override
    public boolean isInGoal(int n2, int n3, int n4) {
        int n5 = n2 -= this.x;
        int n6 = n3 -= this.y;
        int n7 = n4 -= this.z;
        return n5 * n5 + n6 * n6 + n7 * n7 <= this.rangeSq;
    }

    @Override
    public double heuristic(int n2, int n3, int n4) {
        return GoalBlock.calculate(n2 -= this.x, n3 -= this.y, n4 -= this.z);
    }

    @Override
    public double heuristic() {
        double d2;
        int n2 = (int)Math.ceil(Math.sqrt(this.rangeSq));
        DoubleOpenHashSet doubleOpenHashSet = new DoubleOpenHashSet();
        double d3 = Double.POSITIVE_INFINITY;
        for (int i2 = -n2; i2 <= n2; ++i2) {
            for (int i3 = -n2; i3 <= n2; ++i3) {
                for (int i4 = -n2; i4 <= n2; ++i4) {
                    double d4;
                    GoalNear goalNear = this;
                    d2 = goalNear.heuristic(goalNear.x + i2, this.y + i3, this.z + i4);
                    if (d4 < d3) {
                        GoalNear goalNear2 = this;
                        if (goalNear2.isInGoal(goalNear2.x + i2, this.y + i3, this.z + i4)) {
                            doubleOpenHashSet.add(d2);
                            continue;
                        }
                    }
                    d3 = Math.min(d3, d2);
                }
            }
        }
        double d5 = Double.NEGATIVE_INFINITY;
        DoubleIterator doubleIterator = doubleOpenHashSet.iterator();
        while (doubleIterator.hasNext()) {
            double d6;
            d2 = doubleIterator.nextDouble();
            if (!(d6 < d3)) continue;
            d5 = Math.max(d5, d2);
        }
        return d5;
    }

    @Override
    public BlockPos getGoalPos() {
        return new BlockPos(this.x, this.y, this.z);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        object = (GoalNear)object;
        return this.x == ((GoalNear)object).x && this.y == ((GoalNear)object).y && this.z == ((GoalNear)object).z && this.rangeSq == ((GoalNear)object).rangeSq;
    }

    public int hashCode() {
        return (int)BetterBlockPos.longHash(this.x, this.y, this.z) + this.rangeSq;
    }

    public String toString() {
        return String.format("GoalNear{x=%s, y=%s, z=%s, rangeSq=%d}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z), this.rangeSq);
    }
}

