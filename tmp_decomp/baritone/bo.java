/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class bo
extends Command {
    public bo(a a2) {
        super(a2, "thisway", "forward");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        iArgConsumer.requireExactly(1);
        object = GoalXZ.fromDirection(this.ctx.playerFeetAsVec(), this.ctx.player().getYHeadRot(), iArgConsumer.getAs(Double.class));
        this.baritone.getCustomGoalProcess().setGoal((Goal)object);
        this.logDirect(String.format("Goal: %s", object));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Travel in your current direction";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Creates a GoalXZ some amount of blocks in the direction you're currently looking", "", "Usage:", "> thisway <distance> - makes a GoalXZ distance blocks in front of you");
    }
}

