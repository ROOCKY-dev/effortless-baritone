/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.CullState
extends RenderState {
    public static RenderState.CullState create(String name, boolean cull) {
        return RenderStateFactory.getInstance().createCullState(name, cull);
    }
}
