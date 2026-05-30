/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Axis;

final class Axis.3
extends Axis {
    private Axis.3(String name) {
    }

    @Override
    public int choose(int x, int y, int z) {
        return z;
    }

    @Override
    public double choose(double x, double y, double z) {
        return z;
    }
}
