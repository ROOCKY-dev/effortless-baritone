/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class bh
extends Command {
    public bh(a a2) {
        super(a2, "render");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(0);
        object = this.ctx.playerFeet();
        int n2 = (Integer)this.ctx.minecraft().options.renderDistance().get() + 1 << 4;
        this.ctx.minecraft().levelRenderer.setBlocksDirty(((BetterBlockPos)((Object)object)).x - n2, this.ctx.world().getMinBuildHeight(), ((BetterBlockPos)((Object)object)).z - n2, ((BetterBlockPos)((Object)object)).x + n2, this.ctx.world().getMaxBuildHeight(), ((BetterBlockPos)((Object)object)).z + n2);
        this.logDirect("Done");
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Fix glitched chunks";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The render command fixes glitched chunk rendering without having to reload all of them.", "", "Usage:", "> render");
    }
}

