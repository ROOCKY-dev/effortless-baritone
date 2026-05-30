/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.BetterBlockPos;
import baritone.fx;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dm
extends fx {
    private final List<BetterBlockPos> a;
    private final List<IMovement> b;
    private final int a;
    private final Goal a;

    private dm(List<BetterBlockPos> list, List<IMovement> list2, int n2, Goal goal) {
        this.a = list;
        this.b = list2;
        this.a = n2;
        this.a = goal;
        this.sanityCheck();
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

    @Override
    public final int length() {
        return this.a.size();
    }

    public static Optional<dm> a(IPath iPath, IPath iPath2) {
        int n2;
        if (iPath2 == null || iPath == null) {
            return Optional.empty();
        }
        if (!iPath.getDest().equals((Object)iPath2.getSrc())) {
            return Optional.empty();
        }
        AbstractCollection abstractCollection = new HashSet<BetterBlockPos>(iPath2.positions());
        int n3 = -1;
        for (n2 = 0; n2 < iPath.length() - 1; ++n2) {
            if (!((HashSet)abstractCollection).contains((Object)iPath.positions().get(n2))) continue;
            n3 = n2;
            break;
        }
        if (n3 != -1) {
            return Optional.empty();
        }
        n3 = iPath.length() - 1;
        n2 = iPath2.positions().indexOf((Object)iPath.positions().get(n3));
        if (n2 != 0) {
            throw new IllegalStateException("Paths to be spliced are overlapping incorrectly");
        }
        abstractCollection = new ArrayList();
        ArrayList<IMovement> arrayList = new ArrayList<IMovement>();
        abstractCollection.addAll(iPath.positions().subList(0, n3 + 1));
        arrayList.addAll(iPath.movements().subList(0, n3));
        abstractCollection.addAll(iPath2.positions().subList(n2 + 1, iPath2.length()));
        arrayList.addAll(iPath2.movements().subList(n2, iPath2.length() - 1));
        return Optional.of(new dm((List<BetterBlockPos>)((Object)abstractCollection), arrayList, iPath.getNumNodesConsidered() + iPath2.getNumNodesConsidered(), iPath.getGoal()));
    }
}

