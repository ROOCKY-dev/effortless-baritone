/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.command.datatypes.RelativeGoal;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class ax
extends Command {
    public ax(a a2) {
        super(a2, "goal");
    }

    @Override
    public final void execute(String object, IArgConsumer object2) {
        object = this.baritone.getCustomGoalProcess();
        if (object2.hasAny() && Arrays.asList("reset", "clear", "none").contains(object2.peekString())) {
            object2.requireMax(1);
            if (object.getGoal() != null) {
                object.setGoal(null);
                this.logDirect("Cleared goal");
                return;
            }
            this.logDirect("There was no goal to clear");
            return;
        }
        object2.requireMax(3);
        BetterBlockPos betterBlockPos = this.ctx.playerFeet();
        object2 = (Goal)object2.getDatatypePost(RelativeGoal.INSTANCE, betterBlockPos);
        object.setGoal((Goal)object2);
        this.logDirect(String.format("Goal: %s", object2.toString()));
    }

    @Override
    public final Stream<String> tabComplete(String object, IArgConsumer iArgConsumer) {
        object = new TabCompleteHelper();
        if (iArgConsumer.hasExactlyOne()) {
            ((TabCompleteHelper)object).append("reset", "clear", "none", "~");
        } else if (iArgConsumer.hasAtMost(3)) {
            while (iArgConsumer.has(2) && iArgConsumer.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
                iArgConsumer.get();
                if (iArgConsumer.has(2)) continue;
                ((TabCompleteHelper)object).append("~");
            }
        }
        return ((TabCompleteHelper)object).filterPrefix(iArgConsumer.getString()).stream();
    }

    @Override
    public final String getShortDesc() {
        return "Set or clear the goal";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The goal command allows you to set or clear Baritone's goal.", "", "Wherever a coordinate is expected, you can use ~ just like in regular Minecraft commands. Or, you can just use regular numbers.", "", "Usage:", "> goal - Set the goal to your current position", "> goal <reset/clear/none> - Erase the goal", "> goal <y> - Set the goal to a Y level", "> goal <x> <z> - Set the goal to an X,Z position", "> goal <x> <y> <z> - Set the goal to an X,Y,Z position");
    }
}

