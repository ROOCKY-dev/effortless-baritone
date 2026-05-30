/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.dimension.DimensionType
 */
package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.cache.ICachedRegion;
import baritone.api.cache.ICachedWorld;
import baritone.api.cache.IWorldData;
import baritone.api.utils.Helper;
import baritone.m;
import baritone.n;
import baritone.p;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class o
implements ICachedWorld,
Helper {
    private Long2ObjectMap<n> a;
    private final String a;
    final LinkedBlockingQueue<ChunkPos> a;
    final Map<ChunkPos, LevelChunk> a;
    private final DimensionType a = CacheBuilder.newBuilder().softValues().build().asMap();

    o(Path path, DimensionType dimensionType) {
        if (!Files.exists(path, new LinkOption[0])) {
            try {
                Files.createDirectories(path, new FileAttribute[0]);
            }
            catch (IOException iOException) {}
        }
        this.a = path.toString();
        this.a = dimensionType;
        System.out.println("Cached world directory: " + String.valueOf(path));
        baritone.a.a().execute(new a(this));
        baritone.a.a().execute(() -> {
            try {
                Thread.sleep(30000L);
                while (true) {
                    this.save();
                    Thread.sleep(600000L);
                }
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return;
            }
        });
    }

    @Override
    public final void queueForPacking(LevelChunk levelChunk) {
        if (this.a.put(levelChunk.getPos(), levelChunk) == null) {
            this.a.add((n)levelChunk.getPos());
        }
    }

    @Override
    public final boolean isCached(int n2, int n3) {
        n n4 = this.a(n2 >> 9, n3 >> 9);
        if (n4 == null) {
            return false;
        }
        return n4.isCached(n2 & 0x1FF, n3 & 0x1FF);
    }

    @Override
    public final ArrayList<BlockPos> getLocationsOf(String string, int n2, int n3, int n4, int n5) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        n3 >>= 9;
        n4 >>= 9;
        for (int i2 = 0; i2 <= n5; ++i2) {
            for (int i3 = -i2; i3 <= i2; ++i3) {
                for (int i4 = -i2; i4 <= i2; ++i4) {
                    int n6;
                    int n7;
                    n n8;
                    int n9 = i3;
                    int n10 = i4;
                    if (n9 * n9 + n10 * n10 != i2 || (n8 = this.b(n7 = i3 + n3, n6 = i4 + n4)) == null) continue;
                    String string2 = string;
                    ArrayList arrayList2 = new ArrayList();
                    for (int i5 = 0; i5 < 32; ++i5) {
                        for (int i6 = 0; i6 < 32; ++i6) {
                            Object object;
                            if (n8.a[i5][i6] == null) continue;
                            String string3 = string2;
                            m m2 = n8.a[i5][i6];
                            if (m2.a.get(string3) == null) {
                                object = null;
                            } else {
                                ArrayList<BlockPos> arrayList3 = new ArrayList<BlockPos>();
                                for (BlockPos blockPos : (List)m2.a.get(string3)) {
                                    arrayList3.add(new BlockPos(blockPos.getX() + (m2.b << 4), blockPos.getY(), blockPos.getZ() + (m2.c << 4)));
                                }
                                object = m2 = arrayList3;
                            }
                            if (object == null) continue;
                            arrayList2.addAll(m2);
                        }
                    }
                    arrayList.addAll(arrayList2);
                }
            }
            if (arrayList.size() < n2) continue;
            return arrayList;
        }
        return arrayList;
    }

    @Override
    public final void save() {
        if (!((Boolean)baritone.a.a().chunkCaching.value).booleanValue()) {
            System.out.println("Not saving to disk; chunk caching is disabled.");
            this.a().forEach(n2 -> {
                if (n2 != null) {
                    n2.a();
                }
            });
            this.a();
            return;
        }
        long l2 = System.nanoTime() / 1000000L;
        this.a().parallelStream().forEach(n2 -> {
            if (n2 != null) {
                n2.a(this.a);
            }
        });
        long l3 = System.nanoTime() / 1000000L;
        System.out.println("World save took " + (l3 - l2) + "ms");
        this.a();
    }

    private synchronized void a() {
        BlockPos blockPos;
        Object object;
        block4: {
            if (!((Boolean)baritone.a.a().pruneRegionsFromRAM.value).booleanValue()) {
                return;
            }
            object = this;
            for (IBaritone object2 : BaritoneAPI.getProvider().getAllBaritones()) {
                IWorldData iWorldData = object2.getWorldProvider().getCurrentWorld();
                if (iWorldData == null || iWorldData.getCachedWorld() != object || object2.getPlayerContext().player() == null) continue;
                blockPos = object2.getPlayerContext().playerFeet();
                break block4;
            }
            Object object4 = null;
            for (n n2 : ((o)object).a()) {
                if (n2 == null || (object = n2.a()) == null || object4 != null && ((m)object4).a >= ((m)object).a) continue;
                object4 = object;
            }
            blockPos = object4 == null ? new BlockPos(0, 0, 0) : new BlockPos((((m)object4).b << 4) + 8, 0, (((m)object4).c << 4) + 8);
        }
        object = blockPos;
        for (n n3 : this.a()) {
            if (n3 == null) continue;
            int n4 = (n3.getX() << 9) + 256 - object.getX();
            int n5 = (n3.getZ() << 9) + 256 - object.getZ();
            int n6 = n4;
            int n7 = n5;
            if (!(Math.sqrt(n6 * n6 + n7 * n7) > 1024.0)) continue;
            this.logDebug("Deleting cached region from ram");
            this.a.remove(o.a(n3.getX(), n3.getZ()));
        }
    }

    private synchronized List<n> a() {
        return new ArrayList<n>((Collection<n>)this.a.values());
    }

    @Override
    public final void reloadAllFromDisk() {
        long l2 = System.nanoTime() / 1000000L;
        this.a().forEach(n2 -> {
            if (n2 != null) {
                n2.b(this.a);
            }
        });
        long l3 = System.nanoTime() / 1000000L;
        System.out.println("World load took " + (l3 - l2) + "ms");
    }

    public final synchronized n a(int n2, int n3) {
        return (n)this.a.get(o.a(n2, n3));
    }

    public final synchronized n b(int n2, int n3) {
        return (n)this.a.computeIfAbsent(o.a(n2, n3), l2 -> {
            n n4 = new n(n2, n3, this.a);
            n4.b(this.a);
            return n4;
        });
    }

    private static long a(int n2, int n3) {
        if (!o.a(n2, n3)) {
            return 0L;
        }
        return (long)n2 & 0xFFFFFFFFL | ((long)n3 & 0xFFFFFFFFL) << 32;
    }

    private static boolean a(int n2, int n3) {
        return n2 <= 58594 && n2 >= -58594 && n3 <= 58594 && n3 >= -58594;
    }

    @Override
    public final /* synthetic */ ICachedRegion getRegion(int n2, int n3) {
        return this.a(n2, n3);
    }

    final class a
    implements Runnable {
        private /* synthetic */ o a;

        a(o o2) {
            this.a = o2;
        }

        @Override
        public final void run() {
            while (true) {
                try {
                    while (true) {
                        Object object = (ChunkPos)this.a.a.take();
                        object = (LevelChunk)this.a.a.remove(object);
                        if (this.a.a.size() > (Integer)baritone.a.a().chunkPackerQueueMaxSize.value) continue;
                        object = p.a((LevelChunk)object);
                        this.a.b(object.b >> 5, object.c >> 5).a(object.b & 0x1F, object.c & 0x1F, (m)object);
                    }
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                    return;
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                    continue;
                }
                break;
            }
        }
    }
}

