/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.TransparencyState
extends RenderState {
    public static RenderState.TransparencyState create(String name, Type type) {
        return RenderStateFactory.getInstance().createTransparencyState(name, type);
    }

    public static enum Type {
        NO,
        ADDITIVE,
        LIGHTNING,
        GLINT,
        CRUMBLING,
        TRANSLUCENT;

    }
}
