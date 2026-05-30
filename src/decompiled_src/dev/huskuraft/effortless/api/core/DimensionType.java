/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface DimensionType
extends PlatformReference {
    public boolean hasSkyLight();

    public boolean hasCeiling();

    public double coordinateScale();

    public int minY();

    public int height();

    public int logicalHeight();
}
