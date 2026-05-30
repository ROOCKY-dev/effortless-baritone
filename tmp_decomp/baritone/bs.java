/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 */
package baritone;

import baritone.api.cache.IWaypoint;
import baritone.api.cache.IWorldData;
import baritone.api.cache.Waypoint;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForWaypoints;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.helpers.Paginator;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.BetterBlockPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

public final class bs
extends Command {
    private Map<IWorldData, List<IWaypoint>> a = new HashMap<IWorldData, List<IWaypoint>>();

    public bs(baritone.a a2) {
        super(a2, "waypoints", "waypoint", "wp");
    }

    @Override
    public final void execute(String string, IArgConsumer iArgConsumer) {
        a a3 = iArgConsumer.hasAny() ? baritone.bs$a.a(iArgConsumer.getString()) : baritone.bs$a.a;
        if (a3 == null) {
            throw new CommandInvalidTypeException(iArgConsumer.consumed(), "an action");
        }
        Object object2 = (iWaypoint, a2) -> {
            MutableComponent mutableComponent = Component.literal((String)"");
            MutableComponent mutableComponent2 = Component.literal((String)(iWaypoint.getTag().name() + " "));
            mutableComponent2.setStyle(mutableComponent2.getStyle().withColor(ChatFormatting.GRAY));
            String string2 = iWaypoint.getName();
            MutableComponent mutableComponent3 = Component.literal((String)(!string2.isEmpty() ? string2 : "<empty>"));
            mutableComponent3.setStyle(mutableComponent3.getStyle().withColor(!string2.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.DARK_GRAY));
            string2 = Component.literal((String)(" @ " + String.valueOf(new Date(iWaypoint.getCreationTimestamp()))));
            string2.setStyle(string2.getStyle().withColor(ChatFormatting.DARK_GRAY));
            mutableComponent.append((Component)mutableComponent2);
            mutableComponent.append((Component)mutableComponent3);
            mutableComponent.append((Component)string2);
            MutableComponent mutableComponent4 = mutableComponent;
            mutableComponent4.setStyle(mutableComponent4.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)"Click to select"))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s %s %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, a2.a[0], iWaypoint.getTag().getName(), iWaypoint.getCreationTimestamp()))));
            return mutableComponent;
        };
        Function<IWaypoint, Component> function = iWaypoint -> (Component)object2.apply(iWaypoint, a3 == baritone.bs$a.a ? baritone.bs$a.d : a3);
        if (a3 == baritone.bs$a.a) {
            IWaypoint[] iWaypointArray;
            IWaypoint.Tag tag = iArgConsumer.hasAny() ? IWaypoint.Tag.getByName(iArgConsumer.peekString()) : null;
            if (tag != null) {
                iArgConsumer.get();
            }
            if ((iWaypointArray = tag != null ? ForWaypoints.getWaypointsByTag(this.baritone, tag) : ForWaypoints.getWaypoints(this.baritone)).length > 0) {
                iArgConsumer.requireMax(1);
                Paginator.paginate(iArgConsumer, iWaypointArray, () -> this.logDirect(tag != null ? String.format("All waypoints by tag %s:", tag.name()) : "All waypoints:"), function, String.format("%s%s %s%s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, a3.a[0], tag != null ? " " + tag.getName() : ""));
                return;
            }
            iArgConsumer.requireMax(0);
            throw new CommandInvalidStateException(tag != null ? "No waypoints found by that tag" : "No waypoints found");
        }
        if (a3 == baritone.bs$a.c) {
            IWaypoint.Tag tag = iArgConsumer.hasAny() ? IWaypoint.Tag.getByName(iArgConsumer.peekString()) : null;
            if (tag == null) {
                tag = IWaypoint.Tag.USER;
            } else {
                iArgConsumer.get();
            }
            String string2 = iArgConsumer.hasExactlyOne() || iArgConsumer.hasExactly(4) ? iArgConsumer.getString() : "";
            BetterBlockPos betterBlockPos = iArgConsumer.hasAny() ? (BetterBlockPos)((Object)iArgConsumer.getDatatypePost(RelativeBlockPos.INSTANCE, this.ctx.playerFeet())) : this.ctx.playerFeet();
            iArgConsumer.requireMax(0);
            Waypoint waypoint = new Waypoint(string2, tag, betterBlockPos);
            ForWaypoints.waypoints(this.baritone).addWaypoint(waypoint);
            MutableComponent mutableComponent = Component.literal((String)"Waypoint added: ");
            mutableComponent.setStyle(mutableComponent.getStyle().withColor(ChatFormatting.GRAY));
            mutableComponent.append((Component)object2.apply(waypoint, baritone.bs$a.d));
            this.logDirect(new Component[]{mutableComponent});
            return;
        }
        if (a3 == baritone.bs$a.b) {
            MutableComponent mutableComponent;
            iArgConsumer.requireMax(1);
            String string3 = iArgConsumer.getString();
            IWaypoint.Tag tag = IWaypoint.Tag.getByName(string3);
            if (tag == null) {
                throw new CommandInvalidStateException("Invalid tag, \"" + string3 + "\"");
            }
            MutableComponent mutableComponent2 = mutableComponent = ForWaypoints.getWaypointsByTag(this.baritone, tag);
            int n2 = ((IWaypoint[])mutableComponent).length;
            for (int i2 = 0; i2 < n2; ++i2) {
                IWaypoint iWaypoint2 = mutableComponent2[i2];
                ForWaypoints.waypoints(this.baritone).removeWaypoint(iWaypoint2);
            }
            this.a.computeIfAbsent(this.baritone.getWorldProvider().getCurrentWorld(), iWorldData -> new ArrayList()).addAll(Arrays.asList(mutableComponent));
            mutableComponent2 = Component.literal((String)String.format("Cleared %d waypoints, click to restore them", ((IWaypoint[])mutableComponent).length));
            mutableComponent2.setStyle(mutableComponent2.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s restore @ %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, Stream.of(mutableComponent).map(iWaypoint -> Long.toString(iWaypoint.getCreationTimestamp())).collect(Collectors.joining(" "))))));
            this.logDirect(new Component[]{mutableComponent2});
            return;
        }
        if (a3 == baritone.bs$a.f) {
            ArrayList<Object> arrayList = new ArrayList();
            List list = this.a.getOrDefault(this.baritone.getWorldProvider().getCurrentWorld(), Collections.emptyList());
            if (iArgConsumer.peekString().equals("@")) {
                iArgConsumer.get();
                block5: while (iArgConsumer.hasAny()) {
                    long l2 = iArgConsumer.getAs(Long.class);
                    for (Object object2 : list) {
                        if (object2.getCreationTimestamp() != l2) continue;
                        arrayList.add(object2);
                        continue block5;
                    }
                }
            } else {
                iArgConsumer.requireExactly(1);
                int n3 = list.size();
                int n4 = Math.min(n3, iArgConsumer.getAs(Integer.class));
                arrayList = new ArrayList(list.subList(n3 - n4, n3));
            }
            arrayList.forEach(ForWaypoints.waypoints(this.baritone)::addWaypoint);
            list.removeIf(arrayList::contains);
            this.logDirect(String.format("Restored %d waypoints", arrayList.size()));
            return;
        }
        MutableComponent mutableComponent = (MutableComponent)iArgConsumer.getDatatypeFor(ForWaypoints.INSTANCE);
        IWaypoint iWaypoint3 = null;
        if (iArgConsumer.hasAny() && iArgConsumer.peekString().equals("@")) {
            iArgConsumer.requireExactly(2);
            iArgConsumer.get();
            long l3 = iArgConsumer.getAs(Long.class);
            for (IWaypoint iWaypoint4 : mutableComponent) {
                if (iWaypoint4.getCreationTimestamp() != l3) continue;
                iWaypoint3 = iWaypoint4;
                break;
            }
            if (iWaypoint3 == null) {
                throw new CommandInvalidStateException("Timestamp was specified but no waypoint was found");
            }
        } else {
            switch (((IWaypoint[])mutableComponent).length) {
                case 0: {
                    throw new CommandInvalidStateException("No waypoints found");
                }
                case 1: {
                    iWaypoint3 = mutableComponent[0];
                }
            }
        }
        if (iWaypoint3 == null) {
            iArgConsumer.requireMax(1);
            Paginator.paginate(iArgConsumer, mutableComponent, () -> this.logDirect("Multiple waypoints were found:"), function, String.format("%s%s %s %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, a3.a[0], iArgConsumer.consumedString()));
            return;
        }
        if (a3 == baritone.bs$a.d) {
            this.logDirect(function.apply(iWaypoint3));
            this.logDirect(String.format("Position: %s", new Object[]{iWaypoint3.getLocation()}));
            MutableComponent mutableComponent3 = Component.literal((String)"Click to delete this waypoint");
            mutableComponent3.setStyle(mutableComponent3.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s delete %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, iWaypoint3.getTag().getName(), iWaypoint3.getCreationTimestamp()))));
            MutableComponent mutableComponent4 = Component.literal((String)"Click to set goal to this waypoint");
            mutableComponent4.setStyle(mutableComponent4.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s goal %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, iWaypoint3.getTag().getName(), iWaypoint3.getCreationTimestamp()))));
            MutableComponent mutableComponent5 = Component.literal((String)"Click to show a command to recreate this waypoint");
            mutableComponent5.setStyle(mutableComponent5.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("%s%s save %s %s %s %s %s", baritone.a.a().prefix.value, string, iWaypoint3.getTag().getName(), iWaypoint3.getName(), iWaypoint3.getLocation().x, iWaypoint3.getLocation().y, iWaypoint3.getLocation().z))));
            MutableComponent mutableComponent6 = Component.literal((String)"Click to return to the waypoints list");
            mutableComponent6.setStyle(mutableComponent6.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s list", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string))));
            this.logDirect(new Component[]{mutableComponent3});
            this.logDirect(new Component[]{mutableComponent4});
            this.logDirect(new Component[]{mutableComponent5});
            this.logDirect(new Component[]{mutableComponent6});
            return;
        }
        if (a3 == baritone.bs$a.e) {
            ForWaypoints.waypoints(this.baritone).removeWaypoint(iWaypoint3);
            this.a.computeIfAbsent(this.baritone.getWorldProvider().getCurrentWorld(), iWorldData -> new ArrayList()).add(iWaypoint3);
            MutableComponent mutableComponent7 = Component.literal((String)"That waypoint has successfully been deleted, click to restore it");
            mutableComponent7.setStyle(mutableComponent7.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s%s restore @ %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, string, iWaypoint3.getCreationTimestamp()))));
            this.logDirect(new Component[]{mutableComponent7});
            return;
        }
        if (a3 == baritone.bs$a.g) {
            GoalBlock goalBlock = new GoalBlock(iWaypoint3.getLocation());
            this.baritone.getCustomGoalProcess().setGoal(goalBlock);
            this.logDirect(String.format("Goal: %s", goalBlock));
            return;
        }
        if (a3 == baritone.bs$a.h) {
            GoalBlock goalBlock = new GoalBlock(iWaypoint3.getLocation());
            this.baritone.getCustomGoalProcess().setGoalAndPath(goalBlock);
            this.logDirect(String.format("Going to: %s", goalBlock));
        }
    }

    @Override
    public final Stream<String> tabComplete(String object, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasAny()) {
            if (iArgConsumer.hasExactlyOne()) {
                return new TabCompleteHelper().append(baritone.bs$a.a()).sortAlphabetically().filterPrefix(iArgConsumer.getString()).stream();
            }
            object = baritone.bs$a.a(iArgConsumer.getString());
            if (iArgConsumer.hasExactlyOne()) {
                if (object == baritone.bs$a.a || object == baritone.bs$a.c || object == baritone.bs$a.b) {
                    return new TabCompleteHelper().append(IWaypoint.Tag.getAllNames()).sortAlphabetically().filterPrefix(iArgConsumer.getString()).stream();
                }
                if (object == baritone.bs$a.f) {
                    return Stream.empty();
                }
                return iArgConsumer.tabCompleteDatatype(ForWaypoints.INSTANCE);
            }
            if (iArgConsumer.has(3) && object == baritone.bs$a.c) {
                iArgConsumer.get();
                iArgConsumer.get();
                return iArgConsumer.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
            }
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Manage waypoints";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The waypoint command allows you to manage Baritone's waypoints.", "", "Waypoints can be used to mark positions for later. Waypoints are each given a tag and an optional name.", "", "Note that the info, delete, and goal commands let you specify a waypoint by tag. If there is more than one waypoint with a certain tag, then they will let you select which waypoint you mean.", "", "Missing arguments for the save command use the USER tag, creating an unnamed waypoint and your current position as defaults.", "", "Usage:", "> wp [l/list] - List all waypoints.", "> wp <l/list> <tag> - List all waypoints by tag.", "> wp <s/save> - Save an unnamed USER waypoint at your current position", "> wp <s/save> [tag] [name] [pos] - Save a waypoint with the specified tag, name and position.", "> wp <i/info/show> <tag/name> - Show info on a waypoint by tag or name.", "> wp <d/delete> <tag/name> - Delete a waypoint by tag or name.", "> wp <restore> <n> - Restore the last n deleted waypoints.", "> wp <c/clear> <tag> - Delete all waypoints with the specified tag.", "> wp <g/goal> <tag/name> - Set a goal to a waypoint by tag or name.", "> wp <goto> <tag/name> - Set a goal to a waypoint by tag or name and start pathing.");
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class a
    extends Enum<a> {
        public static final /* enum */ a a = new a("list", "get", "l");
        public static final /* enum */ a b = new a("clear", "c");
        public static final /* enum */ a c = new a("save", "s");
        public static final /* enum */ a d = new a("info", "show", "i");
        public static final /* enum */ a e = new a("delete", "d");
        public static final /* enum */ a f = new a("restore");
        public static final /* enum */ a g = new a("goal", "g");
        public static final /* enum */ a h = new a("goto");
        final String[] a;
        private static final /* synthetic */ a[] a;

        public static a[] values() {
            return (a[])a.clone();
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        private a(String ... stringArray) {
            this.a = stringArray;
        }

        public static a a(String string) {
            for (a a2 : baritone.bs$a.values()) {
                String[] stringArray = a2.a;
                int n2 = a2.a.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    if (!stringArray[i2].equalsIgnoreCase(string)) continue;
                    return a2;
                }
            }
            return null;
        }

        public static String[] a() {
            HashSet<String> hashSet = new HashSet<String>();
            for (a a2 : baritone.bs$a.values()) {
                hashSet.addAll(Arrays.asList(a2.a));
            }
            return hashSet.toArray(new String[0]);
        }

        static {
            a = new a[]{a, b, c, d, e, f, g, h};
        }
    }
}

