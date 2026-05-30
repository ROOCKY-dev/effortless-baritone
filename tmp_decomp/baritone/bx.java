/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.core.Vec3i
 */
package baritone;

import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.by;
import baritone.ca;
import baritone.cb;
import baritone.ce;
import baritone.dk;
import baritone.fx;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Vec3i;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class bx
extends fx {
    private final BetterBlockPos a;
    private final BetterBlockPos b;
    private final List<BetterBlockPos> a;
    private final List<cb> b;
    private final List<by> c;
    private final Goal a;
    private final int a;
    private final ca a;
    private volatile boolean a;

    bx(BetterBlockPos betterBlockPos, by by2, by by3, int n2, Goal goal, ca object) {
        this.b = new BetterBlockPos(by3.a, by3.b, by3.c);
        this.a = n2;
        this.b = new ArrayList();
        this.a = goal;
        this.a = object;
        Object object2 = by3;
        object = new ArrayList();
        ArrayList<by> arrayList = new ArrayList<by>();
        while (object2 != null) {
            arrayList.add((by)object2);
            object.add(new BetterBlockPos(object2.a, object2.b, object2.c));
            object2 = object2.a;
        }
        object2 = new BetterBlockPos(by2.a, by2.b, by2.c);
        if (!betterBlockPos.equals(object2) && by2.equals(by3)) {
            this.a = betterBlockPos;
            by2 = new by(betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, goal);
            new by(betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, goal).b = 0.0;
            arrayList.add(by2);
            object.add(betterBlockPos);
        } else {
            this.a = object2;
        }
        this.a = Lists.reverse((List)object);
        this.c = Lists.reverse(arrayList);
    }

    @Override
    public final Goal getGoal() {
        return this.a;
    }

    @Override
    public final IPath postProcess() {
        boolean bl2;
        block8: {
            if (this.a) {
                throw new IllegalStateException("Path must not be verified twice");
            }
            this.a = true;
            bx bx2 = this;
            if (bx2.a.isEmpty() || !bx2.b.isEmpty()) {
                throw new IllegalStateException("Path must not be empty");
            }
            for (int i2 = 0; i2 < bx2.a.size() - 1; ++i2) {
                Object object;
                bx bx3;
                block7: {
                    double d2 = bx2.c.get((int)(i2 + 1)).b - bx2.c.get((int)i2).b;
                    bx bx4 = bx2;
                    double d3 = d2;
                    BetterBlockPos betterBlockPos = (BetterBlockPos)((Object)bx2.a.get(i2 + 1));
                    BetterBlockPos betterBlockPos2 = (BetterBlockPos)((Object)bx4.a.get(i2));
                    bx3 = bx4;
                    ce[] ceArray = ce.values();
                    int n2 = ceArray.length;
                    for (int i3 = 0; i3 < n2; ++i3) {
                        cb cb3 = ceArray[i3].a(bx3.a, betterBlockPos2);
                        if (!cb3.getDest().equals((Object)betterBlockPos)) continue;
                        cb3.a = Math.min(cb3.a(bx3.a), d3);
                        object = cb3;
                        break block7;
                    }
                    Helper.HELPER.logDebug("Movement became impossible during calculation " + String.valueOf((Object)betterBlockPos2) + " " + String.valueOf((Object)betterBlockPos) + " " + String.valueOf(betterBlockPos.subtract((Vec3i)betterBlockPos2)));
                    object = bx3 = null;
                }
                if (object == null) {
                    bl2 = true;
                    break block8;
                }
                bx2.b.add(bx3);
            }
            bl2 = false;
        }
        boolean bl3 = bl2;
        this.b.forEach(cb2 -> {
            ca ca2 = this.a;
            v0.a = ca2.a.a(cb2.b.x, cb2.b.z);
        });
        if (bl3) {
            bx bx5 = this;
            dk dk2 = new dk(bx5, bx5.movements().size());
            if (dk2.movements().size() != this.b.size()) {
                throw new IllegalStateException("Path has wrong size after cutoff");
            }
            return dk2;
        }
        this.sanityCheck();
        return this;
    }

    @Override
    public final List<IMovement> movements() {
        if (!this.a) {
            throw new IllegalStateException("Path not yet verified");
        }
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
    public final BetterBlockPos getSrc() {
        return this.a;
    }

    @Override
    public final BetterBlockPos getDest() {
        return this.b;
    }
}

