package dev.roocky.effortlessbaritone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a single batch of block operations queued from Effortless Building.
 * Tasks are assigned unique IDs for easy management via chat commands.
 */
public class BuildTask {
    private final String id;
    private final Map<BlockPos, BlockState> blocks;
    private final long timestamp;
    private boolean active;

    public BuildTask(Map<BlockPos, BlockState> blocks) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.blocks = blocks;
        this.timestamp = System.currentTimeMillis();
        this.active = false;
    }

    public String getId() {
        return id;
    }

    public Map<BlockPos, BlockState> getBlocks() {
        return blocks;
    }

    public int getBlockCount() {
        return blocks.size();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Creates a new BuildTask by merging multiple existing tasks.
     * Block positions from later tasks will overwrite those from earlier tasks.
     */
    public static BuildTask merge(java.util.List<BuildTask> tasks) {
        Map<BlockPos, BlockState> mergedBlocks = new java.util.HashMap<>();
        for (BuildTask task : tasks) {
            mergedBlocks.putAll(task.getBlocks());
        }
        return new BuildTask(mergedBlocks);
    }
}
