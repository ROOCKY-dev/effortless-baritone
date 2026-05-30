/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface PlayerProfile
extends PlatformReference {
    public UUID getId();

    public String getName();

    public Map<String, ? extends Collection<?>> getProperties();
}
