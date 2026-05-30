/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.Block
 */
package baritone.api.cache;

import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.IPlayerContext;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

public interface IWorldScanner {
    public List<BlockPos> scanChunkRadius(IPlayerContext var1, BlockOptionalMetaLookup var2, int var3, int var4, int var5);

    default public List<BlockPos> scanChunkRadius(IPlayerContext iPlayerContext, List<Block> list, int n2, int n3, int n4) {
        return this.scanChunkRadius(iPlayerContext, new BlockOptionalMetaLookup(list.toArray(new Block[0])), n2, n3, n4);
    }

    public List<BlockPos> scanChunk(IPlayerContext var1, BlockOptionalMetaLookup var2, ChunkPos var3, int var4, int var5);

    default public List<BlockPos> scanChunk(IPlayerContext iPlayerContext, List<Block> list, ChunkPos chunkPos, int n2, int n3) {
        return this.scanChunk(iPlayerContext, new BlockOptionalMetaLookup(list), chunkPos, n2, n3);
    }

    public int repack(IPlayerContext var1);

    public int repack(IPlayerContext var1, int var2);
}

