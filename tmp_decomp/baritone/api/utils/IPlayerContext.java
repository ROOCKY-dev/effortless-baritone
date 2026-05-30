/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package baritone.api.utils;

import baritone.api.cache.IWorldData;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerController;
import baritone.api.utils.Rotation;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public interface IPlayerContext {
    public Minecraft minecraft();

    public LocalPlayer player();

    public IPlayerController playerController();

    public Level world();

    default public Iterable<Entity> entities() {
        return ((ClientLevel)this.world()).entitiesForRendering();
    }

    default public Stream<Entity> entitiesStream() {
        return StreamSupport.stream(this.entities().spliterator(), false);
    }

    public IWorldData worldData();

    public HitResult objectMouseOver();

    default public BetterBlockPos playerFeet() {
        BetterBlockPos betterBlockPos = new BetterBlockPos(this.player().position().x, this.player().position().y + 0.1251, this.player().position().z);
        try {
            if (this.world().getBlockState((BlockPos)betterBlockPos).getBlock() instanceof SlabBlock) {
                return betterBlockPos.above();
            }
        }
        catch (NullPointerException nullPointerException) {}
        return betterBlockPos;
    }

    default public Vec3 playerFeetAsVec() {
        return new Vec3(this.player().position().x, this.player().position().y, this.player().position().z);
    }

    default public Vec3 playerHead() {
        return new Vec3(this.player().position().x, this.player().position().y + (double)this.player().getEyeHeight(), this.player().position().z);
    }

    default public Vec3 playerMotion() {
        return this.player().getDeltaMovement();
    }

    public BetterBlockPos viewerPos();

    default public Rotation playerRotations() {
        return new Rotation(this.player().getYRot(), this.player().getXRot());
    }

    @Deprecated
    public static double eyeHeight(boolean bl2) {
        if (bl2) {
            return 1.27;
        }
        return 1.62;
    }

    default public Optional<BlockPos> getSelectedBlock() {
        HitResult hitResult = this.objectMouseOver();
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            return Optional.of(((BlockHitResult)hitResult).getBlockPos());
        }
        return Optional.empty();
    }

    default public boolean isLookingAt(BlockPos blockPos) {
        return this.getSelectedBlock().equals(Optional.of(blockPos));
    }
}

