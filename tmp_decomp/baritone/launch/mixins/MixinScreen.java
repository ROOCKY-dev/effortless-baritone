/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Style
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.event.events.ChatEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Screen.class})
public abstract class MixinScreen {
    @Inject(at={@At(value="INVOKE", target="Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", remap=false, ordinal=1)}, method={"handleComponentClicked"}, cancellable=true)
    public void handleCustomClickEvent(Style object, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if ((object = object.getClickEvent()) == null) {
            return;
        }
        if ((object = object.getValue()) == null || !((String)object).startsWith(IBaritoneChatControl.FORCE_COMMAND_PREFIX)) {
            return;
        }
        IBaritone iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        if (iBaritone != null) {
            iBaritone.getGameEventHandler().onSendChatMessage(new ChatEvent((String)object));
        }
        callbackInfoReturnable.setReturnValue((Object)Boolean.TRUE);
        callbackInfoReturnable.cancel();
    }
}

