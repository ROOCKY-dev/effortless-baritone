/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Registry<V> {
    private final Deque<V> _entries = new LinkedList<V>();
    private final Set<V> registered = new HashSet<V>();
    public final Collection<V> entries = Collections.unmodifiableCollection(this._entries);

    public boolean registered(V v2) {
        return this.registered.contains(v2);
    }

    public boolean register(V v2) {
        if (!this.registered(v2)) {
            this._entries.addFirst(v2);
            this.registered.add(v2);
            return true;
        }
        return false;
    }

    public void unregister(V v2) {
        if (!this.registered(v2)) {
            return;
        }
        this._entries.remove(v2);
        this.registered.remove(v2);
    }

    public Iterator<V> iterator() {
        return this._entries.iterator();
    }

    public Iterator<V> descendingIterator() {
        return this._entries.descendingIterator();
    }

    public Stream<V> stream() {
        return this._entries.stream();
    }

    public Stream<V> descendingStream() {
        return StreamSupport.stream(Spliterators.spliterator(this.descendingIterator(), (long)this._entries.size(), 16448), false);
    }
}

