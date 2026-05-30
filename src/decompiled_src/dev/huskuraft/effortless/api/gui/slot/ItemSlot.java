/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.slot;

import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.gui.slot.Slot;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;

public class ItemSlot
extends Slot {
    private ItemStack itemStack;

    public ItemSlot(Entrance entrance, int x, int y, int width, int height, Item item, Text message) {
        super(entrance, x, y, width, height, message);
        this.itemStack = item.getDefaultStack();
    }

    public ItemSlot(Entrance entrance, int x, int y, int width, int height, ItemStack itemStack, Text message) {
        super(entrance, x, y, width, height, message);
        this.itemStack = itemStack;
    }

    @Override
    public int getFullWidth() {
        return this.getWidth();
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        renderer.renderRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -1620284308);
        renderer.renderItem(this.getTypeface(), this.itemStack, this.getX(), this.getY(), this.getMessage());
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setDescription(Text description) {
        this.setMessage(description);
    }

    private int getBlitOffset() {
        return 0;
    }
}
