/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.LayeringState
extends RenderState {
    public static RenderState.LayeringState create(String name, Type type) {
        return RenderStateFactory.getInstance().createLayeringState(name, type);
    }

    public static enum Type {
        NO,
        POLYGON_OFFSET,
        VIEW_OFFSET_Z;

    }
}
