/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic;

import baritone.api.schematic.ISchematic;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public class RotatedSchematic
implements ISchematic {
    private final ISchematic schematic;
    private final Rotation rotation;
    private final Rotation inverseRotation;

    public RotatedSchematic(ISchematic iSchematic, Rotation rotation) {
        this.schematic = iSchematic;
        this.rotation = rotation;
        Rotation rotation2 = rotation;
        this.inverseRotation = rotation2.getRotated(rotation2).getRotated(rotation);
    }

    @Override
    public boolean inSchematic(int n2, int n3, int n4, BlockState blockState) {
        return this.schematic.inSchematic(RotatedSchematic.rotateX(n2, n4, this.widthX(), this.lengthZ(), this.inverseRotation), n3, RotatedSchematic.rotateZ(n2, n4, this.widthX(), this.lengthZ(), this.inverseRotation), RotatedSchematic.rotate(blockState, this.inverseRotation));
    }

    @Override
    public BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        return RotatedSchematic.rotate(this.schematic.desiredState(RotatedSchematic.rotateX(n2, n4, this.widthX(), this.lengthZ(), this.inverseRotation), n3, RotatedSchematic.rotateZ(n2, n4, this.widthX(), this.lengthZ(), this.inverseRotation), RotatedSchematic.rotate(blockState, this.inverseRotation), RotatedSchematic.rotate(list, this.inverseRotation)), this.rotation);
    }

    @Override
    public void reset() {
        this.schematic.reset();
    }

    @Override
    public int widthX() {
        if (RotatedSchematic.flipsCoordinates(this.rotation)) {
            return this.schematic.lengthZ();
        }
        return this.schematic.widthX();
    }

    @Override
    public int heightY() {
        return this.schematic.heightY();
    }

    @Override
    public int lengthZ() {
        if (RotatedSchematic.flipsCoordinates(this.rotation)) {
            return this.schematic.widthX();
        }
        return this.schematic.lengthZ();
    }

    private static boolean flipsCoordinates(Rotation rotation) {
        return rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90;
    }

    private static int rotateX(int n2, int n3, int n4, int n5, Rotation rotation) {
        switch (rotation) {
            case NONE: {
                return n2;
            }
            case CLOCKWISE_90: {
                return n5 - n3 - 1;
            }
            case CLOCKWISE_180: {
                return n4 - n2 - 1;
            }
            case COUNTERCLOCKWISE_90: {
                return n3;
            }
        }
        throw new IllegalArgumentException("Unknown rotation");
    }

    private static int rotateZ(int n2, int n3, int n4, int n5, Rotation rotation) {
        switch (rotation) {
            case NONE: {
                return n3;
            }
            case CLOCKWISE_90: {
                return n2;
            }
            case CLOCKWISE_180: {
                return n5 - n3 - 1;
            }
            case COUNTERCLOCKWISE_90: {
                return n4 - n2 - 1;
            }
        }
        throw new IllegalArgumentException("Unknown rotation");
    }

    private static BlockState rotate(BlockState blockState, Rotation rotation) {
        if (blockState == null) {
            return null;
        }
        return blockState.rotate(rotation);
    }

    private static List<BlockState> rotate(List<BlockState> list, Rotation rotation) {
        if (list == null) {
            return null;
        }
        return list.stream().map(blockState -> RotatedSchematic.rotate(blockState, rotation)).collect(Collectors.toList());
    }
}

