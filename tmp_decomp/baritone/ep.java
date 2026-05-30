/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.a;
import baritone.api.event.events.BlockChangeEvent;
import baritone.api.utils.BetterBlockPos;
import baritone.em;
import baritone.er;
import baritone.fr;
import dev.babbaj.pathfinder.NetherPathfinder;
import dev.babbaj.pathfinder.Octree;
import java.lang.ref.SoftReference;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ep {
    private static final BlockState a = Blocks.AIR.defaultBlockState();
    public final Object a;
    final long a;
    public final long b;
    final ExecutorService a = new Object();

    public ep(long l2) {
        this.a = NetherPathfinder.newContext(l2);
        this.b = l2;
        this.a = Executors.newSingleThreadExecutor();
    }

    public final void a(int n2, int n3, int n4, em em2) {
        this.a.execute(() -> {
            Object object = this.a;
            synchronized (object) {
                em2.b = 0L;
                NetherPathfinder.cullFarChunks(this.a, n2, n3, n4);
                return;
            }
        });
    }

    public final void a(LevelChunk object) {
        object = new SoftReference<LevelChunk>((LevelChunk)object);
        this.a.execute(() -> this.a((SoftReference)object));
    }

    public final void a(BlockChangeEvent blockChangeEvent) {
        this.a.execute(() -> {
            ChunkPos chunkPos = blockChangeEvent.getChunkPos();
            long l2 = NetherPathfinder.getChunkPointer(this.a, chunkPos.x, chunkPos.z);
            if (l2 == 0L) {
                return;
            }
            blockChangeEvent.getBlocks().forEach(pair -> {
                BlockPos blockPos = (BlockPos)pair.first();
                if (blockPos.getY() >= 128) {
                    return;
                }
                boolean bl2 = pair.second() != a;
                Octree.setBlock(l2, blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF, bl2);
            });
        });
    }

    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    public final CompletableFuture a(BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        return CompletableFuture.supplyAsync(() -> {
            if ((betterBlockPos = NetherPathfinder.pathFind(this.a, betterBlockPos.getX(), betterBlockPos.getY(), betterBlockPos.getZ(), betterBlockPos2.getX(), betterBlockPos2.getY(), betterBlockPos2.getZ(), true, false, 10000, (Boolean)baritone.a.a().elytraPredictTerrain.value == false)) == null) {
                throw new er("Path calculation failed");
            }
            return betterBlockPos;
        }, this.a);
    }

    public final boolean a(Vec3 vec3, Vec3 vec32) {
        return NetherPathfinder.isVisible(this.a, NetherPathfinder.CACHE_MISS_SOLID, vec3.x, vec3.y, vec3.z, vec32.x, vec32.y, vec32.z);
    }

    public final boolean a(double[] dArray, double[] dArray2) {
        return NetherPathfinder.isVisibleMulti$eb34986(this.a, NetherPathfinder.CACHE_MISS_SOLID, dArray, dArray2) == -1;
    }

    public static boolean a() {
        return NetherPathfinder.isThisSystemSupported();
    }

    private /* synthetic */ void a(SoftReference object) {
        if ((object = (LevelChunk)object.get()) != null) {
            long l2;
            long l3 = l2 = NetherPathfinder.getOrCreateChunk(this.a, object.getPos().x, object.getPos().z);
            try {
                object = object.getSections();
                for (int i2 = 0; i2 < 8; ++i2) {
                    LevelChunkSection levelChunkSection = object[i2];
                    if (levelChunkSection == null) continue;
                    levelChunkSection = levelChunkSection.getStates();
                    int n2 = ((fr)levelChunkSection).getPalette().idFor((Object)a);
                    if ((levelChunkSection = ((fr)levelChunkSection).getStorage()) == null) continue;
                    long[] lArray = levelChunkSection.getRaw();
                    int n3 = levelChunkSection.getSize();
                    int n4 = levelChunkSection.getBits();
                    long l4 = (1L << n4) - 1L;
                    int n5 = i2 << 4;
                    int n6 = 0;
                    for (int i3 = 0; i3 < lArray.length && n6 < n3; ++i3) {
                        long l5 = lArray[i3];
                        for (int i4 = 0; i4 <= 64 - n4 && n6 < n3; i4 += n4, ++n6) {
                            int n7 = (int)(l5 >> i4 & l4);
                            int n8 = n6 & 0xF;
                            int n9 = n5 + (n6 >> 8);
                            int n10 = n6 >> 4 & 0xF;
                            Octree.setBlock(l3, n8, n9, n10, n7 != n2);
                        }
                    }
                }
                Octree.setIsFromJava(l3);
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
                throw new RuntimeException((Throwable)object);
            }
        }
    }
}

