/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.argument;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.datatypes.IDatatype;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.datatypes.IDatatypePost;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

public interface IArgConsumer {
    public LinkedList<ICommandArgument> getArgs();

    public Deque<ICommandArgument> getConsumed();

    public boolean has(int var1);

    public boolean hasAny();

    public boolean hasAtMost(int var1);

    public boolean hasAtMostOne();

    public boolean hasExactly(int var1);

    public boolean hasExactlyOne();

    public ICommandArgument peek(int var1);

    public ICommandArgument peek();

    public boolean is(Class<?> var1, int var2);

    public boolean is(Class<?> var1);

    public String peekString(int var1);

    public String peekString();

    public <E extends Enum<?>> E peekEnum(Class<E> var1, int var2);

    public <E extends Enum<?>> E peekEnum(Class<E> var1);

    public <E extends Enum<?>> E peekEnumOrNull(Class<E> var1, int var2);

    public <E extends Enum<?>> E peekEnumOrNull(Class<E> var1);

    public <T> T peekAs(Class<T> var1, int var2);

    public <T> T peekAs(Class<T> var1);

    public <T> T peekAsOrDefault(Class<T> var1, T var2, int var3);

    public <T> T peekAsOrDefault(Class<T> var1, T var2);

    public <T> T peekAsOrNull(Class<T> var1, int var2);

    public <T> T peekAsOrNull(Class<T> var1);

    public <T> T peekDatatype(IDatatypeFor<T> var1);

    public <T, O> T peekDatatype(IDatatypePost<T, O> var1);

    public <T, O> T peekDatatype(IDatatypePost<T, O> var1, O var2);

    public <T> T peekDatatypeOrNull(IDatatypeFor<T> var1);

    public <T, O> T peekDatatypeOrNull(IDatatypePost<T, O> var1);

    public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(D var1, O var2);

    public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(D var1, O var2, T var3);

    public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(D var1, O var2);

    public <T, D extends IDatatypeFor<T>> T peekDatatypeFor(Class<D> var1);

    public <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(Class<D> var1, T var2);

    public <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(Class<D> var1);

    public ICommandArgument get();

    public String getString();

    public <E extends Enum<?>> E getEnum(Class<E> var1);

    public <E extends Enum<?>> E getEnumOrDefault(Class<E> var1, E var2);

    public <E extends Enum<?>> E getEnumOrNull(Class<E> var1);

    public <T> T getAs(Class<T> var1);

    public <T> T getAsOrDefault(Class<T> var1, T var2);

    public <T> T getAsOrNull(Class<T> var1);

    public <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(D var1, O var2);

    public <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(D var1, O var2, T var3);

    public <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(D var1, O var2);

    public <T, D extends IDatatypeFor<T>> T getDatatypeFor(D var1);

    public <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(D var1, T var2);

    public <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(D var1);

    public <T extends IDatatype> Stream<String> tabCompleteDatatype(T var1);

    public String rawRest();

    public void requireMin(int var1);

    public void requireMax(int var1);

    public void requireExactly(int var1);

    public boolean hasConsumed();

    public ICommandArgument consumed();

    public String consumedString();

    public IArgConsumer copy();
}

