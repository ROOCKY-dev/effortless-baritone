/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalInverted;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class ba
extends Command {
    public ba(a a2) {
        super(a2, "invert");
    }

    @Override
    public final void execute(String object, IArgConsumer object2) {
        object2.requireMax(0);
        object = this.baritone.getCustomGoalProcess();
        object2 = object.getGoal();
        if (object2 == null) {
            throw new CommandInvalidStateException("No goal");
        }
        object2 = object2 instanceof GoalInverted ? ((GoalInverted)object2).origin : new GoalInverted((Goal)object2);
        object.setGoalAndPath((Goal)object2);
        this.logDirect(String.format("Goal: %s", object2.toString()));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Run away from the current goal";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The invert command tells Baritone to head away from the current goal rather than towards it.", "", "Usage:", "> invert - Invert the current goal.");
    }
}

