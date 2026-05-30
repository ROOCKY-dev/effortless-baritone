/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.MeshData
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.fp;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface fe {
    public static final Tesselator a = Tesselator.getInstance();
    public static final fp a = (fp)Minecraft.getInstance().getEntityRenderDispatcher();
    public static final TextureManager a = Minecraft.getInstance().getTextureManager();
    public static final Settings a = BaritoneAPI.getSettings();
    public static final float[] a = new float[]{1.0f, 1.0f, 1.0f, 255.0f};

    public static void a(Color object, float f2) {
        object = ((Color)object).getColorComponents(null);
        fe.a[0] = (float)object[0];
        fe.a[1] = (float)object[1];
        fe.a[2] = (float)object[2];
        fe.a[3] = f2;
    }

    public static BufferBuilder a(Color color, float f2, float f3, boolean bl2) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        fe.a(color, f2);
        RenderSystem.lineWidth((float)f3);
        RenderSystem.depthMask((boolean)false);
        RenderSystem.disableCull();
        if (bl2) {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        return a.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
    }

    public static BufferBuilder a(Color color, float f2, boolean bl2) {
        return fe.a(color, 0.4f, f2, bl2);
    }

    public static void a(BufferBuilder bufferBuilder, boolean bl2) {
        if ((bufferBuilder = bufferBuilder.build()) != null) {
            BufferUploader.drawWithShader((MeshData)bufferBuilder);
        }
        if (bl2) {
            RenderSystem.enableDepthTest();
        }
        RenderSystem.enableCull();
        RenderSystem.depthMask((boolean)true);
        RenderSystem.disableBlend();
    }

    public static void a(BufferBuilder bufferBuilder, PoseStack poseStack, double d2, double d3, double d4, double d5, double d6, double d7) {
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        double d10 = d7 - d4;
        double d11 = d8;
        double d12 = d9;
        double d13 = d10;
        double d14 = 1.0 / Math.sqrt(d11 * d11 + d12 * d12 + d13 * d13);
        float f2 = (float)(d8 * d14);
        float f3 = (float)(d9 * d14);
        float f4 = (float)(d10 * d14);
        fe.a(bufferBuilder, poseStack, d2, d3, d4, d5, d6, d7, (double)f2, (double)f3, (double)f4);
    }

    public static void a(BufferBuilder bufferBuilder, PoseStack poseStack, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
        fe.a(bufferBuilder, poseStack, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, (float)d9, (float)d10);
    }

    public static void a(BufferBuilder bufferBuilder, PoseStack poseStack, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        poseStack = poseStack.last();
        bufferBuilder.addVertex((PoseStack.Pose)poseStack, f2, f3, f4).setColor(a[0], a[1], a[2], a[3]).setNormal((PoseStack.Pose)poseStack, f8, f9, f10);
        bufferBuilder.addVertex((PoseStack.Pose)poseStack, f5, f6, f7).setColor(a[0], a[1], a[2], a[3]).setNormal((PoseStack.Pose)poseStack, f8, f9, f10);
    }

    public static void a(BufferBuilder bufferBuilder, PoseStack poseStack, AABB aABB) {
        aABB = aABB.move(-a.renderPosX(), -a.renderPosY(), -a.renderPosZ());
        fe.a(bufferBuilder, poseStack, aABB.minX, aABB.minY, aABB.minZ, aABB.maxX, aABB.minY, aABB.minZ, 1.0, 0.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.maxX, aABB.minY, aABB.minZ, aABB.maxX, aABB.minY, aABB.maxZ, 0.0, 0.0, 1.0);
        fe.a(bufferBuilder, poseStack, aABB.maxX, aABB.minY, aABB.maxZ, aABB.minX, aABB.minY, aABB.maxZ, -1.0, 0.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.minX, aABB.minY, aABB.maxZ, aABB.minX, aABB.minY, aABB.minZ, 0.0, 0.0, -1.0);
        fe.a(bufferBuilder, poseStack, aABB.minX, aABB.maxY, aABB.minZ, aABB.maxX, aABB.maxY, aABB.minZ, 1.0, 0.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.maxX, aABB.maxY, aABB.minZ, aABB.maxX, aABB.maxY, aABB.maxZ, 0.0, 0.0, 1.0);
        fe.a(bufferBuilder, poseStack, aABB.maxX, aABB.maxY, aABB.maxZ, aABB.minX, aABB.maxY, aABB.maxZ, -1.0, 0.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.minX, aABB.maxY, aABB.maxZ, aABB.minX, aABB.maxY, aABB.minZ, 0.0, 0.0, -1.0);
        fe.a(bufferBuilder, poseStack, aABB.minX, aABB.minY, aABB.minZ, aABB.minX, aABB.maxY, aABB.minZ, 0.0, 1.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.maxX, aABB.minY, aABB.minZ, aABB.maxX, aABB.maxY, aABB.minZ, 0.0, 1.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.maxX, aABB.minY, aABB.maxZ, aABB.maxX, aABB.maxY, aABB.maxZ, 0.0, 1.0, 0.0);
        fe.a(bufferBuilder, poseStack, aABB.minX, aABB.minY, aABB.maxZ, aABB.minX, aABB.maxY, aABB.maxZ, 0.0, 1.0, 0.0);
    }

    public static void a(BufferBuilder bufferBuilder, PoseStack poseStack, AABB aABB, double d2) {
        double d3 = d2;
        fe.a(bufferBuilder, poseStack, aABB.inflate(d3, d3, d2));
    }

    public static void a(BufferBuilder bufferBuilder, PoseStack poseStack, Vec3 vec3, Vec3 vec32) {
        double d2 = a.renderPosX();
        double d3 = a.renderPosY();
        double d4 = a.renderPosZ();
        fe.a(bufferBuilder, poseStack, vec3.x - d2, vec3.y - d3, vec3.z - d4, vec32.x - d2, vec32.y - d3, vec32.z - d4);
    }
}

