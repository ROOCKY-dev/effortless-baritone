package dev.roocky.effortlessbaritone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.List;

/**
 * A POJO version of ISchematic that carries build data.
 * Used via Proxy in BaritoneAccess to avoid direct dependency on Baritone API at classload time.
 */
public class BaritoneSchematicAdapter {
    private final Map<BlockPos, BlockState> blocks;
    private final int minX, minY, minZ;
    private final int maxX, maxY, maxZ;

    public BaritoneSchematicAdapter(Map<BlockPos, BlockState> blocks) {
        this.blocks = blocks;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;

        for (BlockPos pos : blocks.keySet()) {
            minX = Math.min(minX, pos.getX());
            minY = Math.min(minY, pos.getY());
            minZ = Math.min(minZ, pos.getZ());
            maxX = Math.max(maxX, pos.getX());
            maxY = Math.max(maxY, pos.getY());
            maxZ = Math.max(maxZ, pos.getZ());
        }

        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public BlockState desiredState(int x, int y, int z, BlockState current, List<BlockState> results) {
        BlockState state = blocks.get(new BlockPos(x + minX, y + minY, z + minZ));
        return state != null ? state : current;
    }
    
    // Compatibility with different Baritone versions
    public BlockState desiredState(int x, int y, int z, BlockState current, Object results) {
        BlockState state = blocks.get(new BlockPos(x + minX, y + minY, z + minZ));
        return state != null ? state : current;
    }

    public boolean inSchematic(int x, int y, int z, BlockState state) {
        return x >= 0 && x < widthX() && y >= 0 && y < heightY() && z >= 0 && z < lengthZ();
    }

    public boolean inSchematic(int x, int y, int z) {
        return x >= 0 && x < widthX() && y >= 0 && y < heightY() && z >= 0 && z < lengthZ();
    }

    public int size(Object axis) {
        String name = axis.toString().toLowerCase();
        if (name.equals("x")) return widthX();
        if (name.equals("y")) return heightY();
        if (name.equals("z")) return lengthZ();
        return 0;
    }

    public int widthX() { return maxX - minX + 1; }
    public int heightY() { return maxY - minY + 1; }
    public int lengthZ() { return maxZ - minZ + 1; }

    public int getMinX() { return minX; }
    public int getMinY() { return minY; }
    public int getMinZ() { return minZ; }
    
    public int getMaxX() { return maxX; }
    public int getMaxY() { return maxY; }
    public int getMaxZ() { return maxZ; }
}
