package dev.roocky.effortlessbaritone;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles all chat-based interaction for Effortless Baritone.
 * 
 * Hardened to have ZERO static dependencies on Baritone to prevent classloading crashes.
 */
public class StartCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> eb = LiteralArgumentBuilder.literal("eb");

        // --- START COMMANDS ---
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("start")
            .executes(context -> {
                if (EffortlessBaritoneMod.TASK_QUEUE.isEmpty()) {
                    sendMessage("§cNo tasks in queue!");
                    return 0;
                }
                BaritoneAccess.startBuild(EffortlessBaritoneMod.TASK_QUEUE.get(0));
                return 1;
            })
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("all")
                .executes(context -> {
                    if (EffortlessBaritoneMod.TASK_QUEUE.isEmpty()) {
                        sendMessage("§cNo tasks in queue!");
                        return 0;
                    }
                    mergeAndStartAll();
                    return 1;
                })
            )
            .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("id", StringArgumentType.string())
                .executes(context -> {
                    String id = StringArgumentType.getString(context, "id");
                    Optional<BuildTask> task = EffortlessBaritoneMod.TASK_QUEUE.stream()
                        .filter(t -> t.getId().equals(id))
                        .findFirst();
                    if (task.isPresent()) {
                        BaritoneAccess.startBuild(task.get());
                    } else {
                        sendMessage("§cTask " + id + " not found!");
                    }
                    return 1;
                })
            )
        );

        // --- BATCHING COMMANDS ---
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("merge")
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("all")
                .executes(context -> {
                    mergeAll();
                    return 1;
                })
            )
            .then(LiteralArgumentBuilder.<CommandSourceStack>literal("last")
                .then(RequiredArgumentBuilder.<CommandSourceStack, Integer>argument("count", IntegerArgumentType.integer(2))
                    .executes(context -> {
                        int count = IntegerArgumentType.getInteger(context, "count");
                        mergeLast(count);
                        return 1;
                    })
                )
            )
        );

        // --- CONFIG COMMANDS ---
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("safejop")
            .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("value", StringArgumentType.string())
                .executes(context -> {
                    String value = StringArgumentType.getString(context, "value");
                    if (value.equalsIgnoreCase("true") || value.equals("1")) {
                        EffortlessBaritoneMod.SAFE_STOP = true;
                        sendMessage("§aSafeStop (safejop) enabled! Tasks will stop on death.");
                    } else if (value.equalsIgnoreCase("false") || value.equals("0")) {
                        EffortlessBaritoneMod.SAFE_STOP = false;
                        sendMessage("§eSafeStop (safejop) disabled!");
                    } else {
                        sendMessage("§cInvalid value! Use true, false, 1, or 0.");
                    }
                    return 1;
                })
            )
            .executes(context -> {
                sendMessage("§6SafeStop (safejop) is currently: " + (EffortlessBaritoneMod.SAFE_STOP ? "§aENABLED" : "§cDISABLED"));
                return 1;
            })
        );

        // --- CONTROL COMMANDS ---
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("stop")
            .executes(context -> {
                BaritoneAccess.stop();
                sendMessage("§aBaritone stopped.");
                return 1;
            })
        );

        // /eb clear
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("clear")
            .executes(context -> {
                EffortlessBaritoneMod.TASK_QUEUE.clear();
                sendMessage("§aQueue cleared!");
                return 1;
            })
        );

        // /eb list
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("list")
            .executes(context -> {
                if (EffortlessBaritoneMod.TASK_QUEUE.isEmpty()) {
                    sendMessage("§eQueue is empty.");
                } else {
                    sendMessage("§6Current Tasks:");
                    for (BuildTask task : EffortlessBaritoneMod.TASK_QUEUE) {
                        String status = task.isActive() ? "§a[ACTIVE]" : "§7[QUEUED]";
                        sendMessage(status + " §f" + task.getId() + " - " + task.getBlockCount() + " blocks");
                    }
                }
                return 1;
            })
        );

        // /eb remove <id|last>
        eb.then(LiteralArgumentBuilder.<CommandSourceStack>literal("remove")
            .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("id", StringArgumentType.string())
                .executes(context -> {
                    String id = StringArgumentType.getString(context, "id");
                    if (id.equalsIgnoreCase("last")) {
                        if (!EffortlessBaritoneMod.TASK_QUEUE.isEmpty()) {
                            BuildTask removed = EffortlessBaritoneMod.TASK_QUEUE.remove(EffortlessBaritoneMod.TASK_QUEUE.size() - 1);
                            sendMessage("§aRemoved last task: " + removed.getId());
                        } else {
                            sendMessage("§cQueue is already empty!");
                        }
                    } else {
                        boolean removed = EffortlessBaritoneMod.TASK_QUEUE.removeIf(t -> t.getId().equals(id));
                        if (removed) {
                            sendMessage("§aRemoved task: " + id);
                        } else {
                            sendMessage("§cTask " + id + " not found!");
                        }
                    }
                    return 1;
                })
            )
        );

        dispatcher.register(eb);
    }

    private static void mergeAll() {
        if (EffortlessBaritoneMod.TASK_QUEUE.size() < 2) {
            sendMessage("§cNeed at least 2 tasks to merge!");
            return;
        }

        BuildTask mergedTask = BuildTask.merge(new ArrayList<>(EffortlessBaritoneMod.TASK_QUEUE));
        EffortlessBaritoneMod.TASK_QUEUE.clear();
        EffortlessBaritoneMod.TASK_QUEUE.add(mergedTask);

        sendMessage("§aMerged all tasks into " + mergedTask.getId() + " (" + mergedTask.getBlockCount() + " blocks)");
    }

    private static void mergeLast(int count) {
        int size = EffortlessBaritoneMod.TASK_QUEUE.size();
        if (size < 2) {
            sendMessage("§cNeed at least 2 tasks to merge!");
            return;
        }

        int actualCount = Math.min(count, size);
        List<BuildTask> toMerge = new ArrayList<>();
        for (int i = 0; i < actualCount; i++) {
            toMerge.add(0, EffortlessBaritoneMod.TASK_QUEUE.remove(EffortlessBaritoneMod.TASK_QUEUE.size() - 1));
        }

        BuildTask mergedTask = BuildTask.merge(toMerge);
        EffortlessBaritoneMod.TASK_QUEUE.add(mergedTask);

        sendMessage("§aMerged last " + actualCount + " tasks into " + mergedTask.getId() + " (" + mergedTask.getBlockCount() + " blocks)");
    }

    private static void mergeAndStartAll() {
        BuildTask mergedTask = BuildTask.merge(new ArrayList<>(EffortlessBaritoneMod.TASK_QUEUE));
        EffortlessBaritoneMod.TASK_QUEUE.clear();
        EffortlessBaritoneMod.TASK_QUEUE.add(mergedTask);
        BaritoneAccess.startBuild(mergedTask);
    }

    private static void sendMessage(String text) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal(text), false);
        }
    }
}
