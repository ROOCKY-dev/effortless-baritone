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

public final class bb
extends Command {
    public bb(a a2) {
        super(a2, "litematica");
    }

    @Override
    public final void execute(String string, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(1);
        int n2 = iArgConsumer.hasAny() ? iArgConsumer.getAs(Integer.class) - 1 : 0;
        this.baritone.getBuilderProcess().buildOpenLitematic(n2);
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Builds the loaded schematic";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Build a schematic currently open in Litematica.", "", "Usage:", "> litematica", "> litematica <#>");
    }
}

