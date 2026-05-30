/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.a;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cb;
import baritone.cc;
import baritone.cd;
import baritone.fb;
import baritone.fw;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dd
extends cb {
    private static final double a = Math.sqrt(2.0);

    public dd(IBaritone iBaritone, BetterBlockPos betterBlockPos, Direction direction, Direction direction2, int n2) {
        BetterBlockPos betterBlockPos2 = betterBlockPos;
        this(iBaritone, betterBlockPos2, betterBlockPos2.relative(direction), betterBlockPos.relative(direction2), direction2, n2);
    }

    private dd(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2, BetterBlockPos betterBlockPos3, Direction direction, int n2) {
        this(iBaritone, betterBlockPos, betterBlockPos2.relative(direction).above(n2), betterBlockPos2, betterBlockPos3);
    }

    private dd(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2, BetterBlockPos betterBlockPos3, BetterBlockPos betterBlockPos4) {
        super(iBaritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[]{betterBlockPos3, betterBlockPos3.above(), betterBlockPos4, betterBlockPos4.above(), betterBlockPos2, betterBlockPos2.above()});
    }

    @Override
    public final boolean b(cd cd2) {
        cd2 = ((cb)this).a.player();
        double d2 = cd2.position().x;
        double d3 = cd2.position().y - 1.0;
        double d4 = cd2.position().z;
        if (((cb)this).a.playerFeet().equals((Object)((cb)this).a)) {
            return true;
        }
        if (cc.b(((cb)this).a, new BlockPos(((cb)this).a.x, ((cb)this).a.y - 1, this.b.z)) && cc.b(((cb)this).a, new BlockPos(this.b.x, ((cb)this).a.y - 1, ((cb)this).a.z))) {
            return true;
        }
        if (((cb)this).a.playerFeet().equals((Object)new BetterBlockPos(((cb)this).a.x, ((cb)this).a.y, this.b.z)) || ((cb)this).a.playerFeet().equals((Object)new BetterBlockPos(this.b.x, ((cb)this).a.y, ((cb)this).a.z))) {
            return cc.b(((cb)this).a, new BetterBlockPos(d2 + 0.25, d3, d4 + 0.25)) || cc.b(((cb)this).a, new BetterBlockPos(d2 + 0.25, d3, d4 - 0.25)) || cc.b(((cb)this).a, new BetterBlockPos(d2 - 0.25, d3, d4 + 0.25)) || cc.b(((cb)this).a, new BetterBlockPos(d2 - 0.25, d3, d4 - 0.25));
        }
        return true;
    }

    @Override
    public final double a(ca ca2) {
        fw fw2 = new fw();
        dd.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z, this.b.x, this.b.z, fw2);
        if (fw2.b != this.b.y) {
            return 1000000.0;
        }
        return fw2.a;
    }

    @Override
    public final Set<BetterBlockPos> a() {
        BetterBlockPos betterBlockPos = new BetterBlockPos(((cb)this).a.x, ((cb)this).a.y, this.b.z);
        BetterBlockPos betterBlockPos2 = new BetterBlockPos(this.b.x, ((cb)this).a.y, ((cb)this).a.z);
        if (this.b.y < ((cb)this).a.y) {
            return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)this.b.above()), (Object)((Object)betterBlockPos), (Object)((Object)betterBlockPos2), (Object)((Object)this.b), (Object)((Object)betterBlockPos.below()), (Object[])new BetterBlockPos[]{betterBlockPos2.below()});
        }
        if (this.b.y > ((cb)this).a.y) {
            return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)((cb)this).a.above()), (Object)((Object)betterBlockPos), (Object)((Object)betterBlockPos2), (Object)((Object)this.b), (Object)((Object)betterBlockPos.above()), (Object[])new BetterBlockPos[]{betterBlockPos2.above()});
        }
        return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)this.b), (Object)((Object)betterBlockPos), (Object)((Object)betterBlockPos2));
    }

    public static void a(ca ca2, int n2, int n3, int n4, int n5, int n6, fw fw2) {
        BlockState blockState;
        BlockState blockState2;
        if (!cc.a(ca2, n5, n3 + 1, n6)) {
            return;
        }
        BlockState blockState3 = ca2.a(n5, n3, n6);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (!cc.a(ca2, n5, n3, n6, blockState3)) {
            bl2 = true;
            if (!(ca2.m && cc.a(ca2, n2, n3 + 2, n4) && cc.b(ca2, n5, n3, n6, blockState3) && cc.a(ca2, n5, n3 + 2, n6))) {
                return;
            }
            blockState2 = blockState3;
            blockState = ca2.a(n2, n3 - 1, n4);
        } else {
            blockState2 = ca2.a(n5, n3 - 1, n6);
            blockState = ca2.a(n2, n3 - 1, n4);
            bl4 = cc.c(ca2, n2, n3 - 1, n4, blockState) && cc.b(ca2, blockState2);
            if (!bl4 && !cc.b(ca2, n5, n3 - 1, n6, blockState2)) {
                bl3 = true;
                if (!(ca2.l && cc.c(ca2, n5, n3 - 2, n6) && cc.a(ca2, n5, n3 - 1, n6, blockState2))) {
                    return;
                }
            }
            bl4 &= !ca2.j;
        }
        double d2 = 4.63284688441047;
        if (blockState2.getBlock() == Blocks.SOUL_SAND) {
            d2 = 6.949270326615705;
        } else if (!bl4 && blockState2.getBlock() == Blocks.WATER) {
            d2 = 4.63284688441047 + ca2.f * a;
        }
        blockState = blockState.getBlock();
        if (blockState == Blocks.LADDER || blockState == Blocks.VINE) {
            return;
        }
        if (blockState == Blocks.SOUL_SAND) {
            d2 += 2.316423442205235;
        }
        if ((blockState = ca2.a(n2, n3 - 1, n6)).getBlock() == Blocks.MAGMA_BLOCK || cc.e(blockState)) {
            return;
        }
        blockState = ca2.a(n5, n3 - 1, n4);
        if (blockState.getBlock() == Blocks.MAGMA_BLOCK || cc.e(blockState)) {
            return;
        }
        boolean bl5 = false;
        blockState2 = ca2.a(n2, n3, n4);
        Block block = blockState2.getBlock();
        if (cc.d(blockState2) || cc.d(blockState3)) {
            if (bl2) {
                return;
            }
            d2 = ca2.b;
            bl5 = true;
        }
        blockState3 = ca2.a(n2, n3, n6);
        blockState2 = ca2.a(n5, n3, n4);
        if (bl2) {
            boolean bl6 = cc.a(ca2, n2, n3 + 2, n6);
            boolean bl7 = cc.a(ca2, n2, n3 + 1, n6);
            boolean bl8 = cc.a(ca2, n2, n3, n6, blockState3);
            boolean bl9 = cc.a(ca2, n5, n3 + 2, n4);
            bl2 = cc.a(ca2, n5, n3 + 1, n4);
            boolean bl10 = cc.a(ca2, n5, n3, n4, blockState2);
            if ((!bl6 || !bl7 || !bl8) && (!bl9 || !bl2 || !bl10) || cc.b(blockState3) || cc.b(blockState2) || bl6 && bl7 && cc.b(ca2, n2, n3, n6, blockState3) || bl9 && bl2 && cc.b(ca2, n5, n3, n4, blockState2) || !bl6 && bl7 && bl8 || !bl9 && bl2 && bl10) {
                return;
            }
            fw2.a = d2 * a + JUMP_ONE_BLOCK_COST;
            fw2.a = n5;
            fw2.c = n6;
            fw2.b = n3 + 1;
            return;
        }
        double d3 = cc.a(ca2, n2, n3, n6, blockState3, false);
        double d4 = cc.a(ca2, n5, n3, n4, blockState2, false);
        if (d3 != 0.0 && d4 != 0.0) {
            return;
        }
        BlockState blockState4 = ca2.a(n2, n3 + 1, n6);
        if ((d3 += cc.a(ca2, n2, n3 + 1, n6, blockState4, true)) != 0.0 && d4 != 0.0) {
            return;
        }
        BlockState blockState5 = ca2.a(n5, n3 + 1, n4);
        if (d3 == 0.0 && (cc.b(blockState2) && blockState2.getBlock() != Blocks.WATER || cc.b(blockState5))) {
            return;
        }
        if (d3 != 0.0 && (d4 += cc.a(ca2, n5, n3 + 1, n4, blockState5, true)) != 0.0) {
            return;
        }
        if (d4 == 0.0 && (cc.b(blockState3) && blockState3.getBlock() != Blocks.WATER || cc.b(blockState4))) {
            return;
        }
        if (d3 != 0.0 || d4 != 0.0) {
            d2 *= a - 0.001;
            if (block == Blocks.LADDER || block == Blocks.VINE) {
                return;
            }
        } else if (ca2.d && !bl5) {
            d2 *= 0.7692444761225944;
        }
        fw2.a = d2 * a;
        if (bl3) {
            fw2.a += Math.max(FALL_N_BLOCKS_COST[1], 0.9265693768820937);
            fw2.b = n3 - 1;
        } else {
            fw2.b = n3;
        }
        fw2.a = n5;
        fw2.c = n6;
    }

    @Override
    public final cd a(cd object) {
        boolean bl2;
        block8: {
            super.a((cd)object);
            if (((cd)object).a != MovementStatus.RUNNING) {
                return object;
            }
            if (((cb)this).a.playerFeet().equals((Object)this.b)) {
                cd cd2 = object;
                object = MovementStatus.SUCCESS;
                cd cd3 = cd2;
                cd2.a = object;
                return cd3;
            }
            if (!(this.a() || cc.e(((cb)this).a, ((cb)this).a) && this.b().contains((Object)((cb)this).a.playerFeet().above()))) {
                Object object2 = object;
                object = MovementStatus.UNREACHABLE;
                Object object3 = object2;
                object2.a = object;
                return object3;
            }
            if (this.b.y > ((cb)this).a.y && ((cb)this).a.player().position().y < (double)((cb)this).a.y + 0.1 && ((cb)this).a.player().horizontalCollision) {
                ((cd)object).a(Input.JUMP, true);
            }
            dd dd2 = this;
            if (cc.e(((cb)dd2).a, ((cb)dd2).a.playerFeet()) && !((Boolean)baritone.a.a().sprintInWater.value).booleanValue()) {
                bl2 = false;
            } else {
                for (int i2 = 0; i2 < 4; ++i2) {
                    if (cc.a(((cb)dd2).a, ((cb)dd2).a[i2])) continue;
                    bl2 = false;
                    break block8;
                }
                bl2 = true;
            }
        }
        if (bl2) {
            ((cd)object).a(Input.SPRINT, true);
        }
        cc.a(((cb)this).a, (cd)object, (BlockPos)this.b);
        return object;
    }

    @Override
    public final boolean a(cd cd2) {
        return true;
    }

    @Override
    public final List<BlockPos> a(fb fb2) {
        if (((cb)this).a != null) {
            return ((cb)this).a;
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        for (int i2 = 4; i2 < 6; ++i2) {
            if (cc.a(fb2, ((cb)this).a[i2].x, ((cb)this).a[i2].y, ((cb)this).a[i2].z)) continue;
            arrayList.add(((cb)this).a[i2]);
        }
        ((cb)this).a = arrayList;
        return arrayList;
    }

    @Override
    public final List<BlockPos> c(fb fb2) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        ArrayList<BetterBlockPos> arrayList = new ArrayList<BetterBlockPos>();
        for (int i2 = 0; i2 < 4; ++i2) {
            if (cc.a(fb2, ((cb)this).a[i2].x, ((cb)this).a[i2].y, ((cb)this).a[i2].z)) continue;
            arrayList.add(((cb)this).a[i2]);
        }
        this.b = arrayList;
        return this.b;
    }
}

