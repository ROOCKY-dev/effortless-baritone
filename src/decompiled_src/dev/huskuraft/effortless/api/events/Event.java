/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

public interface Event<T> {
    public T invoker();

    public void register(T var1);

    public void unregister(T var1);

    public boolean isRegistered(T var1);

    public void clear();
}
