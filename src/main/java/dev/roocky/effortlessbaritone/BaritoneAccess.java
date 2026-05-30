package dev.roocky.effortlessbaritone;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hardened reflection bridge for Baritone.
 * All Baritone API interactions MUST go through this class.
 * NO Baritone imports are allowed in any other class.
 */
public class BaritoneAccess {

    private static Class<?> getBaritoneApiClass() throws Exception {
        try {
            return Class.forName("baritone.api.BaritoneAPI");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName("baritone.api.BaritoneAPI", true, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e2) {
                // Last ditch effort: find the mod container and use its classloader
                try {
                    return net.neoforged.fml.ModList.get().getModContainerById("baritoe")
                        .map(container -> {
                            try {
                                // ModContainer.getMod() is the mod instance, its classloader can see the mod
                                Method getMod = container.getClass().getMethod("getMod");
                                Object modInstance = getMod.invoke(container);
                                return Class.forName("baritone.api.BaritoneAPI", true, modInstance.getClass().getClassLoader());
                            } catch (Exception ex) {
                                return null;
                            }
                        })
                        .orElseThrow(() -> new ClassNotFoundException("BaritoneAPI not found via ModContainer classloader", e2));
                } catch (Throwable e3) {
                    try {
                        return Class.forName("baritone.api.BaritoneAPI", true, Minecraft.class.getClassLoader());
                    } catch (ClassNotFoundException e4) {
                        throw new ClassNotFoundException("BaritoneAPI not found in any classloader (checked Default, Context, ModContainer, and Minecraft)", e4);
                    }
                }
            }
        }
    }

    private static Object getBaritone() throws Exception {
        Class<?> baritoneApiClass = getBaritoneApiClass();
        Method getProvider = baritoneApiClass.getMethod("getProvider");
        Object provider = getProvider.invoke(null);
        Method getPrimaryBaritone = provider.getClass().getMethod("getPrimaryBaritone");
        return getPrimaryBaritone.invoke(provider);
    }

    private static Object getSettings() throws Exception {
        Class<?> baritoneApiClass = getBaritoneApiClass();
        Method getSettings = baritoneApiClass.getMethod("getSettings");
        return getSettings.invoke(null);
    }

    public static void registerDeathListener() {
        try {
            Object baritone = getBaritone();
            Method getGameEventHandler = baritone.getClass().getMethod("getGameEventHandler");
            Object eventBus = getGameEventHandler.invoke(baritone);

            Class<?> iGameEventListenerClass = Class.forName("baritone.api.event.listener.IGameEventListener", true, getBaritoneApiClass().getClassLoader());

            Object listenerProxy = Proxy.newProxyInstance(
                iGameEventListenerClass.getClassLoader(),
                new Class<?>[]{iGameEventListenerClass},
                (proxy, method, args) -> {
                    if (method.getName().equals("onPlayerDeath")) {
                        onPlayerDeathInternal();
                    }
                    if (method.getReturnType().equals(Void.TYPE)) return null;
                    if (method.getReturnType().equals(Boolean.TYPE)) return false;
                    return null;
                }
            );

            Method registerEventListener = eventBus.getClass().getMethod("registerEventListener", iGameEventListenerClass);
            registerEventListener.invoke(eventBus, listenerProxy);

        } catch (Throwable ignored) {}
    }

    @SuppressWarnings("unchecked")
    public static void startBuild(BuildTask task) {
        try {
            Object baritone = getBaritone();
            Object settings = getSettings();
            
            // Access settings fields via reflection
            // Settings has public fields of type Setting<T>
            updateSetting(settings, "allowBreak", true);
            updateSetting(settings, "allowPlace", true);
            updateSetting(settings, "buildInLayers", true);
            updateSetting(settings, "layerHeight", 2);
            updateSetting(settings, "backfill", true);
            updateSetting(settings, "buildIgnoreDirection", true);
            updateSetting(settings, "preferSilkTouch", true);
            updateSetting(settings, "autoTool", true);

            // buildIgnoreProperties
            Object ignoredPropsSetting = settings.getClass().getField("buildIgnoreProperties").get(settings);
            Field valueField = ignoredPropsSetting.getClass().getField("value");
            List<String> ignoredProps = new ArrayList<>((List<String>) valueField.get(ignoredPropsSetting));
            String[] standardIgnores = {"snowy", "waterlogged", "lit", "powered"};
            for (String p : standardIgnores) {
                if (!ignoredProps.contains(p)) ignoredProps.add(p);
            }
            valueField.set(ignoredPropsSetting, ignoredProps);
            System.out.println("[EffortlessBaritone-Debug] Updated buildIgnoreProperties");

            // buildValidSubstitutes
            Object validSubsSetting = settings.getClass().getField("buildValidSubstitutes").get(settings);
            Map<Block, List<Block>> validSubs = new HashMap<>((Map<Block, List<Block>>) valueField.get(validSubsSetting));
            for (net.minecraft.world.level.block.state.BlockState bs : task.getBlocks().values()) {
                Block b = bs.getBlock();
                List<Block> subs = FuzzyMatcher.getSubstitutes(b);
                if (!subs.isEmpty()) {
                    List<Block> currentSubs = new ArrayList<>(validSubs.getOrDefault(b, List.of()));
                    for (Block sub : subs) {
                        if (!currentSubs.contains(sub)) currentSubs.add(sub);
                    }
                    validSubs.put(b, currentSubs);
                }
            }
            valueField.set(validSubsSetting, validSubs);
            System.out.println("[EffortlessBaritone-Debug] Updated buildValidSubstitutes");

            // Create Schematic Proxy
            Class<?> iSchematicClass = Class.forName("baritone.api.schematic.ISchematic", true, getBaritoneApiClass().getClassLoader());
            BaritoneSchematicAdapter adapter = new BaritoneSchematicAdapter(task.getBlocks());
            
            Object schematicProxy = Proxy.newProxyInstance(
                iSchematicClass.getClassLoader(),
                new Class<?>[]{iSchematicClass},
                (proxy, method, args) -> {
                    // Forward calls to our adapter
                    try {
                        Method targetMethod = adapter.getClass().getMethod(method.getName(), method.getParameterTypes());
                        return targetMethod.invoke(adapter, args);
                    } catch (NoSuchMethodException e) {
                        // Fallback: try finding a method with the same name and parameter count
                        for (Method m : adapter.getClass().getMethods()) {
                            if (m.getName().equals(method.getName()) && (args == null ? 0 : args.length) == m.getParameterCount()) {
                                try {
                                    return m.invoke(adapter, args);
                                } catch (Exception ignored) {}
                            }
                        }
                        // Handle ISchematic default methods or unknown methods gracefully
                        if (method.getName().equals("isModuleCustom")) return false;
                        Class<?> rt = method.getReturnType();
                        if (rt == boolean.class) return false;
                        if (rt == int.class || rt == short.class || rt == byte.class || rt == char.class) return 0;
                        if (rt == long.class) return 0L;
                        if (rt == float.class) return 0.0f;
                        if (rt == double.class) return 0.0;
                        return null;
                    }
                }
            );

            // Execute build
            Method getBuilderProcess = baritone.getClass().getMethod("getBuilderProcess");
            Object builderProcess = getBuilderProcess.invoke(baritone);
            
            BlockPos origin = new BlockPos(adapter.getMinX(), adapter.getMinY(), adapter.getMinZ());
            Method build = builderProcess.getClass().getMethod("build", String.class, iSchematicClass, net.minecraft.core.Vec3i.class);
            build.invoke(builderProcess, "effortless_build_" + task.getId(), schematicProxy, origin);
            
            task.setActive(true);

        } catch (Throwable t) {
            t.printStackTrace();
            sendMessage("§c[BaritoneBridge] Failed to start build: " + t.getMessage());
        }
    }

    public static void stop() {
        try {
            Object baritone = getBaritone();
            Method getBuilderProcess = baritone.getClass().getMethod("getBuilderProcess");
            Object builderProcess = getBuilderProcess.invoke(baritone);
            
            Method isActive = builderProcess.getClass().getMethod("isActive");
            
            Method getPathingBehavior = baritone.getClass().getMethod("getPathingBehavior");
            Object pathingBehavior = getPathingBehavior.invoke(baritone);
            Method isPathing = pathingBehavior.getClass().getMethod("isPathing");
            
            if ((boolean)isActive.invoke(builderProcess) || (boolean)isPathing.invoke(pathingBehavior)) {
                Method forceCancel = pathingBehavior.getClass().getMethod("forceCancel");
                forceCancel.invoke(pathingBehavior);
                EffortlessBaritoneMod.TASK_QUEUE.forEach(t -> t.setActive(false));
            }
        } catch (Throwable ignored) {}
    }

    private static void updateSetting(Object settings, String fieldName, Object value) {
        try {
            Object setting = settings.getClass().getField(fieldName).get(settings);
            setting.getClass().getField("value").set(setting, value);
            System.out.println("[EffortlessBaritone-Debug] Set setting " + fieldName + " to " + value);
        } catch (Throwable t) {
            System.err.println("[EffortlessBaritone-Debug] Failed to set setting " + fieldName + ": " + t.getMessage());
            t.printStackTrace();
        }
    }

    private static void onPlayerDeathInternal() {
        if (EffortlessBaritoneMod.SAFE_STOP) {
            stop();
            sendMessage("§c[EffortlessBaritone] Player died! Stopping all tasks due to SafeStop.");
        }
    }

    private static void sendMessage(String text) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal(text), false);
        }
    }
}
