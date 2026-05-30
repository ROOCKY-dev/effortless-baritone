/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.Widget;
import dev.huskuraft.effortless.api.gui.container.AbstractEntryList;
import dev.huskuraft.effortless.api.platform.Entrance;
import java.util.Comparator;

public static class SimpleEntryList.Entry
extends AbstractEntryList.Entry {
    protected SimpleEntryList.Entry(Entrance entrance) {
        super(entrance);
    }

    @Override
    public int getHeight() {
        return this.children().stream().map(Widget::getBottom).max(Comparator.naturalOrder()).orElse(0) - this.children().stream().map(Widget::getTop).min(Comparator.naturalOrder()).orElse(0) + 4;
    }
}
