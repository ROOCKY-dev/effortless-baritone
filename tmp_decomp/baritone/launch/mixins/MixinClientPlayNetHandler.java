/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.CommonListenerCookie
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket
 *  net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.BlockChangeEvent;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.type.Cancellable;
import baritone.api.event.events.type.EventState;
import baritone.api.utils.Pair;
import baritone.m;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class})
public abstract class MixinClientPlayNetHandler
extends ClientCommonPacketListenerImpl {
    protected MixinClientPlayNetHandler(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method={"sendChat(Ljava/lang/String;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void sendChatMessage(String object, CallbackInfo callbackInfo) {
        object = new ChatEvent((String)object);
        IBaritone iBaritone = BaritoneAPI.getProvider().getBaritoneForPlayer(this.minecraft.player);
        if (iBaritone == null) {
            return;
        }
        iBaritone.getGameEventHandler().onSendChatMessage((ChatEvent)object);
        if (((Cancellable)object).isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"handleLevelChunkWithLight"}, at={@At(value="RETURN")})
    private void postHandleChunkData(ClientboundLevelChunkWithLightPacket clientboundLevelChunkWithLightPacket, CallbackInfo object) {
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            LocalPlayer localPlayer = iBaritone.getPlayerContext().player();
            if (localPlayer == null || localPlayer.connection != (ClientPacketListener)this) continue;
            iBaritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, !clientboundLevelChunkWithLightPacket.isSkippable() ? ChunkEvent.Type.POPULATE_FULL : ChunkEvent.Type.POPULATE_PARTIAL, clientboundLevelChunkWithLightPacket.getX(), clientboundLevelChunkWithLightPacket.getZ()));
        }
    }

    @Inject(method={"handleForgetLevelChunk"}, at={@At(value="HEAD")})
    private void preChunkUnload(ClientboundForgetLevelChunkPacket clientboundForgetLevelChunkPacket, CallbackInfo object) {
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            LocalPlayer localPlayer = iBaritone.getPlayerContext().player();
            if (localPlayer == null || localPlayer.connection != (ClientPacketListener)this) continue;
            iBaritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.PRE, ChunkEvent.Type.UNLOAD, clientboundForgetLevelChunkPacket.pos().x, clientboundForgetLevelChunkPacket.pos().z));
        }
    }

    @Inject(method={"handleForgetLevelChunk"}, at={@At(value="RETURN")})
    private void postChunkUnload(ClientboundForgetLevelChunkPacket clientboundForgetLevelChunkPacket, CallbackInfo object) {
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            LocalPlayer localPlayer = iBaritone.getPlayerContext().player();
            if (localPlayer == null || localPlayer.connection != (ClientPacketListener)this) continue;
            iBaritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, ChunkEvent.Type.UNLOAD, clientboundForgetLevelChunkPacket.pos().x, clientboundForgetLevelChunkPacket.pos().z));
        }
    }

    @Inject(method={"handleBlockUpdate"}, at={@At(value="RETURN")})
    private void postHandleBlockChange(ClientboundBlockUpdatePacket clientboundBlockUpdatePacket, CallbackInfo object) {
        if (!((Boolean)a.a().repackOnAnyBlockChange.value).booleanValue()) {
            return;
        }
        if (!m.a.contains((Object)clientboundBlockUpdatePacket.getBlockState().getBlock())) {
            return;
        }
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            LocalPlayer localPlayer = iBaritone.getPlayerContext().player();
            if (localPlayer == null || localPlayer.connection != (ClientPacketListener)this) continue;
            iBaritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, ChunkEvent.Type.POPULATE_FULL, clientboundBlockUpdatePacket.getPos().getX() >> 4, clientboundBlockUpdatePacket.getPos().getZ() >> 4));
        }
    }

    @Inject(method={"handleChunkBlocksUpdate"}, at={@At(value="RETURN")})
    private void postHandleMultiBlockChange(ClientboundSectionBlocksUpdatePacket clientboundSectionBlocksUpdatePacket, CallbackInfo object) {
        object = BaritoneAPI.getProvider().getBaritoneForConnection((ClientPacketListener)this);
        if (object == null) {
            return;
        }
        ArrayList<Pair<BlockPos, BlockState>> arrayList = new ArrayList<Pair<BlockPos, BlockState>>();
        clientboundSectionBlocksUpdatePacket.runUpdates((blockPos, blockState) -> arrayList.add(new Pair<BlockPos, BlockState>(blockPos.immutable(), (BlockState)blockState)));
        if (arrayList.isEmpty()) {
            return;
        }
        object.getGameEventHandler().onBlockChange(new BlockChangeEvent(new ChunkPos((BlockPos)((Pair)arrayList.get(0)).first()), arrayList));
    }

    @Inject(method={"handlePlayerCombatKill"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;shouldShowDeathScreen()Z")})
    private void onPlayerDeath(ClientboundPlayerCombatKillPacket object, CallbackInfo object22) {
        for (IBaritone iBaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            LocalPlayer localPlayer = iBaritone.getPlayerContext().player();
            if (localPlayer == null || localPlayer.connection != (ClientPacketListener)this) continue;
            iBaritone.getGameEventHandler().onPlayerDeath();
        }
    }
}

