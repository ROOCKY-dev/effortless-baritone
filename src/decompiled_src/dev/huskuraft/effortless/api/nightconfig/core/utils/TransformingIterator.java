/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransformingIterator<InternalV, ExternalV>
implements Iterator<ExternalV> {
    protected final Function<? super InternalV, ? extends ExternalV> readTransformation;
    protected final Iterator<InternalV> internalIterator;

    public TransformingIterator(Iterator<InternalV> internalIterator, Function<? super InternalV, ? extends ExternalV> readTransformation) {
        this.readTransformation = readTransformation;
        this.internalIterator = internalIterator;
    }

    @Override
    public boolean hasNext() {
        return this.internalIterator.hasNext();
    }

    @Override
    public ExternalV next() {
        return this.readTransformation.apply(this.internalIterator.next());
    }

    @Override
    public void remove() {
        this.internalIterator.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super ExternalV> action) {
        this.internalIterator.forEachRemaining((? super E internalV) -> action.accept((ExternalV)this.readTransformation.apply(internalV)));
    }

    public int hashCode() {
        return this.internalIterator.hashCode();
    }

    public boolean equals(Object obj) {
        return this.internalIterator.equals(obj);
    }

    public String toString() {
        return this.internalIterator.toString();
    }
}
