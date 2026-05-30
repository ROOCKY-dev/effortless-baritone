/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package baritone;

import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.world.level.chunk.LevelChunk;

public interface fn {
    public void copyFrom(fn var1);

    public AtomicReferenceArray<LevelChunk> getChunks();

    public int centerX();

    public int centerZ();

    public int viewDistance();
}

