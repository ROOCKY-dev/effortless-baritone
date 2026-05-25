package dev.roocky.effortlessbaritone;

import baritone.api.schematic.AbstractSchematic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class BaritoneSchematicAdapter extends AbstractSchematic {
    private final Map<BlockPos, BlockState> blocks;
    private final int width;
    private final int height;
    private final int length;
    private final int minX, minY, minZ;

    public BaritoneSchematicAdapter(Map<BlockPos, BlockState> blocks) {
        super(0, 0, 0); // Dimensions updated below
        this.blocks = blocks;
        
        if (blocks.isEmpty()) {
            this.width = 0;
            this.height = 0;
            this.length = 0;
            this.minX = 0;
            this.minY = 0;
            this.minZ = 0;
            return;
        }

        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;
        int currentMinX = Integer.MAX_VALUE, currentMinY = Integer.MAX_VALUE, currentMinZ = Integer.MAX_VALUE;

        for (BlockPos pos : blocks.keySet()) {
            if (pos.getX() > maxX) maxX = pos.getX();
            if (pos.getY() > maxY) maxY = pos.getY();
            if (pos.getZ() > maxZ) maxZ = pos.getZ();
            if (pos.getX() < currentMinX) currentMinX = pos.getX();
            if (pos.getY() < currentMinY) currentMinY = pos.getY();
            if (pos.getZ() < currentMinZ) currentMinZ = pos.getZ();
        }

        this.minX = currentMinX;
        this.minY = currentMinY;
        this.minZ = currentMinZ;
        this.width = maxX - currentMinX + 1;
        this.height = maxY - currentMinY + 1;
        this.length = maxZ - currentMinZ + 1;
    }

    @Override
    public BlockState desiredState(int x, int y, int z, BlockState current, java.util.List<BlockState> approxPlaceable) {
        BlockPos absolutePos = new BlockPos(minX + x, minY + y, minZ + z);
        if (blocks.containsKey(absolutePos)) {
            return blocks.get(absolutePos);
        }
        return current; // Unaffected by this schematic
    }

    @Override
    public int widthX() {
        return width;
    }

    @Override
    public int heightY() {
        return height;
    }

    @Override
    public int lengthZ() {
        return length;
    }

    @Override
    public boolean inSchematic(int x, int y, int z, BlockState currentState) {
        // Return true only for blocks in the schematic bounds
        BlockPos absolutePos = new BlockPos(minX + x, minY + y, minZ + z);
        return blocks.containsKey(absolutePos);
    }
}
