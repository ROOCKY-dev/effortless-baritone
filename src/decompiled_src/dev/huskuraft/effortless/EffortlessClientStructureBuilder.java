/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.Effortless;
import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.EffortlessKeys;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.Interaction;
import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.core.InteractionType;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Items;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.core.Tuple2;
import dev.huskuraft.effortless.api.events.EventResult;
import dev.huskuraft.effortless.api.events.lifecycle.ClientTick;
import dev.huskuraft.effortless.api.math.BoundingBox3d;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3i;
import dev.huskuraft.effortless.api.platform.Client;
import dev.huskuraft.effortless.api.sound.SoundInstance;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.building.BuildResult;
import dev.huskuraft.effortless.building.BuildStage;
import dev.huskuraft.effortless.building.BuildState;
import dev.huskuraft.effortless.building.BuildType;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.SingleCommand;
import dev.huskuraft.effortless.building.StructureBuilder;
import dev.huskuraft.effortless.building.clipboard.Clipboard;
import dev.huskuraft.effortless.building.clipboard.Snapshot;
import dev.huskuraft.effortless.building.clipboard.SnapshotTransform;
import dev.huskuraft.effortless.building.config.BuilderConfig;
import dev.huskuraft.effortless.building.config.ClientConfig;
import dev.huskuraft.effortless.building.history.OperationResultStack;
import dev.huskuraft.effortless.building.operation.ItemSummary;
import dev.huskuraft.effortless.building.operation.OperationResult;
import dev.huskuraft.effortless.building.operation.OperationTooltip;
import dev.huskuraft.effortless.building.operation.batch.BatchOperationResult;
import dev.huskuraft.effortless.building.operation.block.BlockOperation;
import dev.huskuraft.effortless.building.operation.block.BlockOperationResult;
import dev.huskuraft.effortless.building.pattern.Pattern;
import dev.huskuraft.effortless.building.replace.Replace;
import dev.huskuraft.effortless.building.session.BatchBuildSession;
import dev.huskuraft.effortless.building.structure.BuildFeature;
import dev.huskuraft.effortless.building.structure.BuildFeatures;
import dev.huskuraft.effortless.building.structure.BuildMode;
import dev.huskuraft.effortless.building.structure.builder.Structure;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerCommandPacket;
import dev.huskuraft.effortless.renderer.opertaion.children.BlockOperationRenderer;
import dev.huskuraft.effortless.renderer.outliner.OutlineRenderLayers;
import dev.huskuraft.effortless.screen.wheel.AbstractWheelScreen;
import dev.huskuraft.effortless.session.config.ConstraintConfig;
import dev.huskuraft.effortless.session.config.SessionConfig;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EffortlessClientStructureBuilder
extends StructureBuilder {
    private final EffortlessClient entrance;
    private final Map<UUID, Context> contexts = new HashMap<UUID, Context>();
    private final Map<UUID, Context> historyContexts = new HashMap<UUID, Context>();
    private final Map<UUID, OperationResultStack> undoRedoStacks = new HashMap<UUID, OperationResultStack>();
    private final AtomicReference<ResourceLocation> lastClientPlayerLevel = new AtomicReference();
    private boolean isBuildMessageVisible = false;

    public EffortlessClientStructureBuilder(EffortlessClient entrance) {
        this.entrance = entrance;
        this.getEntrance().getEventRegistry().getClientTickEvent().register(this::onClientTick);
    }

    private EffortlessClient getEntrance() {
        return this.entrance;
    }

    private Player getPlayer() {
        return this.getEntrance().getClient().getPlayer();
    }

    @Override
    public BuildResult updateContext(Player player, UnaryOperator<Context> updater) {
        Context context = (Context)updater.apply(this.getContext(player));
        if (context.isFulfilled()) {
            this.setContext(player, this.getContext(this.getPlayer()).newInteraction());
            Context finalizedContext = context.finalize(player, BuildStage.INTERACT);
            Context clientContext = finalizedContext.withBuildType(BuildType.BUILD_CLIENT);
            BatchOperationResult result = new BatchBuildSession(this.getEntrance(), player, clientContext).commit();
            this.getEntrance().getChannel().sendPacket(new PlayerBuildPacket(this.getPlayer().getId(), finalizedContext));
            this.showContext(context.id(), 1024, player, context, result);
            this.playSoundInBatch(player, result);
            this.showTooltip(context.id(), 1024, player, result.getTooltip());
            this.getEntrance().getClientManager().getTooltipRenderer().hideEntry(this.generateId(player.getId(), Context.class), 0, true);
            return BuildResult.COMPLETED;
        }
        this.setContext(player, context);
        if (context.isIdle()) {
            return BuildResult.CANCELED;
        }
        return BuildResult.PARTIAL;
    }

    private void playSoundInBatch(Player player, BatchOperationResult batchOperationResult) {
        Context context;
        Optional<BlockInteraction> optional;
        HashMap<TypedBlockSound, Integer> soundMap = new HashMap<TypedBlockSound, Integer>();
        for (OperationResult operationResult : batchOperationResult.getResults()) {
            BlockOperationResult blockOperationResult;
            if (soundMap.size() >= 4) break;
            if (!(operationResult instanceof BlockOperationResult) || (blockOperationResult = (BlockOperationResult)operationResult).getBlockStateForRenderer() == null) continue;
            switch (blockOperationResult.getOperation().getType()) {
                case UPDATE: {
                    if (!blockOperationResult.getBlockStateForRenderer().isAir()) {
                        if (blockOperationResult.result().success()) {
                            soundMap.compute(TypedBlockSound.placeSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                            break;
                        }
                        soundMap.compute(TypedBlockSound.failSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                        break;
                    }
                    if (blockOperationResult.result().success()) {
                        soundMap.compute(TypedBlockSound.breakSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                        break;
                    }
                    soundMap.compute(TypedBlockSound.failSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                    break;
                }
                case INTERACT: {
                    if (blockOperationResult.result().success()) {
                        soundMap.compute(TypedBlockSound.hitSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                        break;
                    }
                    soundMap.compute(TypedBlockSound.failSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                    break;
                }
                case COPY: {
                    soundMap.compute(TypedBlockSound.hitSound(blockOperationResult.getBlockStateForRenderer()), (o, i) -> i == null ? 1 : i + 1);
                }
            }
        }
        if ((optional = (context = batchOperationResult.getOperation().getContext()).interactions().results().stream().filter(Objects::nonNull).min(Comparator.comparing(interaction1 -> interaction1.getBlockPosition().getCenter().distance(player.getEyePosition())))).isEmpty()) {
            return;
        }
        double distance = player.getEyePosition().distance(optional.get().getBlockPosition().getCenter());
        Vector3d location = player.getEyePosition().add(player.getEyeDirection().mul(Math.min(distance, 12.0)));
        for (Map.Entry entry : soundMap.entrySet()) {
            TypedBlockSound typedSound = (TypedBlockSound)entry.getKey();
            Integer count = (Integer)entry.getValue();
            for (int i2 = 0; i2 <= MathUtils.min(count / 2, 4); ++i2) {
                if (typedSound.blockState() == null) continue;
                SoundInstance sound = switch (typedSound.soundType().ordinal()) {
                    default -> throw new MatchException(null, null);
                    case 0 -> SoundInstance.createBlock(typedSound.blockState().getSoundSet().breakSound(), (typedSound.blockState().getSoundSet().volume() + 1.0f) / 2.0f, typedSound.blockState().getSoundSet().pitch() * 0.8f, location);
                    case 1 -> SoundInstance.createBlock(typedSound.blockState().getSoundSet().placeSound(), (typedSound.blockState().getSoundSet().volume() + 1.0f) / 2.0f, typedSound.blockState().getSoundSet().pitch() * 0.8f, location);
                    case 2 -> SoundInstance.createBlock(typedSound.blockState().getSoundSet().hitSound(), (typedSound.blockState().getSoundSet().volume() + 1.0f) / 2.0f, typedSound.blockState().getSoundSet().pitch() * 0.8f, location);
                    case 3 -> SoundInstance.createBlock(typedSound.blockState().getSoundSet().hitSound(), (typedSound.blockState().getSoundSet().volume() + 1.0f) / 3.0f, typedSound.blockState().getSoundSet().pitch() * 0.5f, location);
                };
                this.getPlayer().getClient().getSoundManager().playDelayed(sound, i2);
            }
        }
    }

    public void onSessionConfig(SessionConfig sessionConfig) {
        for (UUID uuid : this.getAllContexts().keySet()) {
            ConstraintConfig config = sessionConfig.getByPlayer(uuid);
            this.getAllContexts().computeIfPresent(uuid, (uuid1, context) -> context.withConstraintConfig(config));
        }
    }

    @Override
    public Context getDefaultContext(Player player) {
        SessionConfig constraintConfig = this.getEntrance().getSessionManager().getServerSessionConfig();
        BuilderConfig builderConfig = ((ClientConfig)this.getEntrance().getConfigStorage().get()).builderConfig();
        if (constraintConfig == null) {
            return Context.defaultSet().withConstraintConfig(ConstraintConfig.EMPTY).withBuilderConfig(builderConfig);
        }
        return Context.defaultSet().withConstraintConfig(constraintConfig.getByPlayer(player)).withBuilderConfig(builderConfig);
    }

    @Override
    public Context getContext(Player player) {
        return this.contexts.computeIfAbsent(player.getId(), uuid -> this.getDefaultContext(player));
    }

    private Context getHistoryContext(Player player) {
        return this.historyContexts.computeIfAbsent(player.getId(), uuid -> this.getDefaultContext(player));
    }

    private Context putHistoryContext(Player player, Context context) {
        return this.historyContexts.put(player.getId(), context);
    }

    @Override
    public Context getContextTraced(Player player) {
        Context context = this.getContext(player).finalize(player, BuildStage.INTERACT);
        if (context.isInteractionEmpty()) {
            context = context.clipboard().enabled() ? (context.clipboard().isEmpty() ? context.withBuildState(BuildState.COPY_STRUCTURE) : context.withBuildState(BuildState.PASTE_STRUCTURE)) : (player.getItemStack(InteractionHand.MAIN).isEmpty() || !player.getItemStack(InteractionHand.MAIN).isBlock() ? context.withBuildState(BuildState.INTERACT_BLOCK) : context.withBuildState(BuildState.PLACE_BLOCK));
        }
        return context.withNextInteraction(context.trace(player));
    }

    @Override
    public Map<UUID, Context> getAllContexts() {
        return this.contexts;
    }

    @Override
    public boolean setContext(Player player, Context context) {
        this.contexts.put(player.getId(), context);
        return true;
    }

    public boolean checkPermission(Player player) {
        if (!this.isSessionValid(player)) {
            this.getEntrance().getSessionManager().notifyPlayer();
            return false;
        }
        if (!this.isPermissionGranted(player)) {
            player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.no_permission")));
            return false;
        }
        return true;
    }

    @Override
    public boolean setStructure(Player player, Structure structure) {
        if (!this.checkPermission(player)) {
            return false;
        }
        this.updateContext(player, context -> context.withNoInteraction().withStructure(structure).withEmptyClipboard());
        if (structure.getMode().isDisabled()) {
            this.getEntrance().getClientManager().getTooltipRenderer().hideAllEntries(false);
            this.updateContext(player, context -> context);
        }
        return true;
    }

    @Override
    public boolean setClipboard(Player player, Clipboard clipboard) {
        if (!this.checkPermission(player)) {
            return false;
        }
        this.updateContext(player, context -> context.newInteraction().withClipboard(clipboard));
        return true;
    }

    @Override
    public boolean setPattern(Player player, Pattern pattern) {
        if (!this.checkPermission(player)) {
            return false;
        }
        this.updateContext(player, context -> context.withPattern(pattern).finalize(player, BuildStage.SET_PATTERN));
        return true;
    }

    @Override
    public boolean setReplace(Player player, Replace replace) {
        if (!this.checkPermission(player)) {
            return false;
        }
        this.updateContext(player, context -> context.withReplace(replace));
        return true;
    }

    @Override
    public void resetAll() {
        this.lastClientPlayerLevel.set(null);
        this.contexts.clear();
        this.undoRedoStacks.clear();
        this.getEntrance().getConfigStorage().update(config -> new ClientConfig(config.renderConfig(), config.patternConfig(), config.clipboardConfig()));
    }

    public EventResult onPlayerInteract(Player player, InteractionType type, InteractionHand hand) {
        if (((ClientConfig)this.getEntrance().getConfigStorage().get()).builderConfig().passiveMode() && !EffortlessKeys.PASSIVE_BUILD_MODIFIER.getKeyBinding().isDown() && !this.getContext(player).isBuilding()) {
            return EventResult.pass();
        }
        if (type == InteractionType.UNKNOWN) {
            return EventResult.pass();
        }
        BuildResult buildResult = this.updateContext(player, context -> {
            BuildState state = switch (type) {
                default -> throw new MatchException(null, null);
                case InteractionType.ATTACK -> {
                    if (context.clipboard().enabled()) {
                        yield BuildState.COPY_STRUCTURE;
                    }
                    yield BuildState.BREAK_BLOCK;
                }
                case InteractionType.USE_ITEM -> {
                    if (context.clipboard().enabled()) {
                        yield BuildState.PASTE_STRUCTURE;
                    }
                    if (player.getItemStack(hand).isEmpty() || !player.getItemStack(hand).isBlock()) {
                        yield BuildState.INTERACT_BLOCK;
                    }
                    yield BuildState.PLACE_BLOCK;
                }
                case InteractionType.UNKNOWN -> BuildState.IDLE;
            };
            BlockInteraction interaction = context.withBuildState(state).trace(player);
            Context nextContext = context.withBuildState(state).withNextInteraction(interaction);
            if (interaction == null) {
                return context.newInteraction();
            }
            if (interaction.getTarget() == Interaction.Target.MISS) {
                BlockInteraction traced = player.raytrace(32767.0, 0.0f, false);
                Text message = Text.empty().append(" (").append(Text.text(String.valueOf(MathUtils.round(traced.getPosition().distance(player.getEyePosition())))).withStyle(ChatFormatting.RED)).append(Text.text("/")).append(Text.text(String.valueOf(context.configs().constraintConfig().maxReachDistance()))).append(Text.text(")"));
                player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.cannot_reach_target").append(message)));
                return context.newInteraction();
            }
            if (interaction.getTarget() == Interaction.Target.ENTITY) {
                player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.cannot_reach_entity")));
                return context.newInteraction();
            }
            if (context.isBuilding() && context.buildState() != state) {
                switch (context.buildState()) {
                    case BREAK_BLOCK: {
                        player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.block_breaking_canceled")));
                        break;
                    }
                    case PLACE_BLOCK: {
                        player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.block_placing_canceled")));
                        break;
                    }
                    case INTERACT_BLOCK: {
                        player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.block_interacting_canceled")));
                        break;
                    }
                    case COPY_STRUCTURE: {
                        player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.structure_copying_canceled")));
                        break;
                    }
                    case PASTE_STRUCTURE: {
                        player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.structure_pasting_canceled")));
                    }
                }
                return context.newInteraction();
            }
            if (context.buildState() == BuildState.IDLE && state == BuildState.COPY_STRUCTURE && !context.clipboard().snapshot().isEmpty()) {
                player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.structure_pasting_canceled")));
                return context.newInteraction().withEmptyClipboard();
            }
            if (!context.withBuildState(state).hasPermission()) {
                if (state == BuildState.BREAK_BLOCK) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.no_block_break_permission")));
                }
                if (state == BuildState.PLACE_BLOCK) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.no_block_place_permission")));
                }
                if (state == BuildState.INTERACT_BLOCK) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.no_block_interact_permission")));
                }
                if (state == BuildState.COPY_STRUCTURE) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.no_structure_copy_permission")));
                }
                if (state == BuildState.PASTE_STRUCTURE) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.no_structure_paste_permission")));
                }
                return context.newInteraction();
            }
            if (!nextContext.isVolumeInBounds()) {
                if (nextContext.buildState() == BuildState.BREAK_BLOCK) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.block_break_volume_too_large").append(" (").append(Text.text(String.valueOf(nextContext.getVolume())).withStyle(ChatFormatting.RED)).append("/").append(String.valueOf(nextContext.getMaxVolume())).append(")")));
                }
                if (nextContext.buildState() == BuildState.PLACE_BLOCK) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.block_place_volume_too_large").append(" (").append(Text.text(String.valueOf(nextContext.getVolume())).withStyle(ChatFormatting.RED)).append("/").append(String.valueOf(nextContext.getMaxVolume())).append(")")));
                }
                if (nextContext.buildState() == BuildState.INTERACT_BLOCK) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.block_interact_volume_too_large").append(" (").append(Text.text(String.valueOf(nextContext.getVolume())).withStyle(ChatFormatting.RED)).append("/").append(String.valueOf(nextContext.getMaxVolume())).append(")")));
                }
                if (nextContext.buildState() == BuildState.COPY_STRUCTURE) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.structure_copy_volume_too_large").append(" (").append(Text.text(String.valueOf(nextContext.getVolume())).withStyle(ChatFormatting.RED)).append("/").append(String.valueOf(nextContext.getMaxVolume())).append(")")));
                }
                if (nextContext.buildState() == BuildState.PASTE_STRUCTURE) {
                    player.sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.structure_paste_volume_too_large").append(" (").append(Text.text(String.valueOf(nextContext.getVolume())).withStyle(ChatFormatting.RED)).append("/").append(String.valueOf(nextContext.getMaxVolume())).append(")")));
                }
                return context.newInteraction();
            }
            return nextContext;
        });
        if (buildResult.isSuccess()) {
            player.swing(hand);
        }
        return EventResult.interrupt(buildResult.isSuccess());
    }

    @Override
    public void onContextReceived(Player player, Context context) {
        if (context.isBuildType()) {
            return;
        }
        BatchOperationResult result = new BatchBuildSession(this.getEntrance(), player, context).commit();
        this.showContext(player.getId(), 1024, player, context, result);
        this.showTooltip(context.id(), 1024, player, result.getTooltip());
        if (context.isBuildClientType()) {
            this.playSoundInBatch(player, result);
        }
    }

    public void onTooltipReceived(Player player, OperationTooltip operationTooltip) {
        switch (operationTooltip.type()) {
            case BUILD: {
                this.showTooltip(operationTooltip.context().id(), 1024, player, operationTooltip);
                break;
            }
            default: {
                if (operationTooltip.context().buildMode() == BuildMode.DISABLED) {
                    ArrayList<Object> entries = new ArrayList<Object>();
                    entries.add(operationTooltip.itemSummary().values().stream().flatMap(Collection::stream).toList());
                    entries.add(Text.translate("effortless.history." + operationTooltip.type().getName()));
                    entries.add(operationTooltip.context().buildMode().getIcon());
                    this.getEntrance().getClientManager().getTooltipRenderer().showGroupEntry(UUID.randomUUID(), 1025, entries, true);
                    break;
                }
                this.showTooltip(operationTooltip.context().id(), 1024, player, operationTooltip);
            }
        }
    }

    public void onSnapshotCaptured(Player player, Snapshot snapshot) {
        this.updateContext(player, context -> context.withClipboard(context.clipboard().withSnapshot(snapshot)));
    }

    public void updateClipboard(Player player, SnapshotTransform action) {
        this.updateContext(player, context -> context.withClipboard(context.clipboard().withSnapshot(context.clipboard().snapshot().update(action))));
    }

    @Override
    public OperationResultStack getOperationResultStack(Player player) {
        return null;
    }

    @Override
    public void undo(Player player) {
        if (!this.checkPermission(player)) {
            return;
        }
        this.getEntrance().getChannel().sendPacket(new PlayerCommandPacket(SingleCommand.UNDO));
    }

    @Override
    public void redo(Player player) {
        if (!this.checkPermission(player)) {
            return;
        }
        this.getEntrance().getChannel().sendPacket(new PlayerCommandPacket(SingleCommand.REDO));
    }

    public void onClientTick(Client client, ClientTick.Phase phase) {
        if (phase == ClientTick.Phase.END) {
            return;
        }
        if (this.getEntrance().getClient() == null || this.getPlayer() == null) {
            this.resetAll();
            return;
        }
        Player player = this.getPlayer();
        if (!this.isSessionValid(player)) {
            this.resetContext(player);
            return;
        }
        if (!this.isPermissionGranted(player)) {
            this.resetContext(player);
            return;
        }
        if (player.isDeadOrDying()) {
            this.resetInteractions(player);
            return;
        }
        if (!player.getWorld().getDimensionId().location().equals(this.lastClientPlayerLevel.get())) {
            this.resetInteractions(player);
            this.lastClientPlayerLevel.set(player.getWorld().getDimensionId().location());
            return;
        }
        if (this.getContext(player).isDisabled()) {
            this.clearBuildMessage(player);
            return;
        }
        if (((ClientConfig)this.getEntrance().getConfigStorage().get()).builderConfig().passiveMode() && !EffortlessKeys.PASSIVE_BUILD_MODIFIER.getKeyBinding().isDown() && !this.getContext(player).isBuilding()) {
            this.getEntrance().getClientManager().getTooltipRenderer().hideEntry(this.generateId(player.getId(), Context.class), 0, false);
            return;
        }
        this.reloadContext(player);
        Context context1 = this.getContextTraced(player);
        Context context = context1.withBuildType(BuildType.PREVIEW);
        if (context.getVolume() > ((ClientConfig)this.getEntrance().getConfigStorage().get()).renderConfig().maxRenderVolume()) {
            this.showContext(player.getId(), 0, player, context, null);
            this.showTooltip(player.getId(), 0, player, OperationTooltip.build(context));
        } else {
            BatchOperationResult result = new BatchBuildSession(this.getEntrance(), player, context.withBuildType(BuildType.PREVIEW)).commit();
            this.showContext(player.getId(), 0, player, context, result);
            this.showTooltip(player.getId(), 0, player, result.getTooltip());
        }
        this.showBuildMessage(player, context);
        if (this.getHistoryContext(player).getVolume() != context.getVolume()) {
            this.putHistoryContext(player, context);
            Optional<BlockInteraction> nearestInteraction = context.interactions().results().stream().filter(Objects::nonNull).min(Comparator.comparing(interaction1 -> interaction1.getBlockPosition().getCenter().distance(player.getEyePosition())));
            if (nearestInteraction.isEmpty()) {
                return;
            }
            BlockState blockState = Items.AIR.item().getBlock().getDefaultBlockState();
            double distance = player.getEyePosition().distance(nearestInteraction.get().getBlockPosition().getCenter());
            Vector3d location = player.getEyePosition().add(player.getEyeDirection().mul(Math.min(distance, 3.0)));
            SoundInstance sound = SoundInstance.createBlock(blockState.getSoundSet().hitSound(), (blockState.getSoundSet().volume() + 1.0f) / 2.0f * 0.1f, blockState.getSoundSet().pitch() * 0.2f, location);
            this.getEntrance().getClient().getSoundManager().play(sound);
        }
        this.getEntrance().getChannel().sendPacket(new PlayerBuildPacket(this.getPlayer().getId(), context));
    }

    private void reloadContext(Player player) {
        this.setContext(player, this.getContext(player).finalize(player, BuildStage.TICK));
    }

    private boolean isSessionValid(Player player) {
        return this.getEntrance().getSessionManager().isSessionValid();
    }

    private boolean isPermissionGranted(Player player) {
        return this.getEntrance().getSessionManager().getServerSessionConfig().getByPlayer(player).allowUseMod();
    }

    private UUID generateId(UUID uuid, Object tag) {
        return new UUID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() + (long)tag.hashCode());
    }

    public void showContext(UUID uuid, int priority, Player player, Context context, OperationResult result) {
        if (player.getId() != this.getPlayer().getId() && !((ClientConfig)this.getEntrance().getConfigStorage().get()).renderConfig().showOtherPlayersBuild()) {
            return;
        }
        this.getEntrance().getClientManager().getPatternRenderer().showPattern(uuid, context);
        if (context.interactions().isEmpty()) {
            this.getEntrance().getClientManager().getOutlineRenderer().remove(this.generateId(uuid, BoundingBox3d.class));
        } else {
            BoundingBox3d box = BoundingBox3d.fromLowerCornersOf((Vector3i[])context.interactions().results().stream().filter(Objects::nonNull).map(BlockInteraction::getBlockPosition).map(BlockPosition::toVector3i).toArray(Vector3i[]::new));
            this.getEntrance().getClientManager().getOutlineRenderer().showBoundingBox(this.generateId(uuid, BoundingBox3d.class), box).texture(OutlineRenderLayers.CHECKERED_THIN_TEXTURE_LOCATION).lightMap(240).disableNormals().colored(Color.DARK_GRAY).stroke(0.03125f);
        }
        if (result instanceof BatchOperationResult) {
            BatchOperationResult batchOperationResult = (BatchOperationResult)result;
            this.getEntrance().getClientManager().getOperationsRenderer().showResult(uuid, result);
            Map<Color, List<BlockOperationResult>> resultMap = batchOperationResult.getResults().stream().filter(BlockOperationResult.class::isInstance).map(BlockOperationResult.class::cast).filter(blockOperationResult -> BlockOperationRenderer.getColorByOpResult(blockOperationResult) != null).collect(Collectors.groupingBy(BlockOperationRenderer::getColorByOpResult));
            for (Color allColor : BlockOperationRenderer.getAllColors()) {
                if (resultMap.get(allColor) == null) {
                    this.getEntrance().getClientManager().getOutlineRenderer().remove(this.generateId(uuid, allColor));
                    continue;
                }
                List<BlockPosition> locations = resultMap.get(allColor).stream().map(BlockOperationResult::getOperation).map(BlockOperation::getBlockPosition).filter(Objects::nonNull).toList();
                this.getEntrance().getClientManager().getOutlineRenderer().showCluster(this.generateId(uuid, allColor), locations).texture(OutlineRenderLayers.CHECKERED_THIN_TEXTURE_LOCATION).lightMap(240).disableNormals().colored(allColor).stroke(0.03125f);
            }
        } else {
            this.getEntrance().getClientManager().getOperationsRenderer().remove(uuid);
            for (Color allColor : BlockOperationRenderer.getAllColors()) {
                this.getEntrance().getClientManager().getOutlineRenderer().remove(this.generateId(uuid, allColor));
            }
        }
    }

    public void showTooltip(UUID id, int priority, Player player, OperationTooltip tooltip) {
        Context context = tooltip.context();
        if (player == null) {
            player = this.getPlayer();
        }
        if (player.getId() != this.getPlayer().getId() && !((ClientConfig)this.getEntrance().getConfigStorage().get()).renderConfig().showOtherPlayersBuildTooltips()) {
            return;
        }
        if (player.getGameMode().isSpectator()) {
            this.getEntrance().getClientManager().getTooltipRenderer().hideEntry(this.generateId(id, Context.class), priority, false);
            return;
        }
        ArrayList<Object> entries = new ArrayList<Object>();
        Map<ItemSummary, List<ItemStack>> blockStateSummary = tooltip.itemSummary();
        if (!blockStateSummary.isEmpty()) {
            ArrayList allProducts = new ArrayList();
            for (ItemSummary summary : ItemSummary.values()) {
                List items = blockStateSummary.getOrDefault((Object)summary, List.of());
                if (items.isEmpty()) continue;
                ChatFormatting color = switch (summary) {
                    default -> throw new MatchException(null, null);
                    case ItemSummary.BLOCKS_PLACED -> ChatFormatting.WHITE;
                    case ItemSummary.BLOCKS_DESTROYED -> ChatFormatting.RED;
                    case ItemSummary.BLOCKS_INTERACTED -> ChatFormatting.YELLOW;
                    case ItemSummary.BLOCKS_COPIED -> ChatFormatting.GREEN;
                    case ItemSummary.BLOCKS_NOT_REPLACEABLE -> ChatFormatting.GRAY;
                    case ItemSummary.BLOCKS_NOT_BREAKABLE -> ChatFormatting.GRAY;
                    case ItemSummary.BLOCKS_NOT_INTERACTABLE -> ChatFormatting.GRAY;
                    case ItemSummary.BLOCKS_NOT_COPYABLE -> ChatFormatting.GRAY;
                    case ItemSummary.BLOCKS_ITEMS_INSUFFICIENT -> ChatFormatting.RED;
                    case ItemSummary.BLOCKS_TOOLS_INSUFFICIENT -> ChatFormatting.GRAY;
                    case ItemSummary.BLOCKS_BLACKLISTED -> ChatFormatting.GRAY;
                    case ItemSummary.BLOCKS_NO_PERMISSION -> ChatFormatting.GRAY;
                    case ItemSummary.CONTAINER_CONSUMED -> ChatFormatting.WHITE;
                    case ItemSummary.CONTAINER_DROPPED -> ChatFormatting.WHITE;
                };
                entries.add(new Tuple2(items, color.getColor()));
                entries.add(Text.translate("effortless.build.summary." + summary.name().toLowerCase(Locale.ROOT)).withStyle(color));
                allProducts.addAll(items);
            }
            if (allProducts.isEmpty()) {
                entries.add(Text.translate("effortless.build.summary.no_item_summary").withStyle(ChatFormatting.GRAY));
            }
        } else {
            entries.add(Text.translate("effortless.build.summary.pending_item_summary").withStyle(ChatFormatting.GRAY));
        }
        ArrayList<Tuple2<Text, Text>> texts = new ArrayList<Tuple2<Text, Text>>();
        texts.add(new Tuple2<Text, Text>(Text.translate("effortless.build.summary.structure").withStyle(ChatFormatting.WHITE), context.buildMode().getDisplayName().withStyle(ChatFormatting.GOLD)));
        texts.add(new Tuple2<Text, Text>(AbstractWheelScreen.button(context.replaceStrategy()).getCategory().withStyle(ChatFormatting.WHITE), AbstractWheelScreen.button(context.replaceStrategy()).getName().withStyle(ChatFormatting.GOLD)));
        for (BuildFeatures supportedFeature : context.structure().getSupportedFeatures()) {
            Optional<BuildFeature> option = context.buildFeatures().stream().filter(feature -> Objects.equals(feature.getCategory(), supportedFeature.getName())).findFirst();
            if (option.isEmpty()) continue;
            AbstractWheelScreen.Button<BuildFeature> button = AbstractWheelScreen.button(option.get());
            texts.add(new Tuple2<Text, Text>(button.getCategory().withStyle(ChatFormatting.WHITE), button.getName().withStyle(ChatFormatting.GOLD)));
        }
        if (context.pattern().enabled()) {
            texts.add(new Tuple2<Text, Text>(Text.translate("effortless.build.summary.pattern").withStyle(ChatFormatting.WHITE), (context.pattern().enabled() ? Text.translate("effortless.build.summary.pattern_enabled") : Text.translate("effortless.build.summary.pattern_disabled")).withStyle(ChatFormatting.GOLD)));
        }
        entries.add(texts);
        entries.add(context.buildMode().getIcon());
        this.getEntrance().getClientManager().getTooltipRenderer().showGroupEntry(this.generateId(id, Context.class), priority, entries, context.isBuildType());
    }

    public void clearBuildMessage(Player player) {
        if (this.isBuildMessageVisible) {
            player.sendClientMessage(Text.empty(), true);
            this.isBuildMessageVisible = false;
        }
    }

    public void showBuildMessage(Player player, Context context) {
        List<Integer> dimensions = Stream.of(context.getInteractionBox().x(), context.getInteractionBox().y(), context.getInteractionBox().z()).filter(i -> i > 1).toList();
        if (dimensions.isEmpty()) {
            dimensions = List.of(Integer.valueOf(1));
        }
        Text message = Text.empty();
        message = context.tracingResult().isSuccess() ? message.append(context.buildState().getDisplayName(context.buildMode())).append(" ").append("(").append(dimensions.stream().map(String::valueOf).collect(Collectors.joining("x"))).append("=").append(Text.text(String.valueOf(context.getVolume())).withStyle(!context.isVolumeInBounds() ? ChatFormatting.RED : ChatFormatting.WHITE)).append(")") : Text.empty();
        player.sendClientMessage(message, true);
        this.isBuildMessageVisible = true;
    }

    record TypedBlockSound(SoundType soundType, BlockState blockState) {
        static TypedBlockSound breakSound(BlockState blockState) {
            return new TypedBlockSound(SoundType.BREAK, blockState);
        }

        static TypedBlockSound failSound(BlockState blockState) {
            return new TypedBlockSound(SoundType.FAIL, blockState);
        }

        static TypedBlockSound placeSound(BlockState blockState) {
            return new TypedBlockSound(SoundType.PLACE, blockState);
        }

        static TypedBlockSound hitSound(BlockState blockState) {
            return new TypedBlockSound(SoundType.HIT, blockState);
        }

        static enum SoundType {
            BREAK,
            PLACE,
            HIT,
            FAIL;

        }
    }
}
