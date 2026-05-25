package dev.roocky.effortlessbaritone.mixin;

import dev.huskuraft.effortless.api.networking.Packet;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPacket;
import dev.roocky.effortlessbaritone.EffortlessBaritoneMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = dev.huskuraft.effortless.api.networking.NetworkChannel.class, remap = false)
public class BuildPacketInterceptorMixin {

    @Inject(method = "sendPacket(Ldev/huskuraft/effortless/api/networking/Packet;Ldev/huskuraft/effortless/api/core/Player;)V", at = @At("HEAD"), cancellable = true)
    private void interceptBuildPacket(Packet<?> packet, Player player, CallbackInfo ci) {
        if (packet instanceof PlayerBuildPacket) {
            PlayerBuildPacket buildPacket = (PlayerBuildPacket) packet;
            EffortlessBaritoneMod.queueBuildTask(buildPacket.context());
            ci.cancel();
        }
    }
}
