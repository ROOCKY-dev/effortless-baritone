/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.behavior;

import baritone.api.behavior.IBehavior;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.calc.IPathFinder;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.path.IPathExecutor;
import java.util.Optional;

public interface IPathingBehavior
extends IBehavior {
    default public Optional<Double> ticksRemainingInSegment() {
        return this.ticksRemainingInSegment(true);
    }

    default public Optional<Double> ticksRemainingInSegment(boolean bl2) {
        IPathExecutor iPathExecutor = this.getCurrent();
        if (iPathExecutor == null) {
            return Optional.empty();
        }
        int n2 = bl2 ? iPathExecutor.getPosition() : iPathExecutor.getPosition() + 1;
        return Optional.of(iPathExecutor.getPath().ticksRemainingFrom(n2));
    }

    public Optional<Double> estimatedTicksToGoal();

    public Goal getGoal();

    public boolean isPathing();

    default public boolean hasPath() {
        return this.getCurrent() != null;
    }

    public boolean cancelEverything();

    public void forceCancel();

    default public Optional<IPath> getPath() {
        return Optional.ofNullable(this.getCurrent()).map(IPathExecutor::getPath);
    }

    public Optional<? extends IPathFinder> getInProgress();

    public IPathExecutor getCurrent();

    public IPathExecutor getNext();
}

