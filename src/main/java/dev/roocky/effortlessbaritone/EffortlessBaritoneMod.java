package dev.roocky.effortlessbaritone;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.building.BuildType;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.operation.OperationResult;
import dev.huskuraft.effortless.building.operation.batch.BatchOperationResult;
import dev.huskuraft.effortless.building.operation.block.BlockOperationResult;
import dev.huskuraft.effortless.building.session.BatchBuildSession;
import dev.huskuraft.effortless.neoforge.core.MinecraftPlayer;
import dev.huskuraft.effortless.EffortlessClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import java.util.HashMap;
import java.util.Map;

@Mod(EffortlessBaritoneMod.MODID)
public class EffortlessBaritoneMod {
    public static final String MODID = "effortless_baritone";
    
    // Store the queued blocks to build/break
    // True = Place, False = Break
    public static final Map<net.minecraft.core.BlockPos, net.minecraft.world.level.block.state.BlockState> QUEUED_PLACEMENTS = new HashMap<>();
    
    public EffortlessBaritoneMod(IEventBus modEventBus) {
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(EffortlessBaritoneMod::onClientCommand);
    }
    
    public static void queueBuildTask(Context context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        dev.huskuraft.effortless.api.core.Player player = EffortlessClient.getInstance().getClient().getPlayer();
        Context previewContext = context.withBuildType(BuildType.PREVIEW);
        
        BatchBuildSession session = new BatchBuildSession(EffortlessClient.getInstance(), player, previewContext);
        BatchOperationResult result = (BatchOperationResult) session.commit();
        
        int added = 0;
        for (OperationResult opRes : result.getResults()) {
            if (opRes instanceof BlockOperationResult blockRes) {
                BlockState targetState = blockRes.getBlockStateForRenderer();
                if (targetState != null) {
                    BlockPosition pos = blockRes.getOperation().getBlockPosition();
                    if (pos != null) {
                        net.minecraft.core.BlockPos mcPos = new net.minecraft.core.BlockPos(pos.x(), pos.y(), pos.z());
                        net.minecraft.world.level.block.state.BlockState mcState = null;
                        if (!targetState.isAir()) {
                            // Convert Effortless BlockState to Minecraft BlockState
                            if (targetState instanceof dev.huskuraft.effortless.neoforge.core.MinecraftBlockState mcbs) {
                                mcState = mcbs.refs();
                            }
                        } else {
                            mcState = net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
                        }
                        
                        if (mcState != null) {
                            QUEUED_PLACEMENTS.put(mcPos, mcState);
                            added++;
                        }
                    }
                }
            }
        }
        
        mc.player.displayClientMessage(Component.literal("§a[EffortlessBaritone] Task added to queue. " + added + " blocks to process. Type /eb start to begin."), false);
    }
    
    public static void onClientCommand(RegisterClientCommandsEvent event) {
        StartCommand.register(event.getDispatcher());
    }
}
