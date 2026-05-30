/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import javax.annotation.Nullable;

public interface Registry<T extends PlatformReference>
extends Iterable<T>,
PlatformReference {
    public static final int DEFAULT = -1;

    public int getId(T var1);

    @Nullable
    public T byId(int var1);

    default public T byIdOrThrow(int key) {
        T $$1 = this.byId(key);
        if ($$1 == null) {
            throw new IllegalArgumentException("No value with id " + key);
        }
        return $$1;
    }
}
