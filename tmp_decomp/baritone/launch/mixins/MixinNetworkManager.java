/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandlerContext
 *  net.minecraft.network.Connection
 *  net.minecraft.network.PacketSendListener
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketFlow
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.type.EventState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Connection.class})
public class MixinNetworkManager {
    @Shadow
    private Channel channel;
    @Shadow
    @Final
    private PacketFlow receiving;

    @Inject(method={"sendPacket"}, at={@At(value="HEAD")})
    private void preDispatchPacket(Packet<?> packet, PacketSendListener object, boolean bl2, CallbackInfo callbackInfo) {
        if (this.receiving != PacketFlow.CLIENTBOUND) {
            return;
        }
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            if (iBaritone.getPlayerContext().player() == null || iBaritone.getPlayerContext().player().connection.getConnection() != (Connection)this) continue;
            iBaritone.getGameEventHandler().onSendPacket(new PacketEvent((Connection)this, EventState.PRE, packet));
        }
    }

    @Inject(method={"sendPacket"}, at={@At(value="RETURN")})
    private void postDispatchPacket(Packet<?> packet, PacketSendListener object, boolean bl2, CallbackInfo callbackInfo) {
        if (this.receiving != PacketFlow.CLIENTBOUND) {
            return;
        }
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            if (iBaritone.getPlayerContext().player() == null || iBaritone.getPlayerContext().player().connection.getConnection() != (Connection)this) continue;
            iBaritone.getGameEventHandler().onSendPacket(new PacketEvent((Connection)this, EventState.POST, packet));
        }
    }

    @Inject(method={"channelRead0"}, at={@At(value="INVOKE", target="net/minecraft/network/Connection.genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V")})
    private void preProcessPacket(ChannelHandlerContext object, Packet<?> packet, CallbackInfo object22) {
        if (this.receiving != PacketFlow.CLIENTBOUND) {
            return;
        }
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            if (iBaritone.getPlayerContext().player() == null || iBaritone.getPlayerContext().player().connection.getConnection() != (Connection)this) continue;
            iBaritone.getGameEventHandler().onReceivePacket(new PacketEvent((Connection)this, EventState.PRE, packet));
        }
    }

    @Inject(method={"channelRead0"}, at={@At(value="RETURN")})
    private void postProcessPacket(ChannelHandlerContext object, Packet<?> packet, CallbackInfo object22) {
        if (!this.channel.isOpen() || this.receiving != PacketFlow.CLIENTBOUND) {
            return;
        }
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            if (iBaritone.getPlayerContext().player() == null || iBaritone.getPlayerContext().player().connection.getConnection() != (Connection)this) continue;
            iBaritone.getGameEventHandler().onReceivePacket(new PacketEvent((Connection)this, EventState.POST, packet));
        }
    }
}

