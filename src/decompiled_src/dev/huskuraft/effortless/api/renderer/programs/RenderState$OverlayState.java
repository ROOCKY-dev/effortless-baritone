/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.OverlayState
extends RenderState {
    public static RenderState.OverlayState create(String name, boolean overlay) {
        return RenderStateFactory.getInstance().createOverlayState(name, overlay);
    }
}
