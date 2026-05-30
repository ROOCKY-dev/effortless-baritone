/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.DepthTestState
extends RenderState {
    public static RenderState.DepthTestState create(String name, int function) {
        return RenderStateFactory.getInstance().createDepthTestState(name, function);
    }
}
