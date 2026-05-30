/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command;

import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.Helper;
import java.util.List;
import java.util.stream.Stream;

public interface ICommand
extends Helper {
    public void execute(String var1, IArgConsumer var2);

    public Stream<String> tabComplete(String var1, IArgConsumer var2);

    public String getShortDesc();

    public List<String> getLongDesc();

    public List<String> getNames();

    default public boolean hiddenFromHelp() {
        return false;
    }
}

