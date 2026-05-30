/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandInvalidArgumentException;

public class CommandInvalidTypeException
extends CommandInvalidArgumentException {
    public CommandInvalidTypeException(ICommandArgument iCommandArgument, String string) {
        super(iCommandArgument, String.format("Expected %s", string));
    }

    public CommandInvalidTypeException(ICommandArgument iCommandArgument, String string, Throwable throwable) {
        super(iCommandArgument, String.format("Expected %s", string), throwable);
    }

    public CommandInvalidTypeException(ICommandArgument iCommandArgument, String string, String string2) {
        super(iCommandArgument, String.format("Expected %s, but got %s instead", string, string2));
    }

    public CommandInvalidTypeException(ICommandArgument iCommandArgument, String string, String string2, Throwable throwable) {
        super(iCommandArgument, String.format("Expected %s, but got %s instead", string, string2), throwable);
    }
}

