/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.RotationMoveEvent;
import java.util.Optional;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LivingEntity.class})
public abstract class MixinLivingEntity
extends Entity {
    @Unique
    private RotationMoveEvent jumpRotationEvent;
    @Unique
    private RotationMoveEvent elytraRotationEvent;

    private MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method={"jumpFromGround"}, at={@At(value="HEAD")})
    private void preMoveRelative(CallbackInfo callbackInfo) {
        this.getBaritone().ifPresent(iBaritone -> {
            this.jumpRotationEvent = new RotationMoveEvent(RotationMoveEvent.Type.JUMP, this.getYRot(), this.getXRot());
            iBaritone.getGameEventHandler().onPlayerRotationMove(this.jumpRotationEvent);
        });
    }

    @Redirect(method={"jumpFromGround"}, at=@At(value="INVOKE", target="net/minecraft/world/entity/LivingEntity.getYRot()F"))
    private float overrideYaw(LivingEntity livingEntity) {
        if (livingEntity instanceof LocalPlayer && BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this) != null) {
            return this.jumpRotationEvent.getYaw();
        }
        return livingEntity.getYRot();
    }

    @Inject(method={"travel"}, at={@At(value="INVOKE", target="net/minecraft/world/entity/LivingEntity.getLookAngle()Lnet/minecraft/world/phys/Vec3;")})
    private void onPreElytraMove(Vec3 vec3, CallbackInfo callbackInfo) {
        this.getBaritone().ifPresent(iBaritone -> {
            this.elytraRotationEvent = new RotationMoveEvent(RotationMoveEvent.Type.MOTION_UPDATE, this.getYRot(), this.getXRot());
            iBaritone.getGameEventHandler().onPlayerRotationMove(this.elytraRotationEvent);
            MixinLivingEntity mixinLivingEntity = this;
            mixinLivingEntity.setYRot(mixinLivingEntity.elytraRotationEvent.getYaw());
            MixinLivingEntity mixinLivingEntity2 = this;
            mixinLivingEntity2.setXRot(mixinLivingEntity2.elytraRotationEvent.getPitch());
        });
    }

    @Inject(method={"travel"}, at={@At(value="INVOKE", target="net/minecraft/world/entity/LivingEntity.move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", shift=At.Shift.AFTER)})
    private void onPostElytraMove(Vec3 vec3, CallbackInfo callbackInfo) {
        if (this.elytraRotationEvent != null) {
            MixinLivingEntity mixinLivingEntity = this;
            mixinLivingEntity.setYRot(mixinLivingEntity.elytraRotationEvent.getOriginal().getYaw());
            MixinLivingEntity mixinLivingEntity2 = this;
            mixinLivingEntity2.setXRot(mixinLivingEntity2.elytraRotationEvent.getOriginal().getPitch());
            this.elytraRotationEvent = null;
        }
    }

    @Unique
    private Optional<IBaritone> getBaritone() {
        if (LocalPlayer.class.isInstance((Object)this)) {
            return Optional.ofNullable(BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this));
        }
        return Optional.empty();
    }
}

