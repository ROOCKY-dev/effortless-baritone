/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.AirBlock
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AirBlock;

public final class bn
extends Command {
    protected bn(a a2) {
        super(a2, "surface", "top");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        object = this.ctx.playerFeet();
        int n2 = this.ctx.world().getSeaLevel();
        int n3 = this.ctx.world().getHeight();
        if (object.getY() > n2 && this.ctx.world().getBlockState((BlockPos)((BetterBlockPos)((Object)object)).above()).getBlock() instanceof AirBlock) {
            this.logDirect("Already at surface");
            return;
        }
        for (n2 = Math.max(object.getY(), n2); n2 < n3; ++n2) {
            BetterBlockPos betterBlockPos = new BetterBlockPos(object.getX(), n2, object.getZ());
            if (this.ctx.world().getBlockState((BlockPos)betterBlockPos).getBlock() instanceof AirBlock || betterBlockPos.getY() <= object.getY()) continue;
            object = new GoalBlock(betterBlockPos.above());
            this.logDirect(String.format("Going to: %s", object.toString()));
            this.baritone.getCustomGoalProcess().setGoalAndPath((Goal)object);
            return;
        }
        this.logDirect("No higher location found");
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Used to get out of caves, mines, ...";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The surface/top command tells Baritone to head towards the closest surface-like area.", "", "This can be the surface or the highest available air space, depending on circumstances.", "", "Usage:", "> surface - Used to get out of caves, mines, ...", "> top - Used to get out of caves, mines, ...");
    }
}

