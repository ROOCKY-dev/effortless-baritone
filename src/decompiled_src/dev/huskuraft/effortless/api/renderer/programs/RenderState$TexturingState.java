/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.TexturingState
extends RenderState {
    public static RenderState.TexturingState create(String name, Runnable setupState, Runnable clearState) {
        return RenderStateFactory.getInstance().createTexturingState(name, setupState, clearState);
    }
}
