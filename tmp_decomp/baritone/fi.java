/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone;

import baritone.a;
import baritone.api.pathing.calc.IPathingControlManager;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.fj;
import baritone.i;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class fi
implements IPathingControlManager {
    final a a;
    private final HashSet<IBaritoneProcess> a;
    private final List<IBaritoneProcess> a;
    private IBaritoneProcess a;
    private IBaritoneProcess b;
    PathingCommand a;

    public fi(a a2) {
        this.a = a2;
        this.a = new HashSet();
        this.a = new ArrayList();
        a2.getGameEventHandler().registerEventListener(new fj(this));
    }

    @Override
    public final void registerProcess(IBaritoneProcess iBaritoneProcess) {
        iBaritoneProcess.onLostControl();
        ((HashSet)((Object)this.a)).add(iBaritoneProcess);
    }

    public final void a() {
        this.a = null;
        this.b = null;
        this.a = null;
        this.a.clear();
        Iterator iterator = ((HashSet)((Object)this.a)).iterator();
        while (iterator.hasNext()) {
            IBaritoneProcess iBaritoneProcess = (IBaritoneProcess)iterator.next();
            iBaritoneProcess.onLostControl();
            if (!iBaritoneProcess.isActive() || iBaritoneProcess.isTemporary()) continue;
            throw new IllegalStateException(iBaritoneProcess.displayName() + " stayed active after being cancelled");
        }
    }

    @Override
    public final Optional<IBaritoneProcess> mostRecentInControl() {
        return Optional.ofNullable(this.b);
    }

    @Override
    public final Optional<PathingCommand> mostRecentCommand() {
        return Optional.ofNullable(this.a);
    }

    public final void b() {
        i i2;
        block14: {
            PathingCommand pathingCommand;
            IBaritoneProcess iBaritoneProcess;
            this.a = this.b;
            this.b = null;
            i2 = this.a.a;
            fi fi2 = this;
            Iterator<IBaritoneProcess> iterator = ((HashSet)((Object)fi2.a)).iterator();
            while (iterator.hasNext()) {
                iBaritoneProcess = (IBaritoneProcess)iterator.next();
                if (iBaritoneProcess.isActive()) {
                    if (fi2.a.contains(iBaritoneProcess)) continue;
                    fi2.a.add(0, iBaritoneProcess);
                    continue;
                }
                fi2.a.remove(iBaritoneProcess);
            }
            fi2.a.sort(Comparator.comparingDouble(IBaritoneProcess::priority).reversed());
            iterator = fi2.a.iterator();
            while (iterator.hasNext()) {
                PathingCommand pathingCommand2 = iBaritoneProcess.onTick(Objects.equals(iBaritoneProcess = (IBaritoneProcess)iterator.next(), fi2.a) && fi2.a.a.b, fi2.a.a.a());
                if (pathingCommand2 == null) {
                    if (!iBaritoneProcess.isActive()) continue;
                    throw new IllegalStateException(iBaritoneProcess.displayName() + " actively returned null PathingCommand");
                }
                if (pathingCommand2.commandType == PathingCommandType.DEFER) continue;
                fi2.b = iBaritoneProcess;
                if (!iBaritoneProcess.isTemporary()) {
                    iterator.forEachRemaining(IBaritoneProcess::onLostControl);
                }
                pathingCommand = pathingCommand2;
                break block14;
            }
            pathingCommand = fi2.a = null;
        }
        if (this.a == null) {
            i2.b();
            i2.a = null;
            return;
        }
        if (!Objects.equals(this.b, this.a) && this.a.commandType != PathingCommandType.REQUEST_PAUSE && this.a != null && !this.a.isTemporary()) {
            i2.b();
        }
        switch (this.a.commandType) {
            case SET_GOAL_AND_PAUSE: {
                i2.a(this.a);
            }
            case REQUEST_PAUSE: {
                i2.a = true;
                return;
            }
            case CANCEL_AND_SET_GOAL: {
                i2.a = this.a.goal;
                i2.b();
                return;
            }
            case FORCE_REVALIDATE_GOAL_AND_PATH: 
            case REVALIDATE_GOAL_AND_PATH: {
                if (i2.isPathing() || i2.getInProgress().isPresent()) break;
                i2.a(this.a);
                return;
            }
            case SET_GOAL_AND_PATH: {
                if (this.a.goal == null) break;
                i2.a(this.a);
                return;
            }
            default: {
                throw new IllegalStateException("Unexpected command type " + String.valueOf((Object)this.a.commandType));
            }
        }
    }

    public final boolean a(Goal goal) {
        Goal goal2;
        Object object = this.a.a.a;
        return object != null && (goal2 = object.getPath().getGoal()).isInGoal((BlockPos)(object = object.getPath().getDest())) && !goal.isInGoal((BlockPos)object);
    }
}

