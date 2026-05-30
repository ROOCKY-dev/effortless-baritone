package dev.roocky.effortlessbaritone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Isolated bridge for Effortless Building using reflection.
 * Prevents direct class references that cause crashes if the mod is missing or classloaded differently.
 */
public class EffortlessBridge {

    private static Class<?> getEffortlessClass(String name) throws ClassNotFoundException {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e2) {
                try {
                    return Class.forName(name, true, net.minecraft.client.Minecraft.class.getClassLoader());
                } catch (ClassNotFoundException e3) {
                    throw e3;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<BlockPos, BlockState> processContext(Object contextObj) {
        try {
            // context.buildType()
            Method buildTypeMethod = contextObj.getClass().getMethod("buildType");
            Object buildType = buildTypeMethod.invoke(contextObj);

            // Check if buildType == PREVIEW (enum comparison)
            if (buildType.toString().equals("PREVIEW")) {
                return null;
            }

            // EffortlessClient.getInstance()
            Class<?> effortlessClientClass = getEffortlessClass("dev.huskuraft.effortless.EffortlessClient");
            Method getInstance = effortlessClientClass.getMethod("getInstance");
            Object clientInstance = getInstance.invoke(null);

            // clientInstance.getClient().getPlayer()
            Method getClient = clientInstance.getClass().getMethod("getClient");
            Object client = getClient.invoke(clientInstance);
            Method getPlayer = client.getClass().getMethod("getPlayer");
            Object effortlessPlayer = getPlayer.invoke(client);

            // context.withBuildType(PREVIEW)
            Class<?> buildTypeClass = getEffortlessClass("dev.huskuraft.effortless.building.BuildType");
            Object previewEnum = Enum.valueOf((Class<Enum>) buildTypeClass, "PREVIEW");
            Method withBuildType = contextObj.getClass().getMethod("withBuildType", buildTypeClass);
            Object previewContext = withBuildType.invoke(contextObj, previewEnum);

            // new BatchBuildSession(clientInstance, player, previewContext)
            Class<?> batchBuildSessionClass = getEffortlessClass("dev.huskuraft.effortless.building.session.BatchBuildSession");
            Class<?> contextClass = getEffortlessClass("dev.huskuraft.effortless.building.Context");
            Class<?> playerClass = getEffortlessClass("dev.huskuraft.effortless.api.core.Player");
            Class<?> entranceClass = getEffortlessClass("dev.huskuraft.effortless.api.platform.Entrance");
            
            Object session = batchBuildSessionClass.getConstructor(entranceClass, playerClass, contextClass)
                .newInstance(clientInstance, effortlessPlayer, previewContext);

            // session.commit()
            Method commit = session.getClass().getMethod("commit");
            Object batchResult = commit.invoke(session);

            // result.getResults()
            Method getResults = batchResult.getClass().getMethod("getResults");
            List<Object> results = (List<Object>) getResults.invoke(batchResult);

            Map<BlockPos, BlockState> blocks = new HashMap<>();
            
            Class<?> blockOperationResultClass = getEffortlessClass("dev.huskuraft.effortless.building.operation.block.BlockOperationResult");
            Class<?> blockStateClass = getEffortlessClass("dev.huskuraft.effortless.api.core.BlockState");
            Class<?> mcBlockStateClass = getEffortlessClass("dev.huskuraft.effortless.neoforge.core.MinecraftBlockState");

            for (Object opRes : results) {
                if (blockOperationResultClass.isInstance(opRes)) {
                    // opRes.getBlockStateToPlace()
                    Method getPlace = blockOperationResultClass.getMethod("getBlockStateToPlace");
                    Object targetState = getPlace.invoke(opRes);

                    boolean isAir = true;
                    if (targetState != null) {
                        Method isAirMethod = blockStateClass.getMethod("isAir");
                        isAir = (boolean) isAirMethod.invoke(targetState);
                    }

                    if (isAir) {
                        // Check if it's a break operation
                        Method getBreak = blockOperationResultClass.getMethod("getBlockStateToBreak");
                        Object broken = getBreak.invoke(opRes);
                        if (broken != null) {
                            Method isAirBreak = blockStateClass.getMethod("isAir");
                            if ((boolean) isAirBreak.invoke(broken)) {
                                continue; // Already air
                            }
                        } else {
                            continue; // Nothing to place or break
                        }
                    }

                    // opRes.getOperation().getBlockPosition()
                    Method getOp = blockOperationResultClass.getMethod("getOperation");
                    Object operation = getOp.invoke(opRes);
                    Method getPos = operation.getClass().getMethod("getBlockPosition");
                    Object pos = getPos.invoke(operation);

                    if (pos != null) {
                        Method getX = pos.getClass().getMethod("x");
                        Method getY = pos.getClass().getMethod("y");
                        Method getZ = pos.getClass().getMethod("z");
                        
                        BlockPos mcPos = new BlockPos((int)getX.invoke(pos), (int)getY.invoke(pos), (int)getZ.invoke(pos));
                        BlockState mcState;

                        if (!isAir && mcBlockStateClass.isInstance(targetState)) {
                            // targetState.refs()
                            Method refs = mcBlockStateClass.getMethod("refs");
                            mcState = (BlockState) refs.invoke(targetState);
                        } else {
                            mcState = net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
                        }

                        if (mcState != null) {
                            blocks.put(mcPos, mcState);
                        }
                    }
                }
            }
            return blocks;

        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}
