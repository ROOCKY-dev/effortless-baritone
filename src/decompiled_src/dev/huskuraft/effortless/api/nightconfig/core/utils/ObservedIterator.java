/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import dev.huskuraft.effortless.api.nightconfig.core.utils.AbstractObserved;
import java.util.Iterator;
import java.util.function.Consumer;

public final class ObservedIterator<E>
extends AbstractObserved
implements Iterator<E> {
    private final Iterator<E> iterator;

    public ObservedIterator(Iterator<E> iterator, Runnable callback) {
        super(callback);
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public E next() {
        return this.iterator.next();
    }

    @Override
    public void remove() {
        this.iterator.remove();
        this.callback.run();
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        this.iterator.forEachRemaining(action);
    }

    public boolean equals(Object obj) {
        return this.iterator.equals(obj);
    }

    public int hashCode() {
        return this.iterator.hashCode();
    }
}
