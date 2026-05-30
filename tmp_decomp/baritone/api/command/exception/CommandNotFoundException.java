/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.ICommand;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandException;
import baritone.api.utils.Helper;
import java.util.List;

public class CommandNotFoundException
extends CommandException {
    public final String command;

    public CommandNotFoundException(String string) {
        super(String.format("Command not found: %s", string));
        this.command = string;
    }

    @Override
    public void handle(ICommand iCommand, List<ICommandArgument> list) {
        Helper.HELPER.logDirect(this.getMessage());
    }
}

