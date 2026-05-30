/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;
import net.minecraft.util.Mth;

public enum RelativeGoalYLevel implements IDatatypePost<GoalYLevel, BetterBlockPos>
{
    INSTANCE;


    @Override
    public final GoalYLevel apply(IDatatypeContext iDatatypeContext, BetterBlockPos betterBlockPos) {
        if (betterBlockPos == null) {
            betterBlockPos = BetterBlockPos.ORIGIN;
        }
        return new GoalYLevel(Mth.floor((double)((Double)iDatatypeContext.getConsumer().getDatatypePost(RelativeCoordinate.INSTANCE, Double.valueOf(betterBlockPos.y)))));
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext object) {
        if ((object = object.getConsumer()).hasAtMost(1)) {
            return object.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}

