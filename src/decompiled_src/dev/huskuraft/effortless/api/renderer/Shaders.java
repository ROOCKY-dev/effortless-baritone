/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.events.render.RegisterShader;
import dev.huskuraft.effortless.api.renderer.LazyShader;
import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.Uniform;
import dev.huskuraft.effortless.api.renderer.VertexFormat;
import dev.huskuraft.effortless.api.renderer.VertexFormats;
import javax.annotation.Nullable;

public enum Shaders implements Shader
{
    POSITION_COLOR_LIGHTMAP,
    POSITION,
    POSITION_COLOR_TEX,
    POSITION_TEX,
    POSITION_COLOR_TEX_LIGHTMAP,
    POSITION_COLOR,
    SOLID,
    CUTOUT_MIPPED,
    CUTOUT,
    TRANSLUCENT,
    TRANSLUCENT_MOVING_BLOCK,
    TRANSLUCENT_NO_CRUMBLING,
    ARMOR_CUTOUT_NO_CULL,
    ENTITY_SOLID,
    ENTITY_CUTOUT,
    ENTITY_CUTOUT_NO_CULL,
    ENTITY_CUTOUT_NO_CULL_Z_OFFSET,
    ITEM_ENTITY_TRANSLUCENT_CULL,
    ENTITY_TRANSLUCENT_CULL,
    ENTITY_TRANSLUCENT,
    ENTITY_TRANSLUCENT_EMISSIVE,
    ENTITY_SMOOTH_CUTOUT,
    BEACON_BEAM,
    ENTITY_DECAL,
    ENTITY_NO_OUTLINE,
    ENTITY_SHADOW,
    ENTITY_ALPHA,
    EYES,
    ENERGY_SWIRL,
    LEASH,
    WATER_MASK,
    OUTLINE,
    ARMOR_GLINT,
    ARMOR_ENTITY_GLINT,
    GLINT_TRANSLUCENT,
    GLINT,
    GLINT_DIRECT,
    ENTITY_GLINT,
    ENTITY_GLINT_DIRECT,
    CRUMBLING,
    TEXT,
    TEXT_BACKGROUND,
    TEXT_INTENSITY,
    TEXT_SEE_THROUGH,
    TEXT_BACKGROUND_SEE_THROUGH,
    TEXT_INTENSITY_SEE_THROUGH,
    LIGHTNING,
    TRIPWIRE,
    END_PORTAL,
    END_GATEWAY,
    LINES,
    GUI("rendertype_gui", VertexFormats.POSITION_COLOR),
    GUI_OVERLAY("rendertype_gui_overlay", VertexFormats.POSITION_COLOR),
    GUI_TEXT_HIGHLIGHT("rendertype_gui_text_highlight", VertexFormats.POSITION_COLOR),
    GUI_GHOST_RECIPE_OVERLAY;

    private final Shader shader;

    private Shaders() {
        this.shader = null;
    }

    private Shaders(String resource, VertexFormat vertexFormat) {
        this.shader = new LazyShader(ResourceLocation.vanilla(resource), vertexFormat);
    }

    @Override
    public void register(RegisterShader.ShadersSink sink) {
        if (this.shader != null) {
            this.shader.register(sink);
        }
    }

    private Shader getShader() {
        return RenderStateFactory.getInstance().getShader(this).ifUnavailable(() -> this.shader);
    }

    @Override
    public ResourceLocation getResource() {
        return this.getShader().getResource();
    }

    @Override
    public VertexFormat getVertexFormat() {
        return this.getShader().getVertexFormat();
    }

    @Override
    @Nullable
    public Uniform getUniform(String param) {
        return this.getShader().getUniform(param);
    }

    @Override
    public Object refs() {
        return this.getShader().refs();
    }
}
