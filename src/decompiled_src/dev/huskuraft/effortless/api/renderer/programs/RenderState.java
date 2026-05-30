/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.renderer.RenderLayers;
import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.programs.CompositeRenderState;

public interface RenderState
extends PlatformReference {
    public static CompositeBuilder builder() {
        return new CompositeBuilder.Default();
    }

    public static abstract class CompositeBuilder
    extends RenderLayers {
        public abstract CompositeBuilder setTextureState(TextureState var1);

        public abstract CompositeBuilder setShaderState(ShaderState var1);

        public abstract CompositeBuilder setTransparencyState(TransparencyState var1);

        public abstract CompositeBuilder setDepthTestState(DepthTestState var1);

        public abstract CompositeBuilder setCullState(CullState var1);

        public abstract CompositeBuilder setLightmapState(LightmapState var1);

        public abstract CompositeBuilder setOverlayState(OverlayState var1);

        public abstract CompositeBuilder setLayeringState(LayeringState var1);

        public abstract CompositeBuilder setOutputState(OutputState var1);

        public abstract CompositeBuilder setTexturingState(TexturingState var1);

        public abstract CompositeBuilder setWriteMaskState(WriteMaskState var1);

        public abstract CompositeBuilder setLineState(LineState var1);

        public abstract CompositeBuilder setColorLogicState(ColorLogicState var1);

        public abstract CompositeRenderState create(boolean var1);

        private static class Default
        extends CompositeBuilder {
            private TextureState textureState = RenderLayers.NO_TEXTURE;
            private ShaderState shaderState = RenderLayers.NO_SHADER_STATE;
            private TransparencyState transparencyState = RenderLayers.NO_TRANSPARENCY;
            private DepthTestState depthTestState = RenderLayers.LEQUAL_DEPTH_TEST;
            private CullState cullState = RenderLayers.CULL;
            private LightmapState lightmapState = RenderLayers.NO_LIGHTMAP;
            private OverlayState overlayState = RenderLayers.NO_OVERLAY;
            private LayeringState layeringState = RenderLayers.NO_LAYERING;
            private OutputState outputState = RenderLayers.NO_TARGET;
            private TexturingState texturingState = RenderLayers.NO_TEXTURING;
            private WriteMaskState writeMaskState = RenderLayers.COLOR_DEPTH_WRITE;
            private LineState lineState = RenderLayers.DEFAULT_WIDTH;
            private ColorLogicState colorLogicState = RenderLayers.NO_COLOR_LOGIC;

            private Default() {
            }

            @Override
            public CompositeBuilder setTextureState(TextureState state) {
                this.textureState = state;
                return this;
            }

            @Override
            public CompositeBuilder setShaderState(ShaderState state) {
                this.shaderState = state;
                return this;
            }

            @Override
            public CompositeBuilder setTransparencyState(TransparencyState state) {
                this.transparencyState = state;
                return this;
            }

            @Override
            public CompositeBuilder setDepthTestState(DepthTestState state) {
                this.depthTestState = state;
                return this;
            }

            @Override
            public CompositeBuilder setCullState(CullState state) {
                this.cullState = state;
                return this;
            }

            @Override
            public CompositeBuilder setLightmapState(LightmapState state) {
                this.lightmapState = state;
                return this;
            }

            @Override
            public CompositeBuilder setOverlayState(OverlayState state) {
                this.overlayState = state;
                return this;
            }

            @Override
            public CompositeBuilder setLayeringState(LayeringState state) {
                this.layeringState = state;
                return this;
            }

            @Override
            public CompositeBuilder setOutputState(OutputState state) {
                this.outputState = state;
                return this;
            }

            @Override
            public CompositeBuilder setTexturingState(TexturingState state) {
                this.texturingState = state;
                return this;
            }

            @Override
            public CompositeBuilder setWriteMaskState(WriteMaskState state) {
                this.writeMaskState = state;
                return this;
            }

            @Override
            public CompositeBuilder setLineState(LineState state) {
                this.lineState = state;
                return this;
            }

            @Override
            public CompositeBuilder setColorLogicState(ColorLogicState state) {
                this.colorLogicState = state;
                return this;
            }

            @Override
            public CompositeRenderState create(boolean affectOutline) {
                return RenderStateFactory.getInstance().createCompositeState(this.textureState, this.shaderState, this.transparencyState, this.depthTestState, this.cullState, this.lightmapState, this.overlayState, this.layeringState, this.outputState, this.texturingState, this.writeMaskState, this.lineState, this.colorLogicState, affectOutline);
            }
        }
    }

    public static interface ColorLogicState
    extends RenderState {
        public static ColorLogicState create(String name, Op op) {
            return RenderStateFactory.getInstance().createColorLogicState(name, op);
        }

        public static enum Op {
            NO_LOGIC,
            OR_REVERSE_LOGIC;

        }
    }

    public static interface LineState
    extends RenderState {
        public static LineState create(String name, Double width) {
            return RenderStateFactory.getInstance().createLineState(name, width);
        }
    }

    public static interface WriteMaskState
    extends RenderState {
        public static WriteMaskState create(String name, boolean writeColor, boolean writeDepth) {
            return RenderStateFactory.getInstance().createWriteMaskState(name, writeColor, writeDepth);
        }
    }

    public static interface OffsetTexturingState
    extends TexturingState {
        public static OffsetTexturingState create(String name, float offsetX, float offsetY) {
            return RenderStateFactory.getInstance().createOffsetTexturingState(name, offsetX, offsetY);
        }
    }

    public static interface TexturingState
    extends RenderState {
        public static TexturingState create(String name, Runnable setupState, Runnable clearState) {
            return RenderStateFactory.getInstance().createTexturingState(name, setupState, clearState);
        }
    }

    public static interface OutputState
    extends RenderState {
        public static OutputState create(String name, Target target) {
            return RenderStateFactory.getInstance().createOutputState(name, target);
        }

        public static enum Target {
            NO,
            OUTLINE,
            TRANSLUCENT,
            PARTICLES,
            WEATHER,
            CLOUDS,
            ITEM_ENTITY;

        }
    }

    public static interface LayeringState
    extends RenderState {
        public static LayeringState create(String name, Type type) {
            return RenderStateFactory.getInstance().createLayeringState(name, type);
        }

        public static enum Type {
            NO,
            POLYGON_OFFSET,
            VIEW_OFFSET_Z;

        }
    }

    public static interface OverlayState
    extends RenderState {
        public static OverlayState create(String name, boolean overlay) {
            return RenderStateFactory.getInstance().createOverlayState(name, overlay);
        }
    }

    public static interface LightmapState
    extends RenderState {
        public static LightmapState create(String name, boolean lightmap) {
            return RenderStateFactory.getInstance().createLightmapState(name, lightmap);
        }
    }

    public static interface CullState
    extends RenderState {
        public static CullState create(String name, boolean cull) {
            return RenderStateFactory.getInstance().createCullState(name, cull);
        }
    }

    public static interface DepthTestState
    extends RenderState {
        public static DepthTestState create(String name, int function) {
            return RenderStateFactory.getInstance().createDepthTestState(name, function);
        }
    }

    public static interface ShaderState
    extends RenderState {
        public static ShaderState create(String name, Shader shader) {
            return RenderStateFactory.getInstance().createShaderState(name, shader);
        }
    }

    public static interface TransparencyState
    extends RenderState {
        public static TransparencyState create(String name, Type type) {
            return RenderStateFactory.getInstance().createTransparencyState(name, type);
        }

        public static enum Type {
            NO,
            ADDITIVE,
            LIGHTNING,
            GLINT,
            CRUMBLING,
            TRANSLUCENT;

        }
    }

    public static interface TextureState
    extends RenderState {
        public static TextureState create(String name, ResourceLocation location, boolean blur, boolean mipmap) {
            return RenderStateFactory.getInstance().createTextureState(name, location == null ? null : new Texture(location, blur, mipmap));
        }

        public record Texture(ResourceLocation location, boolean blur, boolean mipmap) {
        }
    }
}
