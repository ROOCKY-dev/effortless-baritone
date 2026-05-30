/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone.api.process;

import baritone.api.process.IBaritoneProcess;
import net.minecraft.core.BlockPos;

public interface IFarmProcess
extends IBaritoneProcess {
    public void farm(int var1, BlockPos var2);

    default public void farm() {
        this.farm(0, null);
    }

    default public void farm(int n2) {
        this.farm(n2, null);
    }
}

