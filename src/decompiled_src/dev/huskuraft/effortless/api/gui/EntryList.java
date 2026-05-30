/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.Widget;

public interface EntryList
extends Widget {
    public void moveUp(Widget var1);

    public void moveDown(Widget var1);

    public void moveUpNoClamp(Widget var1);

    public void moveDownNoClamp(Widget var1);

    public boolean isEditable();

    public Entry getSelected();

    public static interface Entry
    extends Widget {
        public void onPositionChange(int var1, int var2);

        public void onSelected();

        public void onDeselected();
    }
}
