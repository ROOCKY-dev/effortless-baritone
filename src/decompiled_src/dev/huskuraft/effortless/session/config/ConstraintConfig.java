/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.session.config;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import java.util.List;

public record ConstraintConfig(Boolean useCommands, Boolean allowUseMod, Boolean allowBreakBlocks, Boolean allowPlaceBlocks, Boolean allowInteractBlocks, Boolean allowCopyPasteStructures, Boolean useProperToolsOnly, Integer maxReachDistance, Integer maxBlockBreakVolume, Integer maxBlockPlaceVolume, Integer maxBlockInteractVolume, Integer maxStructureCopyPasteVolume, List<ResourceLocation> whitelistedItems, List<ResourceLocation> blacklistedItems) {
    public static final boolean USE_COMMANDS_DEFAULT = false;
    public static final boolean ALLOW_USE_MOD_DEFAULT = true;
    public static final boolean ALLOW_BREAK_BLOCKS_DEFAULT = true;
    public static final boolean ALLOW_PLACE_BLOCKS_DEFAULT = true;
    public static final boolean ALLOW_INTERACT_BLOCKS_DEFAULT = true;
    public static final boolean ALLOW_COPY_PASTE_STRUCTURES_DEFAULT = true;
    public static final boolean USE_PROPER_TOOLS_ONLY_DEFAULT = true;
    public static final int MAX_REACH_DISTANCE_DEFAULT = 128;
    public static final int MAX_REACH_DISTANCE_RANGE_START = 0;
    public static final int MAX_REACH_DISTANCE_RANGE_END = Short.MAX_VALUE;
    public static final int MAX_BLOCK_BREAK_VOLUME_DEFAULT = 10000;
    public static final int MAX_BLOCK_BREAK_VOLUME_RANGE_START = 0;
    public static final int MAX_BLOCK_BREAK_VOLUME_RANGE_END = 1000000;
    public static final int MAX_BLOCK_PLACE_VOLUME_DEFAULT = 10000;
    public static final int MAX_BLOCK_PLACE_VOLUME_RANGE_START = 0;
    public static final int MAX_BLOCK_PLACE_VOLUME_RANGE_END = 1000000;
    public static final int MAX_BLOCK_INTERACT_VOLUME_DEFAULT = 10000;
    public static final int MAX_BLOCK_INTERACT_VOLUME_RANGE_START = 0;
    public static final int MAX_BLOCK_INTERACT_VOLUME_RANGE_END = 1000000;
    public static final int MAX_STRUCTURE_COPY_PASTE_VOLUME_DEFAULT = 10000;
    public static final int MAX_STRUCTURE_COPY_PASTE_VOLUME_RANGE_START = 0;
    public static final int MAX_STRUCTURE_COPY_PASTE_VOLUME_RANGE_END = 1000000;
    public static final List<ResourceLocation> WHITELISTED_ITEMS_DEFAULT = List.of();
    public static final List<ResourceLocation> BLACKLISTED_ITEMS_DEFAULT = List.of();
    public static final ConstraintConfig DEFAULT = new ConstraintConfig(false, true, true, true, true, true, true, 128, 10000, 10000, 10000, 10000, WHITELISTED_ITEMS_DEFAULT, BLACKLISTED_ITEMS_DEFAULT);
    public static final ConstraintConfig EMPTY = new ConstraintConfig(false, false, false, false, false, false, false, 0, 0, 0, 0, 0, List.of(), List.of());
    public static final ConstraintConfig NULL = new ConstraintConfig(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
}
