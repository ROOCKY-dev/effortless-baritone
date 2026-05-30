/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.LightmapState
extends RenderState {
    public static RenderState.LightmapState create(String name, boolean lightmap) {
        return RenderStateFactory.getInstance().createLightmapState(name, lightmap);
    }
}
