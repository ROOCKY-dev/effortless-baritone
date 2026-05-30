/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.render;

import dev.huskuraft.effortless.api.renderer.Renderer;

@FunctionalInterface
public interface RenderWorld {
    public void onRenderWorld(Renderer var1, float var2);
}
