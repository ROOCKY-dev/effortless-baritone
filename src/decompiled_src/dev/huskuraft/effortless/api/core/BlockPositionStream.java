/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.math.MathUtils;
import java.util.Iterator;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class BlockPositionStream
implements BaseStream<BlockPosition, BlockPositionStream> {
    private static BlockPosition transform(BlockPosition original, Axis first, Axis second) {
        assert (first != second);
        Axis third = Axis.values()[3 - first.ordinal() - second.ordinal()];
        return new BlockPosition(original.get(first), original.get(second), original.get(third));
    }

    private static BlockPosition recover(BlockPosition original, Axis first, Axis second) {
        Axis third = Axis.values()[3 - first.ordinal() - second.ordinal()];
        int[] arr = new int[3];
        arr[first.ordinal()] = original.x();
        arr[second.ordinal()] = original.y();
        arr[third.ordinal()] = original.z();
        return new BlockPosition(arr[0], arr[1], arr[2]);
    }

    static Stream<BlockPosition> of(BlockPosition position) {
        return Stream.of(position);
    }

    static Stream<BlockPosition> between(BlockPosition start, BlockPosition end) {
        return BlockPositionStream.between(start, end, Axis.X, Axis.Y);
    }

    static Stream<BlockPosition> between(BlockPosition start, BlockPosition end, Axis first, Axis second) {
        return StreamSupport.stream(BlockPositionStream.betweenClosed(BlockPositionStream.transform(start, first, second), BlockPositionStream.transform(end, first, second)).spliterator(), false).map(pos -> BlockPositionStream.recover(pos, first, second));
    }

    static Stream<BlockPosition> between(int x1, int y1, int z1, int x2, int y2, int z2) {
        return BlockPositionStream.between(new BlockPosition(x1, y1, z1), new BlockPosition(x2, y2, z2));
    }

    static Stream<BlockPosition> between(int x1, int y1, int z1, int x2, int y2, int z2, Axis first, Axis second) {
        return BlockPositionStream.between(new BlockPosition(x1, y1, z1), new BlockPosition(x2, y2, z2), first, second);
    }

    private static Iterable<BlockPosition> betweenClosed(BlockPosition start, BlockPosition end) {
        return BlockPositionStream.betweenClosed(start.x(), start.y(), start.z(), end.x(), end.y(), end.z());
    }

    private static Iterable<BlockPosition> betweenClosed(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        return () -> new Iterator<BlockPosition>(){
            private final int x;
            private final int y;
            private final int z;
            private final int size;
            private int index;
            {
                this.x = MathUtils.abs(x2 - x1) + 1;
                this.y = MathUtils.abs(y2 - y1) + 1;
                this.z = MathUtils.abs(z2 - z1) + 1;
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
                return new BlockPosition(x1 + ix * (x2 > x1 ? 1 : -1), y1 + jx * (y2 > y1 ? 1 : -1), z1 + kx * (z2 > z1 ? 1 : -1));
            }
        };
    }
}
