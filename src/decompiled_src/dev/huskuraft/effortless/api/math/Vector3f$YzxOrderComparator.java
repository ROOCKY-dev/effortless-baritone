/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.Vector3f;
import java.util.Comparator;

private static final class Vector3f.YzxOrderComparator {
    private static final Comparator<Vector3f> YZX_ORDER = Comparator.comparingDouble(Vector3f::y).thenComparingDouble(Vector3f::z).thenComparingDouble(Vector3f::x);

    private Vector3f.YzxOrderComparator() {
    }
}
