/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.lunatrius.schematica.client.world.SchematicWorld
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.utils.schematic.schematica;

import baritone.api.schematic.IStaticSchematic;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class SchematicAdapter
implements IStaticSchematic {
    private final SchematicWorld a;

    public SchematicAdapter(SchematicWorld schematicWorld) {
        this.a = schematicWorld;
    }

    @Override
    public final BlockState desiredState(int n2, int n3, int n4, BlockState blockState, List<BlockState> list) {
        return this.getDirect(n2, n3, n4);
    }

    @Override
    public final BlockState getDirect(int n2, int n3, int n4) {
        return this.a.getSchematic().getBlockState(new BlockPos(n2, n3, n4));
    }

    @Override
    public final int widthX() {
        return this.a.getSchematic().getWidth();
    }

    @Override
    public final int heightY() {
        return this.a.getSchematic().getHeight();
    }

    @Override
    public final int lengthZ() {
        return this.a.getSchematic().getLength();
    }
}

