/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.WriteMaskState
extends RenderState {
    public static RenderState.WriteMaskState create(String name, boolean writeColor, boolean writeDepth) {
        return RenderStateFactory.getInstance().createWriteMaskState(name, writeColor, writeDepth);
    }
}
