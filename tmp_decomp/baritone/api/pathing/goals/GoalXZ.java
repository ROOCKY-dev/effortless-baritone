/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec3
 */
package baritone.api.pathing.goals;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class GoalXZ
implements Goal {
    private static final double SQRT_2 = Math.sqrt(2.0);
    private final int x;
    private final int z;

    public GoalXZ(int n2, int n3) {
        this.x = n2;
        this.z = n3;
    }

    public GoalXZ(BetterBlockPos betterBlockPos) {
        this.x = betterBlockPos.x;
        this.z = betterBlockPos.z;
    }

    @Override
    public boolean isInGoal(int n2, int n3, int n4) {
        return n2 == this.x && n4 == this.z;
    }

    @Override
    public double heuristic(int n2, int n3, int n4) {
        n3 = n4 - this.z;
        return GoalXZ.calculate(n2 -= this.x, n3);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        object = (GoalXZ)object;
        return this.x == ((GoalXZ)object).x && this.z == ((GoalXZ)object).z;
    }

    public int hashCode() {
        return (1204478626 + this.x) * -1331679453 + this.z;
    }

    public String toString() {
        return String.format("GoalXZ{x=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.z));
    }

    public static double calculate(double d2, double d3) {
        double d4;
        double d5;
        double d6;
        double d7 = Math.abs(d2);
        if (d7 < (d6 = Math.abs(d3))) {
            d5 = d6 - d7;
            d4 = d7;
        } else {
            d5 = d7 - d6;
            d4 = d6;
        }
        return (d4 * SQRT_2 + d5) * (Double)BaritoneAPI.getSettings().costHeuristic.value;
    }

    public static GoalXZ fromDirection(Vec3 vec3, float f2, double d2) {
        f2 = (float)Math.toRadians(f2);
        double d3 = vec3.x - (double)Mth.sin((float)f2) * d2;
        double d4 = vec3.z + (double)Mth.cos((float)f2) * d2;
        return new GoalXZ(Mth.floor((double)d3), Mth.floor((double)d4));
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }
}

