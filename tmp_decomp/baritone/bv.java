/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.status.ChunkStatus
 */
package baritone;

import baritone.a;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import baritone.bw;
import baritone.bx;
import baritone.by;
import baritone.bz;
import baritone.ca;
import baritone.ce;
import baritone.fb;
import baritone.fu;
import baritone.fv;
import baritone.fw;
import baritone.n;
import java.util.Optional;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bv
extends bw {
    private final fv a;
    private final ca a;

    public bv(BetterBlockPos betterBlockPos, int n2, int n3, int n4, Goal goal, fv fv2, ca ca2) {
        super(betterBlockPos, n2, n3, n4, goal, ca2);
        this.a = fv2;
        this.a = ca2;
    }

    @Override
    protected final Optional<IPath> a(long l2, long l3) {
        long l4;
        int n2 = this.a.a.dimensionType().minY();
        int n3 = this.a.a.dimensionType().height();
        ((bw)this).a = this.a(((bw)this).a, this.b, this.c, BetterBlockPos.longHash(((bw)this).a, this.b, this.c));
        ((bw)this).a.b = 0.0;
        ((bw)this).a.c = ((bw)this).a.a;
        bz bz2 = new bz();
        bz2.a(((bw)this).a);
        double[] dArray = new double[((fv)a).length];
        for (int i2 = 0; i2 < dArray.length; ++i2) {
            dArray[i2] = ((bw)this).a.a;
            ((bw)this).a[i2] = ((bw)this).a;
        }
        fw fw2 = new fw();
        fu fu2 = new fu(this.a.a.getWorldBorder());
        long l5 = System.currentTimeMillis();
        boolean bl2 = (Boolean)baritone.a.a().slowPath.value;
        if (bl2) {
            this.logDebug("slowPath is on, path timeout will be " + String.valueOf(baritone.a.a().slowPathTimeoutMS.value) + "ms instead of " + l2 + "ms");
        }
        long l6 = l5 + (bl2 ? (Long)baritone.a.a().slowPathTimeoutMS.value : l2);
        long l7 = l5 + (bl2 ? (Long)baritone.a.a().slowPathTimeoutMS.value : l3);
        boolean bl3 = true;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        boolean bl4 = !this.a.a.isEmpty();
        int n7 = (Integer)baritone.a.a().pathingMaxChunkBorderFetch.value;
        double d2 = (Boolean)baritone.a.a().minimumImprovementRepropagation.value != false ? 0.01 : 0.0;
        ce[] ceArray = ce.values();
        while (!(bz2.a == 0 || n6 >= n7 || ((bw)this).a || (n4 & 0x3F) == 0 && ((l4 = System.currentTimeMillis()) - l7 >= 0L || !bl3 && l4 - l6 >= 0L))) {
            by by2;
            Object object;
            int n8;
            by by3;
            if (bl2) {
                try {
                    Thread.sleep((Long)baritone.a.a().slowPathTimeDelayMS.value);
                }
                catch (InterruptedException interruptedException) {}
            }
            bz bz3 = bz2;
            if (bz3.a == 0) {
                throw new IllegalStateException("Cannot remove from empty heap");
            }
            by by4 = bz3.a[1];
            bz3.a[1] = by3 = bz3.a[bz3.a];
            by3.d = 1;
            bz3.a[bz3.a] = null;
            --bz3.a;
            by4.d = -1;
            if (bz3.a >= 2) {
                int n9 = 1;
                n8 = 2;
                double d3 = by3.c;
                do {
                    object = bz3.a[n8];
                    double d4 = ((by)object).c;
                    if (n8 < bz3.a) {
                        by by5 = bz3.a[n8 + 1];
                        double d5 = by5.c;
                        if (d4 > d5) {
                            ++n8;
                            d4 = d5;
                            object = by5;
                        }
                    }
                    if (d3 <= d4) break;
                    bz3.a[n9] = object;
                    bz3.a[n8] = by3;
                    by3.d = n8;
                    ((by)object).d = n9;
                    n9 = n8;
                } while ((n8 <<= 1) <= bz3.a);
            }
            this.b = by2 = by4;
            ++n4;
            if (((bw)this).a.isInGoal(by2.a, by2.b, by2.c)) {
                this.logDebug("Took " + (System.currentTimeMillis() - l5) + "ms, " + n5 + " movements considered");
                return Optional.of(new bx(((bw)this).a, ((bw)this).a, by2, n4, ((bw)this).a, this.a));
            }
            ce[] ceArray2 = ceArray;
            int n10 = ceArray.length;
            for (int i3 = 0; i3 < n10; ++i3) {
                Object object2 = ceArray2[i3];
                int n11 = by2.a + object2.a;
                int n12 = by2.c + object2.c;
                if (n11 >> 4 != by2.a >> 4 || n12 >> 4 != by2.c >> 4) {
                    boolean bl5;
                    int n13 = n12;
                    int n14 = n11;
                    int n15 = n13;
                    n8 = n14;
                    fb fb2 = this.a.a;
                    LevelChunk levelChunk = fb2.a;
                    if (levelChunk != null && levelChunk.getPos().x == n8 >> 4 && levelChunk.getPos().z == n15 >> 4) {
                        bl5 = true;
                    } else {
                        levelChunk = fb2.a.getChunk(n8 >> 4, n15 >> 4, ChunkStatus.FULL, false);
                        if (levelChunk != null && !levelChunk.isEmpty()) {
                            fb2.a = levelChunk;
                            bl5 = true;
                        } else {
                            object = fb2.a;
                            if (object != null && ((n)object).getX() == n8 >> 9 && ((n)object).getZ() == n15 >> 9) {
                                bl5 = ((n)object).isCached(n8 & 0x1FF, n15 & 0x1FF);
                            } else if (fb2.a == null) {
                                bl5 = false;
                            } else {
                                object = fb2.a.a.a(n8 >> 9, n15 >> 9);
                                if (object == null) {
                                    bl5 = false;
                                } else {
                                    fb2.a = object;
                                    bl5 = ((n)object).isCached(n8 & 0x1FF, n15 & 0x1FF);
                                }
                            }
                        }
                    }
                    if (!bl5) {
                        if (object2.a) continue;
                        ++n6;
                        continue;
                    }
                }
                if (!object2.a && !fu2.a(n11, n12) || by2.b + object2.b > n3 || by2.b + object2.b < n2) continue;
                fw2.a();
                object2.a(this.a, by2.a, by2.b, by2.c, fw2);
                ++n5;
                double d6 = fw2.a;
                if (d6 >= 1000000.0) continue;
                if (d6 <= 0.0 || Double.isNaN(d6)) {
                    throw new IllegalStateException(String.format("%s from %s %s %s calculated implausible cost %s", object2, SettingsUtil.maybeCensor(by2.a), SettingsUtil.maybeCensor(by2.b), SettingsUtil.maybeCensor(by2.c), d6));
                }
                if (object2.a && !fu2.a(fw2.a, fw2.c)) continue;
                if (!(object2.a || fw2.a == n11 && fw2.c == n12)) {
                    throw new IllegalStateException(String.format("%s from %s %s %s ended at x z %s %s instead of %s %s", object2, SettingsUtil.maybeCensor(by2.a), SettingsUtil.maybeCensor(by2.b), SettingsUtil.maybeCensor(by2.c), SettingsUtil.maybeCensor(fw2.a), SettingsUtil.maybeCensor(fw2.c), SettingsUtil.maybeCensor(n11), SettingsUtil.maybeCensor(n12)));
                }
                if (!object2.b && fw2.b != by2.b + object2.b) {
                    throw new IllegalStateException(String.format("%s from %s %s %s ended at y %s instead of %s", object2, SettingsUtil.maybeCensor(by2.a), SettingsUtil.maybeCensor(by2.b), SettingsUtil.maybeCensor(by2.c), SettingsUtil.maybeCensor(fw2.b), SettingsUtil.maybeCensor(by2.b + object2.b)));
                }
                long l8 = BetterBlockPos.longHash(fw2.a, fw2.b, fw2.c);
                if (bl4) {
                    long l9 = l8;
                    d6 *= this.a.a.get(l9);
                }
                object2 = this.a(fw2.a, fw2.b, fw2.c, l8);
                double d7 = by2.b + d6;
                if (!(((by)object2).b - d7 > d2)) continue;
                ((by)object2).a = by2;
                ((by)object2).b = d7;
                ((by)object2).c = d7 + ((by)object2).a;
                if (((by)object2).d != -1) {
                    bz2.b((by)object2);
                } else {
                    bz2.a((by)object2);
                }
                for (n11 = 0; n11 < ((fv)a).length; ++n11) {
                    double d8 = ((by)object2).a + ((by)object2).b / a[n11];
                    if (!(dArray[n11] - d8 > d2)) continue;
                    dArray[n11] = d8;
                    ((bw)this).a[n11] = object2;
                    if (!bl3 || !(this.a((by)object2) > 25.0)) continue;
                    bl3 = false;
                }
            }
        }
        if (((bw)this).a) {
            return Optional.empty();
        }
        System.out.println(n5 + " movements considered");
        System.out.println("Open set size: " + bz2.a);
        System.out.println("PathNode map size: " + this.a());
        System.out.println((int)((double)n4 / (double)((float)(System.currentTimeMillis() - l5) / 1000.0f)) + " nodes per second");
        Optional<IPath> optional = this.a(true, n4);
        if (optional.isPresent()) {
            this.logDebug("Took " + (System.currentTimeMillis() - l5) + "ms, " + n5 + " movements considered");
        }
        return optional;
    }
}

