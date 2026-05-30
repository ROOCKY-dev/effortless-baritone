/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.LineState
extends RenderState {
    public static RenderState.LineState create(String name, Double width) {
        return RenderStateFactory.getInstance().createLineState(name, width);
    }
}
