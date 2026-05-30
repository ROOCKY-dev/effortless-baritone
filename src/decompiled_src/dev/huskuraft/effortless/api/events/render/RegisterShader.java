/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.render;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.VertexFormat;
import java.io.IOException;
import java.util.function.Consumer;

public interface RegisterShader {
    public void onRegisterShader(ShadersSink var1);

    @FunctionalInterface
    public static interface ShadersSink {
        public void register(ResourceLocation var1, VertexFormat var2, Consumer<Shader> var3) throws IOException;
    }
}
