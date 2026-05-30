/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.process.IBaritoneProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class aj
extends Command {
    public aj(a a2) {
        super(a2, "eta");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(0);
        if ((IBaritoneProcess)this.baritone.getPathingControlManager().mostRecentInControl().orElse(null) == null) {
            throw new CommandInvalidStateException("No process in control");
        }
        object = this.baritone.getPathingBehavior();
        double d2 = object.ticksRemainingInSegment().orElse(Double.NaN);
        double d3 = object.estimatedTicksToGoal().orElse(Double.NaN);
        this.logDirect(String.format("Next segment: %.1fs (%.0f ticks)\nGoal: %.1fs (%.0f ticks)", d2 / 20.0, d2, d3 / 20.0, d3));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "View the current ETA";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The ETA command provides information about the estimated time until the next segment.", "and the goal", "", "Be aware that the ETA to your goal is really unprecise", "", "Usage:", "> eta - View ETA, if present");
    }
}

