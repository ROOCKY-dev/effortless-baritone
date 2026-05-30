/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.exception;

import baritone.api.command.exception.CommandUnhandledException;

public class CommandNoParserForTypeException
extends CommandUnhandledException {
    public CommandNoParserForTypeException(Class<?> clazz) {
        super(String.format("Could not find a handler for type %s", clazz.getSimpleName()));
    }
}

