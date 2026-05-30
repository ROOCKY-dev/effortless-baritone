/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.Widget;
import dev.huskuraft.effortless.api.gui.container.AbstractEntryList;
import dev.huskuraft.effortless.api.platform.Entrance;
import java.util.Comparator;
import java.util.function.Consumer;

public class SimpleEntryList
extends AbstractEntryList<Entry> {
    public SimpleEntryList(Entrance entrance, int x, int y, int width, int height) {
        super(entrance, x, y, width, height);
    }

    public Entry addSimpleEntry(final Consumer<Entry> onCreate) {
        return this.addEntry(new Entry(this, this.getEntrance()){

            @Override
            public void onCreate() {
                onCreate.accept(this);
            }
        });
    }

    public static class Entry
    extends AbstractEntryList.Entry {
        protected Entry(Entrance entrance) {
            super(entrance);
        }

        @Override
        public int getHeight() {
            return this.children().stream().map(Widget::getBottom).max(Comparator.naturalOrder()).orElse(0) - this.children().stream().map(Widget::getTop).min(Comparator.naturalOrder()).orElse(0) + 4;
        }
    }
}
