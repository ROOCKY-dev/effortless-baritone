/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderLayers;
import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.CompositeRenderState;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static abstract class RenderState.CompositeBuilder
extends RenderLayers {
    public abstract RenderState.CompositeBuilder setTextureState(RenderState.TextureState var1);

    public abstract RenderState.CompositeBuilder setShaderState(RenderState.ShaderState var1);

    public abstract RenderState.CompositeBuilder setTransparencyState(RenderState.TransparencyState var1);

    public abstract RenderState.CompositeBuilder setDepthTestState(RenderState.DepthTestState var1);

    public abstract RenderState.CompositeBuilder setCullState(RenderState.CullState var1);

    public abstract RenderState.CompositeBuilder setLightmapState(RenderState.LightmapState var1);

    public abstract RenderState.CompositeBuilder setOverlayState(RenderState.OverlayState var1);

    public abstract RenderState.CompositeBuilder setLayeringState(RenderState.LayeringState var1);

    public abstract RenderState.CompositeBuilder setOutputState(RenderState.OutputState var1);

    public abstract RenderState.CompositeBuilder setTexturingState(RenderState.TexturingState var1);

    public abstract RenderState.CompositeBuilder setWriteMaskState(RenderState.WriteMaskState var1);

    public abstract RenderState.CompositeBuilder setLineState(RenderState.LineState var1);

    public abstract RenderState.CompositeBuilder setColorLogicState(RenderState.ColorLogicState var1);

    public abstract CompositeRenderState create(boolean var1);

    private static class Default
    extends RenderState.CompositeBuilder {
        private RenderState.TextureState textureState = RenderLayers.NO_TEXTURE;
        private RenderState.ShaderState shaderState = RenderLayers.NO_SHADER_STATE;
        private RenderState.TransparencyState transparencyState = RenderLayers.NO_TRANSPARENCY;
        private RenderState.DepthTestState depthTestState = RenderLayers.LEQUAL_DEPTH_TEST;
        private RenderState.CullState cullState = RenderLayers.CULL;
        private RenderState.LightmapState lightmapState = RenderLayers.NO_LIGHTMAP;
        private RenderState.OverlayState overlayState = RenderLayers.NO_OVERLAY;
        private RenderState.LayeringState layeringState = RenderLayers.NO_LAYERING;
        private RenderState.OutputState outputState = RenderLayers.NO_TARGET;
        private RenderState.TexturingState texturingState = RenderLayers.NO_TEXTURING;
        private RenderState.WriteMaskState writeMaskState = RenderLayers.COLOR_DEPTH_WRITE;
        private RenderState.LineState lineState = RenderLayers.DEFAULT_WIDTH;
        private RenderState.ColorLogicState colorLogicState = RenderLayers.NO_COLOR_LOGIC;

        private Default() {
        }

        @Override
        public RenderState.CompositeBuilder setTextureState(RenderState.TextureState state) {
            this.textureState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setShaderState(RenderState.ShaderState state) {
            this.shaderState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setTransparencyState(RenderState.TransparencyState state) {
            this.transparencyState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setDepthTestState(RenderState.DepthTestState state) {
            this.depthTestState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setCullState(RenderState.CullState state) {
            this.cullState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setLightmapState(RenderState.LightmapState state) {
            this.lightmapState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setOverlayState(RenderState.OverlayState state) {
            this.overlayState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setLayeringState(RenderState.LayeringState state) {
            this.layeringState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setOutputState(RenderState.OutputState state) {
            this.outputState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setTexturingState(RenderState.TexturingState state) {
            this.texturingState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setWriteMaskState(RenderState.WriteMaskState state) {
            this.writeMaskState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setLineState(RenderState.LineState state) {
            this.lineState = state;
            return this;
        }

        @Override
        public RenderState.CompositeBuilder setColorLogicState(RenderState.ColorLogicState state) {
            this.colorLogicState = state;
            return this;
        }

        @Override
        public CompositeRenderState create(boolean affectOutline) {
            return RenderStateFactory.getInstance().createCompositeState(this.textureState, this.shaderState, this.transparencyState, this.depthTestState, this.cullState, this.lightmapState, this.overlayState, this.layeringState, this.outputState, this.texturingState, this.writeMaskState, this.lineState, this.colorLogicState, affectOutline);
        }
    }
}
