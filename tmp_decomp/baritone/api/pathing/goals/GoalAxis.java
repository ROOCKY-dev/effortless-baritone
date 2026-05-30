/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.pathing.goals;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalYLevel;

public class GoalAxis
implements Goal {
    private static final double SQRT_2_OVER_2 = Math.sqrt(2.0) / 2.0;

    @Override
    public boolean isInGoal(int n2, int n3, int n4) {
        return n3 == (Integer)BaritoneAPI.getSettings().axisHeight.value && (n2 == 0 || n4 == 0 || Math.abs(n2) == Math.abs(n4));
    }

    @Override
    public double heuristic(int n2, int n3, int n4) {
        n2 = Math.abs(n2);
        n4 = Math.abs(n4);
        int n5 = Math.min(n2, n4);
        n5 = Math.max(n2, n4) - n5;
        return Math.min((double)n2, Math.min((double)n4, (double)n5 * SQRT_2_OVER_2)) * (Double)BaritoneAPI.getSettings().costHeuristic.value + GoalYLevel.calculate((Integer)BaritoneAPI.getSettings().axisHeight.value, n3);
    }

    public boolean equals(Object object) {
        return object.getClass() == GoalAxis.class;
    }

    public int hashCode() {
        return 201385781;
    }

    public String toString() {
        return "GoalAxis";
    }
}

