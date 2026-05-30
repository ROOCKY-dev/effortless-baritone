/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.BaseFireBlock
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package baritone.api.utils;

import baritone.api.BaritoneAPI;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.VecUtils;
import java.util.Optional;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class RotationUtils {
    public static final double DEG_TO_RAD = Math.PI / 180;
    public static final float DEG_TO_RAD_F = (float)Math.PI / 180;
    public static final double RAD_TO_DEG = 57.29577951308232;
    public static final float RAD_TO_DEG_F = 57.29578f;
    private static final Vec3[] BLOCK_SIDE_MULTIPLIERS = new Vec3[]{new Vec3(0.5, 0.0, 0.5), new Vec3(0.5, 1.0, 0.5), new Vec3(0.5, 0.5, 0.0), new Vec3(0.5, 0.5, 1.0), new Vec3(0.0, 0.5, 0.5), new Vec3(1.0, 0.5, 0.5)};

    private RotationUtils() {
    }

    public static Rotation calcRotationFromCoords(BlockPos blockPos, BlockPos blockPos2) {
        return RotationUtils.calcRotationFromVec3d(new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), new Vec3((double)blockPos2.getX(), (double)blockPos2.getY(), (double)blockPos2.getZ()));
    }

    public static Rotation wrapAnglesToRelative(Rotation rotation, Rotation rotation2) {
        if (rotation.yawIsReallyClose(rotation2)) {
            return new Rotation(rotation.getYaw(), rotation2.getPitch());
        }
        return rotation2.subtract(rotation).normalize().add(rotation);
    }

    public static Rotation calcRotationFromVec3d(Vec3 vec3, Vec3 vec32, Rotation rotation) {
        return RotationUtils.wrapAnglesToRelative(rotation, RotationUtils.calcRotationFromVec3d(vec3, vec32));
    }

    private static Rotation calcRotationFromVec3d(Vec3 object, Vec3 vec3) {
        double[] dArray = new double[]{object.x - vec3.x, object.y - vec3.y, object.z - vec3.z};
        object = dArray;
        double d2 = Mth.atan2((double)dArray[0], (double)(-object[2]));
        double d3 = Math.sqrt((double)(object[0] * object[0] + object[2] * object[2]));
        double d4 = Mth.atan2((double)object[1], (double)d3);
        return new Rotation((float)(d2 * 57.29577951308232), (float)(d4 * 57.29577951308232));
    }

    public static Vec3 calcLookDirectionFromRotation(Rotation rotation) {
        float f2 = Mth.cos((float)(-rotation.getYaw() * ((float)Math.PI / 180) - (float)Math.PI));
        float f3 = Mth.sin((float)(-rotation.getYaw() * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = -Mth.cos((float)(-rotation.getPitch() * ((float)Math.PI / 180)));
        float f5 = Mth.sin((float)(-rotation.getPitch() * ((float)Math.PI / 180)));
        return new Vec3((double)(f3 * f4), (double)f5, (double)(f2 * f4));
    }

    @Deprecated
    public static Vec3 calcVec3dFromRotation(Rotation rotation) {
        return RotationUtils.calcLookDirectionFromRotation(rotation);
    }

    public static Optional<Rotation> reachable(IPlayerContext iPlayerContext, BlockPos blockPos) {
        return RotationUtils.reachable(iPlayerContext, blockPos, false);
    }

    public static Optional<Rotation> reachable(IPlayerContext iPlayerContext, BlockPos blockPos, boolean bl2) {
        return RotationUtils.reachable(iPlayerContext, blockPos, iPlayerContext.playerController().getBlockReachDistance(), bl2);
    }

    public static Optional<Rotation> reachable(IPlayerContext iPlayerContext, BlockPos blockPos, double d2) {
        return RotationUtils.reachable(iPlayerContext, blockPos, d2, false);
    }

    public static Optional<Rotation> reachable(IPlayerContext iPlayerContext, BlockPos blockPos, double d2, boolean bl2) {
        VoxelShape voxelShape;
        Optional<Rotation> optional;
        if (((Boolean)BaritoneAPI.getSettings().remainWithExistingLookDirection.value).booleanValue() && iPlayerContext.isLookingAt(blockPos)) {
            optional = iPlayerContext.playerRotations().add(new Rotation(0.0f, 1.0E-4f));
            if (bl2) {
                voxelShape = RayTraceUtils.rayTraceTowards((Entity)iPlayerContext.player(), (Rotation)((Object)optional), d2, true);
                if (voxelShape != null && voxelShape.getType() == HitResult.Type.BLOCK && ((BlockHitResult)voxelShape).getBlockPos().equals((Object)blockPos)) {
                    return Optional.of(optional);
                }
            } else {
                return Optional.of(optional);
            }
        }
        if ((optional = RotationUtils.reachableCenter(iPlayerContext, blockPos, d2, bl2)).isPresent()) {
            return optional;
        }
        voxelShape = iPlayerContext.world().getBlockState(blockPos).getShape((BlockGetter)iPlayerContext.world(), blockPos);
        if (voxelShape.isEmpty()) {
            voxelShape = Shapes.block();
        }
        Vec3[] vec3Array = BLOCK_SIDE_MULTIPLIERS;
        int n2 = BLOCK_SIDE_MULTIPLIERS.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            optional = vec3Array[i2];
            double d3 = voxelShape.min(Direction.Axis.X) * ((Vec3)optional).x + voxelShape.max(Direction.Axis.X) * (1.0 - ((Vec3)optional).x);
            double d4 = voxelShape.min(Direction.Axis.Y) * ((Vec3)optional).y + voxelShape.max(Direction.Axis.Y) * (1.0 - ((Vec3)optional).y);
            double d5 = voxelShape.min(Direction.Axis.Z) * ((Vec3)optional).z + voxelShape.max(Direction.Axis.Z) * (1.0 - ((Vec3)optional).z);
            optional = RotationUtils.reachableOffset(iPlayerContext, blockPos, new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()).add(d3, d4, d5), d2, bl2);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    public static Optional<Rotation> reachableOffset(IPlayerContext iPlayerContext, BlockPos blockPos, Vec3 object, double d2, boolean bl2) {
        object = RotationUtils.calcRotationFromVec3d(bl2 ? RayTraceUtils.inferSneakingEyePosition((Entity)iPlayerContext.player()) : iPlayerContext.player().getEyePosition(1.0f), object, iPlayerContext.playerRotations());
        Rotation rotation = BaritoneAPI.getProvider().getBaritoneForPlayer(iPlayerContext.player()).getLookBehavior().getAimProcessor().peekRotation((Rotation)object);
        HitResult hitResult = RayTraceUtils.rayTraceTowards((Entity)iPlayerContext.player(), rotation, d2, bl2);
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            if (((BlockHitResult)hitResult).getBlockPos().equals((Object)blockPos)) {
                return Optional.of(object);
            }
            if (iPlayerContext.world().getBlockState(blockPos).getBlock() instanceof BaseFireBlock && ((BlockHitResult)hitResult).getBlockPos().equals((Object)blockPos.below())) {
                return Optional.of(object);
            }
        }
        return Optional.empty();
    }

    public static Optional<Rotation> reachableCenter(IPlayerContext iPlayerContext, BlockPos blockPos, double d2, boolean bl2) {
        return RotationUtils.reachableOffset(iPlayerContext, blockPos, VecUtils.calculateBlockCenter(iPlayerContext.world(), blockPos), d2, bl2);
    }

    @Deprecated
    public static Optional<Rotation> reachable(LocalPlayer localPlayer, BlockPos blockPos, double d2) {
        return RotationUtils.reachable(localPlayer, blockPos, d2, false);
    }

    @Deprecated
    public static Optional<Rotation> reachable(LocalPlayer localPlayer, BlockPos blockPos, double d2, boolean bl2) {
        return RotationUtils.reachable(BaritoneAPI.getProvider().getBaritoneForPlayer(localPlayer).getPlayerContext(), blockPos, d2, bl2);
    }

    @Deprecated
    public static Optional<Rotation> reachableOffset(Entity entity, BlockPos blockPos, Vec3 object, double d2, boolean bl2) {
        object = RotationUtils.calcRotationFromVec3d(bl2 ? RayTraceUtils.inferSneakingEyePosition(entity) : entity.getEyePosition(1.0f), object, new Rotation(entity.getYRot(), entity.getXRot()));
        HitResult hitResult = RayTraceUtils.rayTraceTowards(entity, (Rotation)object, d2, bl2);
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            if (((BlockHitResult)hitResult).getBlockPos().equals((Object)blockPos)) {
                return Optional.of(object);
            }
            if (entity.level().getBlockState(blockPos).getBlock() instanceof BaseFireBlock && ((BlockHitResult)hitResult).getBlockPos().equals((Object)blockPos.below())) {
                return Optional.of(object);
            }
        }
        return Optional.empty();
    }

    @Deprecated
    public static Optional<Rotation> reachableCenter(Entity entity, BlockPos blockPos, double d2, boolean bl2) {
        return RotationUtils.reachableOffset(entity, blockPos, VecUtils.calculateBlockCenter(entity.level(), blockPos), d2, bl2);
    }
}

