/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.command.argparser.IArgParser;
import baritone.api.command.argparser.IArgParserManager;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.exception.CommandNoParserForTypeException;
import baritone.api.command.registry.Registry;
import baritone.y;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class x
extends Enum<x>
implements IArgParserManager {
    public static final /* enum */ x a = new x("INSTANCE");
    private Registry<IArgParser> a = new Registry();
    private static final /* synthetic */ x[] a;

    public static x[] values() {
        return (x[])a.clone();
    }

    public static x valueOf(String string) {
        return Enum.valueOf(x.class, string);
    }

    private x() {
        y.a.forEach(((Registry)((Object)this.a))::register);
    }

    @Override
    public final <T> IArgParser.Stateless<T> getParserStateless(Class<T> clazz) {
        return ((Registry)((Object)this.a)).descendingStream().filter(IArgParser.Stateless.class::isInstance).map(IArgParser.Stateless.class::cast).filter(stateless -> stateless.getTarget().isAssignableFrom(clazz)).findFirst().orElse(null);
    }

    @Override
    public final <T, S> IArgParser.Stated<T, S> getParserStated(Class<T> clazz, Class<S> clazz2) {
        return ((Registry)((Object)this.a)).descendingStream().filter(IArgParser.Stated.class::isInstance).map(IArgParser.Stated.class::cast).filter(stated -> stated.getTarget().isAssignableFrom(clazz)).filter(stated -> stated.getStateType().isAssignableFrom(clazz2)).map(IArgParser.Stated.class::cast).findFirst().orElse(null);
    }

    @Override
    public final <T> T parseStateless(Class<T> clazz, ICommandArgument iCommandArgument) {
        IArgParser.Stateless<T> stateless = this.getParserStateless(clazz);
        if (stateless == null) {
            throw new CommandNoParserForTypeException(clazz);
        }
        try {
            return stateless.parseArg(iCommandArgument);
        }
        catch (Exception exception) {
            throw new CommandInvalidTypeException(iCommandArgument, clazz.getSimpleName());
        }
    }

    @Override
    public final <T, S> T parseStated(Class<T> clazz, Class<S> object, ICommandArgument iCommandArgument, S s2) {
        if ((object = this.getParserStated(clazz, (Class<S>)object)) == null) {
            throw new CommandNoParserForTypeException(clazz);
        }
        try {
            return object.parseArg(iCommandArgument, s2);
        }
        catch (Exception exception) {
            throw new CommandInvalidTypeException(iCommandArgument, clazz.getSimpleName());
        }
    }

    @Override
    public final Registry<IArgParser> getRegistry() {
        return this.a;
    }

    static {
        a = new x[]{a};
    }
}

