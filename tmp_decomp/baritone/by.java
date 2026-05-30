/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class by {
    public final int a;
    public final int b;
    public final int c;
    public final double a;
    public double b = 1000000.0;
    public double c;
    public by a = null;
    public int d;

    public by(int n2, int n3, int n4, Goal goal) {
        this.a = goal.heuristic(n2, n3, n4);
        if (Double.isNaN(this.a)) {
            throw new IllegalStateException(String.format("%s calculated implausible heuristic NaN at %s %s %s", goal, SettingsUtil.maybeCensor(n2), SettingsUtil.maybeCensor(n3), SettingsUtil.maybeCensor(n4)));
        }
        this.d = -1;
        this.a = n2;
        this.b = n3;
        this.c = n4;
    }

    public final int hashCode() {
        return (int)BetterBlockPos.longHash(this.a, this.b, this.c);
    }

    public final boolean equals(Object object) {
        object = (by)object;
        return this.a == ((by)object).a && this.b == ((by)object).b && this.c == ((by)object).c;
    }
}

