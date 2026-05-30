/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.screen.wheel.AbstractWheelScreen;
import java.util.List;

static class AbstractWheelScreen.3
implements AbstractWheelScreen.ButtonSet<T> {
    final /* synthetic */ List val$entries;

    AbstractWheelScreen.3(List list) {
        this.val$entries = list;
    }

    @Override
    public Text getDisplayName() {
        return null;
    }

    @Override
    public List<? extends AbstractWheelScreen.Button<T>> getButtons() {
        return this.val$entries;
    }
}
