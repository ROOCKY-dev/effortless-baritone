/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.building;

import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.GameMode;
import dev.huskuraft.effortless.api.core.Interaction;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.math.BoundingBox3d;
import dev.huskuraft.effortless.api.math.Vector3i;
import dev.huskuraft.effortless.building.BuildStage;
import dev.huskuraft.effortless.building.BuildState;
import dev.huskuraft.effortless.building.BuildType;
import dev.huskuraft.effortless.building.InventorySnapshot;
import dev.huskuraft.effortless.building.TracingResult;
import dev.huskuraft.effortless.building.clipboard.Clipboard;
import dev.huskuraft.effortless.building.clipboard.Snapshot;
import dev.huskuraft.effortless.building.config.BuilderConfig;
import dev.huskuraft.effortless.building.pattern.Pattern;
import dev.huskuraft.effortless.building.replace.Replace;
import dev.huskuraft.effortless.building.replace.ReplaceStrategy;
import dev.huskuraft.effortless.building.structure.BuildFeature;
import dev.huskuraft.effortless.building.structure.BuildMode;
import dev.huskuraft.effortless.building.structure.builder.Structure;
import dev.huskuraft.effortless.session.config.ConstraintConfig;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public record Context(UUID id, BuildState buildState, BuildType buildType, Interactions interactions, Structure structure, Clipboard clipboard, Pattern pattern, Replace replace, Configs configs, Extras extras) {
    public boolean fillContainers() {
        return true;
    }

    public boolean useProperTool() {
        return this.configs().constraintConfig().useProperToolsOnly();
    }

    public int getReservedToolDurability() {
        return this.configs().builderConfig().reservedToolDurability();
    }

    public boolean useLegacyBlockPlace() {
        return false;
    }

    public static Context defaultSet() {
        return new Context(UUID.randomUUID(), BuildState.IDLE, BuildType.BUILD, Interactions.EMPTY, Structure.DISABLED, Clipboard.DISABLED, Pattern.DISABLED, Replace.DISABLED, new Configs(ConstraintConfig.DEFAULT, BuilderConfig.DEFAULT), null);
    }

    public ReplaceStrategy replaceStrategy() {
        return this.replace.replaceStrategy();
    }

    private static BlockInteraction withPosition(BlockInteraction blockInteraction, BlockPosition blockPosition) {
        return new BlockInteraction(blockInteraction.getPosition().add(blockPosition.sub(blockInteraction.getBlockPosition()).toVector3d()), blockInteraction.getDirection(), blockPosition, blockInteraction.isInside());
    }

    public BuildMode buildMode() {
        return this.structure().getMode();
    }

    public boolean isIdle() {
        return this.buildState.isIdle();
    }

    public boolean isDisabled() {
        return this.buildMode() == BuildMode.DISABLED;
    }

    public boolean isBuildType() {
        return this.buildType() == BuildType.BUILD;
    }

    public boolean isBuildClientType() {
        return this.buildType() == BuildType.BUILD_CLIENT;
    }

    public boolean isPreviewType() {
        return this.buildType() == BuildType.PREVIEW;
    }

    public boolean isBuilding() {
        return this.buildMode() != BuildMode.DISABLED && this.buildState() != BuildState.IDLE;
    }

    public boolean isMissingHit() {
        return this.interactions().isMissing();
    }

    public boolean skipRaytrace() {
        return false;
    }

    public boolean isFulfilled() {
        return this.isBuilding() && (this.buildState() == BuildState.PASTE_STRUCTURE && this.interactionsSize() == 1 || this.structure().traceSize(this) == this.interactionsSize());
    }

    public int interactionsSize() {
        return this.interactions.size();
    }

    public boolean isInteractionEmpty() {
        return this.interactions.isEmpty();
    }

    public BlockPosition getPosition(int index) {
        return this.interactions.get(index).getBlockPosition();
    }

    public List<BlockPosition> getPositions() {
        return this.interactions.results().stream().map(BlockInteraction::getBlockPosition).toList();
    }

    public BlockInteraction getInteraction(int index) {
        return this.interactions.get(index);
    }

    public List<BlockInteraction> getInteractions() {
        return this.interactions.results();
    }

    public Set<BuildFeature> buildFeatures() {
        return this.structure().getFeatures();
    }

    public int axisLimitation() {
        return Integer.MAX_VALUE;
    }

    public int maxNextReachDistance() {
        return 1024;
    }

    public int maxReachDistance() {
        return this.configs.constraintConfig.maxReachDistance();
    }

    public TracingResult tracingResult() {
        if (this.isIdle()) {
            return TracingResult.PASS;
        }
        if (this.isDisabled()) {
            return TracingResult.PASS;
        }
        if (this.isMissingHit()) {
            return TracingResult.FAILED;
        }
        if (this.isFulfilled()) {
            return TracingResult.SUCCESS_FULFILLED;
        }
        return TracingResult.SUCCESS_PARTIAL;
    }

    public Context withBuildState(BuildState state) {
        return new Context(this.id, state, this.buildType, this.interactions, this.structure, this.clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withBuildType(BuildType type) {
        return new Context(this.id, this.buildState, type, this.interactions, this.structure, this.clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withNextInteraction(BlockInteraction interaction) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions.put(interaction), this.structure, this.clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withNoInteraction() {
        return new Context(this.id, this.buildState, this.buildType, Interactions.EMPTY, this.structure, this.clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withStructure(Structure structure) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, structure, this.clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withClipboard(Clipboard clipboard) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withEmptyClipboard() {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, this.clipboard.withSnapshot(Snapshot.EMPTY), this.pattern, this.replace, this.configs, this.extras);
    }

    public Context withPattern(Pattern pattern) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, this.clipboard, pattern, this.replace, this.configs, this.extras);
    }

    public Context withReplace(Replace replace) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, this.clipboard, this.pattern, replace, this.configs, this.extras);
    }

    public Context withExtras(Extras extras) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, this.clipboard, this.pattern, this.replace, this.configs, extras);
    }

    public Context withPlayerExtras(Player player) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, this.clipboard, this.pattern, this.replace, this.configs, new Extras(player));
    }

    public Context finalize(Player player, BuildStage stage) {
        switch (stage) {
            case TICK: {
                return this.withPattern(this.pattern().finalize(player, stage)).withPlayerExtras(player);
            }
        }
        return this.withPattern(this.pattern().finalize(player, stage));
    }

    public Context newInteraction() {
        return new Context(UUID.randomUUID(), BuildState.IDLE, this.buildType, Interactions.EMPTY, this.structure, this.clipboard, this.pattern, this.replace, this.configs, this.extras);
    }

    @Nullable
    public BlockInteraction trace(Player player) {
        return this.structure().trace(player, this);
    }

    public Stream<BlockInteraction> collectInteractions() {
        if (this.tracingResult().isSuccess()) {
            return this.structure().collect(this).map(blockPosition -> Context.withPosition(this.getInteraction(0), blockPosition));
        }
        return Stream.empty();
    }

    public Context withReachParams(Configs configs) {
        return new Context(this.id, this.buildState, this.buildType, this.interactions, this.structure, this.clipboard, this.pattern, this.replace, configs, this.extras);
    }

    public Context withConstraintConfig(ConstraintConfig config) {
        return this.withReachParams(new Configs(config, this.configs().builderConfig()));
    }

    public Context withBuilderConfig(BuilderConfig config) {
        return this.withReachParams(new Configs(this.configs().constraintConfig(), config));
    }

    public Vector3i getInteractionBox() {
        if (this.buildState() == BuildState.PASTE_STRUCTURE) {
            return this.clipboard().snapshot().box();
        }
        if (this.interactions().isEmpty() || this.interactions().isMissing()) {
            return Vector3i.ZERO;
        }
        return BoundingBox3d.fromLowerCornersOf((Vector3i[])this.interactions().results().stream().map(BlockInteraction::getBlockPosition).map(BlockPosition::toVector3i).toArray(Vector3i[]::new)).getSize().toVector3i();
    }

    public int getVolume() {
        if (this.buildState() == BuildState.PASTE_STRUCTURE) {
            return (int)((float)this.clipboard().snapshot().volume() * this.pattern().volumeMultiplier());
        }
        return (int)((float)this.structure().volume(this) * this.pattern().volumeMultiplier());
    }

    public int getMaxVolume() {
        return switch (this.buildState()) {
            default -> throw new MatchException(null, null);
            case BuildState.IDLE -> 0;
            case BuildState.BREAK_BLOCK -> this.configs().constraintConfig().maxBlockBreakVolume();
            case BuildState.PLACE_BLOCK -> this.configs().constraintConfig().maxBlockPlaceVolume();
            case BuildState.INTERACT_BLOCK -> this.configs().constraintConfig().maxBlockInteractVolume();
            case BuildState.COPY_STRUCTURE, BuildState.PASTE_STRUCTURE -> this.configs().constraintConfig().maxStructureCopyPasteVolume();
        };
    }

    public boolean isVolumeInBounds() {
        return this.getVolume() <= this.getMaxVolume();
    }

    public boolean hasPermission() {
        return switch (this.buildState()) {
            default -> throw new MatchException(null, null);
            case BuildState.IDLE -> true;
            case BuildState.BREAK_BLOCK -> this.configs().constraintConfig().allowBreakBlocks();
            case BuildState.PLACE_BLOCK -> this.configs().constraintConfig().allowPlaceBlocks();
            case BuildState.INTERACT_BLOCK -> this.configs().constraintConfig().allowInteractBlocks();
            case BuildState.COPY_STRUCTURE, BuildState.PASTE_STRUCTURE -> this.configs().constraintConfig().allowCopyPasteStructures();
        };
    }

    public record Interactions(List<BlockInteraction> results) {
        public static final Interactions EMPTY = new Interactions(Collections.emptyList());

        public int size() {
            return this.results.size();
        }

        public boolean isEmpty() {
            return this.results.isEmpty();
        }

        public BlockInteraction get(int index) {
            return this.results.get(index);
        }

        public Interactions put(BlockInteraction interaction) {
            return new Interactions(Stream.concat(this.results.stream(), Stream.of(interaction)).toList());
        }

        public boolean isMissing() {
            return this.results.stream().anyMatch(result -> result == null || result.getTarget() != Interaction.Target.BLOCK);
        }
    }

    public record Configs(ConstraintConfig constraintConfig, BuilderConfig builderConfig) {
    }

    public record Extras(ResourceLocation dimensionId, dev.huskuraft.effortless.building.operation.block.Extras extras, GameMode gameMode, long seed, InventorySnapshot inventorySnapshot) {
        public Extras(Player player) {
            this(player.getWorld().getDimensionId().location(), dev.huskuraft.effortless.building.operation.block.Extras.get(player), player.getGameMode(), new Random().nextLong(), new InventorySnapshot(player.getInventory()));
        }
    }
}
