/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4f
 *  org.joml.Vector4f
 */
package baritone;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.fg;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class fd
extends Screen
implements Helper {
    Matrix4f a;
    BlockPos a;
    BlockPos b;

    public fd() {
        super((Component)Component.literal((String)"CLICK"));
    }

    public final boolean isPauseScreen() {
        return false;
    }

    public final void render(GuiGraphics guiGraphics, int n2, int n3, float f2) {
        double d2 = fd.mc.mouseHandler.xpos();
        double d3 = fd.mc.mouseHandler.ypos();
        d3 = ((double)mc.getWindow().getScreenHeight() - d3) * ((double)mc.getWindow().getHeight() / (double)mc.getWindow().getScreenHeight());
        guiGraphics = this.a(d2 *= (double)mc.getWindow().getWidth() / (double)mc.getWindow().getScreenWidth(), d3, 0.0);
        Vec3 vec3 = this.a(d2, d3, 1.0);
        if (guiGraphics != null && vec3 != null) {
            Vec3 vec32 = new Vec3(fg.a(), fg.b(), fg.c());
            LocalPlayer localPlayer = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().player();
            guiGraphics = localPlayer.level().clip(new ClipContext(guiGraphics.add(vec32), vec3.add(vec32), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)localPlayer));
            if (guiGraphics != null && guiGraphics.getType() == HitResult.Type.BLOCK) {
                this.b = ((BlockHitResult)guiGraphics).getBlockPos();
            }
        }
    }

    public final boolean mouseReleased(double d2, double d3, int n2) {
        if (this.b != null) {
            if (n2 == 0) {
                if (this.a != null && !this.a.equals((Object)this.b)) {
                    BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager().removeAllSelections();
                    BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager().addSelection(BetterBlockPos.from(this.a), BetterBlockPos.from(this.b));
                    MutableComponent mutableComponent = Component.literal((String)("Selection made! For usage: " + (String)baritone.a.a().prefix.value + "help sel"));
                    mutableComponent.setStyle(mutableComponent.getStyle().withColor(ChatFormatting.WHITE).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, IBaritoneChatControl.FORCE_COMMAND_PREFIX + "help sel")));
                    Helper.HELPER.logDirect(new Component[]{mutableComponent});
                    this.a = null;
                } else {
                    BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.b));
                }
            } else if (n2 == 1) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.b.above()));
            }
        }
        this.a = null;
        return super.mouseReleased(d2, d3, n2);
    }

    public final boolean mouseClicked(double d2, double d3, int n2) {
        this.a = this.b;
        return super.mouseClicked(d2, d3, n2);
    }

    private Vec3 a(double d2, double d3, double d4) {
        if (this.a == null) {
            return null;
        }
        d2 /= (double)mc.getWindow().getWidth();
        d3 /= (double)mc.getWindow().getHeight();
        d2 = d2 * 2.0 - 1.0;
        d3 = d3 * 2.0 - 1.0;
        Vector4f vector4f = new Vector4f((float)d2, (float)d3, (float)d4, 1.0f);
        this.a.transform(vector4f);
        if (vector4f.w() == 0.0f) {
            return null;
        }
        vector4f.mul(1.0f / vector4f.w());
        return new Vec3((double)vector4f.x(), (double)vector4f.y(), (double)vector4f.z());
    }
}

