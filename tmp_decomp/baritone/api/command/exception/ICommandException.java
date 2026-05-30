/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 */
package baritone.api.command.exception;

import baritone.api.command.ICommand;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.utils.Helper;
import java.util.List;
import net.minecraft.ChatFormatting;

public interface ICommandException {
    public String getMessage();

    default public void handle(ICommand iCommand, List<ICommandArgument> list) {
        Helper.HELPER.logDirect(this.getMessage(), ChatFormatting.RED);
    }
}

