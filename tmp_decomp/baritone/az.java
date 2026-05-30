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

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.ICommand;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandNotFoundException;
import baritone.api.command.helpers.Paginator;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

public final class az
extends Command {
    public az(a a2) {
        super(a2, "help", "?");
    }

    @Override
    public final void execute(String string, IArgConsumer object) {
        object.requireMax(1);
        if (!object.hasAny() || object.is(Integer.class)) {
            Paginator.paginate((IArgConsumer)object, new Paginator(this.baritone.getCommandManager().getRegistry().descendingStream().filter(iCommand -> !iCommand.hiddenFromHelp()).collect(Collectors.toList())), () -> this.logDirect("All Baritone commands (clickable):"), iCommand -> {
                String string = String.join((CharSequence)"/", iCommand.getNames());
                String string2 = iCommand.getNames().get(0);
                MutableComponent mutableComponent = Component.literal((String)(" - " + iCommand.getShortDesc()));
                mutableComponent.setStyle(mutableComponent.getStyle().withColor(ChatFormatting.DARK_GRAY));
                string = Component.literal((String)string);
                string.setStyle(string.getStyle().withColor(ChatFormatting.WHITE));
                MutableComponent mutableComponent2 = Component.literal((String)"");
                mutableComponent2.setStyle(mutableComponent2.getStyle().withColor(ChatFormatting.GRAY));
                mutableComponent2.append((Component)string);
                mutableComponent2.append("\n" + iCommand.getShortDesc());
                mutableComponent2.append("\n\nClick to view full help");
                string = IBaritoneChatControl.FORCE_COMMAND_PREFIX + String.format("%s %s", string, iCommand.getNames().get(0));
                iCommand = Component.literal((String)string2);
                iCommand.setStyle(iCommand.getStyle().withColor(ChatFormatting.GRAY));
                iCommand.append((Component)mutableComponent);
                ICommand iCommand2 = iCommand;
                iCommand2.setStyle(iCommand2.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)mutableComponent2)).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, string)));
                return iCommand;
            }, IBaritoneChatControl.FORCE_COMMAND_PREFIX + string);
            return;
        }
        object = object.getString().toLowerCase();
        ICommand iCommand2 = this.baritone.getCommandManager().getCommand((String)object);
        if (iCommand2 == null) {
            throw new CommandNotFoundException((String)object);
        }
        this.logDirect(String.format("%s - %s", String.join((CharSequence)" / ", iCommand2.getNames()), iCommand2.getShortDesc()));
        this.logDirect("");
        iCommand2.getLongDesc().forEach(this::logDirect);
        this.logDirect("");
        object = Component.literal((String)"Click to return to the help menu");
        object.setStyle(object.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, IBaritoneChatControl.FORCE_COMMAND_PREFIX + string)));
        this.logDirect(new Component[]{object});
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasExactlyOne()) {
            return new TabCompleteHelper().addCommands(this.baritone.getCommandManager()).filterPrefix(iArgConsumer.getString()).stream();
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "View all commands or help on specific ones";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Using this command, you can view detailed help information on how to use certain commands of Baritone.", "", "Usage:", "> help - Lists all commands and their short descriptions.", "> help <command> - Displays help information on a specific command.");
    }
}

