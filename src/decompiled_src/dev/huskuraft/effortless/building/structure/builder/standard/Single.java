/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.building.structure.builder.standard;

import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BucketItem;
import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.building.BuildState;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.structure.BuildMode;
import dev.huskuraft.effortless.building.structure.builder.BlockStructure;
import java.util.Set;
import java.util.stream.Stream;

public record Single() implements BlockStructure
{
    protected static BlockInteraction traceSingle(Player player, Context context) {
        return Single.traceSingle(player, context.buildState(), context.replace().isQuick(), context.maxReachDistance());
    }

    protected static BlockInteraction traceSingle(Player player, Context context, int maxReachDistance) {
        return Single.traceSingle(player, context.buildState(), context.replace().isQuick(), maxReachDistance);
    }

    protected static BlockInteraction traceSingle(Player player, BuildState buildState, boolean relative, int maxReachDistance) {
        boolean isReplaceBlock;
        BucketItem bucketItem;
        BucketItem bucketItem2;
        Item item = player.getItemStack(InteractionHand.MAIN).getItem();
        boolean isHoldingEmptyBucket = item instanceof BucketItem && (bucketItem2 = (BucketItem)item).isEmpty();
        Item item2 = player.getItemStack(InteractionHand.MAIN).getItem();
        boolean isHoldingNonBlockItem = item2 instanceof BucketItem && !(bucketItem = (BucketItem)item2).isEmpty();
        BlockInteraction interaction = player.raytrace(maxReachDistance, 0.0f, isHoldingEmptyBucket);
        BlockPosition startBlockPosition = interaction.getBlockPosition();
        boolean isTracingTarget = buildState == BuildState.BREAK_BLOCK || buildState == BuildState.INTERACT_BLOCK || buildState == BuildState.COPY_STRUCTURE;
        boolean bl = isReplaceBlock = relative || player.getWorld().getBlockState(startBlockPosition).canBeReplaced(player, interaction);
        if ((isHoldingNonBlockItem && buildState == BuildState.INTERACT_BLOCK || !isTracingTarget) && !isReplaceBlock) {
            startBlockPosition = startBlockPosition.relative(interaction.getDirection());
        }
        return interaction.withBlockPosition(startBlockPosition);
    }

    public static Stream<BlockPosition> collectSingleBlocks(Context context) {
        return Stream.of(context.getPosition(0));
    }

    public static void addSingleBlock(Set<BlockPosition> set, BlockPosition pos1) {
        set.add(pos1);
    }

    public static void addSingleBlock(Set<BlockPosition> set, int x1, int y1, int z1) {
        set.add(new BlockPosition(x1, y1, z1));
    }

    @Override
    public BlockInteraction trace(Player player, Context context, int index) {
        return switch (index) {
            case 0 -> Single.traceSingle(player, context);
            default -> null;
        };
    }

    @Override
    public Stream<BlockPosition> collect(Context context, int index) {
        return switch (index) {
            case 1 -> Single.collectSingleBlocks(context);
            default -> Stream.empty();
        };
    }

    @Override
    public int traceSize(Context context) {
        return 1;
    }

    @Override
    public BuildMode getMode() {
        return BuildMode.SINGLE;
    }
}
