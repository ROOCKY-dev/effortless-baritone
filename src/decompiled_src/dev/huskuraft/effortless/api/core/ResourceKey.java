/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface ResourceKey<T>
extends PlatformReference {
    public ResourceLocation registry();

    public ResourceLocation location();
}
