/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.utils.BetterBlockPos;
import dev.babbaj.pathfinder.PathSegment;
import java.util.Arrays;
import java.util.stream.Stream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class es {
    final Stream<BetterBlockPos> a;
    final boolean a;

    public es(Stream<BetterBlockPos> stream, boolean bl2) {
        this.a = stream;
        this.a = bl2;
    }

    public static es a(PathSegment pathSegment) {
        return new es(Arrays.stream(pathSegment.packed).mapToObj(BetterBlockPos::deserializeFromLong), pathSegment.finished);
    }
}

