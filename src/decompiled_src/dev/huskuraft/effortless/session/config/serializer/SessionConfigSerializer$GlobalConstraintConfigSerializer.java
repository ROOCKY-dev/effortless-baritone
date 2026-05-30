/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.session.config.serializer;

import dev.huskuraft.effortless.api.config.ConfigSerializer;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigSpec;
import dev.huskuraft.effortless.session.config.ConstraintConfig;
import dev.huskuraft.effortless.session.config.serializer.SessionConfigSerializer;
import java.util.List;
import java.util.Objects;

public static class SessionConfigSerializer.GlobalConstraintConfigSerializer
implements ConfigSerializer<ConstraintConfig> {
    public static final SessionConfigSerializer.GlobalConstraintConfigSerializer INSTANCE = new SessionConfigSerializer.GlobalConstraintConfigSerializer();

    private SessionConfigSerializer.GlobalConstraintConfigSerializer() {
    }

    @Override
    public ConfigSpec getSpec(Config config) {
        ConfigSpec spec = new ConfigSpec();
        spec.define(SessionConfigSerializer.KEY_USE_COMMANDS, () -> false, Objects::nonNull);
        spec.define(SessionConfigSerializer.KEY_ALLOW_USE_MOD, () -> true, Objects::nonNull);
        spec.define(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS, () -> true, Objects::nonNull);
        spec.define(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS, () -> true, Objects::nonNull);
        spec.define(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS, () -> true, Objects::nonNull);
        spec.define(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE, () -> true, Objects::nonNull);
        spec.define(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY, () -> true, Objects::nonNull);
        spec.defineInRange(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE, Integer.valueOf(128), Integer.valueOf(0), Integer.valueOf(Short.MAX_VALUE));
        spec.defineInRange(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
        spec.defineInRange(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
        spec.defineInRange(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
        spec.defineInRange(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
        spec.defineList(SessionConfigSerializer.KEY_WHITELISTED_ITEMS, () -> ConstraintConfig.WHITELISTED_ITEMS_DEFAULT.stream().map(ResourceLocation::getString).toList(), Objects::nonNull);
        spec.defineList(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS, () -> ConstraintConfig.BLACKLISTED_ITEMS_DEFAULT.stream().map(ResourceLocation::getString).toList(), Objects::nonNull);
        return spec;
    }

    @Override
    public ConstraintConfig getDefault() {
        return ConstraintConfig.DEFAULT;
    }

    @Override
    public ConstraintConfig deserialize(Config config) {
        this.validate(config);
        return new ConstraintConfig((Boolean)config.get(SessionConfigSerializer.KEY_USE_COMMANDS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_USE_MOD), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE), (Boolean)config.get(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY), (Integer)config.get(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE), (Integer)config.get(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME), (Integer)config.get(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME), (Integer)config.get(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME), (Integer)config.get(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME), config.get(SessionConfigSerializer.KEY_WHITELISTED_ITEMS) == null ? null : ((List)config.get(SessionConfigSerializer.KEY_WHITELISTED_ITEMS)).stream().map(ResourceLocation::decompose).toList(), config.get(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS) == null ? null : ((List)config.get(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS)).stream().map(ResourceLocation::decompose).toList());
    }

    @Override
    public Config serialize(ConstraintConfig constraintConfig) {
        CommentedConfig config = CommentedConfig.inMemory();
        config.set(SessionConfigSerializer.KEY_USE_COMMANDS, (Object)constraintConfig.useCommands());
        config.set(SessionConfigSerializer.KEY_ALLOW_USE_MOD, (Object)constraintConfig.allowUseMod());
        config.set(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS, (Object)constraintConfig.allowBreakBlocks());
        config.set(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS, (Object)constraintConfig.allowPlaceBlocks());
        config.set(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS, (Object)constraintConfig.allowInteractBlocks());
        config.set(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE, (Object)constraintConfig.allowCopyPasteStructures());
        config.set(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY, (Object)constraintConfig.useProperToolsOnly());
        config.set(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE, (Object)constraintConfig.maxReachDistance());
        config.set(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME, (Object)constraintConfig.maxBlockBreakVolume());
        config.set(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME, (Object)constraintConfig.maxBlockPlaceVolume());
        config.set(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME, (Object)constraintConfig.maxBlockInteractVolume());
        config.set(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME, (Object)constraintConfig.maxStructureCopyPasteVolume());
        config.set(SessionConfigSerializer.KEY_WHITELISTED_ITEMS, constraintConfig.whitelistedItems() == null ? null : constraintConfig.whitelistedItems().stream().map(ResourceLocation::getString).toList());
        config.set(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS, constraintConfig.blacklistedItems() == null ? null : constraintConfig.blacklistedItems().stream().map(ResourceLocation::getString).toList());
        config.setComment(SessionConfigSerializer.KEY_USE_COMMANDS, "Should use commands to build using this mod.");
        config.setComment(SessionConfigSerializer.KEY_ALLOW_USE_MOD, "Should allow players to use this mod.");
        config.setComment(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS, "Should allow players to break blocks using this mod.");
        config.setComment(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS, "Should allow players to place blocks using this mod.");
        config.setComment(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS, "Should allow players to interact blocks using this mod.");
        config.setComment(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE, "Should allow players to copy and paste structures using this mod.");
        config.setComment(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY, "Should allow players to break blocks with proper tools only in survival mode.");
        config.setComment(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE, "The maximum distance a player can reach when building using this mod. \nRange: 0 ~ 32767");
        config.setComment(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME, "The maximum block volume a player can break at once when building using this mod. \nRange: 0 ~ 1000000");
        config.setComment(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME, "The maximum block volume a player can place at once when building using this mod. \nRange: 0 ~ 1000000");
        config.setComment(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME, "The maximum block volume a player can interact at once when building using this mod. \nRange: 0 ~ 1000000");
        config.setComment(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME, "The maximum structure volume a player can copy and paste at once when building using this mod. \nRange: 0 ~ 1000000");
        config.setComment(SessionConfigSerializer.KEY_WHITELISTED_ITEMS, "The list of items that players are allowed to break/place/interact when building using this mod. \nIf the whitelist is empty, all items are allowed. \nIf the whitelist is not empty, only the items in the whitelist are allowed. \nThe value must be a list of item resource locations like [\"minecraft:stone\", \"minecraft:dirt\"].");
        config.setComment(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS, "The list of items that players are not allowed to break/place/interact when building using this mod. \nIf the blacklist is empty, no items are not allowed. \nIf an item exists both in the blacklist and the whitelist, it will not be allowed. \nThe value must be a list of item resource locations like [\"minecraft:stone\", \"minecraft:dirt\"].");
        this.validate(config);
        return config;
    }
}
