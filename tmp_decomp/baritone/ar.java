/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeGoalXZ;
import baritone.api.pathing.goals.GoalXZ;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class ar
extends Command {
    public ar(a a2) {
        super(a2, "explore");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasAny()) {
            iArgConsumer.requireExactly(2);
        } else {
            iArgConsumer.requireMax(0);
        }
        object = iArgConsumer.hasAny() ? (GoalXZ)iArgConsumer.getDatatypePost(RelativeGoalXZ.INSTANCE, this.ctx.playerFeet()) : new GoalXZ(this.ctx.playerFeet());
        this.baritone.getExploreProcess().explore(((GoalXZ)object).getX(), ((GoalXZ)object).getZ());
        this.logDirect(String.format("Exploring from %s", ((GoalXZ)object).toString()));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasAtMost(2)) {
            return iArgConsumer.tabCompleteDatatype(RelativeGoalXZ.INSTANCE);
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Explore things";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Tell Baritone to explore randomly. If you used explorefilter before this, it will be applied.", "", "Usage:", "> explore - Explore from your current position.", "> explore <x> <z> - Explore from the specified X and Z position.");
    }
}

