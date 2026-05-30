/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.player.Abilities
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Group
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.utils.Rotation;
import baritone.c;
import baritone.f;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LocalPlayer.class})
public class MixinClientPlayerEntity {
    @Unique
    private static final MethodHandle MAY_FLY = MixinClientPlayerEntity.baritone$resolveMayFly();

    @Unique
    private static MethodHandle baritone$resolveMayFly() {
        try {
            return MethodHandles.publicLookup().findVirtual(LocalPlayer.class, "mayFly", MethodType.methodType(Boolean.TYPE));
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="net/minecraft/client/player/AbstractClientPlayer.tick()V", shift=At.Shift.AFTER)})
    private void onPreUpdate(CallbackInfo object) {
        object = BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this);
        if (object != null) {
            object.getGameEventHandler().onPlayerUpdate(new PlayerUpdateEvent(EventState.PRE));
        }
    }

    @Redirect(method={"aiStep"}, at=@At(value="FIELD", target="net/minecraft/world/entity/player/Abilities.mayfly:Z"))
    @Group(name="mayFly", min=1, max=1)
    private boolean isAllowFlying(Abilities abilities) {
        IBaritone iBaritone = BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this);
        if (iBaritone == null) {
            return abilities.mayfly;
        }
        return !iBaritone.getPathingBehavior().isPathing() && abilities.mayfly;
    }

    @Redirect(method={"aiStep"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;mayFly()Z"))
    @Group(name="mayFly", min=1, max=1)
    private boolean onMayFlyNeoforge(LocalPlayer localPlayer) {
        IBaritone iBaritone = BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this);
        if (iBaritone == null) {
            return MAY_FLY.invokeExact(localPlayer);
        }
        return !iBaritone.getPathingBehavior().isPathing() && MAY_FLY.invokeExact(localPlayer);
    }

    @Redirect(method={"aiStep"}, at=@At(value="INVOKE", target="net/minecraft/client/KeyMapping.isDown()Z"))
    private boolean isKeyDown(KeyMapping keyMapping) {
        IBaritone iBaritone = BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this);
        if (iBaritone == null) {
            return keyMapping.isDown();
        }
        SprintStateEvent sprintStateEvent = new SprintStateEvent();
        iBaritone.getGameEventHandler().onPlayerSprintState(sprintStateEvent);
        if (sprintStateEvent.getState() != null) {
            return sprintStateEvent.getState();
        }
        if (iBaritone != BaritoneAPI.getProvider().getPrimaryBaritone()) {
            return false;
        }
        return keyMapping.isDown();
    }

    @Inject(method={"rideTick"}, at={@At(value="HEAD")})
    private void updateRidden(CallbackInfo object) {
        object = BaritoneAPI.getProvider().getBaritoneForPlayer((LocalPlayer)this);
        if (object != null) {
            object = (f)object.getLookBehavior();
            if (((f)object).a != null) {
                Rotation rotation = ((f)object).a.peekRotation(((f)object).a.a);
                ((c)object).a.player().setYRot(rotation.getYaw());
            }
        }
    }

    @Redirect(method={"aiStep"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;tryToStartFallFlying()Z"))
    private boolean tryToStartFallFlying(LocalPlayer localPlayer) {
        IBaritone iBaritone = BaritoneAPI.getProvider().getBaritoneForPlayer(localPlayer);
        if (iBaritone != null && iBaritone.getPathingBehavior().isPathing()) {
            return false;
        }
        return localPlayer.tryToStartFallFlying();
    }
}

