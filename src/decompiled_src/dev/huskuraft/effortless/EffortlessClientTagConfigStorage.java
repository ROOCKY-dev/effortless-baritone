/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.api.file.FileType;
import dev.huskuraft.effortless.api.file.TagElementFileStorage;
import dev.huskuraft.effortless.building.config.ClientConfig;
import dev.huskuraft.effortless.building.config.tag.ClientConfigTagSerializer;

public final class EffortlessClientTagConfigStorage
extends TagElementFileStorage<ClientConfig> {
    public EffortlessClientTagConfigStorage(EffortlessClient entrance) {
        super("effortless-client.dat", FileType.NBT, new ClientConfigTagSerializer());
    }

    @Override
    public ClientConfig getDefault() {
        return ClientConfig.DEFAULT;
    }
}
