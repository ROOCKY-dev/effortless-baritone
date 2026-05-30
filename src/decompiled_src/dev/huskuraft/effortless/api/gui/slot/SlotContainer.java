/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.slot;

import dev.huskuraft.effortless.api.gui.AbstractContainerWidget;
import dev.huskuraft.effortless.api.gui.slot.ItemSlot;
import dev.huskuraft.effortless.api.gui.slot.Slot;
import dev.huskuraft.effortless.api.gui.slot.SlotData;
import dev.huskuraft.effortless.api.gui.slot.TextSlot;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.text.Text;
import java.util.List;

public class SlotContainer
extends AbstractContainerWidget {
    private boolean wrapLines = false;

    public SlotContainer(Entrance entrance, int x, int y, int width, int height) {
        super(entrance, x, y, width, height, Text.empty());
    }

    public void setEntries(List<SlotData> entries) {
        this.clearWidgets();
        int width = 0;
        for (SlotData entry : entries) {
            Slot slot;
            if (entry instanceof SlotData.TextSymbol) {
                SlotData.TextSymbol textEntry = (SlotData.TextSymbol)entry;
                slot = this.addWidget(new TextSlot((Entrance)this.getEntrance(), this.getX() + width, this.getY(), 18, 18, textEntry.text(), textEntry.symbol()));
                width += ((TextSlot)slot).getFullWidth() + 2;
                continue;
            }
            if (!(entry instanceof SlotData.ItemStackSymbol)) continue;
            SlotData.ItemStackSymbol itemEntry = (SlotData.ItemStackSymbol)entry;
            slot = this.addWidget(new ItemSlot((Entrance)this.getEntrance(), this.getX() + width, this.getY(), 18, 18, itemEntry.itemStack(), itemEntry.symbol()));
            width += ((ItemSlot)slot).getFullWidth() + 2;
        }
    }

    public void setWrapLines(boolean wrapLines) {
        this.wrapLines = wrapLines;
    }
}
