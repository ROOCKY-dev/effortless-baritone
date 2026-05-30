/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.x;
import java.util.stream.Stream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class aa
implements ICommandArgument {
    private final int a;
    private final String a;
    private final String b;

    aa(int n2, String string, String string2) {
        this.a = n2;
        this.a = string;
        this.b = string2;
    }

    @Override
    public final int getIndex() {
        return this.a;
    }

    @Override
    public final String getValue() {
        return this.a;
    }

    @Override
    public final String getRawRest() {
        return this.b;
    }

    @Override
    public final <E extends Enum<?>> E getEnum(Class<E> clazz) {
        return (E)Stream.of((Enum[])clazz.getEnumConstants()).filter(enum_ -> enum_.name().equalsIgnoreCase(this.a)).findFirst().orElseThrow(() -> new CommandInvalidTypeException(this, clazz.getSimpleName()));
    }

    @Override
    public final <T> T getAs(Class<T> clazz) {
        return x.a.parseStateless(clazz, this);
    }

    @Override
    public final <T> boolean is(Class<T> clazz) {
        try {
            this.getAs(clazz);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    @Override
    public final <T, S> T getAs(Class<T> clazz, Class<S> clazz2, S s2) {
        return x.a.parseStated(clazz, clazz2, this, s2);
    }

    @Override
    public final <T, S> boolean is(Class<T> clazz, Class<S> clazz2, S s2) {
        try {
            this.getAs(clazz, clazz2, s2);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }
}

