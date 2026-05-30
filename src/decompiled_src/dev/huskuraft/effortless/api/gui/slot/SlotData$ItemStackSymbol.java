/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.slot;

import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.gui.slot.SlotData;
import dev.huskuraft.effortless.api.text.Text;

public record SlotData.ItemStackSymbol(ItemStack itemStack, Text symbol) implements SlotData
{
}
