/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FallingBlock
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
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class db
extends cb {
    private int a = 0;

    public db(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        super(iBaritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[]{betterBlockPos2, betterBlockPos.above(2), betterBlockPos2.above()}, betterBlockPos2.below());
    }

    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }

    @Override
    public final double a(ca ca2) {
        return db.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z, this.b.x, this.b.z);
    }

    @Override
    public final Set<BetterBlockPos> a() {
        BetterBlockPos betterBlockPos;
        BetterBlockPos betterBlockPos2 = betterBlockPos = new BetterBlockPos(((cb)this).a.subtract((Vec3i)this.getDirection()).above());
        return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)((cb)this).a.above()), (Object)((Object)this.b), (Object)((Object)betterBlockPos2), (Object)((Object)betterBlockPos2.above()));
    }

    /*
     * Unable to fully structure code
     */
    public static double a(ca var0, int var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block13: {
            block12: {
                var6_6 = var0.a(var4_4, var2_2, var5_5);
                var7_7 = 0.0;
                if (!cc.b(var0, var4_4, var2_2, var5_5, var6_6)) {
                    var7_7 = var0.a(var4_4, var2_2, var5_5, var6_6);
                    if (v0 >= 1000000.0) {
                        return 1000000.0;
                    }
                    if (!cc.a(var4_4, var5_5, var6_6, var0.a)) {
                        return 1000000.0;
                    }
                    var9_8 = false;
                    for (var10_10 = 0; var10_10 < 5; ++var10_10) {
                        var11_12 = var4_4 + db.a[var10_10].getStepX();
                        var12_13 = var2_2 + db.a[var10_10].getStepY();
                        var13_14 = var5_5 + db.a[var10_10].getStepZ();
                        if (var11_12 == var1_1 && var13_14 == var3_3 || !cc.c(var0.a, var11_12, var12_13, var13_14)) continue;
                        var9_8 = true;
                        break;
                    }
                    if (!var9_8) {
                        return 1000000.0;
                    }
                }
                var9_9 = var0.a(var1_1, var2_2 + 2, var3_3);
                if (var0.a(var1_1, var2_2 + 3, var3_3).getBlock() instanceof FallingBlock && (cc.a(var0, var1_1, var2_2 + 1, var3_3) || !(var9_9.getBlock() instanceof FallingBlock))) {
                    return 1000000.0;
                }
                var10_11 = var0.a(var1_1, var2_2 - 1, var3_3);
                if (var10_11.getBlock() == Blocks.LADDER || var10_11.getBlock() == Blocks.VINE) {
                    return 1000000.0;
                }
                var11_12 = (int)cc.c(var10_11);
                var12_13 = (int)cc.c(var6_6);
                if (var11_12 != 0 && var12_13 == 0) {
                    return 1000000.0;
                }
                if (var12_13 == 0) break block12;
                if (var11_12 != 0) ** GOTO lbl-1000
                var13_15 = 4.63284688441047;
                break block13;
            }
            if (var6_6.getBlock() == Blocks.SOUL_SAND) {
                var13_15 = 9.26569376882094;
            } else lbl-1000:
            // 2 sources

            {
                var13_15 = Math.max(db.JUMP_ONE_BLOCK_COST, 4.63284688441047);
            }
            var13_15 += var0.e;
        }
        var15_16 = var13_15 + var7_7 + cc.a(var0, var1_1, var2_2 + 2, var3_3, var9_9, false);
        if (v1 >= 1000000.0) {
            return 1000000.0;
        }
        var15_16 += cc.a(var0, var4_4, var2_2 + 1, var5_5, false);
        if (v2 >= 1000000.0) {
            return 1000000.0;
        }
        return var15_16 + cc.a(var0, var4_4, var2_2 + 2, var5_5, true);
    }

    @Override
    public final cd a(cd object) {
        boolean bl2;
        double d2;
        double d3;
        block13: {
            if (((cb)this).a.playerFeet().y < ((cb)this).a.y) {
                cd cd2 = object;
                object = MovementStatus.UNREACHABLE;
                cd cd3 = cd2;
                cd2.a = object;
                return cd3;
            }
            super.a((cd)object);
            if (((cd)object).a != MovementStatus.RUNNING) {
                return object;
            }
            if (((cb)this).a.playerFeet().equals((Object)this.b) || ((cb)this).a.playerFeet().equals(this.b.offset((Vec3i)this.getDirection().below()))) {
                Object object2 = object;
                object = MovementStatus.SUCCESS;
                Object object3 = object2;
                object2.a = object;
                return object3;
            }
            BlockState blockState = fb.a(((cb)this).a, (BlockPos)this.c);
            if (!cc.a(((cb)this).a, this.c, blockState)) {
                ++this.a;
                if (cc.a((cd)object, ((cb)this).a, this.b.below(), false, true) == cc.a.a) {
                    ((cd)object).a(Input.SNEAK, true);
                    if (((cb)this).a.player().isCrouching()) {
                        ((cd)object).a(Input.CLICK_RIGHT, true);
                    }
                }
                if (this.a > 10) {
                    ((cd)object).a(Input.MOVE_BACK, true);
                }
                return object;
            }
            cc.a(((cb)this).a, (cd)object, (BlockPos)this.b);
            if (cc.c(blockState) && !cc.c(fb.a(((cb)this).a, (BlockPos)((cb)this).a.below()))) {
                return object;
            }
            if (((Boolean)baritone.a.a().assumeStep.value).booleanValue() || ((cb)this).a.playerFeet().equals((Object)((cb)this).a.above())) {
                return object;
            }
            int n2 = Math.abs(((cb)this).a.getX() - this.b.getX());
            int n3 = Math.abs(((cb)this).a.getZ() - this.b.getZ());
            d3 = (double)n2 * Math.abs((double)this.b.getX() + 0.5 - ((cb)this).a.player().position().x) + (double)n3 * Math.abs((double)this.b.getZ() + 0.5 - ((cb)this).a.player().position().z);
            d2 = (double)n3 * Math.abs((double)this.b.getX() + 0.5 - ((cb)this).a.player().position().x) + (double)n2 * Math.abs((double)this.b.getZ() + 0.5 - ((cb)this).a.player().position().z);
            if (Math.abs((double)n2 * ((cb)this).a.player().getDeltaMovement().z + (double)n3 * ((cb)this).a.player().getDeltaMovement().x) > 0.1) {
                return object;
            }
            db db2 = this;
            BetterBlockPos betterBlockPos = ((cb)db2).a.above(2);
            for (int i2 = 0; i2 < 4; ++i2) {
                BetterBlockPos betterBlockPos2 = betterBlockPos.relative(Direction.from2DDataValue((int)i2));
                if (cc.a(((cb)db2).a, betterBlockPos2)) continue;
                bl2 = false;
                break block13;
            }
            bl2 = true;
        }
        if (bl2) {
            return ((cd)object).a(Input.JUMP, true);
        }
        if (d3 > 1.2 || d2 > 0.2) {
            return object;
        }
        return ((cd)object).a(Input.JUMP, true);
    }

    @Override
    public final boolean b(cd cd2) {
        return cd2.a != MovementStatus.RUNNING || this.a == 0;
    }
}

