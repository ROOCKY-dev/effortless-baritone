/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package baritone;

import baritone.a;
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
import baritone.api.event.events.type.EventState;
import baritone.api.event.listener.IEventBus;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.utils.Helper;
import baritone.api.utils.Pair;
import baritone.fb;
import baritone.m;
import baritone.u;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.chunk.LevelChunk;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bu
implements IEventBus,
Helper {
    private final a a;
    private final List<IGameEventListener> a = new CopyOnWriteArrayList();

    public bu(a a2) {
        this.a = a2;
    }

    @Override
    public final void onTick(TickEvent tickEvent) {
        block3: {
            if (tickEvent.getType() == TickEvent.Type.IN) {
                try {
                    this.a.a = new fb(this.a.getPlayerContext(), true);
                    break block3;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            this.a.a = null;
        }
        this.a.forEach(iGameEventListener -> iGameEventListener.onTick(tickEvent));
    }

    @Override
    public final void onPostTick(TickEvent tickEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onPostTick(tickEvent));
    }

    @Override
    public final void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onPlayerUpdate(playerUpdateEvent));
    }

    @Override
    public final void onSendChatMessage(ChatEvent chatEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onSendChatMessage(chatEvent));
    }

    @Override
    public final void onPreTabComplete(TabCompleteEvent tabCompleteEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onPreTabComplete(tabCompleteEvent));
    }

    @Override
    public final void onChunkEvent(ChunkEvent chunkEvent) {
        boolean bl2;
        EventState eventState = chunkEvent.getState();
        ChunkEvent.Type type = chunkEvent.getType();
        Level level = this.a.getPlayerContext().world();
        boolean bl3 = bl2 = eventState == EventState.PRE && type == ChunkEvent.Type.UNLOAD && level.getChunkSource().getChunk(chunkEvent.getX(), chunkEvent.getZ(), null, false) != null;
        if (chunkEvent.isPostPopulate() || bl2) {
            this.a.a.ifWorldLoaded(iWorldData -> {
                level = level.getChunk(chunkEvent.getX(), chunkEvent.getZ());
                iWorldData.getCachedWorld().queueForPacking((LevelChunk)level);
            });
        }
        this.a.forEach(iGameEventListener -> iGameEventListener.onChunkEvent(chunkEvent));
    }

    @Override
    public final void onBlockChange(BlockChangeEvent blockChangeEvent) {
        if (((Boolean)baritone.a.a().repackOnAnyBlockChange.value).booleanValue()) {
            if (blockChangeEvent.getBlocks().stream().map(Pair::second).map(BlockBehaviour.BlockStateBase::getBlock).anyMatch(arg_0 -> m.a.contains(arg_0))) {
                this.a.a.ifWorldLoaded(iWorldData -> {
                    Level level = this.a.getPlayerContext().world();
                    blockChangeEvent = blockChangeEvent.getChunkPos();
                    iWorldData.getCachedWorld().queueForPacking(level.getChunk(((ChunkPos)blockChangeEvent).x, ((ChunkPos)blockChangeEvent).z));
                });
            }
        }
        this.a.forEach(iGameEventListener -> iGameEventListener.onBlockChange(blockChangeEvent));
    }

    @Override
    public final void onRenderPass(RenderEvent renderEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onRenderPass(renderEvent));
    }

    @Override
    public final void onWorldEvent(WorldEvent worldEvent) {
        u u2 = this.a.a;
        if (worldEvent.getState() == EventState.POST) {
            u2.a();
            if (worldEvent.getWorld() != null) {
                u2.a((Level)worldEvent.getWorld());
            }
        }
        this.a.forEach(iGameEventListener -> iGameEventListener.onWorldEvent(worldEvent));
    }

    @Override
    public final void onSendPacket(PacketEvent packetEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onSendPacket(packetEvent));
    }

    @Override
    public final void onReceivePacket(PacketEvent packetEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onReceivePacket(packetEvent));
    }

    @Override
    public final void onPlayerRotationMove(RotationMoveEvent rotationMoveEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onPlayerRotationMove(rotationMoveEvent));
    }

    @Override
    public final void onPlayerSprintState(SprintStateEvent sprintStateEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onPlayerSprintState(sprintStateEvent));
    }

    @Override
    public final void onBlockInteract(BlockInteractEvent blockInteractEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onBlockInteract(blockInteractEvent));
    }

    @Override
    public final void onPlayerDeath() {
        this.a.forEach(IGameEventListener::onPlayerDeath);
    }

    @Override
    public final void onPathEvent(PathEvent pathEvent) {
        this.a.forEach(iGameEventListener -> iGameEventListener.onPathEvent(pathEvent));
    }

    @Override
    public final void registerEventListener(IGameEventListener iGameEventListener) {
        this.a.add(iGameEventListener);
    }
}

