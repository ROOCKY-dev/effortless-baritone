/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.command;

import dev.huskuraft.effortless.api.command.Command;
import dev.huskuraft.effortless.api.command.CommandRegister;

public abstract class CommandManager
implements CommandRegister {
    public abstract void dispatch(Command var1);
}
