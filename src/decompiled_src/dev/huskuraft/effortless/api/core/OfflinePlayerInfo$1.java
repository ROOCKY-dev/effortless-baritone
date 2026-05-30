/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.PlayerProfile;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

class OfflinePlayerInfo.1
implements PlayerProfile {
    OfflinePlayerInfo.1() {
    }

    @Override
    public UUID getId() {
        return OfflinePlayerInfo.this.id;
    }

    @Override
    public String getName() {
        return OfflinePlayerInfo.this.name;
    }

    @Override
    public Map<String, ? extends Collection<?>> getProperties() {
        return null;
    }

    @Override
    public Object refs() {
        throw new NullPointerException();
    }
}
