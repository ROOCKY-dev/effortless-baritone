/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.math;

import dev.huskuraft.effortless.api.math.Vector3i;
import java.util.Comparator;

private static final class Vector3i.YzxOrderComparator {
    private static final Comparator<Vector3i> YZX_ORDER = Comparator.comparingInt(Vector3i::y).thenComparingInt(Vector3i::z).thenComparingInt(Vector3i::x);

    private Vector3i.YzxOrderComparator() {
    }
}
