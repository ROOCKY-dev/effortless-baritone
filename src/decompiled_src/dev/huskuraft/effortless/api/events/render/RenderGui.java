/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.render;

import dev.huskuraft.effortless.api.renderer.Renderer;

@FunctionalInterface
public interface RenderGui {
    public void onRenderGui(Renderer var1, float var2);
}
