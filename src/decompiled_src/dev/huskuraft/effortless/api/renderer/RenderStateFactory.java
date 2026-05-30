/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.renderer.RenderLayer;
import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.Shaders;
import dev.huskuraft.effortless.api.renderer.VertexFormat;
import dev.huskuraft.effortless.api.renderer.VertexFormats;
import dev.huskuraft.effortless.api.renderer.programs.CompositeRenderState;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public interface RenderStateFactory {
    public static RenderStateFactory getInstance() {
        return PlatformLoader.getSingleton(new RenderStateFactory[0]);
    }

    public RenderLayer createCompositeRenderLayer(String var1, VertexFormat var2, VertexFormat.Mode var3, int var4, boolean var5, boolean var6, CompositeRenderState var7);

    public CompositeRenderState createCompositeState(RenderState.TextureState var1, RenderState.ShaderState var2, RenderState.TransparencyState var3, RenderState.DepthTestState var4, RenderState.CullState var5, RenderState.LightmapState var6, RenderState.OverlayState var7, RenderState.LayeringState var8, RenderState.OutputState var9, RenderState.TexturingState var10, RenderState.WriteMaskState var11, RenderState.LineState var12, RenderState.ColorLogicState var13, boolean var14);

    public RenderState createRenderState(String var1, Runnable var2, Runnable var3);

    public RenderState.TextureState createTextureState(String var1, RenderState.TextureState.Texture var2);

    public RenderState.ShaderState createShaderState(String var1, Shader var2);

    public RenderState.TransparencyState createTransparencyState(String var1, RenderState.TransparencyState.Type var2);

    public RenderState.DepthTestState createDepthTestState(String var1, int var2);

    public RenderState.CullState createCullState(String var1, boolean var2);

    public RenderState.LightmapState createLightmapState(String var1, boolean var2);

    public RenderState.OverlayState createOverlayState(String var1, boolean var2);

    public RenderState.LayeringState createLayeringState(String var1, RenderState.LayeringState.Type var2);

    public RenderState.OutputState createOutputState(String var1, RenderState.OutputState.Target var2);

    public RenderState.TexturingState createTexturingState(String var1, Runnable var2, Runnable var3);

    public RenderState.OffsetTexturingState createOffsetTexturingState(String var1, float var2, float var3);

    public RenderState.WriteMaskState createWriteMaskState(String var1, boolean var2, boolean var3);

    public RenderState.LineState createLineState(String var1, Double var2);

    public RenderState.ColorLogicState createColorLogicState(String var1, RenderState.ColorLogicState.Op var2);

    public Shader getShader(Shaders var1);

    public VertexFormat getVertexFormat(VertexFormats var1);

    public VertexFormat.Mode getVertexFormatMode(VertexFormats.Modes var1);
}
