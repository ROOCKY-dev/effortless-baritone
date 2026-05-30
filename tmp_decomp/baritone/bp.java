/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalStrictDirection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class bp
extends Command {
    public bp(a a2) {
        super(a2, "tunnel");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(3);
        if (iArgConsumer.hasExactly(3)) {
            boolean bl2 = true;
            int n2 = Integer.parseInt(iArgConsumer.getArgs().get(0).getValue());
            int n3 = Integer.parseInt(iArgConsumer.getArgs().get(1).getValue());
            int n4 = Integer.parseInt(iArgConsumer.getArgs().get(2).getValue());
            if (n3 <= 0 || n2 < 2 || n4 <= 0 || n2 > this.ctx.world().getMaxBuildHeight()) {
                this.logDirect("Width and depth must at least be 1 block; Height must at least be 2 blocks, and cannot be greater than the build limit.");
                bl2 = false;
            }
            if (bl2) {
                --n2;
                Direction direction = this.ctx.player().getDirection();
                int n5 = --n3 % 2 == 0 ? 0 : 1;
                BlockPos blockPos = switch (direction) {
                    case Direction.EAST -> {
                        direction = new BlockPos(this.ctx.playerFeet().x, this.ctx.playerFeet().y, this.ctx.playerFeet().z - n3 / 2);
                        yield new BlockPos(this.ctx.playerFeet().x + n4, this.ctx.playerFeet().y + n2, this.ctx.playerFeet().z + n3 / 2 + n5);
                    }
                    case Direction.WEST -> {
                        direction = new BlockPos(this.ctx.playerFeet().x, this.ctx.playerFeet().y, this.ctx.playerFeet().z + n3 / 2 + n5);
                        yield new BlockPos(this.ctx.playerFeet().x - n4, this.ctx.playerFeet().y + n2, this.ctx.playerFeet().z - n3 / 2);
                    }
                    case Direction.NORTH -> {
                        direction = new BlockPos(this.ctx.playerFeet().x - n3 / 2, this.ctx.playerFeet().y, this.ctx.playerFeet().z);
                        yield new BlockPos(this.ctx.playerFeet().x + n3 / 2 + n5, this.ctx.playerFeet().y + n2, this.ctx.playerFeet().z - n4);
                    }
                    case Direction.SOUTH -> {
                        direction = new BlockPos(this.ctx.playerFeet().x + n3 / 2 + n5, this.ctx.playerFeet().y, this.ctx.playerFeet().z);
                        yield new BlockPos(this.ctx.playerFeet().x - n3 / 2, this.ctx.playerFeet().y + n2, this.ctx.playerFeet().z + n4);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
                };
                this.logDirect(String.format("Creating a tunnel %s block(s) high, %s block(s) wide, and %s block(s) deep", n2 + 1, n3 + 1, n4));
                this.baritone.getBuilderProcess().clearArea((BlockPos)direction, blockPos);
            }
            return;
        }
        object = new GoalStrictDirection(this.ctx.playerFeet(), this.ctx.player().getDirection());
        this.baritone.getCustomGoalProcess().setGoalAndPath((Goal)object);
        this.logDirect(String.format("Goal: %s", object.toString()));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Set a goal to tunnel in your current direction";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The tunnel command sets a goal that tells Baritone to mine completely straight in the direction that you're facing.", "", "Usage:", "> tunnel - No arguments, mines in a 1x2 radius.", "> tunnel <height> <width> <depth> - Tunnels in a user defined height, width and depth.");
    }
}

