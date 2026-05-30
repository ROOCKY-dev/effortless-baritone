/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.Widget;
import java.util.List;

public interface ContainerWidget
extends Widget {
    public boolean isDragging();

    public void setDragging(boolean var1);

    public List<? extends Widget> children();

    public Widget getFocused();

    public Widget getSelected();

    public Widget getHovered();

    public Widget getWidget(int var1);

    public Widget getWidgetAt(double var1, double var3);

    default public void recreateChildren() {
        for (Widget widget : this.children()) {
            widget.recreate();
        }
    }
}
