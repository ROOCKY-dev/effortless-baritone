/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.BetterBlockPos;
import baritone.fx;
import java.util.Collections;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dk
extends fx {
    private final List<BetterBlockPos> a;
    private final List<IMovement> b;
    private final int a;
    private final Goal a;

    public dk(IPath iPath, int n2, int n3) {
        this.a = iPath.positions().subList(n2, n3 + 1);
        this.b = iPath.movements().subList(n2, n3);
        this.a = iPath.getNumNodesConsidered();
        this.a = iPath.getGoal();
        this.sanityCheck();
    }

    public dk(fx fx2, int n2) {
        this(fx2, 0, n2);
    }

    @Override
    public final Goal getGoal() {
        return this.a;
    }

    @Override
    public final List<IMovement> movements() {
        return Collections.unmodifiableList(this.b);
    }

    @Override
    public final List<BetterBlockPos> positions() {
        return Collections.unmodifiableList(this.a);
    }

    @Override
    public final int getNumNodesConsidered() {
        return this.a;
    }
}

