package dev.roocky.effortlessbaritone;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.schematic.ISchematic;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class StartCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Register client command
        LiteralArgumentBuilder<CommandSourceStack> command = LiteralArgumentBuilder.<CommandSourceStack>literal("eb")
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("start")
                .executes(context -> {
                    startBaritone();
                    return 1;
                })
            );
        dispatcher.register(command);
    }

    private static void startBaritone() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        if (EffortlessBaritoneMod.QUEUED_PLACEMENTS.isEmpty()) {
            mc.player.displayClientMessage(net.minecraft.network.chat.Component.literal("§c[EffortlessBaritone] Queue is empty!"), false);
            return;
        }

        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        
        // We capture a snapshot of the queued placements and clear the queue
        Map<BlockPos, BlockState> snapshot = new HashMap<>(EffortlessBaritoneMod.QUEUED_PLACEMENTS);
        EffortlessBaritoneMod.QUEUED_PLACEMENTS.clear();
        
        ISchematic schematic = new BaritoneSchematicAdapter(snapshot);
        
        // Feed it to Baritone BuilderProcess
        baritone.getBuilderProcess().build("effortless_build", schematic, BlockPos.ZERO);
        
        mc.player.displayClientMessage(net.minecraft.network.chat.Component.literal("§a[EffortlessBaritone] Baritone started!"), false);
    }
}
