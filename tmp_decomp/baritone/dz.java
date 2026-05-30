/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.Settings;
import baritone.api.event.events.BlockChangeEvent;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.pathing.movement.IMovement;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.IElytraProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Pair;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.df;
import baritone.en;
import baritone.ep;
import baritone.eq;
import baritone.ey;
import baritone.fb;
import baritone.fe;
import baritone.fg;
import baritone.fh;
import com.mojang.blaze3d.vertex.BufferBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.awt.Color;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dz
extends ey
implements AbstractGameEventListener,
IBaritoneProcess,
IElytraProcess {
    public a a;
    private boolean a;
    private BetterBlockPos a;
    private boolean b;
    private GoalYLevel a;
    private en a;
    private boolean c;
    private Set<BetterBlockPos> a = new HashSet();

    @Override
    public final void onLostControl() {
        this.a = baritone.dz$a.d;
        this.a = false;
        this.a = null;
        this.b = false;
        this.a = null;
        this.a();
    }

    private dz(baritone.a a2) {
        super(a2);
        a2.getGameEventHandler().registerEventListener(this);
    }

    public static IElytraProcess a(baritone.a a2) {
        if (ep.a()) {
            return new dz(a2);
        }
        return new eq(a2);
    }

    @Override
    public final boolean isActive() {
        return this.a != null;
    }

    @Override
    public final void resetState() {
        BlockPos blockPos = this.currentDestination();
        this.onLostControl();
        if (blockPos != null) {
            this.pathTo(blockPos);
            this.repackChunks();
        }
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        Object object;
        Object object2;
        Runnable runnable;
        Object object3;
        if ((Long)baritone.a.a().elytraNetherSeed.value != this.a.a.b) {
            this.logDirect("Nether seed changed, recalculating path");
            this.resetState();
        }
        if (this.c != (Boolean)baritone.a.a().elytraPredictTerrain.value) {
            this.logDirect("elytraPredictTerrain setting changed, recalculating path");
            this.c = (Boolean)baritone.a.a().elytraPredictTerrain.value;
            this.resetState();
        }
        Object object4 = this.a;
        Object object5 = ((en)object4).a.a;
        synchronized (object5) {
            object3 = object4;
            ((en)object4).a = null;
            if (object3.a != null) {
                try {
                    object3.a = (en.f)object3.a.get();
                }
                catch (Exception exception) {
                }
                finally {
                    object3.a = null;
                }
            }
            Object object6 = object3;
            if (((en)object6).d <= 0 && (runnable = (Runnable)((en)object6).a.poll()) != null) {
                runnable.run();
                ((en)object6).d = (Integer)baritone.a.a().ticksBetweenInventoryMoves.value;
            }
            if (((en)object6).d > 0) {
                --((en)object6).d;
            }
            if (object3.a > 0) {
                --object3.a;
            }
            if (object3.b > 0) {
                --object3.b;
            }
            if (!object3.a().isPresent()) {
                object3.c = 0;
            }
            object3.a.clear();
            object3.b.clear();
            object3.d = null;
            object3.c = null;
            object3.a = null;
            object2 = object3.a.a;
            if (!object2.isEmpty()) {
                if (object3.b == null) {
                    object3.a.a();
                } else {
                    object3.a = new fb(object3.a);
                    object6 = object3.a;
                    ((en.d)object6).c();
                    int n2 = ((en.d)object6).a;
                    ((en.d)object6).a = Math.max(((en.d)object6).a, ((en.d)object6).c);
                    ((en.d)object6).b = ((en.d)object6).a == n2 && ((en.d)object6).a.a.player().isFallFlying() ? ++((en.d)object6).b : 0;
                    ((en.d)object6).b();
                    if (!((en.d)object6).b) {
                        n2 = ((en.d)object6).a.size() - 1;
                        if (!((en.d)object6).a && ((en.d)object6).a.a.world().isLoaded((BlockPos)((en.d)object6).a.a(n2))) {
                            ((en.d)object6).a(n2);
                        }
                    }
                    int n3 = object3.a.c;
                    object3.d = object2.subList(Math.max(n3 - 30, 0), Math.min(n3 + 100, object2.size()));
                }
            }
        }
        long l2 = System.currentTimeMillis();
        if ((l2 - ((en)object4).a) / 1000L > (Long)baritone.a.a().elytraTimeBetweenCacheCullSecs.value) {
            ((en)object4).a.a(((en)object4).a.player().chunkPosition().x, ((en)object4).a.player().chunkPosition().z, (int)((Integer)baritone.a.a().elytraCacheCullDistance.value), ((en)object4).a);
            ((en)object4).a = l2;
        }
        if (bl2) {
            this.onLostControl();
            this.logDirect("Failed to compute a walking path to a spot to jump off from. Consider starting from a higher location, near an overhang. Or, you can disable elytraAutoJump and just manually begin gliding.");
            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
        bl2 = false;
        if (((ey)this).a.player().isFallFlying() && this.a()) {
            if (((Boolean)baritone.a.a().elytraAllowEmergencyLand.value).booleanValue()) {
                this.logDirect("Emergency landing - almost out of elytra durability or fireworks");
                bl2 = true;
            } else {
                this.logDirect("almost out of elytra durability or fireworks, but I'm going to continue since elytraAllowEmergencyLand is false");
            }
        }
        if (((ey)this).a.player().isFallFlying() && this.a != baritone.dz$a.f && (this.a.a.a || bl2)) {
            object3 = this.a.a.a.a();
            if (object3 != null && (((ey)this).a.player().position().distanceToSqr(object3.getCenter()) < 2304.0 || bl2) && (!this.a || bl2 && this.a == null)) {
                Object object7;
                HashSet<BetterBlockPos> hashSet;
                block83: {
                    this.logDirect("Path complete, picking a nearby safe landing spot...");
                    dz dz2 = this;
                    BetterBlockPos betterBlockPos3 = ((ey)dz2).a.playerFeet();
                    object4 = dz2;
                    object = new PriorityQueue<BetterBlockPos>(Comparator.comparingInt(betterBlockPos2 -> (betterBlockPos2.x - betterBlockPos.x) * (betterBlockPos2.x - betterBlockPos.x) + (betterBlockPos2.z - betterBlockPos.z) * (betterBlockPos2.z - betterBlockPos.z)).thenComparingInt(betterBlockPos -> -betterBlockPos.y));
                    hashSet = new HashSet<BetterBlockPos>();
                    object2 = new LongOpenHashSet();
                    object.add((BetterBlockPos)betterBlockPos3);
                    while (!object.isEmpty()) {
                        Object object8;
                        Object object9;
                        Object object10;
                        BetterBlockPos betterBlockPos4;
                        block80: {
                            betterBlockPos4 = (BetterBlockPos)((Object)object.poll());
                            if (!((ey)object4).a.world().isLoaded((BlockPos)betterBlockPos4) || !((object10 = betterBlockPos4).getY() >= 0 && object10.getY() < 128) || ((ey)object4).a.world().getBlockState((BlockPos)betterBlockPos4).getBlock() != Blocks.AIR) continue;
                            Object object11 = object2;
                            object9 = betterBlockPos4;
                            object10 = object4;
                            betterBlockPos3 = new BlockPos.MutableBlockPos(object9.getX(), object9.getY(), object9.getZ());
                            while (betterBlockPos3.getY() >= 0 && !object11.contains(betterBlockPos3.asLong())) {
                                object11.add(betterBlockPos3.asLong());
                                Block block = ((ey)object10).a.world().getBlockState((BlockPos)betterBlockPos3).getBlock();
                                if (dz.a(block)) {
                                    object11 = object10;
                                    BetterBlockPos betterBlockPos5 = betterBlockPos3;
                                    object8 = !(!((dz)object11).a(betterBlockPos5.north()) || !((dz)object11).a(betterBlockPos5.south()) || !((dz)object11).a(betterBlockPos5.east()) || !((dz)object11).a(betterBlockPos5.west()) || !((dz)object11).a(betterBlockPos5.north().west()) || !((dz)object11).a(betterBlockPos5.north().east()) || !((dz)object11).a(betterBlockPos5.south().west()) || !((dz)object11).a(betterBlockPos5.south().east())) ? new BetterBlockPos(betterBlockPos3) : null;
                                    break block80;
                                }
                                if (block != Blocks.AIR) break;
                                BetterBlockPos betterBlockPos6 = betterBlockPos3;
                                betterBlockPos6.set(betterBlockPos6.getX(), betterBlockPos3.getY() - 1, betterBlockPos3.getZ());
                            }
                            object8 = runnable = null;
                        }
                        if (object8 != null) {
                            boolean bl4;
                            int n4;
                            int n5;
                            block81: {
                                object9 = runnable;
                                object10 = object4;
                                betterBlockPos3 = new BlockPos.MutableBlockPos(object9.getX(), object9.getY(), object9.getZ());
                                n5 = betterBlockPos3.getY() + 15;
                                for (n4 = betterBlockPos3.getY() + 1; n4 <= n5; ++n4) {
                                    BetterBlockPos betterBlockPos7 = betterBlockPos3;
                                    betterBlockPos7.set(betterBlockPos7.getX(), n4, betterBlockPos3.getZ());
                                    if (((ey)object10).a.world().getBlockState((BlockPos)betterBlockPos3).getBlock() instanceof AirBlock) continue;
                                    bl4 = false;
                                    break block81;
                                }
                                bl4 = true;
                            }
                            if (bl4) {
                                boolean bl5;
                                block82: {
                                    object9 = ((BetterBlockPos)((Object)runnable)).above(15);
                                    object10 = object4;
                                    betterBlockPos3 = new BlockPos.MutableBlockPos();
                                    for (n5 = -4; n5 <= 4; ++n5) {
                                        for (n4 = -4; n4 <= 4; ++n4) {
                                            for (int i2 = -4; i2 <= 4; ++i2) {
                                                betterBlockPos3.set(object9.getX() + n5, object9.getY() + n4, object9.getZ() + i2);
                                                if (((ey)object10).a.world().getBlockState((BlockPos)betterBlockPos3).getBlock() instanceof AirBlock) continue;
                                                bl5 = false;
                                                break block82;
                                            }
                                        }
                                    }
                                    bl5 = true;
                                }
                                if (bl5 && !((dz)object4).a.contains((Object)((BetterBlockPos)((Object)runnable)).above(15))) {
                                    object7 = ((BetterBlockPos)((Object)runnable)).above(15);
                                    break block83;
                                }
                            }
                        }
                        if (hashSet.add(betterBlockPos4.north())) {
                            object.add(betterBlockPos4.north());
                        }
                        if (hashSet.add(betterBlockPos4.east())) {
                            object.add(betterBlockPos4.east());
                        }
                        if (hashSet.add(betterBlockPos4.south())) {
                            object.add(betterBlockPos4.south());
                        }
                        if (hashSet.add(betterBlockPos4.west())) {
                            object.add(betterBlockPos4.west());
                        }
                        if (hashSet.add(betterBlockPos4.above())) {
                            object.add(betterBlockPos4.above());
                        }
                        if (!hashSet.add(betterBlockPos4.below())) continue;
                        object.add(betterBlockPos4.below());
                    }
                    object7 = hashSet = null;
                }
                if (object7 != null) {
                    this.a((BlockPos)hashSet, true);
                    this.a = hashSet;
                }
                this.a = true;
            }
            if (object3 != null && ((ey)this).a.player().position().distanceToSqr(object3.getCenter()) < 1.0) {
                if (((Boolean)baritone.a.a().notificationOnPathComplete.value).booleanValue() && !this.b) {
                    this.logNotification("Pathing complete", false);
                }
                if (((Boolean)baritone.a.a().disconnectOnArrival.value).booleanValue() && !this.b) {
                    this.onLostControl();
                    ((ey)this).a.world().disconnect();
                    return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                this.b = true;
                if (this.a) {
                    this.a = baritone.dz$a.f;
                    this.logDirect("Above the landing spot, landing...");
                }
            }
        }
        if (this.a == baritone.dz$a.f) {
            Object object12 = object3 = this.a != null ? this.a : this.a.a.a.a();
            if (((ey)this).a.player().isFallFlying() && object3 != null) {
                Object object13 = ((ey)this).a.player().position();
                object4 = new Vec3((double)((BetterBlockPos)((Object)object3)).x + 0.5, ((Vec3)object13).y, (double)((BetterBlockPos)((Object)object3)).z + 0.5);
                object13 = RotationUtils.calcRotationFromVec3d((Vec3)object13, (Vec3)object4, ((ey)this).a.playerRotations());
                ((ey)this).a.a.updateTarget(new Rotation(((Rotation)object13).getYaw(), 0.0f), false);
                if (((ey)this).a.player().position().y < (double)(((BetterBlockPos)((Object)object3)).y - 15)) {
                    this.logDirect("bad landing spot, trying again...");
                    this.a((BetterBlockPos)((Object)object3));
                }
            }
        }
        if (((ey)this).a.player().isFallFlying()) {
            this.a.a = this.a == baritone.dz$a.f;
            this.a = null;
            ((ey)this).a.a.clearAllKeys();
            object4 = this.a;
            if (!((en)object4).a.a.isEmpty()) {
                boolean bl6;
                ItemStack itemStack;
                object2 = object4;
                if (((Boolean)baritone.a.a().elytraAutoSwap.value).booleanValue() && ((en)object2).a.isEmpty() && (itemStack = ((en)object2).a.player().getItemBySlot(EquipmentSlot.CHEST)).getItem() == Items.ELYTRA && itemStack.getMaxDamage() - itemStack.getDamageValue() <= (Integer)baritone.a.a().elytraMinimumDurability.value) {
                    int n6;
                    int n7;
                    block84: {
                        NonNullList nonNullList = ((en)object2).a.player().getInventory().items;
                        for (int i3 = 0; i3 < nonNullList.size(); ++i3) {
                            ItemStack itemStack2 = (ItemStack)nonNullList.get(i3);
                            if (itemStack2.getItem() != Items.ELYTRA || itemStack2.getMaxDamage() - itemStack2.getDamageValue() <= (Integer)baritone.a.a().elytraMinimumDurability.value) continue;
                            n7 = i3;
                            break block84;
                        }
                        n7 = n6 = -1;
                    }
                    if (n7 != -1) {
                        int n8 = n6 < 9 ? n6 + 36 : n6;
                        Object object14 = object2;
                        ((en)object14).a(((en)object14).a.player().inventoryMenu.containerId, n8, ClickType.PICKUP);
                        Object object15 = object2;
                        ((en)object15).a(((en)object15).a.player().inventoryMenu.containerId, 6, ClickType.PICKUP);
                        Object object16 = object2;
                        ((en)object16).a(((en)object16).a.player().inventoryMenu.containerId, n8, ClickType.PICKUP);
                    }
                }
                if (((en)object4).a.player().horizontalCollision) {
                    ((en)object4).a("hbonk");
                }
                if (((en)object4).a.player().verticalCollision) {
                    ((en)object4).a("vbonk");
                }
                en.g g2 = new en.g((en)object4, false);
                ((en)object4).d = true;
                object = ((en)object4).a == null || !((en)object4).a.a.equals(g2) ? ((en)object4).a(g2) : ((en)object4).a;
                if (((en)object4).b) {
                    int[] nArray = ((en)object4).a;
                    int n9 = g2.a.a() ? 1 : 0;
                    nArray[n9] = nArray[n9] + 1;
                    ((en)object4).b = false;
                }
                if (bl6 = ((en)object4).a.player().isInLava()) {
                    ((en)object4).a.a.setInputForceState(Input.JUMP, true);
                }
                if (object == null) {
                    ((en)object4).a("no solution");
                } else {
                    ((en)object4).a.a.updateTarget(((en.f)object).a, false);
                    if (!((en.f)object).a) {
                        ((en)object4).a("no pitch solution, probably gonna crash in a few ticks LOL!!!");
                    } else {
                        ((en)object4).a = new BetterBlockPos(((en.f)object).a.x, ((en.f)object).a.y, ((en.f)object).a.z);
                        ((en)object4).a(((en.f)object).a.a, ((en.f)object).a, ((en.f)object).a.a.a(), ((en.f)object).b || bl6);
                    }
                }
            }
            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
        if (this.a == baritone.dz$a.f) {
            if (((ey)this).a.playerMotion().multiply(1.0, 0.0, 1.0).length() > 0.001) {
                this.logDirect("Landed, but still moving, waiting for velocity to die down... ");
                ((ey)this).a.a.setInputForceState(Input.SNEAK, true);
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
            this.logDirect("Done :)");
            ((ey)this).a.a.clearAllKeys();
            this.onLostControl();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        if (this.a == baritone.dz$a.e || this.a == baritone.dz$a.d) {
            a a2 = this.a = ((ey)this).a.player().onGround() && (Boolean)baritone.a.a().elytraAutoJump.value != false ? baritone.dz$a.a : baritone.dz$a.d;
        }
        if (this.a == baritone.dz$a.a) {
            if (this.a()) {
                this.logDirect("Not taking off, because elytra durability or fireworks are so low that I would immediately emergency land anyway.");
                this.onLostControl();
                return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
            if (this.a == null) {
                this.a = new GoalYLevel(31);
            }
            if ((object3 = ((ey)this).a.a.a) != null && object3.getPath().getGoal() == this.a) {
                IMovement iMovement2 = object3.getPath().movements().stream().filter(iMovement -> iMovement instanceof df).findFirst().orElse(null);
                if (iMovement2 != null) {
                    object4 = new BetterBlockPos((iMovement2.getSrc().x + iMovement2.getDest().x) / 2, (iMovement2.getSrc().y + iMovement2.getDest().y) / 2, (iMovement2.getSrc().z + iMovement2.getDest().z) / 2);
                    this.a.a.a((BetterBlockPos)((Object)object4)).whenComplete((void_, throwable) -> {
                        if (throwable == null) {
                            this.a = baritone.dz$a.c;
                            return;
                        }
                        this.onLostControl();
                    });
                    this.a = baritone.dz$a.b;
                } else {
                    this.onLostControl();
                    this.logDirect("Failed to compute a walking path to a spot to jump off from. Consider starting from a higher location, near an overhang. Or, you can disable elytraAutoJump and just manually begin gliding.");
                    return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
            }
            return new fh(this.a, PathingCommandType.SET_GOAL_AND_PAUSE, new b(((ey)this).a));
        }
        if (this.a == baritone.dz$a.b) {
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        if (this.a == baritone.dz$a.c) {
            object3 = ((ey)this).a.a.a;
            if (((ey)this).a.player().fallDistance > 1.0f && !bl3 && object3 != null && object3.getPath().movements().get(object3.getPosition()) instanceof df) {
                this.a = baritone.dz$a.d;
            } else {
                return new PathingCommand(null, PathingCommandType.SET_GOAL_AND_PATH);
            }
        }
        if (this.a == baritone.dz$a.d) {
            if (!bl3) {
                ((ey)this).a.a.b();
            }
            ((ey)this).a.a.clearAllKeys();
            if (((ey)this).a.player().fallDistance > 1.0f) {
                ((ey)this).a.a.setInputForceState(Input.JUMP, true);
            }
        }
        return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
    }

    public final void a(BetterBlockPos betterBlockPos) {
        this.a.add(betterBlockPos);
        this.a = false;
        this.a = null;
        this.a = baritone.dz$a.e;
    }

    private void a() {
        en en2 = this.a;
        if (en2 != null) {
            this.a = null;
            baritone.a.a().execute(en2::b);
        }
    }

    @Override
    public final double priority() {
        return 0.0;
    }

    @Override
    public final String displayName0() {
        return "Elytra - " + this.a.a;
    }

    @Override
    public final void repackChunks() {
        if (this.a != null) {
            this.a.c();
        }
    }

    @Override
    public final BlockPos currentDestination() {
        if (this.a != null) {
            return this.a.b;
        }
        return null;
    }

    @Override
    public final void pathTo(BlockPos blockPos) {
        this.a(blockPos, false);
    }

    private void a(BlockPos blockPos, boolean bl2) {
        if (((ey)this).a.player() == null || ((ey)this).a.player().level().dimension() != Level.NETHER) {
            return;
        }
        this.onLostControl();
        this.c = (Boolean)baritone.a.a().elytraPredictTerrain.value;
        this.a = new en(((ey)this).a, this, blockPos, bl2);
        if (((ey)this).a.world() != null) {
            this.a.c();
        }
        this.a.a();
    }

    @Override
    public final void pathTo(Goal goal) {
        int n2;
        int n3;
        int n4;
        if (goal instanceof GoalXZ) {
            GoalXZ goalXZ = (GoalXZ)goal;
            n4 = goalXZ.getX();
            n3 = 64;
            n2 = goalXZ.getZ();
        } else if (goal instanceof GoalBlock) {
            GoalBlock goalBlock = (GoalBlock)goal;
            n4 = goalBlock.x;
            n3 = goalBlock.y;
            n2 = goalBlock.z;
        } else {
            throw new IllegalArgumentException("The goal must be a GoalXZ or GoalBlock");
        }
        if (n3 <= 0 || n3 >= 128) {
            throw new IllegalArgumentException("The y of the goal is not between 0 and 128");
        }
        this.pathTo(new BlockPos(n4, n3, n2));
    }

    private boolean a() {
        ItemStack itemStack = ((ey)this).a.player().getItemBySlot(EquipmentSlot.CHEST);
        if (itemStack.getItem() != Items.ELYTRA || itemStack.getMaxDamage() - itemStack.getDamageValue() < (Integer)baritone.a.a().elytraMinimumDurability.value) {
            return true;
        }
        itemStack = ((ey)this).a.player().getInventory().items;
        int n2 = 0;
        for (int i2 = 0; i2 < 36; ++i2) {
            if (!en.a((ItemStack)itemStack.get(i2))) continue;
            n2 += ((ItemStack)itemStack.get(i2)).getCount();
        }
        return n2 <= (Integer)baritone.a.a().elytraMinFireworksBeforeLanding.value;
    }

    @Override
    public final boolean isLoaded() {
        return true;
    }

    @Override
    public final boolean isSafeToCancel() {
        return !this.isActive() || this.a != baritone.dz$a.e && this.a != baritone.dz$a.d;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final void onRenderPass(RenderEvent object) {
        if (this.a != null) {
            Iterator<Object> iterator;
            BufferBuilder bufferBuilder;
            Object renderEvent = object;
            object = this.a;
            Settings settings = baritone.a.a();
            if (((en)object).d != null) {
                fg.a(((RenderEvent)renderEvent).getModelViewStack(), ((en)object).d, 0, Color.RED, false, 0, 0, 0.0);
            }
            if (((en)object).a != null) {
                fg.a(((RenderEvent)renderEvent).getModelViewStack(), ((en)object).a, new GoalBlock(((en)object).a), ((RenderEvent)renderEvent).getPartialTicks(), Color.GREEN);
            }
            if (!((en)object).a.isEmpty() && ((Boolean)settings.elytraRenderRaytraces.value).booleanValue()) {
                bufferBuilder = fe.a(Color.GREEN, ((Float)settings.pathRenderLineWidthPixels.value).floatValue(), (Boolean)settings.renderPathIgnoreDepth.value);
                iterator = ((en)object).a.iterator();
                while (iterator.hasNext()) {
                    Pair pair = (Pair)iterator.next();
                    fe.a(bufferBuilder, ((RenderEvent)renderEvent).getModelViewStack(), (Vec3)pair.first(), (Vec3)pair.second());
                }
                fe.a(bufferBuilder, (Boolean)settings.renderPathIgnoreDepth.value);
            }
            if (!((en)object).b.isEmpty() && ((Boolean)baritone.a.a().elytraRenderRaytraces.value).booleanValue()) {
                bufferBuilder = fe.a(Color.BLUE, ((Float)settings.pathRenderLineWidthPixels.value).floatValue(), (Boolean)settings.renderPathIgnoreDepth.value);
                for (Pair pair : ((en)object).b) {
                    fe.a(bufferBuilder, ((RenderEvent)renderEvent).getModelViewStack(), (Vec3)pair.first(), (Vec3)pair.second());
                }
                fe.a(bufferBuilder, (Boolean)settings.renderPathIgnoreDepth.value);
            }
            if (((en)object).c != null && ((Boolean)baritone.a.a().elytraRenderSimulation.value).booleanValue()) {
                void var6_10;
                bufferBuilder = fe.a(new Color(3591388), ((Float)settings.pathRenderLineWidthPixels.value).floatValue(), (Boolean)settings.renderPathIgnoreDepth.value);
                iterator = ((en)object).a.player().getPosition(((RenderEvent)renderEvent).getPartialTicks());
                boolean bl2 = false;
                while (var6_10 < ((en)object).c.size() - 1) {
                    Vec3 vec3 = ((en)object).c.get((int)var6_10).add(iterator);
                    Vec3 vec32 = ((en)object).c.get((int)(var6_10 + true)).add(iterator);
                    fe.a(bufferBuilder, ((RenderEvent)renderEvent).getModelViewStack(), vec3, vec32);
                    ++var6_10;
                }
                fe.a(bufferBuilder, (Boolean)settings.renderPathIgnoreDepth.value);
            }
        }
    }

    @Override
    public final void onWorldEvent(WorldEvent worldEvent) {
        if (worldEvent.getWorld() != null && worldEvent.getState() == EventState.POST) {
            this.a();
        }
    }

    @Override
    public final void onChunkEvent(ChunkEvent object) {
        if (this.a != null) {
            ChunkEvent chunkEvent = object;
            object = this.a;
            if (chunkEvent.isPostPopulate() && ((en)object).a != null) {
                chunkEvent = ((en)object).a.world().getChunk(chunkEvent.getX(), chunkEvent.getZ());
                ((en)object).a.a((LevelChunk)chunkEvent);
            }
        }
    }

    @Override
    public final void onBlockChange(BlockChangeEvent blockChangeEvent) {
        if (this.a != null) {
            this.a.a.a(blockChangeEvent);
        }
    }

    @Override
    public final void onReceivePacket(PacketEvent packetEvent) {
        if (this.a != null) {
            this.a.a(packetEvent);
        }
    }

    @Override
    public final void onPostTick(TickEvent tickEvent) {
        IBaritoneProcess iBaritoneProcess = ((ey)this).a.a.mostRecentInControl().orElse(null);
        if (this.a != null && iBaritoneProcess == this) {
            this.a.a(tickEvent);
        }
    }

    private static boolean a(Block block) {
        return block == Blocks.NETHERRACK || block == Blocks.GRAVEL || block == Blocks.NETHER_BRICKS && (Boolean)baritone.a.a().elytraAllowLandOnNetherFortress.value != false;
    }

    private boolean a(BlockPos blockPos) {
        return dz.a(((ey)this).a.world().getBlockState(blockPos).getBlock());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class a
    extends Enum<a> {
        public static final /* enum */ a a = new a("Finding spot to jump off");
        public static final /* enum */ a b = new a("Waiting for elytra path");
        public static final /* enum */ a c = new a("Walking to takeoff");
        public static final /* enum */ a d = new a("Begin flying");
        public static final /* enum */ a e = new a("Flying");
        public static final /* enum */ a f = new a("Landing");
        public final String a;
        private static final /* synthetic */ a[] a;

        public static a[] values() {
            return (a[])a.clone();
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        private a(String string2) {
            this.a = string2;
        }

        static {
            a = new a[]{a, b, c, d, e, f};
        }
    }

    public static final class b
    extends ca {
        public b(baritone.a a2) {
            super(a2, true);
            this.k = true;
            this.b = 8;
            this.c = 10000;
        }

        @Override
        public final double a(int n2, int n3, int n4, BlockState blockState) {
            return 1000000.0;
        }

        @Override
        public final double b(int n2, int n3, int n4, BlockState blockState) {
            return 1000000.0;
        }

        @Override
        public final double a() {
            return 1000000.0;
        }
    }
}

