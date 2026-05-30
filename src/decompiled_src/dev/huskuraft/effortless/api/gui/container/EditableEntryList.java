/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.EntryList;
import dev.huskuraft.effortless.api.gui.Widget;
import dev.huskuraft.effortless.api.gui.container.AbstractEntryList;
import dev.huskuraft.effortless.api.platform.Entrance;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class EditableEntryList<T>
extends AbstractEntryList<Entry<T>> {
    protected EditableEntryList(Entrance entrance, int x, int y, int width, int height) {
        super(entrance, x, y, width, height);
    }

    protected abstract Entry<T> createHolder(T var1);

    public boolean hasSelected() {
        return this.getSelected() != null;
    }

    public void insertSelected(T item) {
        Entry<T> entry = this.createHolder(item);
        int index = this.getSelected() == null ? this.children().size() : this.children().indexOf(this.getSelected()) + 1;
        this.addEntry(index, entry);
        this.setSelected(entry);
    }

    public void replaceSelect(T item) {
        ((Entry)this.getSelected()).setItem(item);
    }

    public void deleteSelected() {
        Entry selected = (Entry)this.getSelected();
        int index = this.children().indexOf(selected);
        this.removeEntry(selected);
        if (index >= 0 && index < this.children().size()) {
            this.setSelected((Entry)this.children().get(index));
        }
    }

    public Entry<T> get(int i) {
        return (Entry)this.children().get(i);
    }

    public int indexOf(Object entry) {
        return this.children().indexOf(entry);
    }

    public int indexOfSelected() {
        return this.children().indexOf(this.getSelected());
    }

    public void moveUp(int i) {
        this.swap(i, i - 1);
    }

    public void moveDown(int i) {
        this.swap(i, i + 1);
    }

    public void moveUpSelected() {
        this.moveUp(this.indexOfSelected());
    }

    public void moveDownSelected() {
        this.moveDown(this.indexOfSelected());
    }

    @Override
    public void moveUp(Widget widget) {
        int index = this.indexOf(widget);
        if (index > 0) {
            this.moveUp(index);
            this.setScrollAmountNoClamp(this.getScrollAmount() - (double)((Entry)this.getOrThrow(index)).getHeight());
        }
    }

    @Override
    public void moveDown(Widget widget) {
        int index = this.indexOf(widget);
        if (index < this.children().size() - 1) {
            this.moveDown(index);
            this.setScrollAmountNoClamp(this.getScrollAmount() + (double)((Entry)this.getOrThrow(index)).getHeight());
        }
    }

    @Override
    public void moveUpNoClamp(Widget widget) {
        int index = this.indexOf(widget);
        if (index > 0) {
            this.moveUp(this.indexOf(widget));
            this.setScrollAmountNoClamp(this.getScrollAmount() - (double)((Entry)this.getOrThrow(index)).getHeight());
        }
    }

    @Override
    public void moveDownNoClamp(Widget widget) {
        int index = this.indexOf(widget);
        if (index < this.children().size() - 1) {
            this.moveDown(this.indexOf(widget));
            this.setScrollAmountNoClamp(this.getScrollAmount() + (double)((Entry)this.getOrThrow(index)).getHeight());
        }
    }

    public void reset(Collection<? extends T> items) {
        int i = this.indexOf(this.getSelected());
        this.clearEntries();
        for (T item : items) {
            this.addEntry(this.createHolder(item));
        }
        List list = this.children();
        if (i >= 0 && i < list.size()) {
            this.setSelected((Entry)list.get(i));
        }
    }

    public void clear() {
        this.reset(Collections.emptyList());
    }

    public List<T> items() {
        return this.children().stream().map(entry -> entry.item).toList();
    }

    public static abstract class Entry<T>
    extends AbstractEntryList.Entry {
        private final EntryList entryList;
        protected T item;

        protected Entry(Entrance entrance, T item) {
            this(entrance, null, item);
        }

        protected Entry(Entrance entrance, EntryList entryList, T item) {
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
}
