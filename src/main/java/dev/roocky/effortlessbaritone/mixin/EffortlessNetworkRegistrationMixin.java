package dev.roocky.effortlessbaritone.mixin;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = dev.huskuraft.effortless.neoforge.networking.NeoForgeNetworking.class, remap = false)
public class EffortlessNetworkRegistrationMixin {

    @Redirect(
            method = "lambda$register$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/network/registration/PayloadRegistrar;playBidirectional(Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/codec/StreamCodec;Lnet/neoforged/neoforge/network/handling/IPayloadHandler;)Lnet/neoforged/neoforge/network/registration/PayloadRegistrar;"
            )
    )
    private static <T extends CustomPacketPayload> PayloadRegistrar redirectPlayBidirectional(
            PayloadRegistrar instance,
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super net.minecraft.network.RegistryFriendlyByteBuf, T> codec,
            IPayloadHandler<T> handler
    ) {
        // Force the payload registrar to be optional so the client can connect to servers without the mod
        return instance.optional().playBidirectional(type, codec, handler);
    }
}
