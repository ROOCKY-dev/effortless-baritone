/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.screen.wheel.AbstractWheelScreen;
import java.awt.Color;
import java.util.List;

static class AbstractWheelScreen.2
implements AbstractWheelScreen.Button<T> {
    final /* synthetic */ Object val$id;
    final /* synthetic */ Text val$name;
    final /* synthetic */ Text val$category;
    final /* synthetic */ Text val$summary;
    final /* synthetic */ List val$description;
    final /* synthetic */ ResourceLocation val$icon;
    final /* synthetic */ Object val$content;
    final /* synthetic */ boolean val$activated;

    AbstractWheelScreen.2(Object object, Text text, Text text2, Text text3, List list, ResourceLocation resourceLocation, Object object2, boolean bl) {
        this.val$id = object;
        this.val$name = text;
        this.val$category = text2;
        this.val$summary = text3;
        this.val$description = list;
        this.val$icon = resourceLocation;
        this.val$content = object2;
        this.val$activated = bl;
    }

    @Override
    public Object getId() {
        return this.val$id;
    }

    @Override
    public Text getName() {
        return this.val$name;
    }

    @Override
    public Text getCategory() {
        return this.val$category;
    }

    @Override
    public Text getSummary() {
        return this.val$summary;
    }

    @Override
    public List<Text> getDescriptions() {
        return this.val$description;
    }

    @Override
    public ResourceLocation getIcon() {
        return this.val$icon;
    }

    @Override
    public Color getTintColor() {
        return null;
    }

    @Override
    public T getContent() {
        return this.val$content;
    }

    @Override
    public boolean isActivated() {
        return this.val$activated;
    }
}
