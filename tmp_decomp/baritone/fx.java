/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.dk;
import baritone.fb;
import net.minecraft.core.BlockPos;

public abstract class fx
implements IPath {
    @Override
    public /* synthetic */ IPath staticCutoff(Goal object) {
        Goal goal = object;
        object = this;
        int n2 = (Integer)BaritoneAPI.getSettings().pathCutoffMinimumLength.value;
        if (object.length() < n2) {
            return object;
        }
        if (goal == null || goal.isInGoal(object.getDest())) {
            return object;
        }
        double d2 = (Double)BaritoneAPI.getSettings().pathCutoffFactor.value;
        int n3 = (int)((double)(object.length() - n2) * d2) + n2 - 1;
        return new dk((fx)object, n3);
    }

    @Override
    public /* synthetic */ IPath cutoffAtLoadedChunks(Object object) {
        Object object2 = object;
        object = this;
        if (((Boolean)a.a().cutoffAtLoadBoundary.value).booleanValue()) {
            object2 = (fb)object2;
            for (int i2 = 0; i2 < object.positions().size(); ++i2) {
                BlockPos blockPos = object.positions().get(i2);
                if (((fb)object2).a(blockPos.getX(), blockPos.getZ())) continue;
                return new dk((fx)object, i2);
            }
        }
        return object;
    }
}

