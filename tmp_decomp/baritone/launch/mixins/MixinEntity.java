/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.event.events.RotationMoveEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Entity.class})
public class MixinEntity {
    @Shadow
    private float yRot;
    @Shadow
    private float xRot;
    @Unique
    private RotationMoveEvent motionUpdateRotationEvent;

    @Inject(method={"moveRelative"}, at={@At(value="HEAD")})
    private void moveRelativeHead(CallbackInfo callbackInfo) {
        if (!LocalPlayer.class.isInstance(this) || BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this) == null) {
            return;
        }
        this.motionUpdateRotationEvent = new RotationMoveEvent(RotationMoveEvent.Type.MOTION_UPDATE, this.yRot, this.xRot);
        BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this).getGameEventHandler().onPlayerRotationMove(this.motionUpdateRotationEvent);
        this.yRot = this.motionUpdateRotationEvent.getYaw();
        this.xRot = this.motionUpdateRotationEvent.getPitch();
    }

    @Inject(method={"moveRelative"}, at={@At(value="RETURN")})
    private void moveRelativeReturn(CallbackInfo callbackInfo) {
        if (this.motionUpdateRotationEvent != null) {
            this.yRot = this.motionUpdateRotationEvent.getOriginal().getYaw();
            this.xRot = this.motionUpdateRotationEvent.getOriginal().getPitch();
            this.motionUpdateRotationEvent = null;
        }
    }
}

