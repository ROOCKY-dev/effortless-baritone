/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.schematic.ISchematic;
import baritone.api.schematic.MaskSchematic;
import baritone.api.selection.ISelection;
import java.util.stream.Stream;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;

public final class gd
extends MaskSchematic {
    private final ISelection[] a;

    public gd(ISchematic iSchematic, Vec3i vec3i, ISelection[] iSelectionArray) {
        super(iSchematic);
        this.a = (ISelection[])Stream.of(iSelectionArray).map(iSelection -> iSelection.shift(Direction.WEST, vec3i.getX()).shift(Direction.DOWN, vec3i.getY()).shift(Direction.NORTH, vec3i.getZ())).toArray(ISelection[]::new);
    }

    @Override
    public final boolean partOfMask(int n2, int n3, int n4, BlockState iSelectionArray) {
        iSelectionArray = this.a;
        int n5 = this.a.length;
        for (int i2 = 0; i2 < n5; ++i2) {
            ISelection iSelection = iSelectionArray[i2];
            if (n2 < iSelection.min().x || n3 < iSelection.min().y || n4 < iSelection.min().z || n2 > iSelection.max().x || n3 > iSelection.max().y || n4 > iSelection.max().z) continue;
            return true;
        }
        return false;
    }
}

