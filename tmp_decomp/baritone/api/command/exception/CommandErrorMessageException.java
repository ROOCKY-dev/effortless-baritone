/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.exception.CommandException;

public abstract class CommandErrorMessageException
extends CommandException {
    public CommandErrorMessageException(String string) {
        super(string);
    }

    protected CommandErrorMessageException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

