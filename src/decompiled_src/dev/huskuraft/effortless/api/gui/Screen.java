/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.ContainerWidget;
import dev.huskuraft.effortless.api.text.Text;

public interface Screen
extends ContainerWidget {
    public Text getScreenTitle();

    public void init(int var1, int var2);

    public void onAttach();

    public void onDetach();

    public boolean isPauseGame();

    public void attach();

    public void detach();
}
