/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.VertexFormat;
import dev.huskuraft.effortless.api.renderer.programs.CompositeRenderState;

public interface RenderLayer
extends PlatformReference {
    public static RenderLayer createComposite(String name, VertexFormat vertexFormat, VertexFormat.Mode vertexMode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, CompositeRenderState state) {
        return RenderStateFactory.getInstance().createCompositeRenderLayer(name, vertexFormat, vertexMode, bufferSize, affectsCrumbling, sortOnUpload, state);
    }

    public static RenderLayer createComposite(String name, VertexFormat vertexFormat, VertexFormat.Mode vertexMode, int bufferSize, CompositeRenderState state) {
        return RenderStateFactory.getInstance().createCompositeRenderLayer(name, vertexFormat, vertexMode, bufferSize, false, false, state);
    }
}
