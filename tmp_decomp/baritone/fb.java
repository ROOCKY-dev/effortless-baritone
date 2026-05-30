/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientChunkCache
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.status.ChunkStatus
 */
package baritone;

import baritone.a;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.fc;
import baritone.fo;
import baritone.fu;
import baritone.n;
import baritone.t;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class fb {
    public final ClientChunkCache a;
    public final t a;
    protected final Level a;
    private BlockPos.MutableBlockPos a;
    private fc a;
    public final fu a;
    public LevelChunk a;
    public n a;
    private final boolean a;
    private static final BlockState a = Blocks.AIR.defaultBlockState();

    public fb(IPlayerContext iPlayerContext) {
        this(iPlayerContext, false);
    }

    public fb(IPlayerContext iPlayerContext, boolean bl2) {
        this.a = null;
        this.a = null;
        this.a = iPlayerContext.world();
        this.a = new fu(this.a.getWorldBorder());
        this.a = (t)iPlayerContext.worldData();
        this.a = bl2 ? ((fo)this.a.getChunkSource()).createThreadSafeCopy() : (ClientChunkCache)this.a.getChunkSource();
        boolean bl3 = this.a = (Boolean)baritone.a.a().pathThroughCachedOnly.value == false;
        if (!iPlayerContext.minecraft().isSameThread()) {
            throw new IllegalStateException("BlockStateInterface must be constructed on the main thread");
        }
        this.a = new BlockPos.MutableBlockPos();
        this.a = new fc(this);
    }

    public final boolean a(int n2, int n3) {
        return this.a.hasChunk(n2 >> 4, n3 >> 4);
    }

    public static Block a(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos) {
        return fb.a(iPlayerContext, (BlockPos)betterBlockPos).getBlock();
    }

    public static BlockState a(IPlayerContext iPlayerContext, BlockPos blockPos) {
        return new fb(iPlayerContext).a(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public final BlockState a(BlockPos blockPos) {
        return this.a(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public final BlockState a(int n2, int n3, int n4) {
        Object object;
        if ((n3 -= this.a.dimensionType().minY()) < 0 || n3 >= this.a.dimensionType().height()) {
            return a;
        }
        if (this.a) {
            object = this.a;
            if (object != null && object.getPos().x == n2 >> 4 && object.getPos().z == n4 >> 4) {
                return fb.a((LevelChunk)object, n2, n3, n4);
            }
            object = this.a.getChunk(n2 >> 4, n4 >> 4, ChunkStatus.FULL, false);
            if (object != null && !object.isEmpty()) {
                this.a = object;
                return fb.a((LevelChunk)object, n2, n3, n4);
            }
        }
        if ((object = this.a) == null || ((n)object).getX() != n2 >> 9 || ((n)object).getZ() != n4 >> 9) {
            if (this.a == null) {
                return a;
            }
            object = this.a.a.a(n2 >> 9, n4 >> 9);
            if (object == null) {
                return a;
            }
            this.a = object;
        }
        if ((object = ((n)object).getBlock(n2 & 0x1FF, n3 + this.a.dimensionType().minY(), n4 & 0x1FF)) == null) {
            return a;
        }
        return object;
    }

    public static BlockState a(LevelChunk levelChunk, int n2, int n3, int n4) {
        if ((levelChunk = levelChunk.getSections()[n3 >> 4]).hasOnlyAir()) {
            return a;
        }
        return levelChunk.getBlockState(n2 & 0xF, n3 & 0xF, n4 & 0xF);
    }
}

