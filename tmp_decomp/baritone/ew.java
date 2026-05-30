/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.phys.AABB
 */
package baritone;

import baritone.a;
import baritone.api.Settings;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.selection.ISelection;
import baritone.ev;
import baritone.fe;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public final class ew
implements AbstractGameEventListener,
fe {
    private final ev a;

    ew(a a2, ev ev2) {
        this.a = ev2;
        a2.getGameEventHandler().registerEventListener(this);
    }

    @Override
    public final void onRenderPass(RenderEvent renderEvent) {
        ISelection iSelection;
        int n2;
        ISelection[] iSelectionArray = this.a.getSelections();
        renderEvent = renderEvent.getModelViewStack();
        float f2 = ((Float)((Settings)((Object)ew.a)).selectionOpacity.value).floatValue();
        boolean bl2 = (Boolean)((Settings)((Object)ew.a)).renderSelectionIgnoreDepth.value;
        float f3 = ((Float)((Settings)((Object)ew.a)).selectionLineWidth.value).floatValue();
        if (!((Boolean)((Settings)((Object)ew.a)).renderSelection.value).booleanValue() || iSelectionArray.length == 0) {
            return;
        }
        BufferBuilder bufferBuilder = fe.a((Color)((Settings)((Object)ew.a)).colorSelection.value, f2, f3, bl2);
        ISelection[] iSelectionArray2 = iSelectionArray;
        int n3 = iSelectionArray.length;
        for (n2 = 0; n2 < n3; ++n2) {
            iSelection = iSelectionArray2[n2];
            fe.a(bufferBuilder, (PoseStack)renderEvent, iSelection.aabb(), 0.005);
        }
        if (((Boolean)((Settings)((Object)ew.a)).renderSelectionCorners.value).booleanValue()) {
            fe.a((Color)((Settings)((Object)ew.a)).colorSelectionPos1.value, f2);
            iSelectionArray2 = iSelectionArray;
            n3 = iSelectionArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                iSelection = iSelectionArray2[n2];
                fe.a(bufferBuilder, (PoseStack)renderEvent, new AABB((BlockPos)iSelection.pos1()));
            }
            fe.a((Color)((Settings)((Object)ew.a)).colorSelectionPos2.value, f2);
            iSelectionArray2 = iSelectionArray;
            n3 = iSelectionArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                iSelection = iSelectionArray2[n2];
                fe.a(bufferBuilder, (PoseStack)renderEvent, new AABB((BlockPos)iSelection.pos2()));
            }
        }
        fe.a(bufferBuilder, bl2);
    }
}

