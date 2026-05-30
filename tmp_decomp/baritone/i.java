/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 */
package baritone;

import baritone.a;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.event.events.PathEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.process.PathingCommand;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.PathCalculationResult;
import baritone.api.utils.interfaces.IGoalRenderPos;
import baritone.bv;
import baritone.bw;
import baritone.c;
import baritone.ca;
import baritone.cc;
import baritone.dl;
import baritone.fg;
import baritone.fh;
import baritone.fv;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class i
extends c
implements IPathingBehavior,
Helper {
    public dl a;
    public dl b;
    public Goal a;
    public ca a;
    private int a;
    private BetterBlockPos a;
    private boolean c;
    public boolean a;
    private boolean d;
    private boolean e;
    private boolean f;
    public boolean b;
    private volatile bv a;
    private final Object a;
    private final Object b;
    private boolean g;
    private BetterBlockPos b;
    private final LinkedBlockingQueue<PathEvent> a = new Object();

    public i(a a2) {
        super(a2);
        this.b = new Object();
        this.a = new LinkedBlockingQueue();
    }

    private void a(PathEvent pathEvent) {
        ((AbstractQueue)((Object)this.a)).add(pathEvent);
    }

    private void c() {
        Object object = new ArrayList();
        ((LinkedBlockingQueue)((Object)this.a)).drainTo(object);
        this.b = ((ArrayList)object).contains((Object)PathEvent.CALC_FAILED);
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            PathEvent pathEvent = (PathEvent)((Object)object.next());
            ((c)this).a.getGameEventHandler().onPathEvent(pathEvent);
        }
    }

    @Override
    public final void onTick(TickEvent object) {
        block40: {
            this.c();
            if (((TickEvent)object).getType() == TickEvent.Type.OUT) {
                this.b();
                ((c)this).a.a.a();
                return;
            }
            this.b = this.a();
            ((c)this).a.a.b();
            object = this;
            this.e = false;
            if (((i)object).a && ((i)object).c) {
                ((i)object).a = false;
                if (((i)object).d) {
                    ((c)object).a.a.clearAllKeys();
                    ((c)object).a.a.a.a();
                }
                ((i)object).d = false;
                ((i)object).e = true;
            } else {
                ((i)object).d = true;
                if (((i)object).f) {
                    ((i)object).f = false;
                    ((c)object).a.a.clearAllKeys();
                }
                Object object2 = ((i)object).b;
                synchronized (object2) {
                    Object object3 = ((i)object).a;
                    synchronized (object3) {
                        if (((i)object).a != null) {
                            BetterBlockPos betterBlockPos = ((i)object).a.a();
                            Optional<IPath> optional = ((i)object).a.bestPathSoFar();
                            if (!(((i)object).a != null && ((i)object).a.getPath().getDest().equals((Object)betterBlockPos) || betterBlockPos.equals((Object)((c)object).a.playerFeet()) || betterBlockPos.equals((Object)((i)object).b) || optional.isPresent() && (optional.get().positions().contains((Object)((c)object).a.playerFeet()) || optional.get().positions().contains((Object)((i)object).b)))) {
                                ((i)object).a.a();
                            }
                        }
                    }
                    if (((i)object).a == null) {
                        break block40;
                    }
                    ((i)object).c = ((i)object).a.a();
                    if (((i)object).a.a || ((i)object).a.b()) {
                        ((i)object).a = null;
                        if (((i)object).a == null || ((i)object).a.isInGoal(((c)object).a.playerFeet())) {
                            Object object4 = object;
                            object4.logDebug("All done. At " + String.valueOf(((i)object4).a));
                            ((i)object).a(PathEvent.AT_GOAL);
                            ((i)object).b = null;
                            if (((Boolean)baritone.a.a().disconnectOnArrival.value).booleanValue()) {
                                ((c)object).a.world().disconnect();
                            }
                            break block40;
                        }
                        if (((i)object).b != null && !((i)object).b.getPath().positions().contains((Object)((c)object).a.playerFeet()) && !((i)object).b.getPath().positions().contains((Object)((i)object).b)) {
                            object.logDebug("Discarding next path as it does not contain current position");
                            ((i)object).a(PathEvent.DISCARD_NEXT);
                            ((i)object).b = null;
                        }
                        if (((i)object).b != null) {
                            object.logDebug("Continuing on to planned next path");
                            ((i)object).a(PathEvent.CONTINUING_ONTO_PLANNED_NEXT);
                            ((i)object).a = ((i)object).b;
                            ((i)object).b = null;
                            ((i)object).a.a();
                            break block40;
                        }
                        object3 = ((i)object).a;
                        synchronized (object3) {
                            if (((i)object).a != null) {
                                ((i)object).a(PathEvent.PATH_FINISHED_NEXT_STILL_CALCULATING);
                                break block40;
                            }
                            ((i)object).a(PathEvent.CALC_STARTED);
                            Object object5 = object;
                            ((i)object5).a(((i)object5).b, true, ((i)object).a);
                        }
                    }
                    if (((i)object).c && ((i)object).b != null) {
                        boolean bl2;
                        object3 = ((i)object).b;
                        if (!((dl)object3).a.player().onGround() && ((dl)object3).a.world().getFluidState((BlockPos)((dl)object3).a.playerFeet()).isEmpty()) {
                            bl2 = false;
                        } else if (((dl)object3).a.player().getDeltaMovement().y < -0.1) {
                            bl2 = false;
                        } else {
                            int n2 = ((dl)object3).a.positions().indexOf((Object)((dl)object3).a.playerFeet());
                            if (n2 == -1) {
                                bl2 = false;
                            } else {
                                ((dl)object3).a = n2;
                                ((dl)object3).a();
                                bl2 = true;
                            }
                        }
                        if (bl2) {
                            object.logDebug("Splicing into planned next path early...");
                            ((i)object).a(PathEvent.SPLICING_ONTO_NEXT_EARLY);
                            ((i)object).a = ((i)object).b;
                            ((i)object).b = null;
                            ((i)object).a.a();
                            break block40;
                        }
                    }
                    if (((Boolean)baritone.a.a().splicePath.value).booleanValue()) {
                        ((i)object).a = ((i)object).a.a(((i)object).b);
                    }
                    if (((i)object).b != null && ((i)object).a.getPath().getDest().equals((Object)((i)object).b.getPath().getDest())) {
                        ((i)object).b = null;
                    }
                    object3 = ((i)object).a;
                    synchronized (object3) {
                        if (((i)object).a != null) {
                            break block40;
                        }
                        if (((i)object).b != null) {
                            break block40;
                        }
                        if (((i)object).a == null || ((i)object).a.isInGoal(((i)object).a.getPath().getDest())) {
                            break block40;
                        }
                        if (object.ticksRemainingInSegment(false).get() < (double)((Integer)baritone.a.a().planningTickLookahead.value).intValue()) {
                            object.logDebug("Path almost over. Planning ahead...");
                            ((i)object).a(PathEvent.NEXT_SEGMENT_CALC_STARTED);
                            Object object6 = object;
                            ((i)object6).a(((i)object6).a.getPath().getDest(), false, ((i)object).a);
                        }
                    }
                }
            }
        }
        ++this.a;
        this.c();
    }

    @Override
    public final void onPlayerSprintState(SprintStateEvent sprintStateEvent) {
        if (this.isPathing()) {
            sprintStateEvent.setState(this.a.b);
        }
    }

    @Override
    public final void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (this.a != null) {
            switch (playerUpdateEvent.getState()) {
                case PRE: {
                    this.g = (Boolean)((c)this).a.minecraft().options.autoJump().get();
                    ((c)this).a.minecraft().options.autoJump().set((Object)Boolean.FALSE);
                    return;
                }
                case POST: {
                    ((c)this).a.minecraft().options.autoJump().set((Object)this.g);
                }
            }
        }
    }

    public final boolean a(PathingCommand object) {
        this.a = ((PathingCommand)object).goal;
        this.a = object instanceof fh ? ((fh)object).a : new ca(((c)this).a, true);
        if (this.a == null) {
            return false;
        }
        if (this.a.isInGoal(((c)this).a.playerFeet())) {
            return false;
        }
        object = this.b;
        synchronized (object) {
            if (this.a != null) {
                return false;
            }
            Object object2 = this.a;
            synchronized (object2) {
                if (this.a != null) {
                    return false;
                }
                this.a(PathEvent.CALC_STARTED);
                i i2 = this;
                i2.a(i2.b, true, this.a);
                return true;
            }
        }
    }

    @Override
    public final Goal getGoal() {
        return this.a;
    }

    @Override
    public final boolean isPathing() {
        return this.hasPath() && !this.e;
    }

    public final Optional<bw> getInProgress() {
        return Optional.ofNullable(this.a);
    }

    public final boolean a() {
        if (this.a == null) {
            return !((c)this).a.getElytraProcess().isActive() || ((c)this).a.getElytraProcess().isSafeToCancel();
        }
        return this.c;
    }

    public final boolean b() {
        if (this.a()) {
            this.b();
            return true;
        }
        return false;
    }

    @Override
    public final boolean cancelEverything() {
        boolean bl2 = this.a();
        if (bl2) {
            this.b();
        }
        ((c)this).a.a.a();
        return bl2;
    }

    public final void a() {
        Object object = this.b;
        synchronized (object) {
            this.getInProgress().ifPresent(bw::a);
            if (!this.a()) {
                return;
            }
            this.a = null;
            this.b = null;
        }
        this.f = true;
    }

    public final void b() {
        this.a(PathEvent.CANCELED);
        Object object = this.b;
        synchronized (object) {
            this.getInProgress().ifPresent(bw::a);
            if (this.a != null) {
                this.a = null;
                this.b = null;
                ((c)this).a.a.clearAllKeys();
                ((c)this).a.a.a.a();
            }
            return;
        }
    }

    @Override
    public final void forceCancel() {
        this.cancelEverything();
        this.b();
        Object object = this.a;
        synchronized (object) {
            this.a = null;
            return;
        }
    }

    @Override
    public final Optional<Double> estimatedTicksToGoal() {
        double d2;
        BetterBlockPos betterBlockPos = ((c)this).a.playerFeet();
        if (this.a == null || betterBlockPos == null || this.a == null) {
            return Optional.empty();
        }
        if (this.a.isInGoal(((c)this).a.playerFeet())) {
            i i2 = this;
            i2.a(i2.b);
            return Optional.of(0.0);
        }
        if (this.a == 0) {
            return Optional.empty();
        }
        double d3 = this.a.heuristic(betterBlockPos.x, betterBlockPos.y, betterBlockPos.z);
        if (d3 == (d2 = this.a.heuristic(this.a.x, this.a.y, this.a.z))) {
            return Optional.empty();
        }
        return Optional.of(Math.abs(d3 - this.a.heuristic()) * (double)this.a / Math.abs(d2 - d3));
    }

    private void a(BetterBlockPos betterBlockPos) {
        this.a = 0;
        this.a = betterBlockPos;
    }

    public final BetterBlockPos a() {
        BetterBlockPos betterBlockPos2 = ((c)this).a.playerFeet();
        if (!cc.b(((c)this).a, betterBlockPos2.below())) {
            if (((c)this).a.player().onGround()) {
                int n2;
                double d2 = ((c)this).a.player().position().x;
                double d3 = ((c)this).a.player().position().z;
                ArrayList<BetterBlockPos> arrayList = new ArrayList<BetterBlockPos>();
                for (n2 = -1; n2 <= 1; ++n2) {
                    for (int i2 = -1; i2 <= 1; ++i2) {
                        arrayList.add(new BetterBlockPos(betterBlockPos2.x + n2, betterBlockPos2.y, betterBlockPos2.z + i2));
                    }
                }
                arrayList.sort(Comparator.comparingDouble(betterBlockPos -> ((double)betterBlockPos.x + 0.5 - d2) * ((double)betterBlockPos.x + 0.5 - d2) + ((double)betterBlockPos.z + 0.5 - d3) * ((double)betterBlockPos.z + 0.5 - d3)));
                for (n2 = 0; n2 < 4; ++n2) {
                    BetterBlockPos betterBlockPos3 = (BetterBlockPos)((Object)arrayList.get(n2));
                    double d4 = Math.abs((double)betterBlockPos3.x + 0.5 - d2);
                    double d5 = Math.abs((double)betterBlockPos3.z + 0.5 - d3);
                    if (d4 > 0.8 && d5 > 0.8 || !cc.b(((c)this).a, betterBlockPos3.below()) || !cc.a(((c)this).a, betterBlockPos3) || !cc.a(((c)this).a, betterBlockPos3.above())) continue;
                    return betterBlockPos3;
                }
            } else if (cc.b(((c)this).a, betterBlockPos2.below().below())) {
                return betterBlockPos2.below();
            }
        }
        return betterBlockPos2;
    }

    private void a(BetterBlockPos betterBlockPos, boolean bl2, ca object) {
        long l2;
        long l3;
        if (!Thread.holdsLock(this.a)) {
            throw new IllegalStateException("Must be called with synchronization on pathCalcLock");
        }
        if (this.a != null) {
            throw new IllegalStateException("Already doing it");
        }
        if (!((ca)object).a) {
            throw new IllegalStateException("Improper context thread safety level");
        }
        Goal goal = this.a;
        if (goal == null) {
            this.logDebug("no goal");
            return;
        }
        if (this.a == null) {
            l3 = (Long)baritone.a.a().primaryTimeoutMS.value;
            l2 = (Long)baritone.a.a().failureTimeoutMS.value;
        } else {
            l3 = (Long)baritone.a.a().planAheadPrimaryTimeoutMS.value;
            l2 = (Long)baritone.a.a().planAheadFailureTimeoutMS.value;
        }
        object = this.a(betterBlockPos, goal, this.a == null ? null : this.a.getPath(), (ca)object);
        if (!Objects.equals(((bw)object).getGoal(), goal)) {
            this.logDebug("Simplifying " + String.valueOf(goal.getClass()) + " to GoalXZ due to distance");
        }
        this.a = object;
        baritone.a.a().execute(() -> this.a(bl2, betterBlockPos, goal, (bw)object, l3, l2));
    }

    private bv a(BetterBlockPos betterBlockPos, Goal object, IPath object2, ca ca2) {
        Goal goal = object;
        if (((Boolean)baritone.a.a().simplifyUnloadedYCoord.value).booleanValue() && object instanceof IGoalRenderPos && !ca2.a.a((object = ((IGoalRenderPos)object).getGoalPos()).getX(), object.getZ())) {
            goal = new GoalXZ(object.getX(), object.getZ());
        }
        object = new fv(ca2.a.getPlayerContext(), (IPath)object2, ca2);
        object2 = ((c)this).a.playerFeet();
        Object object3 = new BetterBlockPos(betterBlockPos);
        BlockPos blockPos = object2.subtract((Vec3i)object3);
        if (object2.getY() == object3.getY() && Math.abs(blockPos.getX()) <= 1 && Math.abs(blockPos.getZ()) <= 1) {
            object3 = object2;
        }
        return new bv((BetterBlockPos)((Object)object3), betterBlockPos.getX(), betterBlockPos.getY(), betterBlockPos.getZ(), goal, (fv)object, ca2);
    }

    @Override
    public final void onRenderPass(RenderEvent renderEvent) {
        fg.a(renderEvent, this);
    }

    private /* synthetic */ void a(boolean bl2, BlockPos blockPos, Goal goal, bw object, long l2, long l3) {
        if (bl2) {
            this.logDebug("Starting to search for path from " + String.valueOf(blockPos) + " to " + String.valueOf(goal));
        }
        object = ((bw)object).calculate(l2, l3);
        Object object2 = this.b;
        synchronized (object2) {
            BlockPos blockPos2 = ((PathCalculationResult)object).getPath().map(iPath -> new dl(this, (IPath)iPath));
            if (this.a == null) {
                if (blockPos2.isPresent()) {
                    if (blockPos2.get().getPath().positions().contains((Object)this.b)) {
                        this.a(PathEvent.CALC_FINISHED_NOW_EXECUTING);
                        this.a = blockPos2.get();
                        blockPos2 = blockPos;
                        this.a(new BetterBlockPos(blockPos2));
                    } else {
                        this.logDebug("Warning: discarding orphan path segment with incorrect start");
                    }
                } else if (((PathCalculationResult)object).getType() != PathCalculationResult.Type.CANCELLATION && ((PathCalculationResult)object).getType() != PathCalculationResult.Type.EXCEPTION) {
                    this.a(PathEvent.CALC_FAILED);
                }
            } else if (this.b == null) {
                if (blockPos2.isPresent()) {
                    if (blockPos2.get().getPath().getSrc().equals((Object)this.a.getPath().getDest())) {
                        this.a(PathEvent.NEXT_SEGMENT_CALC_FINISHED);
                        this.b = (dl)blockPos2.get();
                    } else {
                        this.logDebug("Warning: discarding orphan next segment with incorrect start");
                    }
                } else {
                    this.a(PathEvent.NEXT_CALC_FAILED);
                }
            } else {
                this.logDirect("Warning: PathingBehaivor illegal state! Discarding invalid path!");
            }
            if (bl2 && this.a != null && this.a.getPath() != null) {
                if (goal.isInGoal(this.a.getPath().getDest())) {
                    this.logDebug("Finished finding a path from " + String.valueOf(blockPos) + " to " + String.valueOf(goal) + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                } else {
                    this.logDebug("Found path segment from " + String.valueOf(blockPos) + " towards " + String.valueOf(goal) + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                }
            }
            Object object3 = this.a;
            synchronized (object3) {
                this.a = null;
            }
            return;
        }
    }
}

