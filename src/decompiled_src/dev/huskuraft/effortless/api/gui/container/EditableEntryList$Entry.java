/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.EntryList;
import dev.huskuraft.effortless.api.gui.container.AbstractEntryList;
import dev.huskuraft.effortless.api.platform.Entrance;

public static abstract class EditableEntryList.Entry<T>
extends AbstractEntryList.Entry {
    private final EntryList entryList;
    protected T item;

    protected EditableEntryList.Entry(Entrance entrance, T item) {
        this(entrance, null, item);
    }

    protected EditableEntryList.Entry(Entrance entrance, EntryList entryList, T item) {
        super(entrance);
        this.entryList = entryList;
        this.item = item;
    }

    public T getItem() {
        return this.item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public EntryList getEntryList() {
        return this.entryList;
    }
}
