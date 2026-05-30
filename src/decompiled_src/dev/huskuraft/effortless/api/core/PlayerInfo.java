/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.PlayerProfile;
import dev.huskuraft.effortless.api.core.PlayerSkin;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.text.Text;
import java.util.UUID;

public interface PlayerInfo
extends PlatformReference {
    public PlayerProfile getProfile();

    public Text getDisplayName();

    public PlayerSkin getSkin();

    default public UUID getId() {
        return this.getProfile().getId();
    }

    default public String getName() {
        return this.getProfile().getName();
    }
}
