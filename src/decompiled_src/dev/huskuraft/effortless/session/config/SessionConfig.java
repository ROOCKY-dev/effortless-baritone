/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.session.config;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.session.config.ConstraintConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public record SessionConfig(ConstraintConfig globalConfig, Map<UUID, ConstraintConfig> playerConfigs) {
    public static final SessionConfig EMPTY = new SessionConfig(ConstraintConfig.EMPTY, Map.of());

    public static SessionConfig defaultConfig() {
        return new SessionConfig(ConstraintConfig.DEFAULT, Map.of());
    }

    private <T> T getPlayerOrGlobalEntry(UUID id, Function<ConstraintConfig, T> entry) {
        return entry.apply(this.playerConfigs.get(id) == null || entry.apply(this.playerConfigs.get(id)) == null ? this.globalConfig : this.playerConfigs.get(id));
    }

    private <T> T getPlayerOrNullEntry(UUID id, Function<ConstraintConfig, T> entry) {
        return entry.apply(this.playerConfigs.get(id) == null || entry.apply(this.playerConfigs.get(id)) == null ? ConstraintConfig.NULL : this.playerConfigs.get(id));
    }

    public ConstraintConfig getGlobalConfig() {
        return this.globalConfig;
    }

    public ConstraintConfig getByPlayer(Player player) {
        return this.getByPlayer(player.getId());
    }

    public ConstraintConfig getByPlayer(UUID id) {
        return new ConstraintConfig(this.getPlayerOrGlobalEntry(id, ConstraintConfig::useCommands), this.getPlayerOrGlobalEntry(id, ConstraintConfig::allowUseMod), this.getPlayerOrGlobalEntry(id, ConstraintConfig::allowBreakBlocks), this.getPlayerOrGlobalEntry(id, ConstraintConfig::allowPlaceBlocks), this.getPlayerOrGlobalEntry(id, ConstraintConfig::allowInteractBlocks), this.getPlayerOrGlobalEntry(id, ConstraintConfig::allowCopyPasteStructures), this.getPlayerOrGlobalEntry(id, ConstraintConfig::useProperToolsOnly), this.getPlayerOrGlobalEntry(id, ConstraintConfig::maxReachDistance), this.getPlayerOrGlobalEntry(id, ConstraintConfig::maxBlockBreakVolume), this.getPlayerOrGlobalEntry(id, ConstraintConfig::maxBlockPlaceVolume), this.getPlayerOrGlobalEntry(id, ConstraintConfig::maxBlockInteractVolume), this.getPlayerOrGlobalEntry(id, ConstraintConfig::maxStructureCopyPasteVolume), this.getPlayerOrGlobalEntry(id, ConstraintConfig::whitelistedItems), this.getPlayerOrGlobalEntry(id, ConstraintConfig::blacklistedItems));
    }

    public ConstraintConfig getPlayerConfigOrNull(Player player) {
        return this.getPlayerConfigOrNull(player.getId());
    }

    public ConstraintConfig getPlayerConfigOrNull(UUID id) {
        return new ConstraintConfig(this.getPlayerOrNullEntry(id, ConstraintConfig::useCommands), this.getPlayerOrNullEntry(id, ConstraintConfig::allowUseMod), this.getPlayerOrNullEntry(id, ConstraintConfig::allowBreakBlocks), this.getPlayerOrNullEntry(id, ConstraintConfig::allowPlaceBlocks), this.getPlayerOrNullEntry(id, ConstraintConfig::allowInteractBlocks), this.getPlayerOrNullEntry(id, ConstraintConfig::allowCopyPasteStructures), this.getPlayerOrNullEntry(id, ConstraintConfig::useProperToolsOnly), this.getPlayerOrNullEntry(id, ConstraintConfig::maxReachDistance), this.getPlayerOrNullEntry(id, ConstraintConfig::maxBlockBreakVolume), this.getPlayerOrNullEntry(id, ConstraintConfig::maxBlockPlaceVolume), this.getPlayerOrNullEntry(id, ConstraintConfig::maxBlockInteractVolume), this.getPlayerOrNullEntry(id, ConstraintConfig::maxStructureCopyPasteVolume), this.getPlayerOrNullEntry(id, ConstraintConfig::whitelistedItems), this.getPlayerOrNullEntry(id, ConstraintConfig::blacklistedItems));
    }

    public SessionConfig withPlayerConfig(UUID id, ConstraintConfig config) {
        HashMap<UUID, ConstraintConfig> map = new HashMap<UUID, ConstraintConfig>(this.playerConfigs);
        if (config == null) {
            map.remove(id);
        } else {
            map.put(id, config);
        }
        return new SessionConfig(this.globalConfig, Collections.unmodifiableMap(map));
    }

    public SessionConfig withPlayerConfig(Map<UUID, ConstraintConfig> config) {
        return new SessionConfig(this.globalConfig, config);
    }

    public SessionConfig withGlobalConfig(ConstraintConfig config) {
        return new SessionConfig(config, this.playerConfigs);
    }
}
