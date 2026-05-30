/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.OutputState
extends RenderState {
    public static RenderState.OutputState create(String name, Target target) {
        return RenderStateFactory.getInstance().createOutputState(name, target);
    }

    public static enum Target {
        NO,
        OUTLINE,
        TRANSLUCENT,
        PARTICLES,
        WEATHER,
        CLOUDS,
        ITEM_ENTITY;

    }
}
