/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.process;

import baritone.api.process.IBaritoneProcess;
import baritone.api.schematic.ISchematic;
import java.io.File;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;

public interface IBuilderProcess
extends IBaritoneProcess {
    public void build(String var1, ISchematic var2, Vec3i var3);

    public boolean build(String var1, File var2, Vec3i var3);

    @Deprecated
    default public boolean build(String string, BlockPos blockPos) {
        File file = new File(new File(Minecraft.getInstance().gameDirectory, "schematics"), string);
        return this.build(string, file, (Vec3i)blockPos);
    }

    public void buildOpenSchematic();

    public void buildOpenLitematic(int var1);

    public void pause();

    public boolean isPaused();

    public void resume();

    public void clearArea(BlockPos var1, BlockPos var2);

    public List<BlockState> getApproxPlaceable();

    public Optional<Integer> getMinLayer();

    public Optional<Integer> getMaxLayer();
}

