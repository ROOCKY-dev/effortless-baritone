/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.IBaritone;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.ca;
import baritone.cb;
import baritone.cc;
import baritone.cd;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class de
extends cb {
    private int a = 0;

    public de(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        super(iBaritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[]{betterBlockPos2});
    }

    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }

    @Override
    public final double a(ca ca2) {
        return de.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z);
    }

    @Override
    public final Set<BetterBlockPos> a() {
        return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)this.b));
    }

    public static double a(ca ca2, int n2, int n3, int n4) {
        if (!ca2.n) {
            return 1000000.0;
        }
        if (!cc.c(ca2, n2, n3 - 2, n4)) {
            return 1000000.0;
        }
        BlockState blockState = ca2.a(n2, n3 - 1, n4);
        Block block = blockState.getBlock();
        if (block == Blocks.LADDER || block == Blocks.VINE) {
            return 6.666666666666667;
        }
        return FALL_N_BLOCKS_COST[1] + cc.a(ca2, n2, n3 - 1, n4, blockState, false);
    }

    @Override
    public final cd a(cd object) {
        super.a((cd)object);
        if (object.a != MovementStatus.RUNNING) {
            return object;
        }
        if (((cb)this).a.playerFeet().equals((Object)this.b)) {
            cd cd2 = object;
            object = MovementStatus.SUCCESS;
            cd cd3 = cd2;
            cd2.a = object;
            return cd3;
        }
        if (!this.a()) {
            Object object2 = object;
            object = MovementStatus.UNREACHABLE;
            Object object3 = object2;
            object2.a = object;
            return object3;
        }
        double d2 = ((cb)this).a.player().position().x - ((double)this.b.getX() + 0.5);
        double d3 = ((cb)this).a.player().position().z - ((double)this.b.getZ() + 0.5);
        double d4 = d2;
        double d5 = d3;
        double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        if (this.a++ < 10 && d6 < 0.2) {
            return object;
        }
        cc.a(((cb)this).a, object, (BlockPos)((cb)this).a[0]);
        return object;
    }
}

