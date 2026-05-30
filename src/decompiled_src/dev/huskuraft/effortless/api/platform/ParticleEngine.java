/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.Direction;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface ParticleEngine
extends PlatformReference {
    public void destroy(BlockPosition var1, BlockState var2);

    public void crack(BlockPosition var1, Direction var2);
}
