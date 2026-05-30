/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.cache;

import baritone.api.cache.ICachedWorld;
import baritone.api.cache.IWaypointCollection;

public interface IWorldData {
    public ICachedWorld getCachedWorld();

    public IWaypointCollection getWaypoints();
}

