/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package baritone.api.schematic;

import baritone.api.schematic.AbstractSchematic;
import baritone.api.schematic.ISchematic;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class SubstituteSchematic
extends AbstractSchematic {
    private final ISchematic schematic;
    private final Map<Block, List<Block>> substitutions;
    private final Map<BlockState, Map<Block, BlockState>> blockStateCache = new HashMap<BlockState, Map<Block, BlockState>>();

    public SubstituteSchematic(ISchematic iSchematic, Map<Block, List<Block>> map) {
        super(iSchematic.widthX(), iSchematic.heightY(), iSchematic.lengthZ());
        this.schematic = iSchematic;
        this.substitutions = map;
    }

    @Override
    public boolean inSchematic(int n2, int n3, int n4, BlockState blockState) {
        return this.schematic.inSchematic(n2, n3, n4, blockState);
    }

    @Override
    public BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        BlockState blockState2 = this.schematic.desiredState(n2, n3, n4, blockState, list);
        Object object = blockState2.getBlock();
        if (!this.substitutions.containsKey(object)) {
            return blockState2;
        }
        if ((object = this.substitutions.get(object)).contains(blockState.getBlock()) && !(blockState.getBlock() instanceof AirBlock)) {
            return this.withBlock(blockState2, blockState.getBlock());
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            Block block = (Block)iterator.next();
            if (block instanceof AirBlock) {
                if (blockState.getBlock() instanceof AirBlock) {
                    return blockState;
                }
                return Blocks.AIR.defaultBlockState();
            }
            for (BlockState blockState3 : list) {
                if (!block.equals(blockState3.getBlock())) continue;
                return this.withBlock(blockState2, blockState3.getBlock());
            }
        }
        return ((Block)object.get(0)).defaultBlockState();
    }

    private BlockState withBlock(BlockState blockState2, Block block) {
        if (this.blockStateCache.containsKey(blockState2) && this.blockStateCache.get(blockState2).containsKey(block)) {
            return this.blockStateCache.get(blockState2).get(block);
        }
        Object object = blockState2.getProperties();
        BlockState blockState3 = block.defaultBlockState();
        object = object.iterator();
        while (object.hasNext()) {
            Property property = (Property)object.next();
            try {
                blockState3 = this.copySingleProp(blockState2, blockState3, property);
            }
            catch (IllegalArgumentException illegalArgumentException) {}
        }
        this.blockStateCache.computeIfAbsent(blockState2, blockState -> new HashMap()).put(block, blockState3);
        return blockState3;
    }

    private <T extends Comparable<T>> BlockState copySingleProp(BlockState blockState, BlockState blockState2, Property<T> property) {
        return (BlockState)blockState2.setValue(property, blockState.getValue(property));
    }
}

