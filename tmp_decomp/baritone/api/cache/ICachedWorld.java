/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package baritone.api.cache;

import baritone.api.cache.ICachedRegion;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;

public interface ICachedWorld {
    public ICachedRegion getRegion(int var1, int var2);

    public void queueForPacking(LevelChunk var1);

    public boolean isCached(int var1, int var2);

    public ArrayList<BlockPos> getLocationsOf(String var1, int var2, int var3, int var4, int var5);

    public void reloadAllFromDisk();

    public void save();
}

