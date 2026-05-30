/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class ah
extends Command {
    private final String a;
    private String b;

    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    public ah(a a2, List list, String string, String string2) {
        super(a2, list.toArray(new String[0]));
        this.a = string;
        this.b = string2;
    }

    public ah(a a2, String string, String string2, String string3) {
        super(a2, string);
        this.a = string2;
        this.b = string3;
    }

    @Override
    public final void execute(String string, IArgConsumer iArgConsumer) {
        this.baritone.getCommandManager().execute(String.format("%s %s", this.b, iArgConsumer.rawRest()));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return this.baritone.getCommandManager().tabComplete(String.format("%s %s", this.b, iArgConsumer.rawRest()));
    }

    @Override
    public final String getShortDesc() {
        return this.a;
    }

    @Override
    public final List<String> getLongDesc() {
        return Collections.singletonList(String.format("This command is an alias, for: %s ...", this.b));
    }
}

