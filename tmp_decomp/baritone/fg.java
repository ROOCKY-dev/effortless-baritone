/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BeaconRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.Shapes
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 */
package baritone;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.event.events.RenderEvent;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalInverted;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.interfaces.IGoalRenderPos;
import baritone.c;
import baritone.dl;
import baritone.fb;
import baritone.fd;
import baritone.fe;
import baritone.i;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public final class fg
implements fe {
    private static final ResourceLocation a = ResourceLocation.parse((String)"textures/entity/beacon_beam.png");

    public static double a() {
        return a.renderPosX();
    }

    public static double b() {
        return a.renderPosY();
    }

    public static double c() {
        return a.renderPosZ();
    }

    public static void a(RenderEvent renderEvent, i i2) {
        Object object;
        Object object2;
        Object object3;
        IPlayerContext iPlayerContext = ((c)i2).a;
        if (iPlayerContext.world() == null) {
            return;
        }
        if (iPlayerContext.minecraft().screen instanceof fd) {
            object3 = renderEvent.getProjectionMatrix();
            PoseStack poseStack = renderEvent.getModelViewStack();
            object2 = (fd)iPlayerContext.minecraft().screen;
            ((fd)iPlayerContext.minecraft().screen).a = new Matrix4f((Matrix4fc)object3);
            object2.a.mul((Matrix4fc)poseStack.last().pose());
            object2.a.invert();
            if (object2.b != null) {
                object3 = fd.mc.getCameraEntity();
                fg.a(poseStack, (Entity)object3, Collections.singletonList(object2.b), Color.CYAN);
                if (object2.a != null && !object2.a.equals((Object)object2.b)) {
                    object3 = fe.a(Color.RED, ((Float)baritone.a.a().pathRenderLineWidthPixels.value).floatValue(), true);
                    object = new BetterBlockPos(object2.b);
                    object2 = new BetterBlockPos(object2.a);
                    fe.a((BufferBuilder)object3, poseStack, new AABB((double)Math.min(object.x, object2.x), (double)Math.min(object.y, object2.y), (double)Math.min(object.z, object2.z), (double)(Math.max(object.x, object2.x) + 1), (double)(Math.max(object.y, object2.y) + 1), (double)(Math.max(object.z, object2.z) + 1)));
                    fe.a((BufferBuilder)object3, true);
                }
            }
        }
        float f2 = renderEvent.getPartialTicks();
        object3 = i2.getGoal();
        object2 = iPlayerContext.world().dimensionType();
        if (object2 != (object = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().world().dimensionType())) {
            return;
        }
        if (object3 != null && ((Boolean)fg.a.renderGoal.value).booleanValue()) {
            fg.a(renderEvent.getModelViewStack(), iPlayerContext, (Goal)object3, f2, (Color)fg.a.colorGoalBox.value);
        }
        if (!((Boolean)fg.a.renderPath.value).booleanValue()) {
            return;
        }
        dl dl2 = i2.a;
        object3 = i2.b;
        if (dl2 != null && ((Boolean)fg.a.renderSelectionBoxes.value).booleanValue()) {
            fg.a(renderEvent.getModelViewStack(), (Entity)iPlayerContext.player(), Collections.unmodifiableSet(dl2.a), (Color)fg.a.colorBlocksToBreak.value);
            fg.a(renderEvent.getModelViewStack(), (Entity)iPlayerContext.player(), Collections.unmodifiableSet(dl2.b), (Color)fg.a.colorBlocksToPlace.value);
            fg.a(renderEvent.getModelViewStack(), (Entity)iPlayerContext.player(), Collections.unmodifiableSet(dl2.c), (Color)fg.a.colorBlocksToWalkInto.value);
        }
        if (dl2 != null && dl2.getPath() != null) {
            int n2 = Math.max(dl2.getPosition() - 3, 0);
            fg.a(renderEvent.getModelViewStack(), dl2.getPath().positions(), n2, (Color)fg.a.colorCurrentPath.value, (Boolean)fg.a.fadePath.value);
        }
        if (object3 != null && ((dl)object3).getPath() != null) {
            fg.a(renderEvent.getModelViewStack(), ((dl)object3).getPath().positions(), 0, (Color)fg.a.colorNextPath.value, (Boolean)fg.a.fadePath.value);
        }
        i2.getInProgress().ifPresent(bw2 -> {
            bw2.bestPathSoFar().ifPresent(iPath -> fg.a(renderEvent.getModelViewStack(), iPath.positions(), 0, (Color)fg.a.colorBestPathSoFar.value, (Boolean)fg.a.fadePath.value));
            bw2.pathToMostRecentNodeConsidered().ifPresent(iPath -> {
                fg.a(renderEvent.getModelViewStack(), iPath.positions(), 0, (Color)fg.a.colorMostRecentConsidered.value, (Boolean)fg.a.fadePath.value);
                fg.a(renderEvent.getModelViewStack(), (Entity)iPlayerContext.player(), Collections.singletonList(iPath.getDest()), (Color)fg.a.colorMostRecentConsidered.value);
            });
        });
    }

    private static void a(PoseStack poseStack, List<BetterBlockPos> list, int n2, Color color, boolean bl2) {
        fg.a(poseStack, list, n2, color, bl2, 10, 20, 0.5);
    }

    public static void a(PoseStack poseStack, List<BetterBlockPos> list, int n2, Color color, boolean bl2, int n3, int n4, double d2) {
        BufferBuilder bufferBuilder = fe.a(color, ((Float)fg.a.pathRenderLineWidthPixels.value).floatValue(), (Boolean)fg.a.renderPathIgnoreDepth.value);
        n3 += n2;
        n4 += n2;
        while (n2 < list.size() - 1) {
            BetterBlockPos betterBlockPos = list.get(n2);
            int n5 = n2 + 1;
            BetterBlockPos betterBlockPos2 = list.get(n5);
            int n6 = betterBlockPos2.x - betterBlockPos.x;
            int n7 = betterBlockPos2.y - betterBlockPos.y;
            int n8 = betterBlockPos2.z - betterBlockPos.z;
            while (!(n5 + 1 >= list.size() || bl2 && n5 + 1 >= n3 || n6 != list.get((int)(n5 + 1)).x - betterBlockPos2.x || n7 != list.get((int)(n5 + 1)).y - betterBlockPos2.y || n8 != list.get((int)(n5 + 1)).z - betterBlockPos2.z)) {
                betterBlockPos2 = list.get(++n5);
            }
            if (bl2) {
                float f2;
                if (n2 <= n3) {
                    f2 = 0.4f;
                } else {
                    if (n2 > n4) break;
                    f2 = 0.4f * (1.0f - (float)(n2 - n3) / (float)(n4 - n3));
                }
                fe.a(color, f2);
            }
            fg.a(bufferBuilder, poseStack, betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, betterBlockPos2.x, betterBlockPos2.y, betterBlockPos2.z, d2);
            n2 = n5;
        }
        fe.a(bufferBuilder, (Boolean)fg.a.renderPathIgnoreDepth.value);
    }

    private static void a(BufferBuilder bufferBuilder, PoseStack poseStack, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        double d9 = d8 + 0.03;
        double d10 = fg.a();
        double d11 = fg.b();
        double d12 = fg.c();
        boolean bl2 = (Boolean)fg.a.renderPathAsLine.value == false;
        fe.a(bufferBuilder, poseStack, d2 + d8 - d10, d3 + d8 - d11, d4 + d8 - d12, d5 + d8 - d10, d6 + d8 - d11, d7 + d8 - d12);
        if (bl2) {
            fe.a(bufferBuilder, poseStack, d5 + d8 - d10, d6 + d8 - d11, d7 + d8 - d12, d5 + d8 - d10, d6 + d9 - d11, d7 + d8 - d12);
            fe.a(bufferBuilder, poseStack, d5 + d8 - d10, d6 + d9 - d11, d7 + d8 - d12, d2 + d8 - d10, d3 + d9 - d11, d4 + d8 - d12);
            fe.a(bufferBuilder, poseStack, d2 + d8 - d10, d3 + d9 - d11, d4 + d8 - d12, d2 + d8 - d10, d3 + d8 - d11, d4 + d8 - d12);
        }
    }

    private static void a(PoseStack poseStack, Entity entity, Collection<BlockPos> collection, Color color) {
        color = fe.a(color, ((Float)fg.a.pathRenderLineWidthPixels.value).floatValue(), (Boolean)fg.a.renderSelectionBoxesIgnoreDepth.value);
        fb fb2 = new fb(BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext());
        collection.forEach(arg_0 -> fg.a(fb2, entity, (BufferBuilder)color, poseStack, arg_0));
        fe.a((BufferBuilder)color, (Boolean)fg.a.renderSelectionBoxesIgnoreDepth.value);
    }

    public static void a(PoseStack poseStack, IPlayerContext iPlayerContext, Goal goal, float f2, Color color) {
        fg.a(null, poseStack, iPlayerContext, goal, f2, color, true);
    }

    private static void a(@Nullable BufferBuilder bufferBuilder, PoseStack poseStack, IPlayerContext iPlayerContext, Goal goalArray, float f2, Color color, boolean n2) {
        if (!n2 && bufferBuilder == null) {
            throw new RuntimeException("BufferBuilder must not be null if setupRender is false");
        }
        double d2 = fg.a();
        double d3 = fg.b();
        double d4 = fg.c();
        double d5 = (Boolean)fg.a.renderGoalAnimated.value == false ? (double)0.999f : (double)Mth.cos((float)((float)((double)((float)(System.nanoTime() / 100000L % 20000L) / 20000.0f) * Math.PI * 2.0)));
        if (goalArray instanceof IGoalRenderPos) {
            BlockPos blockPos = ((IGoalRenderPos)goalArray).getGoalPos();
            double d6 = (double)blockPos.getX() + 0.002 - d2;
            double d7 = (double)(blockPos.getX() + 1) - 0.002 - d2;
            double d8 = (double)blockPos.getZ() + 0.002 - d4;
            double d9 = (double)(blockPos.getZ() + 1) - 0.002 - d4;
            if (goalArray instanceof GoalGetToBlock || goalArray instanceof GoalTwoBlocks) {
                d5 /= 2.0;
            }
            double d10 = d5 + 1.0 + (double)blockPos.getY() - d3;
            double d11 = 1.0 - d5 + (double)blockPos.getY() - d3;
            double d12 = (double)blockPos.getY() - d3;
            double d13 = d12 + 2.0;
            if (goalArray instanceof GoalGetToBlock || goalArray instanceof GoalTwoBlocks) {
                d10 -= 0.5;
                d11 -= 0.5;
                d13 -= 1.0;
            }
            fg.a(bufferBuilder, poseStack, color, d6, d7, d8, d9, d12, d13, d10, d11, n2 != 0);
            return;
        }
        if (goalArray instanceof GoalXZ) {
            GoalXZ goalXZ = (GoalXZ)goalArray;
            double d14 = iPlayerContext.world().getMinBuildHeight();
            double d15 = iPlayerContext.world().getMaxBuildHeight();
            if (((Boolean)fg.a.renderGoalXZBeacon.value).booleanValue()) {
                a.bindForSetup(a);
                if (((Boolean)fg.a.renderGoalIgnoreDepth.value).booleanValue()) {
                    RenderSystem.disableDepthTest();
                }
                poseStack.pushPose();
                poseStack.translate((double)goalXZ.getX() - d2, -d3, (double)goalXZ.getZ() - d4);
                BeaconRenderer.renderBeaconBeam((PoseStack)poseStack, (MultiBufferSource)iPlayerContext.minecraft().renderBuffers().bufferSource(), (ResourceLocation)a, (float)((Boolean)fg.a.renderGoalAnimated.value != false ? f2 : 0.0f), (float)1.0f, (long)((Boolean)fg.a.renderGoalAnimated.value != false ? iPlayerContext.world().getGameTime() : 0L), (int)((int)d14), (int)((int)d15), (int)color.getRGB(), (float)0.2f, (float)0.25f);
                poseStack.popPose();
                if (((Boolean)fg.a.renderGoalIgnoreDepth.value).booleanValue()) {
                    RenderSystem.enableDepthTest();
                }
                return;
            }
            double d16 = (double)goalXZ.getX() + 0.002 - d2;
            double d17 = (double)(goalXZ.getX() + 1) - 0.002 - d2;
            double d18 = (double)goalXZ.getZ() + 0.002 - d4;
            double d19 = (double)(goalXZ.getZ() + 1) - 0.002 - d4;
            fg.a(bufferBuilder, poseStack, color, d16, d17, d18, d19, d14 -= d3, d15 -= d3, 0.0, 0.0, n2 != 0);
            return;
        }
        if (goalArray instanceof GoalComposite) {
            boolean bl2 = Arrays.stream(((GoalComposite)goalArray).goals()).allMatch(IGoalRenderPos.class::isInstance);
            if (bl2) {
                bufferBuilder = fe.a(color, ((Float)fg.a.goalRenderLineWidthPixels.value).floatValue(), (Boolean)fg.a.renderGoalIgnoreDepth.value);
            }
            for (Goal goal : ((GoalComposite)goalArray).goals()) {
                fg.a(bufferBuilder, poseStack, iPlayerContext, goal, f2, color, !bl2);
            }
            if (bl2) {
                fe.a(bufferBuilder, (Boolean)fg.a.renderGoalIgnoreDepth.value);
            }
            return;
        }
        if (goalArray instanceof GoalInverted) {
            fg.a(poseStack, iPlayerContext, ((GoalInverted)goalArray).origin, f2, (Color)fg.a.colorInvertedGoalBox.value);
            return;
        }
        if (goalArray instanceof GoalYLevel) {
            GoalYLevel goalYLevel = (GoalYLevel)goalArray;
            double d20 = iPlayerContext.player().position().x - (Double)fg.a.yLevelBoxSize.value - d2;
            double d21 = iPlayerContext.player().position().z - (Double)fg.a.yLevelBoxSize.value - d4;
            double d22 = iPlayerContext.player().position().x + (Double)fg.a.yLevelBoxSize.value - d2;
            double d23 = iPlayerContext.player().position().z + (Double)fg.a.yLevelBoxSize.value - d4;
            double d24 = (double)((GoalYLevel)goalArray).level - d3;
            double d25 = d24 + 2.0;
            double d26 = d5 + 1.0 + (double)goalYLevel.level - d3;
            double d27 = 1.0 - d5 + (double)goalYLevel.level - d3;
            fg.a(bufferBuilder, poseStack, color, d20, d22, d21, d23, d24, d25, d26, d27, n2 != 0);
        }
    }

    private static void a(BufferBuilder bufferBuilder, PoseStack poseStack, Color color, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, boolean bl2) {
        if (bl2) {
            bufferBuilder = fe.a(color, ((Float)fg.a.goalRenderLineWidthPixels.value).floatValue(), (Boolean)fg.a.renderGoalIgnoreDepth.value);
        }
        fg.a(bufferBuilder, poseStack, d2, d3, d4, d5, d8);
        fg.a(bufferBuilder, poseStack, d2, d3, d4, d5, d9);
        for (double d10 = d6; d10 < d7; d10 += 16.0) {
            double d11 = Math.min(d7, d10 + 16.0);
            fe.a(bufferBuilder, poseStack, d2, d10, d4, d2, d11, d4, 0.0, 1.0, 0.0);
            fe.a(bufferBuilder, poseStack, d3, d10, d4, d3, d11, d4, 0.0, 1.0, 0.0);
            fe.a(bufferBuilder, poseStack, d3, d10, d5, d3, d11, d5, 0.0, 1.0, 0.0);
            fe.a(bufferBuilder, poseStack, d2, d10, d5, d2, d11, d5, 0.0, 1.0, 0.0);
        }
        if (bl2) {
            fe.a(bufferBuilder, (Boolean)fg.a.renderGoalIgnoreDepth.value);
        }
    }

    private static void a(BufferBuilder bufferBuilder, PoseStack poseStack, double d2, double d3, double d4, double d5, double d6) {
        if (d6 != 0.0) {
            fe.a(bufferBuilder, poseStack, d2, d6, d4, d3, d6, d4, 1.0, 0.0, 0.0);
            fe.a(bufferBuilder, poseStack, d3, d6, d4, d3, d6, d5, 0.0, 0.0, 1.0);
            fe.a(bufferBuilder, poseStack, d3, d6, d5, d2, d6, d5, -1.0, 0.0, 0.0);
            fe.a(bufferBuilder, poseStack, d2, d6, d5, d2, d6, d4, 0.0, 0.0, -1.0);
        }
    }

    private static /* synthetic */ void a(fb fb2, Entity entity, BufferBuilder bufferBuilder, PoseStack poseStack, BlockPos blockPos) {
        fb2 = ((fb2 = fb2.a(blockPos).getShape((BlockGetter)entity.level(), blockPos)).isEmpty() ? Shapes.block().bounds() : fb2.bounds()).move(blockPos);
        fe.a(bufferBuilder, poseStack, (AABB)fb2, 0.002);
    }
}

