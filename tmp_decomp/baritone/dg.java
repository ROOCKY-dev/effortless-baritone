/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.WaterFluid
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
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.WaterFluid;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dg
extends cb {
    private static final BetterBlockPos[] b = new BetterBlockPos[0];
    private final Direction a;
    private final int a;
    private final boolean a;

    private dg(IBaritone iBaritone, BetterBlockPos betterBlockPos, int n2, Direction direction, boolean bl2) {
        BetterBlockPos betterBlockPos2 = betterBlockPos;
        super(iBaritone, betterBlockPos2, betterBlockPos2.relative(direction, n2).above(bl2 ? 1 : 0), b, betterBlockPos.relative(direction, n2).below(bl2 ? 0 : 1));
        this.a = direction;
        this.a = n2;
        this.a = bl2;
    }

    public static dg a(ca ca2, BetterBlockPos betterBlockPos, Direction direction) {
        fw fw2 = new fw();
        dg.a(ca2, betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, direction, fw2);
        int n2 = Math.abs(fw2.a - betterBlockPos.x) + Math.abs(fw2.c - betterBlockPos.z);
        return new dg(ca2.a, betterBlockPos, n2, direction, fw2.b > betterBlockPos.y);
    }

    public static void a(ca ca2, int n2, int n3, int n4, Direction direction, fw fw2) {
        BlockState blockState;
        int n5;
        int n6;
        int n7;
        if (!ca2.f) {
            return;
        }
        if (!ca2.h && n3 >= ca2.a.getMaxBuildHeight()) {
            return;
        }
        int n8 = direction.getStepX();
        if (!cc.b(ca2, n2 + n8, n3, n4 + (n7 = direction.getStepZ()))) {
            return;
        }
        BlockState blockState2 = ca2.a(n2 + n8, n3 - 1, n4 + n7);
        if (cc.b(ca2, n2 + n8, n3 - 1, n4 + n7, blockState2)) {
            return;
        }
        if (cc.b(blockState2) && !(blockState2.getFluidState().getType() instanceof WaterFluid)) {
            return;
        }
        if (!cc.b(ca2, n2 + n8, n3 + 1, n4 + n7)) {
            return;
        }
        if (!cc.b(ca2, n2 + n8, n3 + 2, n4 + n7)) {
            return;
        }
        if (!cc.b(ca2, n2, n3 + 2, n4)) {
            return;
        }
        blockState2 = ca2.a(n2, n3 - 1, n4);
        if (blockState2.getBlock() == Blocks.VINE || blockState2.getBlock() == Blocks.LADDER || blockState2.getBlock() instanceof StairBlock || cc.c(blockState2)) {
            return;
        }
        if (ca2.j && !blockState2.getFluidState().isEmpty()) {
            return;
        }
        if (!ca2.a(n2, n3, n4).getFluidState().isEmpty()) {
            return;
        }
        int n9 = blockState2.getBlock() == Blocks.SOUL_SAND ? 2 : (ca2.d ? 4 : 3);
        int n10 = 1;
        int n11 = 2;
        while (n11 <= n9 && cc.b(ca2, n6 = n2 + n8 * n11, n3 + 1, n5 = n4 + n7 * n11) && cc.b(ca2, n6, n3 + 2, n5)) {
            blockState = ca2.a.a(n6, n3, n5);
            if (!cc.a(ca2, blockState)) {
                if (n11 > 3 || !ca2.i || !ca2.d || !cc.b(ca2, n6, n3, n5, blockState) || !dg.d(ca2.a, n6 + n8, n3 + 1, n5 + n7)) break;
                fw2.a = n6;
                fw2.b = n3 + 1;
                fw2.c = n5;
                fw2.a = (double)n11 * 3.563791874554526 + ca2.e;
                return;
            }
            BlockState blockState3 = ca2.a.a(n6, n3 - 1, n5);
            if (blockState3.getBlock() != Blocks.FARMLAND && cc.b(ca2, n6, n3 - 1, n5, blockState3) || Math.min(16, ca2.a + 2) >= n11 && cc.b(ca2, blockState3)) {
                if (!dg.d(ca2.a, n6 + n8, n3, n5 + n7)) break;
                fw2.a = n6;
                fw2.b = n3;
                fw2.c = n5;
                fw2.a = dg.a(n11) + ca2.e;
                return;
            }
            if (!cc.b(ca2, n6, n3 + 3, n5)) break;
            n10 = n11++;
        }
        if (!ca2.g) {
            return;
        }
        for (n11 = n10; n11 > 1; --n11) {
            n6 = n2 + n11 * n8;
            n5 = n4 + n11 * n7;
            blockState = ca2.a(n6, n3 - 1, n5);
            double d2 = ca2.a(n6, n3 - 1, n5, blockState);
            if (d2 >= 1000000.0 || !cc.a(n6, n5, blockState, ca2.a) || !dg.d(ca2.a, n6 + n8, n3, n5 + n7)) continue;
            for (n9 = 0; n9 < 5; ++n9) {
                n10 = n6 + a[n9].getStepX();
                int n12 = n3 - 1 + a[n9].getStepY();
                int n13 = n5 + a[n9].getStepZ();
                if (n10 == n6 - n8 && n13 == n5 - n7 || !cc.c(ca2.a, n10, n12, n13)) continue;
                fw2.a = n6;
                fw2.b = n3;
                fw2.c = n5;
                fw2.a = dg.a(n11) + d2 + ca2.e;
                return;
            }
        }
    }

    private static boolean d(fb fb2, int n2, int n3, int n4) {
        return !cc.b(fb2.a(n2, n3, n4)) && !cc.b(fb2.a(n2, n3 + 1, n4));
    }

    private static double a(int n2) {
        switch (n2) {
            case 2: {
                return 9.26569376882094;
            }
            case 3: {
                return 13.89854065323141;
            }
            case 4: {
                return 14.255167498218103;
            }
        }
        throw new IllegalStateException("LOL " + n2);
    }

    @Override
    public final double a(ca ca2) {
        fw fw2 = new fw();
        dg.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z, this.a, fw2);
        if (fw2.a != ((cb)this).b.x || fw2.b != ((cb)this).b.y || fw2.c != ((cb)this).b.z) {
            return 1000000.0;
        }
        return fw2.a;
    }

    @Override
    public final Set<BetterBlockPos> a() {
        HashSet<BetterBlockPos> hashSet = new HashSet<BetterBlockPos>();
        for (int i2 = 0; i2 <= this.a; ++i2) {
            for (int i3 = 0; i3 < 2; ++i3) {
                hashSet.add(((cb)this).a.relative(this.a, i2).above(i3));
            }
        }
        return hashSet;
    }

    @Override
    public final boolean b(cd cd2) {
        return cd2.a != MovementStatus.RUNNING;
    }

    @Override
    public final cd a(cd cd2) {
        super.a(cd2);
        if (cd2.a != MovementStatus.RUNNING) {
            return cd2;
        }
        if (((cb)this).a.playerFeet().y < ((cb)this).a.y) {
            this.logDebug("sorry");
            MovementStatus movementStatus = MovementStatus.UNREACHABLE;
            cd cd3 = cd2;
            cd2.a = movementStatus;
            return cd3;
        }
        if (this.a >= 4 || this.a) {
            cd2.a(Input.SPRINT, true);
        }
        cc.a(((cb)this).a, cd2, (BlockPos)((cb)this).b);
        if (((cb)this).a.playerFeet().equals((Object)((cb)this).b)) {
            Object object = fb.a(((cb)this).a, ((cb)this).b);
            if (object == Blocks.VINE || object == Blocks.LADDER) {
                MovementStatus movementStatus = MovementStatus.SUCCESS;
                object = cd2;
                cd2.a = movementStatus;
                return object;
            }
            if (((cb)this).a.player().position().y - (double)((cb)this).a.playerFeet().getY() < 0.094) {
                cd2.a = MovementStatus.SUCCESS;
            }
        } else if (!((cb)this).a.playerFeet().equals((Object)((cb)this).a)) {
            if (((cb)this).a.playerFeet().equals((Object)((cb)this).a.relative(this.a)) || ((cb)this).a.player().position().y - (double)((cb)this).a.y > 1.0E-4) {
                if (((Boolean)baritone.a.a().allowPlace.value).booleanValue() && ((a)((cb)this).a).a.a() && !cc.b(((cb)this).a, ((cb)this).b.below()) && !((cb)this).a.player().onGround() && cc.a(cd2, ((cb)this).a, ((cb)this).b.below(), true, false) == cc.a.a) {
                    cd2.a(Input.CLICK_RIGHT, true);
                }
                if (this.a == 3 && !this.a) {
                    double d2 = (double)((cb)this).a.x + 0.5 - ((cb)this).a.player().position().x;
                    double d3 = (double)((cb)this).a.z + 0.5 - ((cb)this).a.player().position().z;
                    if (Math.max(Math.abs(d2), Math.abs(d3)) < 0.7) {
                        return cd2;
                    }
                }
                cd2.a(Input.JUMP, true);
            } else if (!((cb)this).a.playerFeet().equals((Object)((cb)this).b.relative(this.a, -1))) {
                cd2.a(Input.SPRINT, false);
                if (((cb)this).a.playerFeet().equals((Object)((cb)this).a.relative(this.a, -1))) {
                    cc.a(((cb)this).a, cd2, (BlockPos)((cb)this).a);
                } else {
                    cc.a(((cb)this).a, cd2, (BlockPos)((cb)this).a.relative(this.a, -1));
                }
            }
        }
        return cd2;
    }
}

