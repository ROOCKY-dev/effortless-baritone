/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.command.datatypes.RelativeGoalBlock;
import baritone.api.command.datatypes.RelativeGoalXZ;
import baritone.api.command.datatypes.RelativeGoalYLevel;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;

public enum RelativeGoal implements IDatatypePost<Goal, BetterBlockPos>
{
    INSTANCE;


    @Override
    public final Goal apply(IDatatypeContext object, BetterBlockPos betterBlockPos) {
        Goal goal;
        if (betterBlockPos == null) {
            betterBlockPos = BetterBlockPos.ORIGIN;
        }
        if ((goal = (GoalBlock)(object = object.getConsumer()).peekDatatypePostOrNull(RelativeGoalBlock.INSTANCE, betterBlockPos)) != null) {
            return goal;
        }
        goal = (GoalXZ)object.peekDatatypePostOrNull(RelativeGoalXZ.INSTANCE, betterBlockPos);
        if (goal != null) {
            return goal;
        }
        if ((object = (GoalYLevel)object.peekDatatypePostOrNull(RelativeGoalYLevel.INSTANCE, betterBlockPos)) != null) {
            return object;
        }
        return new GoalBlock(betterBlockPos);
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext iDatatypeContext) {
        return iDatatypeContext.getConsumer().tabCompleteDatatype(RelativeCoordinate.INSTANCE);
    }
}

