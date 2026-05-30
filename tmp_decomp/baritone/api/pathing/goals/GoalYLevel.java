/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.pathing.goals;

import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.ActionCosts;
import baritone.api.utils.SettingsUtil;

public class GoalYLevel
implements Goal,
ActionCosts {
    public final int level;

    public GoalYLevel(int n2) {
        this.level = n2;
    }

    @Override
    public boolean isInGoal(int n2, int n3, int n4) {
        return n3 == this.level;
    }

    @Override
    public double heuristic(int n2, int n3, int n4) {
        return GoalYLevel.calculate(this.level, n3);
    }

    public static double calculate(int n2, int n3) {
        if (n3 > n2) {
            return FALL_N_BLOCKS_COST[2] / 2.0 * (double)(n3 - n2);
        }
        if (n3 < n2) {
            return (double)(n2 - n3) * JUMP_ONE_BLOCK_COST;
        }
        return 0.0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        object = (GoalYLevel)object;
        return this.level == ((GoalYLevel)object).level;
    }

    public int hashCode() {
        return this.level * 1271009915;
    }

    public String toString() {
        return String.format("GoalYLevel{y=%s}", SettingsUtil.maybeCensor(this.level));
    }
}

