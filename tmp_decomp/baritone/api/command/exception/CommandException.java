/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.exception.ICommandException;

public abstract class CommandException
extends Exception
implements ICommandException {
    protected CommandException(String string) {
        super(string);
    }

    protected CommandException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

