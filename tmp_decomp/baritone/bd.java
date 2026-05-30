/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class bd
extends Command {
    public bd(a a2) {
        super(a2, "path");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        object = this.baritone.getCustomGoalProcess();
        iArgConsumer.requireMax(0);
        BaritoneAPI.getProvider().getWorldScanner().repack(this.ctx);
        object.path();
        this.logDirect("Now pathing");
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Start heading towards the goal";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The path command tells Baritone to head towards the current goal.", "", "Usage:", "> path - Start the pathing.");
    }
}

