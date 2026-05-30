/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface WorldBorder
extends PlatformReference {
    default public boolean isInBounds(BlockPosition blockPosition) {
        return (double)(blockPosition.x() + 1) > this.getMinX() && (double)blockPosition.x() < this.getMaxX() && (double)(blockPosition.z() + 1) > this.getMinZ() && (double)blockPosition.z() < this.getMaxZ();
    }

    default public boolean isInBounds(double x, double y) {
        return x > this.getMinX() && x < this.getMaxX() && y > this.getMinZ() && y < this.getMaxZ();
    }

    default public boolean isInBounds(double x, double y, double extent) {
        return x > this.getMinX() - extent && x < this.getMaxX() + extent && y > this.getMinZ() - extent && y < this.getMaxZ() + extent;
    }

    default public double getDistanceToBorder(double x, double y) {
        return Math.min(Math.min(Math.min(x - this.getMinX(), this.getMaxX() - x), y - this.getMinZ()), this.getMaxZ() - y);
    }

    public double getMinX();

    public double getMinZ();

    public double getMaxX();

    public double getMaxZ();

    public double getCenterX();

    public double getCenterZ();

    public double getSize();
}
