/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.command.datatypes.RelativeGoal;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BlockOptionalMeta;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class ay
extends Command {
    protected ay(a a2) {
        super(a2, "goto");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        if (iArgConsumer.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
            iArgConsumer.requireMax(3);
            object = this.ctx.playerFeet();
            object = (Goal)iArgConsumer.getDatatypePost(RelativeGoal.INSTANCE, object);
            this.logDirect(String.format("Going to: %s", object.toString()));
            this.baritone.getCustomGoalProcess().setGoalAndPath((Goal)object);
            return;
        }
        iArgConsumer.requireMax(1);
        object = (BlockOptionalMeta)iArgConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
        this.baritone.getGetToBlockProcess().getToBlock((BlockOptionalMeta)object);
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(1);
        return iArgConsumer.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
    }

    @Override
    public final String getShortDesc() {
        return "Go to a coordinate or block";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The goto command tells Baritone to head towards a given goal or block.", "", "Wherever a coordinate is expected, you can use ~ just like in regular Minecraft commands. Or, you can just use regular numbers.", "", "Usage:", "> goto <block> - Go to a block, wherever it is in the world", "> goto <y> - Go to a Y level", "> goto <x> <z> - Go to an X,Z position", "> goto <x> <y> <z> - Go to an X,Y,Z position");
    }
}

