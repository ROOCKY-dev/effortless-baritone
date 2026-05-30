/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.slot;

import dev.huskuraft.effortless.api.gui.slot.SlotData;
import dev.huskuraft.effortless.api.text.Text;

public record SlotData.TextSymbol(Text text, Text symbol) implements SlotData
{
    public SlotData.TextSymbol(String text, String symbol) {
        this(Text.text(text), Text.text(symbol));
    }

    public SlotData.TextSymbol(String text, Text symbol) {
        this(Text.text(text), symbol);
    }

    public SlotData.TextSymbol(Text text, String symbol) {
        this(text, Text.text(symbol));
    }
}
