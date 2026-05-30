/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.events.render.RegisterShader;
import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.Uniform;
import dev.huskuraft.effortless.api.renderer.VertexFormat;
import java.io.IOException;

class LazyShader
implements Shader {
    private final ResourceLocation location;
    private final VertexFormat vertexFormat;
    private Shader reference;

    public LazyShader(ResourceLocation location, VertexFormat vertexFormat) {
        this.location = location;
        this.vertexFormat = vertexFormat;
    }

    @Override
    public Object refs() {
        return this.reference.refs();
    }

    @Override
    public ResourceLocation getResource() {
        return this.location;
    }

    @Override
    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    @Override
    public void register(RegisterShader.ShadersSink sink) {
        try {
            sink.register(this.location, this.vertexFormat, shader -> {
                this.reference = shader;
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Uniform getUniform(String param) {
        return this.reference.getUniform(param);
    }
}
