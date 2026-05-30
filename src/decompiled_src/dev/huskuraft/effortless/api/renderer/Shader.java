/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.events.render.RegisterShader;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.renderer.LazyShader;
import dev.huskuraft.effortless.api.renderer.Uniform;
import dev.huskuraft.effortless.api.renderer.VertexFormat;
import java.io.IOException;

public interface Shader
extends PlatformReference {
    public static Shader lazy(ResourceLocation location, VertexFormat format) {
        return new LazyShader(location, format);
    }

    public ResourceLocation getResource();

    public VertexFormat getVertexFormat();

    default public void register(RegisterShader.ShadersSink sink) {
        try {
            sink.register(this.getResource(), this.getVertexFormat(), shader -> {});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Uniform getUniform(String var1);
}
