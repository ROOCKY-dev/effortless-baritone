/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.building.session;

import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.core.Items;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.tag.RecordTag;
import dev.huskuraft.effortless.building.BuildState;
import dev.huskuraft.effortless.building.BuildType;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.Storage;
import dev.huskuraft.effortless.building.clipboard.Snapshot;
import dev.huskuraft.effortless.building.operation.OperationFilter;
import dev.huskuraft.effortless.building.operation.batch.BatchOperation;
import dev.huskuraft.effortless.building.operation.batch.BatchOperationResult;
import dev.huskuraft.effortless.building.operation.batch.DeferredBatchOperation;
import dev.huskuraft.effortless.building.operation.block.BlockInteractOperation;
import dev.huskuraft.effortless.building.operation.block.BlockOperation;
import dev.huskuraft.effortless.building.operation.block.BlockStateCopyOperation;
import dev.huskuraft.effortless.building.operation.block.BlockStateCopyOperationResult;
import dev.huskuraft.effortless.building.operation.block.BlockStateUpdateOperation;
import dev.huskuraft.effortless.building.pattern.Transformer;
import dev.huskuraft.effortless.building.pattern.randomize.ItemRandomizer;
import dev.huskuraft.effortless.building.session.BuildSession;
import dev.huskuraft.effortless.networking.packets.player.PlayerSnapshotCapturePacket;
import java.util.Objects;
import java.util.stream.Stream;

public class BatchBuildSession
implements BuildSession {
    private final Entrance entrance;
    private final World world;
    private final Player player;
    private final Context context;
    private BatchOperationResult lastResult;

    public BatchBuildSession(Entrance entrance, Player player, Context context) {
        this.entrance = entrance;
        this.world = player.getWorld();
        this.player = player;
        this.context = context;
    }

    public Entrance getEntrance() {
        return this.entrance;
    }

    protected BlockOperation createBlockPlaceOperationFromHit(World world, Player player, Context context, Storage storage, BlockInteraction interaction, BlockState blockState, RecordTag entityTag) {
        return new BlockStateUpdateOperation(world, player, context, storage, interaction, blockState, entityTag, context.extras().extras());
    }

    protected BlockOperation createBlockBreakOperationFromHit(World world, Player player, Context context, Storage storage, BlockInteraction interaction) {
        return new BlockStateUpdateOperation(world, player, context, storage, interaction, Items.AIR.item().getBlock().getDefaultBlockState(), null, context.extras().extras());
    }

    protected BlockOperation createBlockInteractOperationFromHit(World world, Player player, Context context, Storage storage, BlockInteraction interaction) {
        return new BlockInteractOperation(world, player, context, storage, interaction, context.extras().extras());
    }

    protected BlockOperation createBlockCopyOperationFromHit(World world, Player player, Context context, Storage storage, BlockInteraction interaction) {
        return new BlockStateCopyOperation(world, player, context, storage, interaction, context.extras().extras());
    }

    protected BatchOperation create(World world, Player player, Context context) {
        Storage storage = Storage.create(player, context.isPreviewType() || context.isBuildClientType());
        ItemRandomizer inHandTransformer = ItemRandomizer.single(null, player.getItemStack(InteractionHand.MAIN).getItem());
        BatchOperation operations = new DeferredBatchOperation(context, () -> switch (context.buildState()) {
            default -> throw new MatchException(null, null);
            case BuildState.IDLE -> Stream.empty();
            case BuildState.BREAK_BLOCK -> context.collectInteractions().map(interaction -> this.createBlockBreakOperationFromHit(world, player, context, storage, (BlockInteraction)interaction));
            case BuildState.PLACE_BLOCK -> context.collectInteractions().map(interaction -> this.createBlockPlaceOperationFromHit(world, player, context, storage, (BlockInteraction)interaction, Items.AIR.item().getBlock().getDefaultBlockState(), null));
            case BuildState.INTERACT_BLOCK -> context.collectInteractions().map(interaction -> this.createBlockInteractOperationFromHit(world, player, context, storage, (BlockInteraction)interaction));
            case BuildState.COPY_STRUCTURE -> context.collectInteractions().map(interaction -> this.createBlockCopyOperationFromHit(world, player, context, storage, (BlockInteraction)interaction));
            case BuildState.PASTE_STRUCTURE -> context.clipboard().snapshot().blockData().stream().map(blockSnapshot -> {
                BlockInteraction interaction = context.getInteraction(0).withBlockPosition(context.getInteraction(0).blockPosition().add(blockSnapshot.blockPosition()));
                return this.createBlockPlaceOperationFromHit(world, player, context, storage, interaction, blockSnapshot.blockState(), blockSnapshot.entityTag());
            });
        });
        if (context.buildState() == BuildState.PLACE_BLOCK) {
            operations = (BatchOperation)inHandTransformer.transform(operations);
        }
        if (context.pattern().enabled()) {
            for (Transformer transformer : context.pattern().transformers()) {
                if (!transformer.isValid()) continue;
                operations = (BatchOperation)transformer.transform(operations);
            }
        }
        operations = operations.flatten().filter(Objects::nonNull).filter(OperationFilter.distinctBlockOperations());
        return operations;
    }

    @Override
    public synchronized BatchOperationResult commit() {
        if (this.lastResult == null) {
            this.lastResult = this.create(this.world, this.player, this.context).commit();
            this.saveClipboard();
        }
        return this.lastResult;
    }

    protected void saveClipboard() {
        if (this.world.isClient()) {
            return;
        }
        if (this.context.buildState() != BuildState.COPY_STRUCTURE) {
            return;
        }
        if (this.context.buildType() != BuildType.BUILD) {
            return;
        }
        if (!this.context.clipboard().enabled()) {
            return;
        }
        Snapshot snapshot = new Snapshot("", System.currentTimeMillis(), this.lastResult.getResults().stream().map(BlockStateCopyOperationResult.class::cast).map(BlockStateCopyOperationResult::getBlockData).filter(blockData -> this.context.clipboard().copyAir() || blockData.blockState() != null && !blockData.blockState().isAir()).toList());
        this.getEntrance().getChannel().sendPacket(new PlayerSnapshotCapturePacket(this.player.getId(), snapshot), this.player);
    }
}
