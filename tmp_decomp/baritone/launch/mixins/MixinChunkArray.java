/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.chunk.LevelChunk
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package baritone.launch.mixins;

import baritone.fn;
import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets={"net.minecraft.client.multiplayer.ClientChunkCache$Storage"})
public abstract class MixinChunkArray
implements fn {
    @Final
    @Shadow
    AtomicReferenceArray<LevelChunk> chunks;
    @Final
    @Shadow
    int chunkRadius;
    @Final
    @Shadow
    private int viewRange;
    @Shadow
    int viewCenterX;
    @Shadow
    int viewCenterZ;
    @Shadow
    int chunkCount;

    @Shadow
    abstract boolean inRange(int var1, int var2);

    @Shadow
    abstract int getIndex(int var1, int var2);

    @Shadow
    protected abstract void replace(int var1, LevelChunk var2);

    @Override
    public int centerX() {
        return this.viewCenterX;
    }

    @Override
    public int centerZ() {
        return this.viewCenterZ;
    }

    @Override
    public int viewDistance() {
        return this.chunkRadius;
    }

    @Override
    public AtomicReferenceArray<LevelChunk> getChunks() {
        return this.chunks;
    }

    @Override
    public void copyFrom(fn object) {
        this.viewCenterX = object.centerX();
        this.viewCenterZ = object.centerZ();
        object = object.getChunks();
        for (int i2 = 0; i2 < ((AtomicReferenceArray)object).length(); ++i2) {
            LevelChunk levelChunk = (LevelChunk)((AtomicReferenceArray)object).get(i2);
            if (levelChunk == null) continue;
            ChunkPos chunkPos = levelChunk.getPos();
            if (!this.inRange(chunkPos.x, chunkPos.z)) continue;
            int n2 = this.getIndex(chunkPos.x, chunkPos.z);
            if (this.chunks.get(n2) != null) {
                throw new IllegalStateException("Doing this would mutate the client's REAL loaded chunks?!");
            }
            this.replace(n2, levelChunk);
        }
    }
}

