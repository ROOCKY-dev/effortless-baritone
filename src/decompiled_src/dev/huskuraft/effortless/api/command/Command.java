/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.command;

import dev.huskuraft.effortless.api.command.CommandResult;
import dev.huskuraft.effortless.api.command.CommandSender;

public interface Command {
    default public CommandResult execute(CommandSender sender) {
        sender.send(this.build());
        return CommandResult.SUCCESS;
    }

    public String build();
}
