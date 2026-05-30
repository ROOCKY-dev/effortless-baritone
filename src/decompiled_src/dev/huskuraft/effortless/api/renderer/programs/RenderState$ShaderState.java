/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.ShaderState
extends RenderState {
    public static RenderState.ShaderState create(String name, Shader shader) {
        return RenderStateFactory.getInstance().createShaderState(name, shader);
    }
}
