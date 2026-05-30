/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Tuple
 */
package baritone;

import baritone.ab;
import baritone.ai;
import baritone.api.IBaritone;
import baritone.api.command.ICommand;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandException;
import baritone.api.command.exception.CommandUnhandledException;
import baritone.api.command.exception.ICommandException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.command.manager.ICommandManager;
import baritone.api.command.registry.Registry;
import baritone.z;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.util.Tuple;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bt
implements ICommandManager {
    private final Registry<ICommand> a;
    private final baritone.a a = new Registry();

    public bt(baritone.a a2) {
        this.a = a2;
        ai.a(a2).forEach(this.a::register);
    }

    @Override
    public final IBaritone getBaritone() {
        return this.a;
    }

    @Override
    public final Registry<ICommand> getRegistry() {
        return this.a;
    }

    @Override
    public final ICommand getCommand(String string) {
        for (ICommand iCommand : this.a.entries) {
            if (!iCommand.getNames().contains(string.toLowerCase(Locale.US))) continue;
            return iCommand;
        }
        return null;
    }

    @Override
    public final boolean execute(String string) {
        return this.execute(bt.a(string, false));
    }

    @Override
    public final boolean execute(Tuple<String, List<ICommandArgument>> object) {
        if ((object = this.a((Tuple<String, List<ICommandArgument>>)object)) != null) {
            Object object2 = object;
            try {
                object2.a.execute(object2.a, object2.a);
            }
            catch (Throwable throwable) {
                Throwable throwable2 = throwable;
                (throwable instanceof ICommandException ? (ICommandException)((Object)throwable2) : new CommandUnhandledException(throwable2)).handle(object2.a, object2.a.getArgs());
            }
        }
        return object != null;
    }

    @Override
    public final Stream<String> tabComplete(Tuple<String, List<ICommandArgument>> object) {
        if ((object = this.a((Tuple<String, List<ICommandArgument>>)object)) == null) {
            return Stream.empty();
        }
        return ((a)object).a();
    }

    @Override
    public final Stream<String> tabComplete(String tuple) {
        tuple = bt.a((String)tuple, true);
        String string = (String)tuple.getA();
        if (((List)tuple.getB()).isEmpty()) {
            return new TabCompleteHelper().addCommands(this.a.a).filterPrefix(string).stream();
        }
        return this.tabComplete(tuple);
    }

    private a a(Tuple<String, List<ICommandArgument>> object) {
        String string = (String)object.getA();
        object = new z(this, (List)object.getB());
        ICommand iCommand = this.getCommand(string);
        if (iCommand == null) {
            return null;
        }
        return new a(iCommand, string, (z)object);
    }

    private static Tuple<String, List<ICommandArgument>> a(String object, boolean bl2) {
        String string = ((String)object).split("\\s", 2)[0];
        object = ab.a(((String)object).substring(string.length()), bl2);
        return new Tuple((Object)string, object);
    }

    public static Tuple<String, List<ICommandArgument>> a(String string) {
        return bt.a(string, false);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class a {
        ICommand a;
        String a;
        z a;

        a(ICommand iCommand, String string, z z2) {
            this.a = iCommand;
            this.a = string;
            this.a = z2;
        }

        final Stream<String> a() {
            try {
                return this.a.tabComplete(this.a, this.a);
            }
            catch (CommandException commandException) {
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return Stream.empty();
        }
    }
}

