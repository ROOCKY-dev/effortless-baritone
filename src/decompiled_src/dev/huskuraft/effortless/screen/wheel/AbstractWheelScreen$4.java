/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.building.Option;
import dev.huskuraft.effortless.screen.wheel.AbstractWheelScreen;
import java.awt.Color;
import java.util.List;
import java.util.function.Supplier;

static class AbstractWheelScreen.4
implements AbstractWheelScreen.Button<T> {
    final /* synthetic */ Supplier val$supplier;

    AbstractWheelScreen.4(Supplier supplier) {
        this.val$supplier = supplier;
    }

    @Override
    public Object getId() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getId();
    }

    @Override
    public Text getName() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getName();
    }

    @Override
    public Text getCategory() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getCategory();
    }

    @Override
    public Text getSummary() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getSummary();
    }

    @Override
    public List<Text> getDescriptions() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getDescriptions();
    }

    @Override
    public ResourceLocation getIcon() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getIcon();
    }

    @Override
    public Color getTintColor() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).getTintColor();
    }

    @Override
    public T getContent() {
        return (Option)((AbstractWheelScreen.Button)this.val$supplier.get()).getContent();
    }

    @Override
    public boolean isActivated() {
        return ((AbstractWheelScreen.Button)this.val$supplier.get()).isActivated();
    }
}
