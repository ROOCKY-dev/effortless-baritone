/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 */
package baritone.api.process;

import baritone.api.process.IBaritoneProcess;
import baritone.api.utils.BlockOptionalMeta;
import net.minecraft.world.level.block.Block;

public interface IGetToBlockProcess
extends IBaritoneProcess {
    public void getToBlock(BlockOptionalMeta var1);

    default public void getToBlock(Block block) {
        this.getToBlock(new BlockOptionalMeta(block));
    }

    public boolean blacklistClosest();
}

