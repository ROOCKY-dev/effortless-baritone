/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.slot;

import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.text.Text;

public sealed interface SlotData {

    public record ItemStackSymbol(ItemStack itemStack, Text symbol) implements SlotData
    {
    }

    public record TextSymbol(Text text, Text symbol) implements SlotData
    {
        public TextSymbol(String text, String symbol) {
            this(Text.text(text), Text.text(symbol));
        }

        public TextSymbol(String text, Text symbol) {
            this(Text.text(text), symbol);
        }

        public TextSymbol(Text text, String symbol) {
            this(text, Text.text(symbol));
        }
    }
}
