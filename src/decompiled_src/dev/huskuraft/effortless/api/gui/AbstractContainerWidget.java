/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.gui.ContainerWidget;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public abstract class AbstractContainerWidget
extends AbstractWidget
implements ContainerWidget {
    private final List<AbstractWidget> children = new ArrayList<AbstractWidget>();
    private boolean isDragging;
    @Nullable
    private AbstractWidget focused;
    @Nullable
    private AbstractWidget hovered;

    protected AbstractContainerWidget(Entrance entrance, int x, int y, int width, int height, Text message) {
        super(entrance, x, y, width, height, message);
    }

    protected AbstractContainerWidget(Entrance entrance, Text message) {
        super(entrance, message);
    }

    protected AbstractContainerWidget(Entrance entrance) {
        super(entrance);
    }

    @Override
    public void onTick() {
        for (AbstractWidget abstractWidget : this.children()) {
            abstractWidget.onTick();
        }
    }

    @Override
    public final boolean isDragging() {
        return this.isDragging;
    }

    @Override
    public final void setDragging(boolean dragging) {
        this.isDragging = dragging;
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        for (AbstractWidget abstractWidget : this.children()) {
            abstractWidget.render(renderer, mouseX, mouseY, deltaTick);
        }
    }

    @Override
    public void renderWidgetOverlay(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidgetOverlay(renderer, mouseX, mouseY, deltaTick);
        for (AbstractWidget abstractWidget : this.children()) {
            abstractWidget.renderOverlay(renderer, mouseX, mouseY, deltaTick);
        }
    }

    public List<? extends AbstractWidget> children() {
        return this.children;
    }

    @Override
    public AbstractWidget getWidget(int i) {
        return this.children().get(i);
    }

    public <T extends AbstractWidget> T addWidget(T widget) {
        widget.setParent(this);
        this.children.add(widget);
        return widget;
    }

    public void removeWidget(AbstractWidget widget) {
        widget.setParent(null);
        this.children().remove(widget);
    }

    public void clearWidgets() {
        this.children().forEach(widget -> widget.setParent(null));
        this.children().clear();
    }

    @Override
    public void onDestroy() {
        this.clearWidgets();
    }

    @Override
    public AbstractWidget getSelected() {
        return this.focused;
    }

    public void setSelected(@Nullable AbstractWidget widget) {
        this.focused = widget;
    }

    @Override
    @Nullable
    public AbstractWidget getFocused() {
        return this.focused;
    }

    protected void setFocused(@Nullable AbstractWidget widget) {
        if (this.focused != null) {
            this.focused.setFocused(false);
        }
        this.focused = widget;
        if (this.focused != null) {
            this.focused.setFocused(true);
        }
    }

    @Override
    public AbstractWidget getHovered() {
        return this.hovered;
    }

    protected void setHovered(@Nullable AbstractWidget widget) {
        this.hovered = widget;
    }

    @Override
    public AbstractWidget getWidgetAt(double mouseX, double mouseY) {
        AbstractWidget target;
        Iterator<? extends AbstractWidget> var5 = this.children().iterator();
        do {
            if (var5.hasNext()) continue;
            return null;
        } while (!(target = var5.next()).isMouseOver(mouseX, mouseY));
        return target;
    }

    @Override
    public void moveX(int x) {
        super.moveX(x);
        for (AbstractWidget abstractWidget : this.children()) {
            abstractWidget.moveX(x);
        }
    }

    @Override
    public void moveY(int y) {
        super.moveY(y);
        for (AbstractWidget abstractWidget : this.children()) {
            abstractWidget.moveY(y);
        }
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        boolean mouseOver = super.onMouseClicked(mouseX, mouseY, button);
        AbstractWidget target = null;
        List<? extends AbstractWidget> list = List.copyOf(this.children());
        for (AbstractWidget abstractWidget : list) {
            if (!abstractWidget.onMouseClicked(mouseX, mouseY, button) || !abstractWidget.isMouseOver(mouseX, mouseY)) continue;
            target = abstractWidget;
        }
        if (target != null && mouseOver) {
            this.setFocused(target);
            if (button == 0) {
                this.setDragging(true);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseReleased(double mouseX, double mouseY, int button) {
        this.setDragging(false);
        AbstractWidget widget = this.getWidgetAt(mouseX, mouseY);
        return widget != null && widget.onMouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return this.getFocused() != null && this.isDragging() && button == 0 && this.getFocused().onMouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean onMouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        AbstractWidget widget = this.getWidgetAt(mouseX, mouseY);
        return widget != null && widget.onMouseScrolled(mouseX, mouseY, amountX, amountY);
    }

    @Override
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && this.getFocused().onKeyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean onKeyReleased(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && this.getFocused().onKeyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean onCharTyped(char character, int modifiers) {
        return this.getFocused() != null && this.getFocused().onCharTyped(character, modifiers);
    }

    @Override
    public boolean onFocusMove(boolean forwards) {
        boolean hasFocus;
        AbstractWidget focused = this.getFocused();
        boolean bl = hasFocus = focused != null;
        if (!hasFocus || !focused.onFocusMove(forwards)) {
            List<? extends AbstractWidget> list = this.children();
            int i = list.indexOf(focused);
            int j = hasFocus && i >= 0 ? i + (forwards ? 1 : 0) : (forwards ? 0 : list.size());
            ListIterator<? extends AbstractWidget> listIterator = list.listIterator(j);
            BooleanSupplier hasValueSupplier = null;
            hasValueSupplier = forwards ? listIterator::hasNext : listIterator::hasPrevious;
            Supplier<Object> valueSupplier = null;
            if (forwards) {
                Objects.requireNonNull(listIterator);
                valueSupplier = listIterator::next;
            } else {
                Objects.requireNonNull(listIterator);
                valueSupplier = listIterator::previous;
            }
            AbstractWidget listener = null;
            do {
                if (hasValueSupplier.getAsBoolean()) continue;
                this.setFocused(null);
                return false;
            } while (!(listener = (AbstractWidget)valueSupplier.get()).onFocusMove(forwards));
            this.setFocused(listener);
        }
        return true;
    }
}
