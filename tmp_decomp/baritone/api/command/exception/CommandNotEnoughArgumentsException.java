/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.exception.CommandErrorMessageException;

public class CommandNotEnoughArgumentsException
extends CommandErrorMessageException {
    public CommandNotEnoughArgumentsException(int n2) {
        super(String.format("Not enough arguments (expected at least %d)", n2));
    }
}

