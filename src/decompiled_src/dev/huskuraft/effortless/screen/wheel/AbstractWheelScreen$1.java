/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.screen.wheel.AbstractWheelScreen;
import java.awt.Color;

static class AbstractWheelScreen.1
implements AbstractWheelScreen.Slot<T> {
    final /* synthetic */ Object val$id;
    final /* synthetic */ Text val$name;
    final /* synthetic */ ResourceLocation val$icon;
    final /* synthetic */ Color val$tintColor;
    final /* synthetic */ Object val$content;

    AbstractWheelScreen.1(Object object, Text text, ResourceLocation resourceLocation, Color color, Object object2) {
        this.val$id = object;
        this.val$name = text;
        this.val$icon = resourceLocation;
        this.val$tintColor = color;
        this.val$content = object2;
    }

    @Override
    public Object getId() {
        return this.val$id;
    }

    @Override
    public Text getDisplayName() {
        return this.val$name;
    }

    @Override
    public Text getDisplayCategory() {
        return null;
    }

    @Override
    public ResourceLocation getIcon() {
        return this.val$icon;
    }

    @Override
    public Color getTintColor() {
        return this.val$tintColor;
    }

    @Override
    public T getContent() {
        return this.val$content;
    }
}
