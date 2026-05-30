/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.argument;

public interface ICommandArgument {
    public int getIndex();

    public String getValue();

    public String getRawRest();

    public <E extends Enum<?>> E getEnum(Class<E> var1);

    public <T> T getAs(Class<T> var1);

    public <T> boolean is(Class<T> var1);

    public <T, S> T getAs(Class<T> var1, Class<S> var2, S var3);

    public <T, S> boolean is(Class<T> var1, Class<S> var2, S var3);
}

