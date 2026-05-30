/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.level.block.Block
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.BlockById;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.utils.BetterBlockPos;
import baritone.m;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;

public final class au
extends Command {
    public au(a a2) {
        super(a2, "find");
    }

    @Override
    public final void execute(String componentArray, IArgConsumer object) {
        object.requireMin(1);
        componentArray = new ArrayList();
        while (object.hasAny()) {
            componentArray.add((Block)object.getDatatypeFor(BlockById.INSTANCE));
        }
        object = this.ctx.playerFeet();
        if ((componentArray = (Component[])componentArray.stream().flatMap(arg_0 -> this.a((BetterBlockPos)((Object)object), arg_0)).map(BetterBlockPos::new).map(this::a).toArray(Component[]::new)).length > 0) {
            Arrays.asList(componentArray).forEach(component -> this.logDirect((Component)component));
            return;
        }
        this.logDirect("No positions known, are you sure the blocks are cached?");
    }

    private Component a(BetterBlockPos betterBlockPos) {
        String string = String.format("%s %s %s", betterBlockPos.x, betterBlockPos.y, betterBlockPos.z);
        String string2 = String.format("%sgoal %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string);
        betterBlockPos = Component.literal((String)betterBlockPos.toString());
        MutableComponent mutableComponent = Component.literal((String)"Click to set goal to this position");
        BetterBlockPos betterBlockPos2 = betterBlockPos;
        betterBlockPos2.setStyle(betterBlockPos2.getStyle().withColor(ChatFormatting.GRAY).withInsertion(string).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, string2)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)mutableComponent)));
        return betterBlockPos;
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return new TabCompleteHelper().append(m.a.stream().map(arg_0 -> ((DefaultedRegistry)BuiltInRegistries.BLOCK).getKey(arg_0)).map(Object::toString)).filterPrefixNamespaced(iArgConsumer.getString()).sortAlphabetically().stream();
    }

    @Override
    public final String getShortDesc() {
        return "Find positions of a certain block";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The find command searches through Baritone's cache and attempts to find the location of the block.", "Tab completion will suggest only cached blocks and uncached blocks can not be found.", "", "Usage:", "> find <block> [...] - Try finding the listed blocks");
    }

    private /* synthetic */ Stream a(BetterBlockPos betterBlockPos, Block block) {
        return this.ctx.worldData().getCachedWorld().getLocationsOf(BuiltInRegistries.BLOCK.getKey((Object)block).getPath(), Integer.MAX_VALUE, betterBlockPos.x, betterBlockPos.y, 4).stream();
    }
}

