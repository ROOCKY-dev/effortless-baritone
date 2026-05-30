/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Stat;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface StatType<T extends PlatformReference>
extends PlatformReference {
    public Stat<T> get(T var1);
}
