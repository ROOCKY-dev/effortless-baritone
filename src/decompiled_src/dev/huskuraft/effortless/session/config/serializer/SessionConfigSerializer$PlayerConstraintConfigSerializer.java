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

public static class SessionConfigSerializer.PlayerConstraintConfigSerializer
implements ConfigSerializer<ConstraintConfig> {
    public static final SessionConfigSerializer.PlayerConstraintConfigSerializer INSTANCE = new SessionConfigSerializer.PlayerConstraintConfigSerializer();

    private SessionConfigSerializer.PlayerConstraintConfigSerializer() {
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
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_USE_COMMANDS, constraintConfig.useCommands());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_USE_MOD, constraintConfig.allowUseMod());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_BREAK_BLOCKS, constraintConfig.allowBreakBlocks());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_PLACE_BLOCKS, constraintConfig.allowPlaceBlocks());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_INTERACT_BLOCKS, constraintConfig.allowInteractBlocks());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_ALLOW_COPY_PASTE_STRUCTURE, constraintConfig.allowCopyPasteStructures());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_USE_PROPER_TOOLS_ONLY, constraintConfig.useProperToolsOnly());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_REACH_DISTANCE, constraintConfig.maxReachDistance());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_BLOCK_BREAK_VOLUME, constraintConfig.maxBlockBreakVolume());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_BLOCK_PLACE_VOLUME, constraintConfig.maxBlockPlaceVolume());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_BLOCK_INTERACT_VOLUME, constraintConfig.maxBlockInteractVolume());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_MAX_STRUCTURE_COPY_PASTE_VOLUME, constraintConfig.maxStructureCopyPasteVolume());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_WHITELISTED_ITEMS, constraintConfig.whitelistedItems() == null ? null : constraintConfig.whitelistedItems().stream().map(ResourceLocation::getString).toList());
        SessionConfigSerializer.PlayerConstraintConfigSerializer.addOrRemove(config, SessionConfigSerializer.KEY_BLACKLISTED_ITEMS, constraintConfig.blacklistedItems() == null ? null : constraintConfig.blacklistedItems().stream().map(ResourceLocation::getString).toList());
        this.validate(config);
        return config;
    }
}
