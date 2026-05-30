/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.ICommand;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.ICommandException;
import baritone.api.utils.Helper;
import java.util.List;

public class CommandUnhandledException
extends RuntimeException
implements ICommandException {
    public CommandUnhandledException(String string) {
        super(string);
    }

    public CommandUnhandledException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public void handle(ICommand iCommand, List<ICommandArgument> list) {
        Helper.HELPER.logUnhandledException(this);
    }
}

