/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.ab;
import baritone.api.IBaritone;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.datatypes.IDatatype;
import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.exception.CommandException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.exception.CommandNotEnoughArgumentsException;
import baritone.api.command.exception.CommandTooManyArgumentsException;
import baritone.api.command.manager.ICommandManager;
import baritone.bt;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class z
implements IArgConsumer {
    final ICommandManager a;
    private final a a;
    private final LinkedList<ICommandArgument> a;
    private final Deque<ICommandArgument> a;

    private z(ICommandManager iCommandManager, Deque<ICommandArgument> deque, Deque<ICommandArgument> deque2) {
        this.a = iCommandManager;
        this.a = new a(this);
        this.a = new LinkedList<ICommandArgument>(deque);
        this.a = new LinkedList<ICommandArgument>(deque2);
    }

    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    public z(bt bt2, List list) {
        this(bt2, new LinkedList<ICommandArgument>(list), new LinkedList<ICommandArgument>());
    }

    @Override
    public final LinkedList<ICommandArgument> getArgs() {
        return this.a;
    }

    @Override
    public final Deque<ICommandArgument> getConsumed() {
        return this.a;
    }

    @Override
    public final boolean has(int n2) {
        return ((LinkedList)((Object)this.a)).size() >= n2;
    }

    @Override
    public final boolean hasAny() {
        return this.has(1);
    }

    @Override
    public final boolean hasAtMost(int n2) {
        return ((LinkedList)((Object)this.a)).size() <= n2;
    }

    @Override
    public final boolean hasAtMostOne() {
        return this.hasAtMost(1);
    }

    @Override
    public final boolean hasExactly(int n2) {
        return ((LinkedList)((Object)this.a)).size() == n2;
    }

    @Override
    public final boolean hasExactlyOne() {
        return this.hasExactly(1);
    }

    @Override
    public final ICommandArgument peek(int n2) {
        this.requireMin(n2 + 1);
        return (ICommandArgument)((LinkedList)((Object)this.a)).get(n2);
    }

    @Override
    public final ICommandArgument peek() {
        return this.peek(0);
    }

    @Override
    public final boolean is(Class<?> clazz, int n2) {
        return this.peek(n2).is(clazz);
    }

    @Override
    public final boolean is(Class<?> clazz) {
        return this.is(clazz, 0);
    }

    @Override
    public final String peekString(int n2) {
        return this.peek(n2).getValue();
    }

    @Override
    public final String peekString() {
        return this.peekString(0);
    }

    @Override
    public final <E extends Enum<?>> E peekEnum(Class<E> clazz, int n2) {
        return this.peek(n2).getEnum(clazz);
    }

    @Override
    public final <E extends Enum<?>> E peekEnum(Class<E> clazz) {
        return this.peekEnum(clazz, 0);
    }

    @Override
    public final <E extends Enum<?>> E peekEnumOrNull(Class<E> clazz, int n2) {
        try {
            return this.peekEnum(clazz, n2);
        }
        catch (CommandInvalidTypeException commandInvalidTypeException) {
            return null;
        }
    }

    @Override
    public final <E extends Enum<?>> E peekEnumOrNull(Class<E> clazz) {
        return this.peekEnumOrNull(clazz, 0);
    }

    @Override
    public final <T> T peekAs(Class<T> clazz, int n2) {
        return this.peek(n2).getAs(clazz);
    }

    @Override
    public final <T> T peekAs(Class<T> clazz) {
        return this.peekAs(clazz, 0);
    }

    @Override
    public final <T> T peekAsOrDefault(Class<T> clazz, T t2, int n2) {
        try {
            return this.peekAs(clazz, n2);
        }
        catch (CommandInvalidTypeException commandInvalidTypeException) {
            return t2;
        }
    }

    @Override
    public final <T> T peekAsOrDefault(Class<T> clazz, T t2) {
        return this.peekAsOrDefault(clazz, t2, 0);
    }

    @Override
    public final <T> T peekAsOrNull(Class<T> clazz, int n2) {
        return this.peekAsOrDefault(clazz, null, n2);
    }

    @Override
    public final <T> T peekAsOrNull(Class<T> clazz) {
        return this.peekAsOrNull(clazz, 0);
    }

    @Override
    public final <T> T peekDatatype(IDatatypeFor<T> iDatatypeFor) {
        return this.a().getDatatypeFor(iDatatypeFor);
    }

    @Override
    public final <T, O> T peekDatatype(IDatatypePost<T, O> iDatatypePost) {
        return this.peekDatatype(iDatatypePost, null);
    }

    @Override
    public final <T, O> T peekDatatype(IDatatypePost<T, O> iDatatypePost, O o2) {
        return this.a().getDatatypePost(iDatatypePost, o2);
    }

    @Override
    public final <T> T peekDatatypeOrNull(IDatatypeFor<T> iDatatypeFor) {
        return this.a().getDatatypeForOrNull(iDatatypeFor);
    }

    @Override
    public final <T, O> T peekDatatypeOrNull(IDatatypePost<T, O> iDatatypePost) {
        return this.a().getDatatypePostOrNull(iDatatypePost, null);
    }

    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(D d2, O o2) {
        return this.a().getDatatypePost(d2, o2);
    }

    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(D d2, O o2, T t2) {
        return this.a().getDatatypePostOrDefault(d2, o2, t2);
    }

    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(D d2, O o2) {
        return this.peekDatatypePostOrDefault(d2, o2, null);
    }

    @Override
    public final <T, D extends IDatatypeFor<T>> T peekDatatypeFor(Class<D> clazz) {
        while (true) {
            z z2 = z2.a();
        }
    }

    @Override
    public final <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(Class<D> clazz, T t2) {
        while (true) {
            z z2 = z2.a();
        }
    }

    @Override
    public final <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(Class<D> clazz) {
        return this.peekDatatypeForOrDefault(clazz, null);
    }

    @Override
    public final ICommandArgument get() {
        this.requireMin(1);
        ICommandArgument iCommandArgument = (ICommandArgument)((LinkedList)((Object)this.a)).removeFirst();
        this.a.add(iCommandArgument);
        return iCommandArgument;
    }

    @Override
    public final String getString() {
        return this.get().getValue();
    }

    @Override
    public final <E extends Enum<?>> E getEnum(Class<E> clazz) {
        return this.get().getEnum(clazz);
    }

    @Override
    public final <E extends Enum<?>> E getEnumOrDefault(Class<E> clazz, E e2) {
        try {
            this.peekEnum(clazz);
            return this.getEnum(clazz);
        }
        catch (CommandInvalidTypeException commandInvalidTypeException) {
            return e2;
        }
    }

    @Override
    public final <E extends Enum<?>> E getEnumOrNull(Class<E> clazz) {
        return this.getEnumOrDefault(clazz, null);
    }

    @Override
    public final <T> T getAs(Class<T> clazz) {
        return this.get().getAs(clazz);
    }

    @Override
    public final <T> T getAsOrDefault(Class<T> clazz, T t2) {
        try {
            clazz = this.peek().getAs(clazz);
            this.get();
            return (T)clazz;
        }
        catch (CommandInvalidTypeException commandInvalidTypeException) {
            return t2;
        }
    }

    @Override
    public final <T> T getAsOrNull(Class<T> clazz) {
        return this.getAsOrDefault(clazz, null);
    }

    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(D d2, O o2) {
        try {
            return d2.apply(this.a, o2);
        }
        catch (Exception exception) {
            if (((Boolean)baritone.a.a().verboseCommandExceptions.value).booleanValue()) {
                exception.printStackTrace();
            }
            throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), d2.getClass().getSimpleName(), exception);
        }
    }

    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(D d2, O o2, T t2) {
        ArrayList arrayList = new ArrayList(this.a);
        ArrayList arrayList2 = new ArrayList(this.a);
        try {
            return this.getDatatypePost(d2, o2);
        }
        catch (Exception exception) {
            ((LinkedList)((Object)this.a)).clear();
            ((LinkedList)((Object)this.a)).addAll(arrayList);
            this.a.clear();
            this.a.addAll(arrayList2);
            return t2;
        }
    }

    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(D d2, O o2) {
        return this.getDatatypePostOrDefault(d2, o2, null);
    }

    @Override
    public final <T, D extends IDatatypeFor<T>> T getDatatypeFor(D d2) {
        try {
            return d2.get(this.a);
        }
        catch (Exception exception) {
            if (((Boolean)baritone.a.a().verboseCommandExceptions.value).booleanValue()) {
                exception.printStackTrace();
            }
            throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), d2.getClass().getSimpleName(), exception);
        }
    }

    @Override
    public final <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(D d2, T t2) {
        ArrayList arrayList = new ArrayList(this.a);
        ArrayList arrayList2 = new ArrayList(this.a);
        try {
            return this.getDatatypeFor(d2);
        }
        catch (Exception exception) {
            ((LinkedList)((Object)this.a)).clear();
            ((LinkedList)((Object)this.a)).addAll(arrayList);
            this.a.clear();
            this.a.addAll(arrayList2);
            return t2;
        }
    }

    @Override
    public final <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(D d2) {
        return this.getDatatypeForOrDefault(d2, null);
    }

    @Override
    public final <T extends IDatatype> Stream<String> tabCompleteDatatype(T t2) {
        try {
            return t2.tabComplete(this.a);
        }
        catch (CommandException commandException) {
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return Stream.empty();
    }

    @Override
    public final String rawRest() {
        if (((LinkedList)((Object)this.a)).size() > 0) {
            return ((ICommandArgument)((LinkedList)((Object)this.a)).getFirst()).getRawRest();
        }
        return "";
    }

    @Override
    public final void requireMin(int n2) {
        if (((LinkedList)((Object)this.a)).size() < n2) {
            throw new CommandNotEnoughArgumentsException(n2 + this.a.size());
        }
    }

    @Override
    public final void requireMax(int n2) {
        if (((LinkedList)((Object)this.a)).size() > n2) {
            throw new CommandTooManyArgumentsException(n2 + this.a.size());
        }
    }

    @Override
    public final void requireExactly(int n2) {
        this.requireMin(n2);
        this.requireMax(n2);
    }

    @Override
    public final boolean hasConsumed() {
        return !this.a.isEmpty();
    }

    @Override
    public final ICommandArgument consumed() {
        if (this.a.size() > 0) {
            return (ICommandArgument)this.a.getLast();
        }
        return ab.a();
    }

    @Override
    public final String consumedString() {
        return this.consumed().getValue();
    }

    private z a() {
        return new z(this.a, (Deque<ICommandArgument>)((Object)this.a), (Deque<ICommandArgument>)((Object)this.a));
    }

    @Override
    public final /* synthetic */ IArgConsumer copy() {
        return this.a();
    }

    final class a
    implements IDatatypeContext {
        private /* synthetic */ z a;

        a(z z2) {
            this.a = z2;
        }

        @Override
        public final IBaritone getBaritone() {
            return this.a.a.getBaritone();
        }
    }
}

