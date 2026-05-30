/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.Widget;

public static interface EntryList.Entry
extends Widget {
    public void onPositionChange(int var1, int var2);

    public void onSelected();

    public void onDeselected();
}
