/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.phys.AABB
 */
package baritone;

import baritone.api.selection.ISelection;
import baritone.api.utils.BetterBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class et
implements ISelection {
    private final BetterBlockPos a;
    private final BetterBlockPos b;
    private final BetterBlockPos c;
    private final BetterBlockPos d;
    private final Vec3i a;
    private final AABB a;

    public et(BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        this.a = betterBlockPos;
        this.b = betterBlockPos2;
        this.c = new BetterBlockPos(Math.min(betterBlockPos.x, betterBlockPos2.x), Math.min(betterBlockPos.y, betterBlockPos2.y), Math.min(betterBlockPos.z, betterBlockPos2.z));
        this.d = new BetterBlockPos(Math.max(betterBlockPos.x, betterBlockPos2.x), Math.max(betterBlockPos.y, betterBlockPos2.y), Math.max(betterBlockPos.z, betterBlockPos2.z));
        this.a = new Vec3i(this.d.x - this.c.x + 1, this.d.y - this.c.y + 1, this.d.z - this.c.z + 1);
        this.a = new AABB((double)this.c.x, (double)this.c.y, (double)this.c.z, (double)(this.d.x + 1), (double)(this.d.y + 1), (double)(this.d.z + 1));
    }

    @Override
    public final BetterBlockPos pos1() {
        return this.a;
    }

    @Override
    public final BetterBlockPos pos2() {
        return this.b;
    }

    @Override
    public final BetterBlockPos min() {
        return this.c;
    }

    @Override
    public final BetterBlockPos max() {
        return this.d;
    }

    @Override
    public final Vec3i size() {
        return this.a;
    }

    @Override
    public final AABB aabb() {
        return this.a;
    }

    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public final String toString() {
        return String.format("Selection{pos1=%s,pos2=%s}", new Object[]{this.a, this.b});
    }

    private boolean a(Direction direction) {
        boolean bl2 = direction.getAxisDirection().getStep() < 0;
        switch (direction.getAxis()) {
            case X: {
                return this.b.x > this.a.x ^ bl2;
            }
            case Y: {
                return this.b.y > this.a.y ^ bl2;
            }
            case Z: {
                return this.b.z > this.a.z ^ bl2;
            }
        }
        throw new IllegalStateException("Bad Direction.Axis");
    }

    @Override
    public final ISelection expand(Direction direction, int n2) {
        if (this.a(direction)) {
            return new et(this.a, this.b.relative(direction, n2));
        }
        return new et(this.a.relative(direction, n2), this.b);
    }

    @Override
    public final ISelection contract(Direction direction, int n2) {
        if (this.a(direction)) {
            return new et(this.a.relative(direction, n2), this.b);
        }
        return new et(this.a, this.b.relative(direction, n2));
    }

    @Override
    public final ISelection shift(Direction direction, int n2) {
        return new et(this.a.relative(direction, n2), this.b.relative(direction, n2));
    }
}

