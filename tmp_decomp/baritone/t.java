/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.dimension.DimensionType
 */
package baritone;

import baritone.a;
import baritone.api.cache.ICachedWorld;
import baritone.api.cache.IWaypointCollection;
import baritone.api.cache.IWorldData;
import baritone.o;
import baritone.s;
import java.nio.file.Path;
import net.minecraft.world.level.dimension.DimensionType;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class t
implements IWorldData {
    public final o a;
    private final s a;
    private Path a;
    private DimensionType a;

    t(Path path, DimensionType dimensionType) {
        this.a = path;
        this.a = new o(path.resolve("cache"), dimensionType);
        this.a = new s(path.resolve("waypoints"));
        this.a = dimensionType;
    }

    public final void a() {
        baritone.a.a().execute(() -> {
            System.out.println("Started saving the world in a new thread");
            this.a.save();
        });
    }

    @Override
    public final ICachedWorld getCachedWorld() {
        return this.a;
    }

    @Override
    public final IWaypointCollection getWaypoints() {
        return this.a;
    }
}

