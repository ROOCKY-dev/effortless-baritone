/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.OffsetTexturingState
extends RenderState.TexturingState {
    public static RenderState.OffsetTexturingState create(String name, float offsetX, float offsetY) {
        return RenderStateFactory.getInstance().createOffsetTexturingState(name, offsetX, offsetY);
    }
}
