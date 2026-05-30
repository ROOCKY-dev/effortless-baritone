/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 */
package baritone.api.utils;

import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class RayTraceUtils {
    private RayTraceUtils() {
    }

    public static HitResult rayTraceTowards(Entity entity, Rotation rotation, double d2) {
        return RayTraceUtils.rayTraceTowards(entity, rotation, d2, false);
    }

    public static HitResult rayTraceTowards(Entity entity, Rotation rotation, double d2, boolean bl2) {
        Vec3 vec3 = bl2 ? RayTraceUtils.inferSneakingEyePosition(entity) : entity.getEyePosition(1.0f);
        rotation = RotationUtils.calcLookDirectionFromRotation(rotation);
        rotation = vec3.add(((Vec3)rotation).x * d2, ((Vec3)rotation).y * d2, ((Vec3)rotation).z * d2);
        return entity.level().clip(new ClipContext(vec3, (Vec3)rotation, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));
    }

    public static Vec3 inferSneakingEyePosition(Entity entity) {
        return new Vec3(entity.getX(), entity.getY() + (double)entity.getEyeHeight(Pose.CROUCHING), entity.getZ());
    }
}

