/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.cc;
import baritone.do;
import baritone.fb;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class dn {
    public final int[] a = new int[Block.BLOCK_STATE_REGISTRY.size()];

    public final int a(int n2, BlockState blockState) {
        int n3;
        int n4 = 0;
        int n5 = cc.c(blockState);
        if (n5 == do.a) {
            n4 = 2;
        }
        if (n5 == do.b) {
            n4 |= 4;
        }
        if ((n5 = cc.a(blockState)) == do.a) {
            n4 |= 8;
        }
        if (n5 == do.b) {
            n4 |= 0x10;
        }
        if ((n3 = cc.b(blockState)) == do.a) {
            n4 |= 0x20;
        }
        if (n3 == do.b) {
            n4 |= 0x40;
        }
        this.a[n2] = n4 |= 1;
        return n4;
    }

    public final boolean a(fb fb2, int n2, int n3, int n4, BlockState blockState) {
        int n5 = Block.BLOCK_STATE_REGISTRY.getId((Object)blockState);
        int n6 = this.a[n5];
        if ((n6 & 1) == 0) {
            n6 = this.a(n5, blockState);
        }
        if ((n6 & 0x10) != 0) {
            return cc.c(fb2, n2, n3, n4, blockState);
        }
        return (n6 & 8) != 0;
    }
}

