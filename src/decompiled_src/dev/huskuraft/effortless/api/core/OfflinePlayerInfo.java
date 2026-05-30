/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.PlayerInfo;
import dev.huskuraft.effortless.api.core.PlayerProfile;
import dev.huskuraft.effortless.api.core.PlayerSkin;
import dev.huskuraft.effortless.api.text.Text;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public record OfflinePlayerInfo(UUID id, String name, Text displayName, PlayerSkin skin) implements PlayerInfo
{
    public OfflinePlayerInfo(UUID id) {
        this(id, "Offline Player", Text.empty(), null);
    }

    @Override
    public PlayerProfile getProfile() {
        return new PlayerProfile(){

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
        };
    }

    @Override
    public Text getDisplayName() {
        return this.displayName;
    }

    @Override
    public PlayerSkin getSkin() {
        return this.skin;
    }

    @Override
    public Object refs() {
        throw new NullPointerException();
    }
}
