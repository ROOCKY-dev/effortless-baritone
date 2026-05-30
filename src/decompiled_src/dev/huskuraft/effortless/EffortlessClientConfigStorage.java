/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.api.file.ConfigFileStorage;
import dev.huskuraft.effortless.api.file.FileType;
import dev.huskuraft.effortless.building.config.ClientConfig;
import dev.huskuraft.effortless.building.config.universal.ClientConfigConfigSerializer;
import dev.huskuraft.effortless.building.structure.BuildMode;
import dev.huskuraft.effortless.building.structure.builder.Structure;

public final class EffortlessClientConfigStorage
extends ConfigFileStorage<ClientConfig> {
    public static final String CONFIG_NAME = "effortless-client.toml";

    public EffortlessClientConfigStorage(EffortlessClient entrance) {
        super(CONFIG_NAME, FileType.TOML, new ClientConfigConfigSerializer());
    }

    public void setStructure(Structure structure) {
        this.set(((ClientConfig)this.get()).withStructure(structure));
    }

    public Structure getStructure(BuildMode buildMode) {
        return ((ClientConfig)this.get()).getStructure(buildMode);
    }
}
