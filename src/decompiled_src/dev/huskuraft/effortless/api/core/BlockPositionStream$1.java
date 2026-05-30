/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.math.MathUtils;
import java.util.Iterator;

static class BlockPositionStream.1
implements Iterator<BlockPosition> {
    private final int x;
    private final int y;
    private final int z;
    private final int size;
    private int index;
    final /* synthetic */ int val$x2;
    final /* synthetic */ int val$x1;
    final /* synthetic */ int val$y2;
    final /* synthetic */ int val$y1;
    final /* synthetic */ int val$z2;
    final /* synthetic */ int val$z1;

    BlockPositionStream.1() {
        this.val$x2 = n;
        this.val$x1 = n2;
        this.val$y2 = n3;
        this.val$y1 = n4;
        this.val$z2 = n5;
        this.val$z1 = n6;
        this.x = MathUtils.abs(this.val$x2 - this.val$x1) + 1;
        this.y = MathUtils.abs(this.val$y2 - this.val$y1) + 1;
        this.z = MathUtils.abs(this.val$z2 - this.val$z1) + 1;
        this.size = this.x * this.y * this.z;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return this.index != this.size;
    }

    @Override
    public BlockPosition next() {
        if (this.index == this.size) {
            return null;
        }
        int ix = this.index % this.x;
        int jx = this.index / this.x % this.y;
        int kx = this.index / (this.x * this.y) % this.z;
        ++this.index;
        return new BlockPosition(this.val$x1 + ix * (this.val$x2 > this.val$x1 ? 1 : -1), this.val$y1 + jx * (this.val$y2 > this.val$y1 ? 1 : -1), this.val$z1 + kx * (this.val$z2 > this.val$z1 ? 1 : -1));
    }
}
