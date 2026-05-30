/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.renderer.Renderer;

public interface Renderable {
    public void render(Renderer var1, int var2, int var3, float var4);

    public void renderOverlay(Renderer var1, int var2, int var3, float var4);
}
