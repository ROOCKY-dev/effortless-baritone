/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.event.events;

import baritone.api.utils.Pair;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

public final class BlockChangeEvent {
    private final ChunkPos chunk;
    private final List<Pair<BlockPos, BlockState>> blocks;

    public BlockChangeEvent(ChunkPos chunkPos, List<Pair<BlockPos, BlockState>> list) {
        this.chunk = chunkPos;
        this.blocks = list;
    }

    public final ChunkPos getChunkPos() {
        return this.chunk;
    }

    public final List<Pair<BlockPos, BlockState>> getBlocks() {
        return this.blocks;
    }
}

