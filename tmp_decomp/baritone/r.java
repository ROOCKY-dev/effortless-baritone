/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkSource
 *  net.minecraft.world.level.chunk.GlobalPalette
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.Palette
 *  net.minecraft.world.level.chunk.SingleValuePalette
 */
package baritone;

import baritone.api.cache.ICachedWorld;
import baritone.api.cache.IWorldScanner;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.IPlayerContext;
import baritone.fr;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.GlobalPalette;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.SingleValuePalette;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class r
extends Enum<r>
implements IWorldScanner {
    public static final /* enum */ r a = new r("INSTANCE");
    private static final BlockState[] a;
    private static final /* synthetic */ r[] a;

    public static r[] values() {
        return (r[])a.clone();
    }

    public static r valueOf(String string) {
        return Enum.valueOf(r.class, string);
    }

    @Override
    public final List<BlockPos> scanChunkRadius(IPlayerContext iPlayerContext, BlockOptionalMetaLookup blockOptionalMetaLookup, int n2, int n3, int n4) {
        assert (iPlayerContext.world() != null);
        if (n4 < 0) {
            throw new IllegalArgumentException("chunkRange must be >= 0");
        }
        n3 = n4;
        int n5 = iPlayerContext.playerFeet().z >> 4;
        int n6 = iPlayerContext.playerFeet().x >> 4;
        ArrayList<ChunkPos> arrayList = new ArrayList<ChunkPos>();
        arrayList.add(new ChunkPos(n6, n5));
        for (int i2 = 1; i2 < n3; ++i2) {
            for (int i3 = 0; i3 <= i2; ++i3) {
                arrayList.add(new ChunkPos(n6 - i3, n5 - i2));
                if (i3 != 0) {
                    arrayList.add(new ChunkPos(n6 + i3, n5 - i2));
                    arrayList.add(new ChunkPos(n6 - i3, n5 + i2));
                }
                arrayList.add(new ChunkPos(n6 + i3, n5 + i2));
                if (i3 == i2) continue;
                arrayList.add(new ChunkPos(n6 - i2, n5 - i3));
                arrayList.add(new ChunkPos(n6 + i2, n5 - i3));
                if (i3 == 0) continue;
                arrayList.add(new ChunkPos(n6 - i2, n5 + i3));
                arrayList.add(new ChunkPos(n6 + i2, n5 + i3));
            }
        }
        return this.a(iPlayerContext, blockOptionalMetaLookup, arrayList, n2);
    }

    @Override
    public final List<BlockPos> scanChunk(IPlayerContext stream, BlockOptionalMetaLookup blockOptionalMetaLookup, ChunkPos chunkPos, int n2, int n3) {
        stream = r.a((IPlayerContext)((Object)stream), blockOptionalMetaLookup, chunkPos);
        if (n2 >= 0) {
            stream = stream.limit(n2);
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public final int repack(IPlayerContext iPlayerContext) {
        return this.repack(iPlayerContext, 40);
    }

    @Override
    public final int repack(IPlayerContext object, int n2) {
        ChunkSource chunkSource = object.world().getChunkSource();
        ICachedWorld iCachedWorld = object.worldData().getCachedWorld();
        object = object.playerFeet();
        int n3 = object.getX() >> 4;
        int n4 = object.getZ() >> 4;
        int n5 = n3 - n2;
        int n6 = n4 - n2;
        n3 += n2;
        n4 += n2;
        n2 = 0;
        while (n5 <= n3) {
            for (int i2 = n6; i2 <= n4; ++i2) {
                LevelChunk levelChunk = chunkSource.getChunk(n5, i2, false);
                if (levelChunk == null || levelChunk.isEmpty()) continue;
                ++n2;
                iCachedWorld.queueForPacking(levelChunk);
            }
            ++n5;
        }
        return n2;
    }

    private List<BlockPos> a(IPlayerContext stream, BlockOptionalMetaLookup blockOptionalMetaLookup, List<ChunkPos> list, int n2) {
        assert (stream.world() != null);
        try {
            stream = list.parallelStream().flatMap(arg_0 -> this.b((IPlayerContext)((Object)stream), blockOptionalMetaLookup, arg_0));
            if (n2 >= 0) {
                stream = stream.limit(n2);
            }
            return stream.collect(Collectors.toList());
        }
        catch (Exception exception) {
            stream = exception;
            exception.printStackTrace();
            throw stream;
        }
    }

    private static Stream<BlockPos> a(IPlayerContext iPlayerContext, BlockOptionalMetaLookup blockOptionalMetaLookup, ChunkPos chunkPos) {
        ChunkSource chunkSource = iPlayerContext.world().getChunkSource();
        if (!chunkSource.hasChunk(chunkPos.x, chunkPos.z)) {
            return Stream.empty();
        }
        long l2 = (long)chunkPos.x << 4;
        long l3 = (long)chunkPos.z << 4;
        int n2 = iPlayerContext.playerFeet().y - iPlayerContext.world().getMinBuildHeight() >> 4;
        return r.a(blockOptionalMetaLookup, chunkSource.getChunk(chunkPos.x, chunkPos.z, false), l2, l3, n2).stream();
    }

    private static List<BlockPos> a(BlockOptionalMetaLookup blockOptionalMetaLookup, LevelChunk levelChunkSectionArray, long l2, long l3, int n2) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        int n3 = levelChunkSectionArray.getMinBuildHeight();
        levelChunkSectionArray = levelChunkSectionArray.getSections();
        int n4 = levelChunkSectionArray.length;
        for (int i2 = n2 - 1; i2 >= 0 || n2 < n4; ++n2, --i2) {
            if (n2 < n4) {
                r.a(blockOptionalMetaLookup, levelChunkSectionArray[n2], arrayList, l2, n3 + (n2 << 4), l3);
            }
            if (i2 < 0) continue;
            r.a(blockOptionalMetaLookup, levelChunkSectionArray[i2], arrayList, l2, n3 + (i2 << 4), l3);
        }
        return arrayList;
    }

    private static void a(BlockOptionalMetaLookup object, LevelChunkSection levelChunkSection, List<BlockPos> list, long l2, int n2, long l3) {
        if (levelChunkSection == null || levelChunkSection.hasOnlyAir()) {
            return;
        }
        Object object2 = levelChunkSection.getStates();
        if (((fr)object2).getStorage() == null) {
            return;
        }
        if ((object2 = ((fr)object2).getPalette()) instanceof SingleValuePalette) {
            if (((BlockOptionalMetaLookup)object).has((BlockState)object2.valueFor(0))) {
                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int i3 = 0; i3 < 16; ++i3) {
                        for (int i4 = 0; i4 < 16; ++i4) {
                            list.add(new BlockPos((int)l2 + i2, n2 + i3, (int)l3 + i4));
                        }
                    }
                }
            }
            return;
        }
        boolean[] blArray = r.a((BlockOptionalMetaLookup)object, object2);
        object = blArray;
        if (blArray.length == 0) {
            return;
        }
        levelChunkSection = ((fr)levelChunkSection.getStates()).getStorage();
        object2 = levelChunkSection.getRaw();
        int n3 = levelChunkSection.getSize();
        int n4 = levelChunkSection.getBits();
        long l4 = (1L << n4) - 1L;
        int n5 = 0;
        for (int i5 = 0; i5 < ((Palette<T>)object2).length && n5 < n3; ++i5) {
            Palette palette = object2[i5];
            for (int i6 = 0; i6 <= 64 - n4 && n5 < n3; i6 += n4, ++n5) {
                int n6 = (int)(palette >> i6 & l4);
                if (object[n6] == false) continue;
                list.add(new BlockPos((int)l2 + (n5 & 0xFF & 0xF), n2 + (n5 >> 8), (int)l3 + ((n5 & 0xFF) >> 4)));
            }
        }
    }

    private static boolean[] a(BlockOptionalMetaLookup blockOptionalMetaLookup, Palette<BlockState> blockStateArray) {
        boolean bl2 = false;
        if ((blockStateArray = r.a(blockStateArray)) == a) {
            return r.a(blockOptionalMetaLookup);
        }
        int n2 = blockStateArray.length;
        boolean[] blArray = new boolean[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            BlockState blockState = blockStateArray[i2];
            if (blockOptionalMetaLookup.has(blockState)) {
                blArray[i2] = true;
                bl2 = true;
                continue;
            }
            blArray[i2] = false;
        }
        if (!bl2) {
            return new boolean[0];
        }
        return blArray;
    }

    private static boolean[] a(BlockOptionalMetaLookup object) {
        boolean[] blArray = new boolean[Block.BLOCK_STATE_REGISTRY.size()];
        object = ((BlockOptionalMetaLookup)object).blocks().iterator();
        while (object.hasNext()) {
            for (BlockState blockState : ((BlockOptionalMeta)object.next()).getAllBlockStates()) {
                blArray[Block.BLOCK_STATE_REGISTRY.getId((Object)blockState)] = true;
            }
        }
        return blArray;
    }

    private static BlockState[] a(Palette<BlockState> palette) {
        if (palette instanceof GlobalPalette) {
            return a;
        }
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
        palette.write(friendlyByteBuf);
        int n2 = friendlyByteBuf.readVarInt();
        BlockState[] blockStateArray = new BlockState[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            BlockState blockState = (BlockState)Block.BLOCK_STATE_REGISTRY.byId(friendlyByteBuf.readVarInt());
            assert (blockState != null);
            blockStateArray[i2] = blockState;
        }
        return blockStateArray;
    }

    private /* synthetic */ Stream b(IPlayerContext iPlayerContext, BlockOptionalMetaLookup blockOptionalMetaLookup, ChunkPos chunkPos) {
        return r.a(iPlayerContext, blockOptionalMetaLookup, chunkPos);
    }

    static {
        a = new r[]{a};
        a = new BlockState[0];
    }
}

