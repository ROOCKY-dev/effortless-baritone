/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.pathing.calc;

import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.BetterBlockPos;
import java.util.HashSet;
import java.util.List;

public interface IPath {
    public List<IMovement> movements();

    public List<BetterBlockPos> positions();

    default public IPath postProcess() {
        throw new UnsupportedOperationException();
    }

    default public int length() {
        return this.positions().size();
    }

    public Goal getGoal();

    public int getNumNodesConsidered();

    default public BetterBlockPos getSrc() {
        return this.positions().get(0);
    }

    default public BetterBlockPos getDest() {
        List<BetterBlockPos> list = this.positions();
        return list.get(list.size() - 1);
    }

    default public double ticksRemainingFrom(int n2) {
        double d2 = 0.0;
        List<IMovement> list = this.movements();
        while (n2 < list.size()) {
            d2 += list.get(n2).getCost();
            ++n2;
        }
        return d2;
    }

    default public IPath cutoffAtLoadedChunks(Object object) {
        throw new UnsupportedOperationException();
    }

    default public IPath staticCutoff(Goal goal) {
        throw new UnsupportedOperationException();
    }

    default public void sanityCheck() {
        List<BetterBlockPos> list = this.positions();
        List<IMovement> list2 = this.movements();
        if (!this.getSrc().equals((Object)list.get(0))) {
            throw new IllegalStateException("Start node does not equal first path element");
        }
        List<BetterBlockPos> list3 = list;
        if (!this.getDest().equals((Object)list3.get(list3.size() - 1))) {
            throw new IllegalStateException("End node does not equal last path element");
        }
        if (list.size() != list2.size() + 1) {
            throw new IllegalStateException("Size of path array is unexpected");
        }
        HashSet<BetterBlockPos> hashSet = new HashSet<BetterBlockPos>();
        for (int i2 = 0; i2 < list.size() - 1; ++i2) {
            BetterBlockPos betterBlockPos = list.get(i2);
            BetterBlockPos betterBlockPos2 = list.get(i2 + 1);
            IMovement iMovement = list2.get(i2);
            if (!betterBlockPos.equals((Object)iMovement.getSrc())) {
                throw new IllegalStateException("Path source is not equal to the movement source");
            }
            if (!betterBlockPos2.equals((Object)iMovement.getDest())) {
                throw new IllegalStateException("Path destination is not equal to the movement destination");
            }
            if (hashSet.contains((Object)betterBlockPos)) {
                throw new IllegalStateException("Path doubles back on itself, making a loop");
            }
            hashSet.add(betterBlockPos);
        }
    }
}

