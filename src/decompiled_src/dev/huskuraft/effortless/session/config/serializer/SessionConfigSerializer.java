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
import dev.huskuraft.effortless.session.config.SessionConfig;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class SessionConfigSerializer
implements ConfigSerializer<SessionConfig> {
    private static final String KEY_GLOBAL = "global";
    private static final String KEY_PLAYER = "player";
    private static final String KEY_USE_COMMANDS = "useCommands";
    private static final String KEY_ALLOW_USE_MOD = "allowUseMod";
    private static final String KEY_ALLOW_BREAK_BLOCKS = "allowBreakBlocks";
    private static final String KEY_ALLOW_PLACE_BLOCKS = "allowPlaceBlocks";
    private static final String KEY_ALLOW_INTERACT_BLOCKS = "allowInteractBlocks";
    private static final String KEY_ALLOW_COPY_PASTE_STRUCTURE = "allowCopyPasteStructure";
    private static final String KEY_USE_PROPER_TOOLS_ONLY = "useProperToolsOnly";
    private static final String KEY_MAX_REACH_DISTANCE = "maxReachDistance";
    private static final String KEY_MAX_BLOCK_BREAK_VOLUME = "maxBlockBreakVolume";
    private static final String KEY_MAX_BLOCK_PLACE_VOLUME = "maxBlockPlaceVolume";
    private static final String KEY_MAX_BLOCK_INTERACT_VOLUME = "maxBlockInteractVolume";
    private static final String KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME = "maxStructureCopyPasteVolume";
    private static final String KEY_WHITELISTED_ITEMS = "whitelistedItems";
    private static final String KEY_BLACKLISTED_ITEMS = "blacklistedItems";

    @Override
    public ConfigSpec getSpec(Config config) {
        ConfigSpec spec = new ConfigSpec();
        spec.define(KEY_GLOBAL, () -> GlobalConstraintConfigSerializer.INSTANCE.serialize(this.getDefault().globalConfig()), Config.class::isInstance);
        spec.define(KEY_PLAYER, () -> Config.inMemory(), Config.class::isInstance);
        return spec;
    }

    @Override
    public SessionConfig getDefault() {
        return SessionConfig.defaultConfig();
    }

    @Override
    public SessionConfig deserialize(Config config) {
        this.validate(config);
        return new SessionConfig(GlobalConstraintConfigSerializer.INSTANCE.deserialize((Config)config.get(KEY_GLOBAL)), ((Config)config.get(KEY_PLAYER)).entrySet().stream().map(e -> {
            try {
                return Map.entry(UUID.fromString(e.getKey()), PlayerConstraintConfigSerializer.INSTANCE.deserialize((Config)e.getValue()));
            }
            catch (IllegalArgumentException | NullPointerException ignored) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
    }

    @Override
    public Config serialize(SessionConfig sessionConfig) {
        CommentedConfig config = CommentedConfig.inMemory();
        config.add(KEY_GLOBAL, (Object)GlobalConstraintConfigSerializer.INSTANCE.serialize(sessionConfig.globalConfig()));
        for (Map.Entry<UUID, ConstraintConfig> entry : sessionConfig.playerConfigs().entrySet()) {
            config.add(List.of(KEY_PLAYER, entry.getKey().toString()), (Object)PlayerConstraintConfigSerializer.INSTANCE.serialize(entry.getValue()));
        }
        this.validate(config);
        return config;
    }

    public static class GlobalConstraintConfigSerializer
    implements ConfigSerializer<ConstraintConfig> {
        public static final GlobalConstraintConfigSerializer INSTANCE = new GlobalConstraintConfigSerializer();

        private GlobalConstraintConfigSerializer() {
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

    public static class PlayerConstraintConfigSerializer
    implements ConfigSerializer<ConstraintConfig> {
        public static final PlayerConstraintConfigSerializer INSTANCE = new PlayerConstraintConfigSerializer();

        private PlayerConstraintConfigSerializer() {
        }

        private static <T> void addOrRemove(Config config, String path, T value) {
            if (value == null) {
                config.remove(path);
            } else {
                config.add(path, value);
            }
        }

        @Override
        public ConfigSpec getSpec(Config config) {
            ConfigSpec spec = new ConfigSpec();
            if (config.contains(SessionConfigSerializer.KEY_USE_COMMANDS)) {
                spec.define(SessionConfigSerializer.KEY_USE_COMMANDS, () -> false, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_ALLOW_USE_MOD)) {
                spec.define(SessionConfigSerializer.KEY_ALLOW_USE_MOD, () -> true, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS)) {
                spec.define(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS, () -> true, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS)) {
                spec.define(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS, () -> true, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS)) {
                spec.define(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS, () -> true, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE)) {
                spec.define(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE, () -> true, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY)) {
                spec.define(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY, () -> true, Boolean.class::isInstance);
            }
            if (config.contains(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE)) {
                spec.defineInRange(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE, Integer.valueOf(128), Integer.valueOf(0), Integer.valueOf(Short.MAX_VALUE));
            }
            if (config.contains(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME)) {
                spec.defineInRange(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
            }
            if (config.contains(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME)) {
                spec.defineInRange(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
            }
            if (config.contains(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME)) {
                spec.defineInRange(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
            }
            if (config.contains(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME)) {
                spec.defineInRange(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME, Integer.valueOf(10000), Integer.valueOf(0), Integer.valueOf(1000000));
            }
            if (config.contains(SessionConfigSerializer.KEY_WHITELISTED_ITEMS)) {
                spec.defineList(SessionConfigSerializer.KEY_WHITELISTED_ITEMS, () -> ConstraintConfig.WHITELISTED_ITEMS_DEFAULT, Objects::nonNull);
            }
            if (config.contains(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS)) {
                spec.defineList(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS, () -> ConstraintConfig.BLACKLISTED_ITEMS_DEFAULT, Objects::nonNull);
            }
            return spec;
        }

        @Override
        public ConstraintConfig getDefault() {
            return ConstraintConfig.NULL;
        }

        @Override
        public ConstraintConfig deserialize(Config config) {
            this.validate(config);
            return new ConstraintConfig((Boolean)config.get(SessionConfigSerializer.KEY_USE_COMMANDS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_USE_MOD), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS), (Boolean)config.get(SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE), (Boolean)config.get(SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY), (Integer)config.get(SessionConfigSerializer.KEY_MAX_REACH_DISTANCE), (Integer)config.get(SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME), (Integer)config.get(SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME), (Integer)config.get(SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME), (Integer)config.get(SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME), config.get(SessionConfigSerializer.KEY_WHITELISTED_ITEMS) == null ? null : ((List)config.get(SessionConfigSerializer.KEY_WHITELISTED_ITEMS)).stream().map(ResourceLocation::decompose).toList(), config.get(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS) == null ? null : ((List)config.get(SessionConfigSerializer.KEY_BLACKLISTED_ITEMS)).stream().map(ResourceLocation::decompose).toList());
        }

        @Override
        public Config serialize(ConstraintConfig constraintConfig) {
            CommentedConfig config = CommentedConfig.inMemory();
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_USE_COMMANDS, constraintConfig.useCommands());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_USE_MOD, constraintConfig.allowUseMod());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS, constraintConfig.allowBreakBlocks());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS, constraintConfig.allowPlaceBlocks());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS, constraintConfig.allowInteractBlocks());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE, constraintConfig.allowCopyPasteStructures());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY, constraintConfig.useProperToolsOnly());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_REACH_DISTANCE, constraintConfig.maxReachDistance());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME, constraintConfig.maxBlockBreakVolume());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME, constraintConfig.maxBlockPlaceVolume());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME, constraintConfig.maxBlockInteractVolume());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME, constraintConfig.maxStructureCopyPasteVolume());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_WHITELISTED_ITEMS, constraintConfig.whitelistedItems() == null ? null : constraintConfig.whitelistedItems().stream().map(ResourceLocation::getString).toList());
            PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_BLACKLISTED_ITEMS, constraintConfig.blacklistedItems() == null ? null : constraintConfig.blacklistedItems().stream().map(ResourceLocation::getString).toList());
            this.validate(config);
            return config;
        }
    }
}
