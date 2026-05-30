/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.Util
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.util.Tuple
 */
package baritone;

import baritone.a;
import baritone.ab;
import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandNotEnoughArgumentsException;
import baritone.api.command.exception.CommandNotFoundException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.TabCompleteEvent;
import baritone.api.event.events.type.Cancellable;
import baritone.api.utils.Helper;
import baritone.api.utils.SettingsUtil;
import baritone.bt;
import baritone.c;
import baritone.z;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.util.Tuple;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class w
extends c
implements Helper {
    private static final Settings a = BaritoneAPI.getSettings();
    private final bt a;

    public w(a a2) {
        super(a2);
        this.a = a2.a;
    }

    @Override
    public final void onSendChatMessage(ChatEvent object) {
        String string = ((ChatEvent)object).getMessage();
        String string2 = (String)w.a.prefix.value;
        boolean bl2 = string.startsWith(IBaritoneChatControl.FORCE_COMMAND_PREFIX);
        if (((Boolean)w.a.prefixControl.value).booleanValue() && string.startsWith(string2) || bl2) {
            ((Cancellable)object).cancel();
            object = string.substring(bl2 ? IBaritoneChatControl.FORCE_COMMAND_PREFIX.length() : string2.length());
            if (!this.a((String)object) && !((String)object).trim().isEmpty()) {
                new CommandNotFoundException((String)bt.a((String)object).getA()).handle(null, null);
            }
            return;
        }
        if ((((Boolean)w.a.chatControl.value).booleanValue() || ((Boolean)w.a.chatControlAnyway.value).booleanValue()) && this.a(string)) {
            ((Cancellable)object).cancel();
        }
    }

    private void a(String object, String object2) {
        if (((Boolean)w.a.echoCommands.value).booleanValue()) {
            object2 = (String)object + (String)object2;
            object = (Boolean)w.a.censorRanCommands.value != false ? (String)object + " ..." : object2;
            object = Component.literal((String)String.format("> %s", object));
            object.setStyle(object.getStyle().withColor(ChatFormatting.WHITE).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)"Click to rerun command"))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, IBaritoneChatControl.FORCE_COMMAND_PREFIX + (String)object2)));
            this.logDirect(new Component[]{object});
        }
    }

    private boolean a(String string) {
        while (true) {
            if (string.trim().equalsIgnoreCase("damn")) {
                this.logDirect("daniel");
                return false;
            }
            if (string.trim().equalsIgnoreCase("orderpizza")) {
                try {
                    Util.getPlatform().openUri("https://www.dominos.com/en/pages/order/");
                }
                catch (Exception exception) {}
                return false;
            }
            if (!string.isEmpty()) break;
            string = "help";
        }
        Tuple<String, List<ICommandArgument>> tuple = bt.a(string);
        String string2 = (String)tuple.getA();
        string = string.substring(((String)tuple.getA()).length());
        z z2 = new z(this.a, (List)tuple.getB());
        if (!z2.hasAny()) {
            Settings.Setting<?> setting = w.a.byLowerName.get(string2.toLowerCase(Locale.US));
            if (setting != null) {
                this.a(string2, string);
                if (setting.getValueClass() == Boolean.class) {
                    this.a.execute(String.format("set toggle %s", setting.getName()));
                } else {
                    this.a.execute(String.format("set %s", setting.getName()));
                }
                return true;
            }
        } else if (z2.hasExactlyOne()) {
            for (Settings.Setting<?> setting : w.a.allSettings) {
                if (setting.isJavaOnly() || !setting.getName().equalsIgnoreCase((String)tuple.getA())) continue;
                this.a(string2, string);
                try {
                    this.a.execute(String.format("set %s %s", setting.getName(), z2.getString()));
                }
                catch (CommandNotEnoughArgumentsException commandNotEnoughArgumentsException) {}
                return true;
            }
        }
        if (this.a.getCommand((String)tuple.getA()) != null) {
            this.a(string2, string);
        }
        return this.a.execute(tuple);
    }

    @Override
    public final void onPreTabComplete(TabCompleteEvent tabCompleteEvent) {
        if (!((Boolean)w.a.prefixControl.value).booleanValue()) {
            return;
        }
        Object object = tabCompleteEvent.prefix;
        String string = (String)w.a.prefix.value;
        if (!((String)object).startsWith(string)) {
            return;
        }
        object = ((String)object).substring(string.length());
        List<ICommandArgument> list = ab.a((String)object, true);
        object = this.a((String)object);
        if (list.size() == 1) {
            object = object.map(string2 -> string + string2);
        }
        tabCompleteEvent.completions = (String[])object.toArray(String[]::new);
    }

    private Stream<String> a(String object) {
        try {
            Object object2 = ab.a((String)object, true);
            object2 = new z(this.a, (List)object2);
            if (((z)object2).hasAtMost(2)) {
                if (((z)object2).hasExactly(1)) {
                    return new TabCompleteHelper().addCommands(this.a).addSettings().filterPrefix(((z)object2).getString()).stream();
                }
                Settings.Setting<?> setting = w.a.byLowerName.get(((z)object2).getString().toLowerCase(Locale.US));
                if (setting != null && !setting.isJavaOnly()) {
                    if (setting.getValueClass() == Boolean.class) {
                        object = new TabCompleteHelper();
                        if (((Boolean)setting.value).booleanValue()) {
                            ((TabCompleteHelper)object).append("true", "false");
                        } else {
                            ((TabCompleteHelper)object).append("false", "true");
                        }
                        return ((TabCompleteHelper)object).filterPrefix(((z)object2).getString()).stream();
                    }
                    return Stream.of(SettingsUtil.settingValueToString(setting));
                }
            }
            return this.a.tabComplete((String)object);
        }
        catch (CommandNotEnoughArgumentsException commandNotEnoughArgumentsException) {
            return Stream.empty();
        }
    }
}

