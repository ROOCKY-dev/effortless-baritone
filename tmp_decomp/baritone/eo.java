/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.utils.BetterBlockPos;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class eo
extends AbstractList<BetterBlockPos> {
    private static final eo a = new eo(Collections.emptyList());
    private final List<BetterBlockPos> a;

    eo(List<BetterBlockPos> list) {
        this.a = list;
    }

    public final BetterBlockPos a(int n2) {
        return (BetterBlockPos)((Object)this.a.get(n2));
    }

    @Override
    public final int size() {
        return this.a.size();
    }

    public final BetterBlockPos a() {
        if (this.isEmpty()) {
            return null;
        }
        return (BetterBlockPos)((Object)this.a.get(this.a.size() - 1));
    }

    public final Vec3 a(int n2) {
        BetterBlockPos betterBlockPos = this.a(n2);
        return new Vec3((double)betterBlockPos.x, (double)betterBlockPos.y, (double)betterBlockPos.z);
    }

    public static eo a() {
        return a;
    }

    @Override
    public final /* synthetic */ Object get(int n2) {
        return this.a(n2);
    }

    public final /* synthetic */ Object getLast() {
        return this.a();
    }
}

