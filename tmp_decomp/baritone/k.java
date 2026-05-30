/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.level.block.BedBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BedPart
 *  net.minecraft.world.level.block.state.properties.Property
 */
package baritone;

import baritone.a;
import baritone.api.cache.IWaypoint;
import baritone.api.cache.Waypoint;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.event.events.BlockInteractEvent;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.c;
import baritone.fb;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.Property;

public final class k
extends c {
    public k(a a2) {
        super(a2);
    }

    @Override
    public final void onBlockInteract(BlockInteractEvent object) {
        BlockState blockState;
        if (!((Boolean)baritone.a.a().doBedWaypoints.value).booleanValue()) {
            return;
        }
        if (object.getType() == BlockInteractEvent.Type.USE && (blockState = fb.a(this.a, (BlockPos)(object = BetterBlockPos.from(object.getPos())))).getBlock() instanceof BedBlock) {
            if (blockState.getValue((Property)BedBlock.PART) == BedPart.FOOT) {
                object = ((BetterBlockPos)((Object)object)).relative((Direction)blockState.getValue((Property)BedBlock.FACING));
            }
            if (!this.a.a.a().getWaypoints().getByTag(IWaypoint.Tag.BED).stream().map(IWaypoint::getLocation).filter(((BetterBlockPos)((Object)object))::equals).findFirst().isPresent()) {
                this.a.a.a().getWaypoints().addWaypoint(new Waypoint("bed", IWaypoint.Tag.BED, (BetterBlockPos)((Object)object)));
            }
        }
    }

    @Override
    public final void onPlayerDeath() {
        if (!((Boolean)baritone.a.a().doDeathWaypoints.value).booleanValue()) {
            return;
        }
        Waypoint waypoint = new Waypoint("death", IWaypoint.Tag.DEATH, this.a.playerFeet());
        this.a.a.a().getWaypoints().addWaypoint(waypoint);
        MutableComponent mutableComponent = Component.literal((String)"Death position saved.");
        mutableComponent.setStyle(mutableComponent.getStyle().withColor(ChatFormatting.WHITE).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)"Click to goto death"))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s goto %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, "wp", waypoint.getTag().getName(), waypoint.getCreationTimestamp()))));
        Helper.HELPER.logDirect(new Component[]{mutableComponent});
    }
}

