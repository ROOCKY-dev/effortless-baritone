/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public interface Tuple
extends Iterable<Object> {
    public List<Object> asList();

    default public Object get(int index) {
        return this.asList().get(index);
    }

    @Override
    default public Iterator<Object> iterator() {
        return Collections.unmodifiableList(this.asList()).iterator();
    }

    default public int size() {
        return this.asList().size();
    }
}
