/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class bj
extends Command {
    public bj(a a2) {
        super(a2, "saveall");
    }

    @Override
    public final void execute(String string, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(0);
        this.ctx.worldData().getCachedWorld().save();
        this.logDirect("Saved");
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Saves Baritone's cache for this world";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The saveall command saves Baritone's world cache.", "", "Usage:", "> saveall");
    }
}

