/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.ReceivingLevelScreen$Reason
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.Slice
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.type.EventState;
import java.util.function.BiFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public class MixinMinecraft {
    @Shadow
    public LocalPlayer player;
    @Shadow
    public ClientLevel level;
    @Unique
    private BiFunction<EventState, TickEvent.Type, TickEvent> tickProvider;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void postInit(CallbackInfo callbackInfo) {
        BaritoneAPI.getProvider().getPrimaryBaritone();
    }

    @Inject(method={"tick"}, at={@At(value="FIELD", opcode=180, target="net/minecraft/client/Minecraft.screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal=0, shift=At.Shift.BEFORE)}, slice={@Slice(from=@At(value="FIELD", opcode=181, target="net/minecraft/client/Minecraft.missTime:I"))})
    private void runTick(CallbackInfo object) {
        this.tickProvider = TickEvent.createNextProvider();
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            TickEvent.Type type = iBaritone.getPlayerContext().player() != null && iBaritone.getPlayerContext().world() != null ? TickEvent.Type.IN : TickEvent.Type.OUT;
            iBaritone.getGameEventHandler().onTick(this.tickProvider.apply(EventState.PRE, type));
        }
    }

    @Inject(method={"tick"}, at={@At(value="RETURN")})
    private void postRunTick(CallbackInfo object) {
        if (this.tickProvider == null) {
            return;
        }
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            TickEvent.Type type = iBaritone.getPlayerContext().player() != null && iBaritone.getPlayerContext().world() != null ? TickEvent.Type.IN : TickEvent.Type.OUT;
            iBaritone.getGameEventHandler().onPostTick(this.tickProvider.apply(EventState.POST, type));
        }
        this.tickProvider = null;
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="net/minecraft/client/multiplayer/ClientLevel.tickEntities()V", shift=At.Shift.AFTER)})
    private void postUpdateEntities(CallbackInfo object) {
        object = BaritoneAPI.getProvider().getBaritoneForPlayer(this.player);
        if (object != null) {
            object.getGameEventHandler().onPlayerUpdate(new PlayerUpdateEvent(EventState.POST));
        }
    }

    @Inject(method={"setLevel"}, at={@At(value="HEAD")})
    private void preLoadWorld(ClientLevel clientLevel, ReceivingLevelScreen.Reason reason, CallbackInfo callbackInfo) {
        if (this.level == null && clientLevel == null) {
            return;
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onWorldEvent(new WorldEvent(clientLevel, EventState.PRE));
    }

    @Inject(method={"setLevel"}, at={@At(value="RETURN")})
    private void postLoadWorld(ClientLevel clientLevel, ReceivingLevelScreen.Reason reason, CallbackInfo callbackInfo) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onWorldEvent(new WorldEvent(clientLevel, EventState.POST));
    }

    @Redirect(method={"tick"}, at=@At(value="FIELD", opcode=180, target="Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;"), slice=@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/client/gui/components/DebugScreenOverlay;showDebugScreen()Z"), to=@At(value="CONSTANT", args={"stringValue=Keybindings"})))
    private Screen passEvents(Minecraft minecraft) {
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && this.player != null) {
            return null;
        }
        return minecraft.screen;
    }
}

