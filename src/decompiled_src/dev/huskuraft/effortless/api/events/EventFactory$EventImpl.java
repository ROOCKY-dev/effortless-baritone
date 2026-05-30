/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

private static class EventFactory.EventImpl<T>
implements Event<T> {
    private final Function<List<T>, T> function;
    private final ArrayList<T> listeners;
    private T invoker = null;

    EventFactory.EventImpl(Function<List<T>, T> function) {
        this.function = function;
        this.listeners = new ArrayList();
    }

    @Override
    public T invoker() {
        if (this.invoker == null) {
            this.update();
        }
        return this.invoker;
    }

    @Override
    public void register(T listener) {
        this.listeners.add(listener);
        this.invoker = null;
    }

    @Override
    public void unregister(T listener) {
        this.listeners.remove(listener);
        this.listeners.trimToSize();
        this.invoker = null;
    }

    @Override
    public boolean isRegistered(T listener) {
        return this.listeners.contains(listener);
    }

    @Override
    public void clear() {
        this.listeners.clear();
        this.listeners.trimToSize();
        this.invoker = null;
    }

    public void update() {
        this.invoker = this.listeners.size() == 1 ? this.listeners.get(0) : this.function.apply(this.listeners);
    }
}
