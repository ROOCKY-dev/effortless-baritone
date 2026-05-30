/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandErrorMessageException;

public abstract class CommandInvalidArgumentException
extends CommandErrorMessageException {
    public final ICommandArgument arg;

    protected CommandInvalidArgumentException(ICommandArgument iCommandArgument, String string) {
        super(CommandInvalidArgumentException.formatMessage(iCommandArgument, string));
        this.arg = iCommandArgument;
    }

    protected CommandInvalidArgumentException(ICommandArgument iCommandArgument, String string, Throwable throwable) {
        super(CommandInvalidArgumentException.formatMessage(iCommandArgument, string), throwable);
        this.arg = iCommandArgument;
    }

    private static String formatMessage(ICommandArgument iCommandArgument, String string) {
        return String.format("Error at argument #%s: %s", iCommandArgument.getIndex() == -1 ? "<unknown>" : Integer.toString(iCommandArgument.getIndex() + 1), string);
    }
}

