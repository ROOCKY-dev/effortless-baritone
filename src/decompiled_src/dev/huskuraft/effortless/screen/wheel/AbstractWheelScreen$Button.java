/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.text.Text;
import java.awt.Color;
import java.util.List;

public static interface AbstractWheelScreen.Button<T> {
    public Object getId();

    public Text getName();

    public Text getCategory();

    public Text getSummary();

    public List<Text> getDescriptions();

    public ResourceLocation getIcon();

    public Color getTintColor();

    public T getContent();

    public boolean isActivated();
}
