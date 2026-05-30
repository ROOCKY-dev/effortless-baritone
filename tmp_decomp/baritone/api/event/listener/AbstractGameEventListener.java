/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.event.listener;

import baritone.api.event.events.BlockChangeEvent;
import baritone.api.event.events.BlockInteractEvent;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.PathEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.TabCompleteEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.listener.IGameEventListener;

public interface AbstractGameEventListener
extends IGameEventListener {
    @Override
    default public void onTick(TickEvent tickEvent) {
    }

    @Override
    default public void onPostTick(TickEvent tickEvent) {
    }

    @Override
    default public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
    }

    @Override
    default public void onSendChatMessage(ChatEvent chatEvent) {
    }

    @Override
    default public void onPreTabComplete(TabCompleteEvent tabCompleteEvent) {
    }

    @Override
    default public void onChunkEvent(ChunkEvent chunkEvent) {
    }

    @Override
    default public void onBlockChange(BlockChangeEvent blockChangeEvent) {
    }

    @Override
    default public void onRenderPass(RenderEvent renderEvent) {
    }

    @Override
    default public void onWorldEvent(WorldEvent worldEvent) {
    }

    @Override
    default public void onSendPacket(PacketEvent packetEvent) {
    }

    @Override
    default public void onReceivePacket(PacketEvent packetEvent) {
    }

    @Override
    default public void onPlayerRotationMove(RotationMoveEvent rotationMoveEvent) {
    }

    @Override
    default public void onPlayerSprintState(SprintStateEvent sprintStateEvent) {
    }

    @Override
    default public void onBlockInteract(BlockInteractEvent blockInteractEvent) {
    }

    @Override
    default public void onPlayerDeath() {
    }

    @Override
    default public void onPathEvent(PathEvent pathEvent) {
    }
}

