/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.renderer.RenderLayer;
import dev.huskuraft.effortless.api.renderer.VertexBuffer;

public interface BufferSource
extends PlatformReference {
    public VertexBuffer getBuffer(RenderLayer var1);

    public void end();
}
