/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FallingBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.IBaritone;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cb;
import baritone.cc;
import baritone.cd;
import baritone.fb;
import baritone.fw;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dc
extends cb {
    private int a;
    public boolean a = false;

    public dc(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        super(iBaritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[]{betterBlockPos2.above(2), betterBlockPos2.above(), betterBlockPos2}, betterBlockPos2.below());
    }

    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
        this.a = false;
    }

    @Override
    public final double a(ca ca2) {
        fw fw2 = new fw();
        dc.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z, this.b.x, this.b.z, fw2);
        if (fw2.b != this.b.y) {
            return 1000000.0;
        }
        return fw2.a;
    }

    @Override
    public final Set<BetterBlockPos> a() {
        return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)this.b.above()), (Object)((Object)this.b));
    }

    public static void a(ca ca2, int n2, int n3, int n4, int n5, int n6, fw fw2) {
        double d2;
        double d3;
        double d4;
        BlockState blockState = ca2.a(n5, n3 - 1, n6);
        double d5 = 0.0 + cc.a(ca2, n5, n3 - 1, n6, blockState, false);
        if (d4 >= 1000000.0) {
            return;
        }
        d5 += cc.a(ca2, n5, n3, n6, false);
        if (d3 >= 1000000.0) {
            return;
        }
        d5 += cc.a(ca2, n5, n3 + 1, n6, true);
        if (d2 >= 1000000.0) {
            return;
        }
        Block block = ca2.a(n2, n3 - 1, n4).getBlock();
        if (block == Blocks.LADDER || block == Blocks.VINE) {
            return;
        }
        BlockState blockState2 = ca2.a(n5, n3 - 2, n6);
        if (!cc.b(ca2, n5, n3 - 2, n6, blockState2)) {
            dc.a(ca2, n3, n5, n6, d5, blockState2, fw2);
            return;
        }
        if (blockState.getBlock() == Blocks.LADDER || blockState.getBlock() == Blocks.VINE) {
            return;
        }
        if (cc.b(ca2, blockState)) {
            return;
        }
        double d6 = 3.7062775075283763;
        if (block == Blocks.SOUL_SAND) {
            d6 = 7.4125550150567525;
        }
        fw2.a = n5;
        fw2.b = n3 - 1;
        fw2.c = n6;
        fw2.a = d5 += d6 + Math.max(FALL_N_BLOCKS_COST[1], 0.9265693768820937);
    }

    public static boolean a(ca ca2, int n2, int n3, int n4, double d2, BlockState blockState, fw fw2) {
        if (d2 != 0.0 && ca2.a(n3, n2 + 2, n4).getBlock() instanceof FallingBlock) {
            return false;
        }
        if (!cc.a(ca2, n3, n2 - 2, n4, blockState)) {
            return false;
        }
        double d3 = 0.0;
        int n5 = n2;
        int n6 = 3;
        int n7;
        while ((n7 = n2 - n6) >= ca2.a.getMinBuildHeight()) {
            boolean bl2 = n6 >= ca2.b;
            BlockState blockState2 = ca2.a(n3, n7, n4);
            int n8 = n6 - (n2 - n5);
            double d4 = 3.7062775075283763 + FALL_N_BLOCKS_COST[n8] + d2 + d3;
            if (bl2 && cc.d(blockState2)) {
                if (!cc.a(ca2, n3, n7, n4, blockState2)) {
                    return false;
                }
                if (ca2.j) {
                    return false;
                }
                if (cc.a(n3, n7, n4, blockState2, ca2.a)) {
                    return false;
                }
                if (!cc.c(ca2, n3, n7 - 1, n4)) {
                    return false;
                }
                fw2.a = n3;
                fw2.b = n7;
                fw2.c = n4;
                fw2.a = d4;
                return false;
            }
            if (bl2 && ca2.k && cc.e(blockState2)) {
                fw2.a = n3;
                fw2.b = n7;
                fw2.c = n4;
                fw2.a = d4;
                return false;
            }
            if (n8 <= 11 && (blockState2.getBlock() == Blocks.VINE || blockState2.getBlock() == Blocks.LADDER)) {
                d3 = d3 + FALL_N_BLOCKS_COST[n8 - 1] + 6.666666666666667;
                n5 = n7;
            } else if (!cc.a(ca2, n3, n7, n4, blockState2)) {
                if (!cc.b(ca2, n3, n7, n4, blockState2)) {
                    return false;
                }
                if (cc.c(blockState2)) {
                    return false;
                }
                if (bl2 && n8 <= ca2.c + 1) {
                    fw2.a = n3;
                    fw2.b = n7 + 1;
                    fw2.c = n4;
                    fw2.a = d4;
                    return false;
                }
                if (bl2 && ca2.b && n8 <= ca2.d + 1) {
                    fw2.a = n3;
                    fw2.b = n7 + 1;
                    fw2.c = n4;
                    fw2.a = d4 + ca2.a();
                    return true;
                }
                return false;
            }
            ++n6;
        }
        return false;
    }

    @Override
    public final cd a(cd object) {
        super.a((cd)object);
        if (((cd)object).a != MovementStatus.RUNNING) {
            return object;
        }
        Object object2 = ((cb)this).a.playerFeet();
        BlockPos blockPos = new BlockPos((this.b.getX() << 1) - ((cb)this).a.getX(), this.b.getY(), (this.b.getZ() << 1) - ((cb)this).a.getZ());
        if ((object2.equals((Object)this.b) || object2.equals(blockPos)) && (cc.e(((cb)this).a, this.b) || ((cb)this).a.player().position().y - (double)this.b.getY() < 0.5)) {
            cd cd2 = object;
            object = MovementStatus.SUCCESS;
            object2 = cd2;
            cd2.a = object;
            return object2;
        }
        if (this.b()) {
            double d2 = ((double)((cb)this).a.getX() + 0.5) * 0.17 + ((double)this.b.getX() + 0.5) * 0.83;
            double d3 = ((double)((cb)this).a.getZ() + 0.5) * 0.17 + ((double)this.b.getZ() + 0.5) * 0.83;
            ((cd)object).a(new cd.a(RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), new Vec3(d2, (double)this.b.getY(), d3), ((cb)this).a.playerRotations()).withPitch(((cb)this).a.playerRotations().getPitch()), false)).a(Input.MOVE_FORWARD, true);
            return object;
        }
        double d4 = ((cb)this).a.player().position().x - ((double)this.b.getX() + 0.5);
        double d5 = ((cb)this).a.player().position().z - ((double)this.b.getZ() + 0.5);
        double d6 = d4;
        double d7 = d5;
        double d8 = Math.sqrt(d6 * d6 + d7 * d7);
        double d9 = ((cb)this).a.player().position().x - ((double)((cb)this).a.getX() + 0.5);
        double d10 = ((cb)this).a.player().position().z - ((double)((cb)this).a.getZ() + 0.5);
        double d11 = d9;
        double d12 = d10;
        double d13 = Math.sqrt(d11 * d11 + d12 * d12);
        if (!object2.equals((Object)this.b) || d8 > 0.25) {
            if (this.a++ < 20 && d13 < 1.25) {
                cc.a(((cb)this).a, (cd)object, blockPos);
            } else {
                cc.a(((cb)this).a, (cd)object, (BlockPos)this.b);
            }
        }
        return object;
    }

    public final boolean b() {
        if (this.a) {
            return true;
        }
        BlockPos blockPos = this.b.subtract((Vec3i)((cb)this).a.below()).offset((Vec3i)this.b);
        if (this.c()) {
            return true;
        }
        for (int i2 = 0; i2 <= 2; ++i2) {
            if (!cc.b(fb.a(((cb)this).a, blockPos.above(i2)))) continue;
            return true;
        }
        return false;
    }

    public final boolean c() {
        BlockPos blockPos = this.b.subtract((Vec3i)((cb)this).a.below()).offset((Vec3i)this.b);
        return !cc.a(((cb)this).a, new BetterBlockPos(blockPos)) && cc.a(((cb)this).a, new BetterBlockPos(blockPos).above()) && cc.a(((cb)this).a, new BetterBlockPos(blockPos).above(2));
    }
}

