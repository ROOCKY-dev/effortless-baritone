/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.pathing.goals;

import baritone.api.pathing.goals.Goal;
import java.util.Arrays;

public class GoalComposite
implements Goal {
    private final Goal[] goals;

    public GoalComposite(Goal ... goalArray) {
        this.goals = goalArray;
    }

    @Override
    public boolean isInGoal(int n2, int n3, int n4) {
        Goal[] goalArray = this.goals;
        int n5 = this.goals.length;
        for (int i2 = 0; i2 < n5; ++i2) {
            if (!goalArray[i2].isInGoal(n2, n3, n4)) continue;
            return true;
        }
        return false;
    }

    @Override
    public double heuristic(int n2, int n3, int n4) {
        double d2 = Double.MAX_VALUE;
        Goal[] goalArray = this.goals;
        int n5 = this.goals.length;
        for (int i2 = 0; i2 < n5; ++i2) {
            Goal goal = goalArray[i2];
            d2 = Math.min(d2, goal.heuristic(n2, n3, n4));
        }
        return d2;
    }

    @Override
    public double heuristic() {
        double d2 = Double.MAX_VALUE;
        Goal[] goalArray = this.goals;
        int n2 = this.goals.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            Goal goal = goalArray[i2];
            d2 = Math.min(d2, goal.heuristic());
        }
        return d2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        object = (GoalComposite)object;
        return Arrays.equals(this.goals, ((GoalComposite)object).goals);
    }

    public int hashCode() {
        return Arrays.hashCode(this.goals);
    }

    public String toString() {
        return "GoalComposite" + Arrays.toString(this.goals);
    }

    public Goal[] goals() {
        return this.goals;
    }
}

