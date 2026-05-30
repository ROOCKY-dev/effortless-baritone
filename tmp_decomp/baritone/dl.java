/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.a;
import baritone.api.IBaritone;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.movement.IMovement;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.pathing.path.IPathExecutor;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.utils.input.Input;
import baritone.bw;
import baritone.c;
import baritone.ca;
import baritone.cb;
import baritone.cc;
import baritone.db;
import baritone.dc;
import baritone.dd;
import baritone.df;
import baritone.dg;
import baritone.di;
import baritone.dk;
import baritone.dm;
import baritone.fb;
import baritone.i;
import java.util.HashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dl
implements IPathExecutor,
Helper {
    public final IPath a;
    public int a;
    private int b;
    private int c;
    private Double a;
    private Integer a;
    public boolean a;
    private boolean c;
    public HashSet<BlockPos> a;
    public HashSet<BlockPos> b;
    public HashSet<BlockPos> c = true;
    private final i a;
    public final IPlayerContext a = new HashSet();
    public boolean b = (int)new HashSet();

    public dl(i i2, IPath iPath) {
        this.c = (int)new HashSet();
        this.a = i2;
        this.a = ((c)i2).a;
        this.a = iPath;
        this.a = 0;
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public final boolean a() {
        block52: {
            block57: {
                block58: {
                    block54: {
                        block56: {
                            block55: {
                                block53: {
                                    block50: {
                                        block51: {
                                            if (this.a == this.a.length() - 1) {
                                                ++this.a;
                                            }
                                            if (this.a >= this.a.length()) {
                                                return true;
                                            }
                                            var1_1 = (cb)this.a.movements().get(this.a);
                                            var2_2 = this.a.playerFeet();
                                            if (!var1_1.b().contains(var2_2)) {
                                                for (var3_4 = 0; var3_4 < this.a && var3_4 < this.a.length(); ++var3_4) {
                                                    if (!((cb)this.a.movements().get(var3_4)).b().contains(var2_2)) continue;
                                                    var2_3 = this.a;
                                                    for (var3_4 = this.a = var3_4; var3_4 <= var2_3; ++var3_4) {
                                                        this.a.movements().get(var3_4).reset();
                                                    }
                                                    this.b();
                                                    this.a();
                                                    return false;
                                                }
                                                for (var3_4 = this.a + 3; var3_4 < this.a.length() - 1; ++var3_4) {
                                                    if (!((cb)this.a.movements().get(var3_4)).b().contains(var2_2)) continue;
                                                    if (var3_4 - this.a > 2) {
                                                        this.logDebug("Skipping forward " + (var3_4 - this.a) + " steps, to " + var3_4);
                                                    }
                                                    this.a = var3_4 - 1;
                                                    this.b();
                                                    this.a();
                                                    return false;
                                                }
                                            }
                                            v0 = this;
                                            var4_10 = v0.a;
                                            var2_2 = v0;
                                            var8_14 = -1.0;
                                            var5_16 /* !! */  = null;
                                            var4_10 = var4_10.movements().iterator();
                                            while (var4_10.hasNext()) {
                                                for (BlockPos var10_19 : ((cb)((IMovement)var4_10.next())).b()) {
                                                    var11_20 = VecUtils.entityDistanceToCenter((Entity)var2_2.a.player(), var10_19);
                                                    if (!(var11_20 < var8_14) && var8_14 != -1.0) continue;
                                                    var8_14 = var11_20;
                                                    var5_16 /* !! */  = var10_19;
                                                }
                                            }
                                            var3_5 = new Tuple((Object)var8_14, var5_16 /* !! */ );
                                            if (this.a((Tuple<Double, BlockPos>)var3_5, 2.0)) {
                                                ++this.b;
                                                System.out.println("FAR AWAY FROM PATH FOR " + this.b + " TICKS. Current distance: " + String.valueOf(var3_5.getA()) + ". Threshold: 2.0");
                                                if ((double)this.b > 200.0) {
                                                    this.logDebug("Too far away from path for too long, cancelling path");
                                                    this.c();
                                                    return false;
                                                }
                                            } else {
                                                this.b = 0;
                                            }
                                            if (this.a((Tuple<Double, BlockPos>)var3_5, 3.0)) {
                                                this.logDebug("too far from path");
                                                this.c();
                                                return false;
                                            }
                                            var2_2 = new fb(this.a);
                                            for (var3_6 = this.a - 10; var3_6 < this.a + 10; ++var3_6) {
                                                if (var3_6 < 0 || var3_6 >= this.a.movements().size()) continue;
                                                var6_22 = (cb)this.a.movements().get(var3_6);
                                                var7_18 = var6_22.a((fb)var2_2);
                                                var4_10 = var6_22.b((fb)var2_2);
                                                var5_16 /* !! */  = var6_22.c((fb)var2_2);
                                                var6_22.resetBlockCache();
                                                if (!var7_18.equals(var6_22.a((fb)var2_2))) {
                                                    this.c = true;
                                                }
                                                if (!var4_10.equals(var6_22.b((fb)var2_2))) {
                                                    this.c = true;
                                                }
                                                if (var5_16 /* !! */ .equals(var6_22.c((fb)var2_2))) continue;
                                                this.c = true;
                                            }
                                            if (this.c) {
                                                var3_7 = new HashSet<BlockPos>();
                                                var6_22 = new HashSet<E>();
                                                var7_18 = new HashSet<E>();
                                                for (var4_11 = this.a; var4_11 < this.a.movements().size(); ++var4_11) {
                                                    var5_16 /* !! */  = (cb)this.a.movements().get(var4_11);
                                                    var3_7.addAll(var5_16 /* !! */ .a((fb)var2_2));
                                                    var6_22.addAll(var5_16 /* !! */ .b((fb)var2_2));
                                                    var7_18.addAll(var5_16 /* !! */ .c((fb)var2_2));
                                                }
                                                this.a = var3_7;
                                                this.b = (int)var6_22;
                                                this.c = (int)var7_18;
                                                this.c = false;
                                            }
                                            if (this.a < this.a.movements().size() - 1) {
                                                var3_8 = this.a.movements().get(this.a + 1);
                                                if (!this.a.a.a.a(var3_8.getDest().x, var3_8.getDest().z)) {
                                                    this.logDebug("Pausing since destination is at edge of loaded chunks");
                                                    this.a();
                                                    return true;
                                                }
                                            }
                                            var3_9 = var1_1.safeToCancel();
                                            if (this.a == null || this.a != this.a) {
                                                this.a = this.a;
                                                this.a = var1_1.getCost();
                                                for (var6_23 = 1; var6_23 < (Integer)baritone.a.a().costVerificationLookahead.value && this.a + var6_23 < this.a.length() - 1; ++var6_23) {
                                                    if (!(((cb)this.a.movements().get(this.a + var6_23)).a(this.a.a) >= 1000000.0) || !var3_9) continue;
                                                    this.logDebug("Something has changed in the world and a future movement has become impossible. Cancelling.");
                                                    this.c();
                                                    return true;
                                                }
                                            }
                                            var4_12 /* !! */  = this.a.a;
                                            var2_2 = var1_1;
                                            var1_1.a = null;
                                            var9_27 = var4_12 /* !! */ ;
                                            var8_15 = var2_2;
                                            if (var8_15.a == null) {
                                                var8_15.a = var8_15.a((ca)var9_27);
                                            }
                                            var6_24 = var8_15.a;
                                            if (v1 >= 1000000.0 && var3_9) {
                                                this.logDebug("Something has changed in the world and this movement has become impossible. Cancelling.");
                                                this.c();
                                                return true;
                                            }
                                            if (!var1_1.calculatedWhileLoaded() && var6_24 - this.a > (Double)baritone.a.a().maxCostIncrease.value && var3_9) {
                                                v2 = this;
                                                v2.logDebug("Original cost " + v2.a + " current cost " + var6_24 + ". Cancelling.");
                                                this.c();
                                                return true;
                                            }
                                            var2_2 = this;
                                            var4_12 /* !! */  = var2_2.a.getInProgress();
                                            if (var4_12 /* !! */ .isPresent() == false ? false : (var2_2.a.player().onGround() == false ? false : (cc.b(var2_2.a, var2_2.a.playerFeet().below()) == false ? false : (cc.a(var2_2.a, var2_2.a.playerFeet()) == false || cc.a(var2_2.a, var2_2.a.playerFeet().above()) == false ? false : (var2_2.a.movements().get(var2_2.a).safeToCancel() == false ? false : ((var8_15 = ((bw)var4_12 /* !! */ .get()).bestPathSoFar()).isPresent() == false ? false : ((var9_27 = ((IPath)var8_15.get()).positions()).size() < 3 ? false : var9_27.subList(1, var9_27.size()).contains((Object)var2_2.a.playerFeet())))))))) {
                                                this.logDebug("Pausing since current best path is a backtrack");
                                                this.a();
                                                return true;
                                            }
                                            var4_12 /* !! */  = var1_1.update();
                                            if (var4_12 /* !! */  == MovementStatus.UNREACHABLE || var4_12 /* !! */  == MovementStatus.FAILED) {
                                                this.logDebug("Movement returns status " + String.valueOf(var4_12 /* !! */ ));
                                                this.c();
                                                return true;
                                            }
                                            if (var4_12 /* !! */  == MovementStatus.SUCCESS) {
                                                ++this.a;
                                                this.b();
                                                this.a();
                                                return true;
                                            }
                                            var2_2 = this;
                                            var4_13 = var2_2.a.a.a.isInputForcedDown(Input.SPRINT);
                                            var2_2.a.a.a.setInputForceState(Input.SPRINT, false);
                                            if (!new ca((IBaritone)var2_2.a.a, (boolean)false).d) ** GOTO lbl-1000
                                            var8_15 = var2_2.a.movements().get(var2_2.a);
                                            if (!(var8_15 instanceof di) || var2_2.a >= var2_2.a.length() - 3 || !((var9_27 = var2_2.a.movements().get(var2_2.a + 1)) instanceof db) || !dl.a(var2_2.a, (di)var8_15, (db)var9_27, var2_2.a.movements().get(var2_2.a + 2))) break block50;
                                            var7_18 = var8_15;
                                            var6_25 = var2_2.a;
                                            if (Math.abs((double)var7_18.getDirection().getX() * ((double)var7_18.getSrc().z + 0.5 - var6_25.player().position().z)) + Math.abs((double)var7_18.getDirection().getZ() * ((double)var7_18.getSrc().x + 0.5 - var6_25.player().position().x)) > 0.1) ** GOTO lbl-1000
                                            var12_28 = var7_18.getSrc().subtract((Vec3i)var7_18.getDirection()).above(2);
                                            if (cc.a(var6_25, var12_28)) {
                                                v3 = true;
                                            } else if (Math.abs((double)var7_18.getDirection().getX() * ((double)var12_28.getX() + 0.5 - var6_25.player().position().x)) + Math.abs((double)var7_18.getDirection().getZ() * ((double)var12_28.getZ() + 0.5 - var6_25.player().position().z)) > 0.8) {
                                                v3 = true;
                                            } else lbl-1000:
                                            // 2 sources

                                            {
                                                v3 = false;
                                            }
                                            if (!v3) break block51;
                                            var2_2.logDebug("Skipping traverse to straight ascend");
                                            ++var2_2.a;
                                            var2_2.b();
                                            var2_2.a();
                                            var2_2.a.a.a.setInputForceState(Input.JUMP, true);
                                            v4 = true;
                                            break block52;
                                        }
                                        var2_2.logDebug("Too far to the side to safely sprint ascend");
                                    }
                                    if (!var4_13) break block53;
                                    v4 = true;
                                    break block52;
                                }
                                if (!(var8_15 instanceof dc)) break block54;
                                if (var2_2.a < var2_2.a.length() - 2 && cc.c(var2_2.a, (var9_27 = var2_2.a.movements().get(var2_2.a + 1)).getDest().below()) && (var9_27 instanceof di || var9_27 instanceof dg)) {
                                    var5_17 = (Boolean)baritone.a.a().allowPlace.value != false && var2_2.a.a.a.a() != false && var9_27 instanceof dg != false;
                                    if (var8_15.getDirection().above().offset((Vec3i)var9_27.getDirection()).equals((Object)BlockPos.ZERO) == false && var8_15.getDirection().above().cross((Vec3i)var9_27.getDirection()).equals((Object)BlockPos.ZERO) != false && !var5_17) {
                                        ((dc)var8_15).a = true;
                                    }
                                }
                                if (!((dc)var8_15).b() || ((dc)var8_15).c()) break block55;
                                var2_2.logDebug("Sprinting would be unsafe");
                                ** GOTO lbl-1000
                            }
                            if (var2_2.a >= var2_2.a.length() - 2) break block54;
                            var9_27 = var2_2.a.movements().get(var2_2.a + 1);
                            if (!(var9_27 instanceof db) || !var8_15.getDirection().above().equals((Object)var9_27.getDirection().below())) break block56;
                            ++var2_2.a;
                            var2_2.b();
                            var2_2.a();
                            var2_2.logDebug("Skipping descend to straight ascend");
                            v4 = true;
                            break block52;
                        }
                        if (!dl.a(var2_2.a, (IMovement)var8_15, (IMovement)var9_27)) break block54;
                        if (var9_27 instanceof dc && var2_2.a < var2_2.a.length() - 3 && (var5_16 /* !! */  = var2_2.a.movements().get(var2_2.a + 2)) instanceof dc && !dl.a(var2_2.a, (IMovement)var9_27, (IMovement)var5_16 /* !! */ )) ** GOTO lbl-1000
                        if (var2_2.a.playerFeet().equals((Object)var8_15.getDest())) {
                            ++var2_2.a;
                            var2_2.b();
                            var2_2.a();
                        }
                        v4 = true;
                        break block52;
                    }
                    if (!(var8_15 instanceof db) || var2_2.a == 0) break block57;
                    var9_27 = var2_2.a.movements().get(var2_2.a - 1);
                    if (!(var9_27 instanceof dc) || !var9_27.getDirection().above().equals((Object)var8_15.getDirection().below()) || !(var2_2.a.player().position().y >= (double)(var5_16 /* !! */  = var8_15.getSrc().above()).getY() - 0.07)) break block58;
                    var2_2.a.a.a.setInputForceState(Input.JUMP, false);
                    v4 = true;
                    break block52;
                }
                if (var2_2.a >= var2_2.a.length() - 2 || !(var9_27 instanceof di) || !dl.a(var2_2.a, (di)var9_27, (db)var8_15, var2_2.a.movements().get(var2_2.a + 1))) break block57;
                v4 = true;
                break block52;
            }
            if (!(var8_15 instanceof df)) ** GOTO lbl-1000
            var7_18 = (df)var8_15;
            var6_26 = var2_2;
            var10_19 = var7_18.getDirection();
            if (var10_19.getY() < -3) {
                v5 /* !! */  = null;
            } else if (!var7_18.a.isEmpty()) {
                v5 /* !! */  = null;
            } else {
                var11_21 = new Vec3i(var10_19.getX(), 0, var10_19.getZ());
                block8: for (var12_29 = var6_26.a + 1; var12_29 < var6_26.a.length() - 1 && var12_29 < var6_26.a + 3 && (var13_30 = var6_26.a.movements().get(var12_29)) instanceof di && var11_21.equals((Object)var13_30.getDirection()); ++var12_29) {
                    for (var14_32 = var13_30.getDest().y; var14_32 <= var7_18.getSrc().y + 1; ++var14_32) {
                        var1_1 = new BlockPos(var13_30.getDest().x, var14_32, var13_30.getDest().z);
                        if (!cc.a(var6_26.a, (BlockPos)var1_1)) break block8;
                    }
                    if (!cc.b(var6_26.a, var13_30.getDest().below())) break;
                }
                if (--var12_29 == var6_26.a) {
                    v5 /* !! */  = null;
                } else {
                    var13_31 = (double)(var12_29 - var6_26.a) - 0.4;
                    v5 /* !! */  = var9_27 = new Tuple((Object)new Vec3((double)var11_21.getX() * var13_31 + (double)var7_18.getDest().x + 0.5, (double)var7_18.getDest().y, (double)var11_21.getZ() * var13_31 + (double)var7_18.getDest().z + 0.5), (Object)var7_18.getDest().offset(var11_21.getX() * (var12_29 - var6_26.a), 0, var11_21.getZ() * (var12_29 - var6_26.a)));
                }
            }
            if (v5 /* !! */  != null) {
                var5_16 /* !! */  = new BetterBlockPos((BlockPos)var9_27.getB());
                if (!var2_2.a.positions().contains(var5_16 /* !! */ )) {
                    throw new IllegalStateException(String.format("Fall override at %s %s %s returned illegal destination %s %s %s", new Object[]{var8_15.getSrc(), var5_16 /* !! */ }));
                }
                if (var2_2.a.playerFeet().equals(var5_16 /* !! */ )) {
                    var2_2.a = var2_2.a.positions().indexOf(var5_16 /* !! */ );
                    var2_2.b();
                    var2_2.a();
                    v4 = true;
                } else {
                    var2_2.a();
                    var2_2.a.a.a.updateTarget(RotationUtils.calcRotationFromVec3d(var2_2.a.playerHead(), (Vec3)var9_27.getA(), var2_2.a.playerRotations()), false);
                    var2_2.a.a.a.setInputForceState(Input.MOVE_FORWARD, true);
                    v4 = true;
                }
            } else lbl-1000:
            // 5 sources

            {
                v4 = var2_2.b = false;
            }
        }
        if (!this.b) {
            this.a.player().setSprinting(false);
        }
        ++this.c;
        if ((double)this.c > this.a + (double)((Integer)baritone.a.a().movementTimeoutTicks.value).intValue()) {
            v6 = this;
            v6.logDebug("This movement has taken too long (" + v6.c + " ticks, expected " + this.a + "). Cancelling.");
            this.c();
            return true;
        }
        return var3_9;
    }

    private boolean a(Tuple<Double, BlockPos> blockPos, double d2) {
        if ((Double)blockPos.getA() > d2) {
            if (this.a.movements().get(this.a) instanceof df) {
                blockPos = this.a.positions().get(this.a + 1);
                return VecUtils.entityFlatDistanceToCenter((Entity)this.a.player(), blockPos) >= d2;
            }
            return true;
        }
        return false;
    }

    private static boolean a(IPlayerContext iPlayerContext, di di2, db db2, IMovement iMovement) {
        if (!((Boolean)baritone.a.a().sprintAscends.value).booleanValue()) {
            return false;
        }
        if (!di2.getDirection().equals((Object)db2.getDirection().below())) {
            return false;
        }
        if (iMovement.getDirection().getX() != db2.getDirection().getX() || iMovement.getDirection().getZ() != db2.getDirection().getZ()) {
            return false;
        }
        if (!cc.b(iPlayerContext, di2.getDest().below())) {
            return false;
        }
        if (!cc.b(iPlayerContext, db2.getDest().below())) {
            return false;
        }
        if (!((cb)db2).a.isEmpty()) {
            return false;
        }
        for (int i2 = 0; i2 < 2; ++i2) {
            for (int i3 = 0; i3 < 3; ++i3) {
                BetterBlockPos betterBlockPos = di2.getSrc().above(i3);
                if (i2 == 1) {
                    betterBlockPos = betterBlockPos.offset((Vec3i)di2.getDirection());
                }
                if (cc.a(iPlayerContext, (BlockPos)betterBlockPos)) continue;
                return false;
            }
        }
        if (cc.b(iPlayerContext.world().getBlockState((BlockPos)di2.getSrc().above(3)))) {
            return false;
        }
        return !cc.b(iPlayerContext.world().getBlockState((BlockPos)db2.getDest().above(2)));
    }

    private static boolean a(IPlayerContext iPlayerContext, IMovement iMovement, IMovement iMovement2) {
        if (iMovement2 instanceof dc && iMovement2.getDirection().equals((Object)iMovement.getDirection())) {
            return true;
        }
        if (!cc.b(iPlayerContext, iMovement.getDest().offset((Vec3i)iMovement.getDirection()))) {
            return false;
        }
        if (iMovement2 instanceof di && iMovement2.getDirection().equals((Object)iMovement.getDirection())) {
            return true;
        }
        return iMovement2 instanceof dd && (Boolean)baritone.a.a().allowOvershootDiagonalDescend.value != false;
    }

    private void b() {
        this.a();
        this.c = 0;
    }

    public final void a() {
        ((c)this.a).a.a.clearAllKeys();
    }

    private void c() {
        this.a();
        ((c)this.a).a.a.a.a();
        this.a = this.a.length() + 3;
        this.a = true;
    }

    @Override
    public final int getPosition() {
        return this.a;
    }

    public final dl a(dl dl2) {
        if (dl2 == null) {
            return this.a();
        }
        return dm.a(this.a, dl2.a).map(dm2 -> {
            if (!dm2.getDest().equals((Object)dl2.getPath().getDest())) {
                throw new IllegalStateException(String.format("Path has end %s instead of %s after splicing", new Object[]{dm2.getDest(), dl2.getPath().getDest()}));
            }
            dl2 = new dl(this.a, (IPath)dm2);
            new dl(this.a, (IPath)dm2).a = this.a;
            dl2.a = this.a;
            dl2.a = this.a;
            dl2.c = this.c;
            return dl2;
        }).orElseGet(this::a);
    }

    private dl a() {
        if (this.a > (Integer)baritone.a.a().maxPathHistoryLength.value) {
            int n2 = (Integer)baritone.a.a().pathHistoryCutoffAmount.value;
            IPath iPath = this.a;
            Object object = new dk(iPath, n2, iPath.length() - 1);
            if (!object.getDest().equals((Object)this.a.getDest())) {
                throw new IllegalStateException(String.format("Path has end %s instead of %s after trimming its start", new Object[]{object.getDest(), this.a.getDest()}));
            }
            dl dl2 = this;
            dl2.logDebug("Discarding earliest segment movements, length cut from " + dl2.a.length() + " to " + object.length());
            object = new dl(this.a, (IPath)object);
            v2.a = this.a - n2;
            ((dl)object).a = this.a;
            if (this.a != null) {
                ((dl)object).a = this.a - n2;
            }
            ((dl)object).c = this.c;
            return object;
        }
        return this;
    }

    @Override
    public final IPath getPath() {
        return this.a;
    }

    public final boolean b() {
        return this.a >= this.a.length();
    }
}

