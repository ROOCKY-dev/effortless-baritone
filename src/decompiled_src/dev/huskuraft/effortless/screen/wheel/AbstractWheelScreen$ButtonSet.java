/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.screen.wheel.AbstractWheelScreen;
import java.util.List;

public static interface AbstractWheelScreen.ButtonSet<T> {
    public Text getDisplayName();

    public List<? extends AbstractWheelScreen.Button<T>> getButtons();
}
