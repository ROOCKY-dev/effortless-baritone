/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.VertexFormat;

public static enum VertexFormats.Modes implements VertexFormat.Mode
{
    LINES,
    LINE_STRIP,
    DEBUG_LINES,
    DEBUG_LINE_STRIP,
    TRIANGLES,
    TRIANGLE_STRIP,
    TRIANGLE_FAN,
    QUADS;


    @Override
    public Object refs() {
        return RenderStateFactory.getInstance().getVertexFormatMode(this).refs();
    }
}
