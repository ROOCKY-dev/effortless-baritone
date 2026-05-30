/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.TextureState
extends RenderState {
    public static RenderState.TextureState create(String name, ResourceLocation location, boolean blur, boolean mipmap) {
        return RenderStateFactory.getInstance().createTextureState(name, location == null ? null : new Texture(location, blur, mipmap));
    }

    public record Texture(ResourceLocation location, boolean blur, boolean mipmap) {
    }
}
