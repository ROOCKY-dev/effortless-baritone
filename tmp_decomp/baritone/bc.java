/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import baritone.api.utils.BlockOptionalMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class bc
extends Command {
    public bc(a a2) {
        super(a2, "mine");
    }

    @Override
    public final void execute(String string, IArgConsumer iArgConsumer) {
        int n2 = iArgConsumer.getAsOrDefault(Integer.class, 0);
        iArgConsumer.requireMin(1);
        ArrayList<BlockOptionalMeta> arrayList = new ArrayList<BlockOptionalMeta>();
        while (iArgConsumer.hasAny()) {
            arrayList.add((BlockOptionalMeta)iArgConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
        }
        BaritoneAPI.getProvider().getWorldScanner().repack(this.ctx);
        this.logDirect(String.format("Mining %s", ((Object)arrayList).toString()));
        this.baritone.getMineProcess().mine(n2, arrayList.toArray(new BlockOptionalMeta[0]));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        iArgConsumer.getAsOrDefault(Integer.class, 0);
        while (iArgConsumer.has(2)) {
            iArgConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
        }
        return iArgConsumer.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
    }

    @Override
    public final String getShortDesc() {
        return "Mine some blocks";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The mine command allows you to tell Baritone to search for and mine individual blocks.", "", "The specified blocks can be ores, or any other block.", "", "Also see the legitMine settings (see #set l legitMine).", "", "Usage:", "> mine diamond_ore - Mines all diamonds it can find.");
    }
}

