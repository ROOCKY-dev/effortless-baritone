/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.math.Vector3d;

public interface Interaction {
    public Target getTarget();

    public Vector3d getPosition();

    default public InteractionHand getHand() {
        return InteractionHand.MAIN;
    }

    public static enum Target {
        MISS,
        BLOCK,
        ENTITY;

    }
}
