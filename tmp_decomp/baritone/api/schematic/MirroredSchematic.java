/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import baritone.api.schematic.ISchematic;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;

public class MirroredSchematic
implements ISchematic {
    private final ISchematic schematic;
    private final Mirror mirror;

    public MirroredSchematic(ISchematic iSchematic, Mirror mirror) {
        this.schematic = iSchematic;
        this.mirror = mirror;
    }

    @Override
    public boolean inSchematic(int n2, int n3, int n4, BlockState blockState) {
        return this.schematic.inSchematic(MirroredSchematic.mirrorX(n2, this.widthX(), this.mirror), n3, MirroredSchematic.mirrorZ(n4, this.lengthZ(), this.mirror), MirroredSchematic.mirror(blockState, this.mirror));
    }

    @Override
    public BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        return MirroredSchematic.mirror(this.schematic.desiredState(MirroredSchematic.mirrorX(n2, this.widthX(), this.mirror), n3, MirroredSchematic.mirrorZ(n4, this.lengthZ(), this.mirror), MirroredSchematic.mirror(blockState, this.mirror), MirroredSchematic.mirror(list, this.mirror)), this.mirror);
    }

    @Override
    public void reset() {
        this.schematic.reset();
    }

    @Override
    public int widthX() {
        return this.schematic.widthX();
    }

    @Override
    public int heightY() {
        return this.schematic.heightY();
    }

    @Override
    public int lengthZ() {
        return this.schematic.lengthZ();
    }

    private static int mirrorX(int n2, int n3, Mirror mirror) {
        switch (mirror) {
            case NONE: 
            case LEFT_RIGHT: {
                return n2;
            }
            case FRONT_BACK: {
                return n3 - n2 - 1;
            }
        }
        throw new IllegalArgumentException("Unknown mirror");
    }

    private static int mirrorZ(int n2, int n3, Mirror mirror) {
        switch (mirror) {
            case NONE: 
            case FRONT_BACK: {
                return n2;
            }
            case LEFT_RIGHT: {
                return n3 - n2 - 1;
            }
        }
        throw new IllegalArgumentException("Unknown mirror");
    }

    private static BlockState mirror(BlockState blockState, Mirror mirror) {
        if (blockState == null) {
            return null;
        }
        return blockState.mirror(mirror);
    }

    private static List<BlockState> mirror(List<BlockState> list, Mirror mirror) {
        if (list == null) {
            return null;
        }
        return list.stream().map(blockState -> MirroredSchematic.mirror(blockState, mirror)).collect(Collectors.toList());
    }
}

