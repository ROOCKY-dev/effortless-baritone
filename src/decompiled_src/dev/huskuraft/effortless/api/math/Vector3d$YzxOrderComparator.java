/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.Vector3d;
import java.util.Comparator;

private static final class Vector3d.YzxOrderComparator {
    private static final Comparator<Vector3d> YZX_ORDER = Comparator.comparingDouble(Vector3d::y).thenComparingDouble(Vector3d::z).thenComparingDouble(Vector3d::x);

    private Vector3d.YzxOrderComparator() {
    }
}
