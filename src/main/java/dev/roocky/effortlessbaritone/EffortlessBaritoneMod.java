package dev.roocky.effortlessbaritone;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main entry point and core orchestrator for Effortless Baritone.
 * 
 * Hardened to have ZERO static dependencies on Baritone or Effortless Building
 * to prevent classloading crashes in complex modpacks.
 */
@Mod(EffortlessBaritoneMod.MODID)
public class EffortlessBaritoneMod {
    public static final String MODID = "effortless_baritone";
    
    public static final List<BuildTask> TASK_QUEUE = new ArrayList<>();
    public static boolean SAFE_STOP = true;
    
    public EffortlessBaritoneMod(IEventBus modEventBus) {
        modEventBus.addListener(this::onClientSetup);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(EffortlessBaritoneMod::onClientCommand);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        // Safe check for Baritone presence before trying to register listener
        BaritoneAccess.registerDeathListener();
    }
    
    private static long lastMessageTime = 0;

    /**
     * Entry point for intercepted builds. Delegates to a reflection-safe bridge.
     */
    public static void onBuildIntercepted(Object context) {
        try {
            // Delegate to EffortlessBridge which handles the heavy lifting via reflection
            Map<net.minecraft.core.BlockPos, net.minecraft.world.level.block.state.BlockState> blocks = EffortlessBridge.processContext(context);
            
            if (blocks != null && !blocks.isEmpty()) {
                BuildTask task = new BuildTask(blocks);
                TASK_QUEUE.add(task);
                
                Minecraft mc = Minecraft.getInstance();
                long now = System.currentTimeMillis();
                if (now - lastMessageTime > 1000) {
                    MutableComponent msg = Component.literal("§a[EffortlessBaritone] Task ")
                        .append(Component.literal(task.getId())
                            .withStyle(style -> style
                                .withColor(ChatFormatting.GOLD)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, task.getId()))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to copy ID")))))
                        .append(Component.literal(" added (" + blocks.size() + " blocks). "))
                        .append(Component.literal("[START]")
                            .withStyle(style -> style
                                .withColor(ChatFormatting.GREEN)
                                .withBold(true)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eb start " + task.getId()))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to start task")))))
                        .append(Component.literal(" "));
                    
                    if (mc.player != null) {
                        mc.player.displayClientMessage(msg, false);
                    }
                    lastMessageTime = now;
                }
            }
        } catch (Throwable t) {
            // Silently fail if something goes wrong during bridge processing
        }
    }
    
    public static void onClientCommand(RegisterClientCommandsEvent event) {
        StartCommand.register(event.getDispatcher());
    }
}
