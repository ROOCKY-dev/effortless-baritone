/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  org.joml.Matrix4f
 */
package baritone.api.event.events;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;

public final class RenderEvent {
    private final float partialTicks;
    private final Matrix4f projectionMatrix;
    private final PoseStack modelViewStack;

    public RenderEvent(float f2, PoseStack poseStack, Matrix4f matrix4f) {
        this.partialTicks = f2;
        this.modelViewStack = poseStack;
        this.projectionMatrix = matrix4f;
    }

    public final float getPartialTicks() {
        return this.partialTicks;
    }

    public final PoseStack getModelViewStack() {
        return this.modelViewStack;
    }

    public final Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
}

