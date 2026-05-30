/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.AbstractContainerWidget;
import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.gui.EntryList;
import dev.huskuraft.effortless.api.gui.Widget;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.RenderLayers;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public abstract class AbstractEntryList<E extends Entry>
extends AbstractContainerWidget
implements EntryList {
    private static final int DEFAULT_VERTICAL_PADDING = 4;
    public static final int DOUBLE_CLICK_THRESHOLD = 250;
    protected boolean backgroundTransparent = false;
    protected boolean renderShadow = true;
    protected boolean renderSelection = true;
    protected boolean isAlwaysShowScrollbar = false;
    protected boolean isShowScrollBar = true;
    protected boolean scrolling;
    protected int x0;
    protected int x1;
    protected int y0;
    protected int y1;
    private double scrollAmount;
    private long lastClickTime = 0L;
    private int firstVisibleIndex = 0;
    private double firstVisibleOffset = 0.0;

    protected AbstractEntryList(Entrance entrance, int x, int y, int width, int height) {
        super(entrance, x, y, width, height, Text.empty());
        this.focusable = true;
        this.x0 = x;
        this.x1 = x + width;
        this.y0 = y;
        this.y1 = y + height;
    }

    public boolean isBackgroundTransparent() {
        return this.backgroundTransparent;
    }

    public void setBackgroundTransparent(boolean backgroundTransparent) {
        this.backgroundTransparent = backgroundTransparent;
    }

    @Nullable
    public E getHovered() {
        return (E)((Entry)super.getHovered());
    }

    public void setHovered(@Nullable E hovered) {
        super.setHovered((AbstractWidget)hovered);
    }

    @Nullable
    public E getSelected() {
        return (E)((Entry)super.getFocused());
    }

    public void setSelected(@Nullable E entry) {
        this.setFocused(entry);
    }

    @Nullable
    public E getFocused() {
        return (E)((Entry)super.getFocused());
    }

    public void setFocused(@Nullable E entry) {
        super.setFocused((AbstractWidget)entry);
    }

    public E getWidget(int i) {
        return (E)((Entry)this.children().get(i));
    }

    public final List<E> children() {
        return super.children();
    }

    @Nullable
    public final E getWidgetAt(double mouseX, double mouseY) {
        double mouseRelativeY = MathUtils.floor(mouseY - (double)this.y0) + (double)((int)this.getScrollAmount()) - 4.0;
        int index = 0;
        double accumulated = mouseRelativeY;
        for (Entry child : this.children()) {
            if ((accumulated -= (double)child.getHeight()) < 0.0) {
                int rowHalfWidth = child.getWidth() / 2;
                int rowCenterX = this.x0 + this.getWidth() / 2;
                int rowLeft = rowCenterX - rowHalfWidth;
                int rowRight = rowCenterX + rowHalfWidth;
                if (mouseX < (double)this.getScrollbarPosition() && mouseX >= (double)rowLeft && mouseX <= (double)rowRight && index >= 0 && mouseRelativeY >= 0.0 && index < this.getEntrySize()) {
                    return (E)child;
                }
                return null;
            }
            ++index;
        }
        return null;
    }

    protected boolean moveSelection(SelectionDirection selectionDirection, Predicate<E> predicate) {
        int i;
        int n = i = selectionDirection == SelectionDirection.UP ? -1 : 1;
        if (!this.children().isEmpty()) {
            int k;
            int j = this.children().indexOf(this.getSelected());
            while (j != (k = MathUtils.clamp(j + i, 0, this.getEntrySize() - 1))) {
                Entry entry = (Entry)this.children().get(k);
                if (predicate.test(entry)) {
                    this.setSelected((E)entry);
                    this.ensureVisible(entry);
                    return true;
                }
                j = k;
            }
        }
        return false;
    }

    public <C extends E> C addEntry(C entry) {
        ((AbstractWidget)entry).setParent(this);
        ((AbstractWidget)entry).onCreate();
        ((AbstractWidget)entry).onReload();
        this.children().add(entry);
        ((Entry)entry).onPositionChange(-1, this.children().size() - 1);
        return entry;
    }

    public <C extends E> C addEntry(int index, C entry) {
        ((AbstractWidget)entry).setParent(this);
        ((AbstractWidget)entry).onCreate();
        ((AbstractWidget)entry).onReload();
        this.children().add(index, entry);
        ((Entry)entry).onPositionChange(-1, index);
        for (int i = 0; i < this.children().size(); ++i) {
            if (i < index) continue;
            ((Entry)this.children().get(i)).onPositionChange(i - 1, i);
        }
        return entry;
    }

    public <C extends E> boolean removeEntry(C entry) {
        ((AbstractWidget)entry).setParent(null);
        int index = this.children().indexOf(entry);
        boolean removed = this.children().remove(entry);
        ((Entry)entry).onPositionChange(index, -1);
        if (removed && entry == this.getSelected()) {
            this.setSelected((E)null);
        }
        for (int i = 0; i < this.children().size(); ++i) {
            if (i < index) continue;
            ((Entry)this.children().get(i)).onPositionChange(i + 1, i);
        }
        return removed;
    }

    public final void clearEntries() {
        this.children().forEach(widget -> widget.setParent(null));
        this.children().clear();
        this.setSelected((E)null);
        this.setFocused((E)null);
    }

    public void replaceEntries(Collection<? extends E> collection) {
        this.children().forEach(widget -> widget.setParent(null));
        this.children().clear();
        collection.forEach(widget -> widget.setParent(this));
        this.children().addAll(collection);
        this.setSelected((E)null);
        this.setFocused((E)null);
    }

    public void swap(int i, int j) {
        if (i == j) {
            return;
        }
        if (i < 0 || j < 0 || i >= this.children().size() || j >= this.children().size()) {
            return;
        }
        Entry old = (Entry)this.children().get(i);
        this.children().set(i, (Entry)this.children().get(j)).onPositionChange(i, j);
        this.children().set(j, old).onPositionChange(j, i);
    }

    protected int getEntrySize() {
        return this.children().size();
    }

    protected boolean isEntrySelected(int i) {
        return this.getSelected() == this.children().get(i);
    }

    protected int getMaxPosition() {
        return this.children().stream().mapToInt(Widget::getHeight).sum();
    }

    protected void clickedHeader(int i, int j) {
    }

    protected int getEntryPosition(E entry) {
        int i = 0;
        for (Entry child : this.children()) {
            if (entry == child) {
                return i;
            }
            i += child.getHeight();
        }
        throw new NoSuchElementException("Entry not found in list");
    }

    protected int getEntryPosition(int i) {
        return this.getEntryPosition(this.getOrThrow(i));
    }

    protected void centerScrollOn(E entry) {
        this.setScrollAmount((double)this.getEntryPosition(entry) + (double)((AbstractWidget)entry).getHeight() / 2.0 - (double)(this.y1 - this.y0) / 2.0);
    }

    protected void ensureVisible(E entry) {
        int k;
        int h = this.children().indexOf(entry);
        int i = this.getRowTop(h);
        int j = i - this.y0 - 4 - ((AbstractWidget)this.getOrThrow(h)).getHeight();
        if (j < 0) {
            this.scroll(j);
        }
        if ((k = this.y1 - i - ((AbstractWidget)this.getOrThrow(h)).getHeight() - ((AbstractWidget)this.getOrThrow(h)).getHeight()) < 0) {
            this.scroll(-k);
        }
    }

    protected void scroll(int i) {
        this.setScrollAmount(this.getScrollAmount() + (double)i);
    }

    protected double getScrollAmount() {
        return this.scrollAmount;
    }

    public void setScrollAmount(double d) {
        double scrollAmountClamped = MathUtils.clamp(d, 0.0, (double)this.getMaxScroll());
        this.scrollBy(scrollAmountClamped - this.scrollAmount);
        this.scrollAmount = scrollAmountClamped;
    }

    private void scrollBy(double scrolledAmount) {
        this.firstVisibleOffset -= scrolledAmount;
        if (this.children().isEmpty()) {
            this.firstVisibleIndex = 0;
            this.firstVisibleOffset = 0.0;
        }
        if (this.children().size() <= this.firstVisibleIndex) {
            this.firstVisibleIndex = 0;
            this.firstVisibleOffset = 0.0;
            return;
        }
        int height = ((Entry)this.children().get(this.firstVisibleIndex)).getHeight();
        if ((double)height + this.firstVisibleOffset < 0.0) {
            int height1;
            for (int index = this.firstVisibleIndex; index < this.children().size() && !(this.firstVisibleOffset + (double)(height1 = ((Entry)this.children().get(index)).getHeight()) >= 0.0); ++index) {
                ++this.firstVisibleIndex;
                this.firstVisibleOffset += (double)height1;
                if (this.firstVisibleOffset >= 0.0) break;
            }
            return;
        }
        if (this.firstVisibleOffset > 0.0) {
            for (int index = this.firstVisibleIndex - 1; index >= 0; --index) {
                int height1 = ((Entry)this.children().get(index)).getHeight();
                --this.firstVisibleIndex;
                this.firstVisibleOffset -= (double)height1;
                if (this.firstVisibleOffset <= 0.0) break;
            }
            return;
        }
    }

    protected void setScrollAmountNoClamp(double d) {
        this.scrollBy(d - this.scrollAmount);
        this.scrollAmount = d;
    }

    protected int getMaxScroll() {
        return MathUtils.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
    }

    protected int getScrollBottom() {
        return (int)this.getScrollAmount() - this.getHeight();
    }

    protected void updateScrollingState(double d, double e, int i) {
        this.scrolling = i == 0 && d >= (double)this.getScrollbarPosition() && d < (double)(this.x0 + this.getScrollbarPosition() + 6);
    }

    protected int getScrollbarPosition() {
        return this.x0 + this.getWidth() + 2;
    }

    protected int getScrollbarWidth() {
        return 6;
    }

    protected void moveSelection(SelectionDirection selectionDirection) {
        this.moveSelection(selectionDirection, entry -> true);
    }

    protected void refreshSelection() {
        AbstractWidget entry = this.getSelected();
        if (entry != null) {
            this.setSelected((E)entry);
            this.ensureVisible(entry);
        }
    }

    public E getOrThrow(int i) {
        if (i < 0 || i >= this.children().size()) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + this.children().size());
        }
        return (E)((Entry)this.children().get(i));
    }

    public boolean contains(E entry) {
        return this.children().contains(entry);
    }

    protected int getRowLeft(int i) {
        return this.x0 + this.getWidth() / 2 - ((Entry)this.getOrThrow(i)).getWidth() / 2;
    }

    protected int getRowLeft(E entry) {
        return this.x0 + this.getWidth() / 2 - ((Entry)entry).getWidth() / 2;
    }

    protected int getRowRight(int i) {
        return this.x0 + this.getWidth() / 2 + ((Entry)this.getOrThrow(i)).getWidth() / 2;
    }

    protected int getRowRight(E entry) {
        return this.x0 + this.getWidth() / 2 + ((Entry)entry).getWidth() / 2;
    }

    protected int getRowTop(int i) {
        int relativeIndex = this.firstVisibleIndex;
        return (int)((double)(this.y0 + 4 + IntStream.range(Math.min(i, relativeIndex), Math.max(i, relativeIndex)).mapToObj(this.children()::get).mapToInt(AbstractWidget::getHeight).sum()) + this.firstVisibleOffset);
    }

    protected int getRowTop(E entry) {
        return this.getRowTop(this.children().indexOf(entry));
    }

    protected int getRowBottom(int i) {
        return this.getRowTop(i) + ((AbstractWidget)this.getOrThrow(i)).getHeight();
    }

    protected int getRowBottom(E entry) {
        return this.getRowTop(entry) + ((AbstractWidget)entry).getHeight();
    }

    public boolean isRenderSelection() {
        return this.renderSelection;
    }

    public void setRenderSelection(boolean renderSelection) {
        this.renderSelection = renderSelection;
    }

    private void renderList(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        if (this.isRenderSelection() && this.getHovered() != null) {
            this.renderSelection(renderer, this.getHovered(), -8355712, -16777216);
        }
        for (int i = 0; i < this.getEntrySize(); ++i) {
            AbstractWidget entry = this.getWidget(i);
            if (i < this.firstVisibleIndex) {
                entry.setVisible(false);
                continue;
            }
            int left = this.getRowLeft(i);
            int top = this.getRowTop(i);
            int bottom = this.getRowBottom(i);
            ((AbstractContainerWidget)entry).moveX(left - entry.getX());
            ((AbstractContainerWidget)entry).moveY(top - entry.getY());
            if (bottom < this.y0) {
                entry.setVisible(false);
                continue;
            }
            if (top > this.y1) {
                for (int j = i; j < this.getEntrySize(); ++j) {
                    this.getWidget(j).setVisible(false);
                }
                break;
            }
            entry.setVisible(true);
            renderer.pushPose();
            if (this.isRenderSelection() && this.getSelected() == entry) {
                this.renderSelection(renderer, entry, this.isFocused() ? -1 : -8355712, -16777216);
            }
            renderer.popPose();
            ((Entry)entry).render(renderer, mouseX, mouseY, deltaTick);
        }
    }

    protected void renderSelection(Renderer renderer, E entry, int outerColor, int innerColor) {
        renderer.pushPose();
        renderer.translate((double)this.getRowLeft(entry), (double)this.getRowTop(entry), 0.0);
        int width = ((Entry)entry).getWidth();
        int height = ((AbstractWidget)entry).getHeight();
        renderer.renderRect(-2, -2, width + 2, height - 2, outerColor);
        renderer.renderRect(-1, -1, width + 1, height - 3, innerColor);
        renderer.popPose();
    }

    @Override
    public void renderWidgetBackground(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        if (this.getEntrance().getClient().isLoaded() && !this.isBackgroundTransparent()) {
            renderer.renderGradientRect(this.x0, this.y0, this.x1, this.y1, -603979776, -603979776);
        }
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        this.setHovered((E)(this.isMouseOver(mouseX, mouseY) ? this.getWidgetAt(mouseX, mouseY) : null));
        int left = this.getScrollbarPosition();
        int width = this.getScrollbarWidth();
        renderer.pushPose();
        renderer.pushScissor(this.x0, this.y0, this.getWidth(), this.getHeight());
        this.renderList(renderer, mouseX, mouseY, deltaTick);
        renderer.popScissor();
        renderer.popPose();
        if (this.renderShadow) {
            renderer.renderGradientRect(RenderLayers.GUI_OVERLAY, this.x0, this.y0, this.x1, this.y0 + 4, -16777216, 0, 0);
            renderer.renderGradientRect(RenderLayers.GUI_OVERLAY, this.x0, this.y1 - 4, this.x1, this.y1, 0, -16777216, 0);
        }
        int renderScrollBar = this.getMaxScroll();
        if (this.isShowScrollBar && (renderScrollBar > 0 || this.isAlwaysShowScrollbar)) {
            int top;
            int size = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            size = MathUtils.clamp(size, 32, this.y1 - this.y0);
            int n = top = renderScrollBar == 0 ? 0 : (int)this.getScrollAmount() * (this.y1 - this.y0 - size) / renderScrollBar + this.y0;
            if (top < this.y0) {
                top = this.y0;
            }
            renderer.renderRect(left, this.y0, left + width, this.y1, -16777216);
            renderer.renderRect(left, top, left + width, top + size, -8355712);
            renderer.renderRect(left, top, left + width - 1, top + size - 1, -4144960);
        }
    }

    public boolean isScrollbarVisible() {
        int renderScrollBar = this.getMaxScroll();
        return this.isShowScrollBar && (renderScrollBar > 0 || this.isAlwaysShowScrollbar);
    }

    public void setAlwaysShowScrollbar(boolean alwaysShowScrollbar) {
        this.isAlwaysShowScrollbar = alwaysShowScrollbar;
    }

    public void setShowScrollBar(boolean showScrollBar) {
        this.isShowScrollBar = showScrollBar;
    }

    @Override
    public void renderWidgetOverlay(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidgetOverlay(renderer, mouseX, mouseY, deltaTick);
        if (this.getHovered() != null) {
            this.getHovered().renderOverlay(renderer, mouseX, mouseY, deltaTick);
        }
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        boolean result;
        this.updateScrollingState(mouseX, mouseY, button);
        boolean bl = result = super.onMouseClicked(mouseX, mouseY, button) || this.scrolling;
        if (result && this.getFocused() != null && this.getFocused().isMouseOver(mouseX, mouseY)) {
            this.lastClickTime = System.currentTimeMillis() - this.lastClickTime > 250L ? System.currentTimeMillis() : System.currentTimeMillis() - this.lastClickTime;
        }
        return result;
    }

    public boolean consumeDoubleClick() {
        boolean clicked;
        boolean bl = clicked = this.lastClickTime > 0L && this.lastClickTime <= 250L;
        if (clicked) {
            this.lastClickTime = 0L;
        }
        return clicked;
    }

    @Override
    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.getSelected() != null && this.isDragging() && button == 0 && this.contains(this.getFocused()) && ((AbstractContainerWidget)this.getSelected()).onMouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true;
        }
        if (button == 0 && this.scrolling) {
            if (mouseY < (double)this.y0) {
                this.setScrollAmount(0.0);
            } else if (mouseY > (double)this.y1) {
                this.setScrollAmount(this.getMaxScroll());
            } else {
                double h = MathUtils.max(1, this.getMaxScroll());
                int j = this.y1 - this.y0;
                int k = MathUtils.clamp((int)((float)(j * j) / (float)this.getMaxPosition()), 32, j - 8);
                double l = MathUtils.max(1.0, h / (double)(j - k));
                this.setScrollAmount(this.getScrollAmount() + deltaY * l);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        boolean consumed;
        AbstractWidget widget = this.getWidgetAt(mouseX, mouseY);
        boolean bl = consumed = widget != null && ((AbstractContainerWidget)widget).onMouseScrolled(mouseX, mouseY, amountX, amountY);
        if (!consumed) {
            this.setScrollAmount(this.getScrollAmount() - amountY * 16.0);
        }
        return true;
    }

    @Override
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.getFocused() != null && ((AbstractContainerWidget)this.getFocused()).onKeyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (keyCode == 264) {
            this.moveSelection(SelectionDirection.DOWN);
            return true;
        }
        if (keyCode == 265) {
            this.moveSelection(SelectionDirection.UP);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyReleased(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && ((AbstractContainerWidget)this.getFocused()).onKeyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean onCharTyped(char character, int modifiers) {
        if (this.getFocused() != null && ((AbstractContainerWidget)this.getFocused()).onCharTyped(character, modifiers)) {
            return true;
        }
        return super.onCharTyped(character, modifiers);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseY >= (double)this.getTop() && mouseY <= (double)this.getBottom() && (mouseX >= (double)this.getLeft() && mouseX <= (double)this.getRight() || mouseX >= (double)this.getScrollbarPosition() && mouseX <= (double)(this.getScrollbarPosition() + this.getScrollbarWidth()));
    }

    @Override
    public void moveDown(Widget widget) {
    }

    @Override
    public void moveUp(Widget widget) {
    }

    @Override
    public void moveDownNoClamp(Widget widget) {
    }

    @Override
    public void moveUpNoClamp(Widget widget) {
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    public static abstract class Entry
    extends AbstractContainerWidget
    implements EntryList.Entry {
        protected Entry(Entrance entrance) {
            super(entrance, 0, 0, 0, 0, Text.empty());
            this.setFocusable(true);
        }

        @Override
        public void onPositionChange(int from, int to) {
        }

        @Override
        public void onSelected() {
        }

        @Override
        public void onDeselected() {
        }

        @Deprecated
        public Text getNarration() {
            return Text.empty();
        }

        @Override
        public void render(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
            renderer.pushScissor(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            super.render(renderer, mouseX, mouseY, deltaTick);
            renderer.popScissor();
        }

        @Override
        public int getWidth() {
            return MathUtils.min(282, this.getParent().getWidth() - 8);
        }

        @Override
        public boolean onMouseClicked(double mouseX, double mouseY, int button) {
            boolean result = super.onMouseClicked(mouseX, mouseY, button);
            boolean mouseOver = this.isMouseOver(mouseX, mouseY);
            if (!result && mouseOver) {
                this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            }
            return result || mouseOver;
        }
    }

    protected static enum SelectionDirection {
        UP,
        DOWN;

    }
}
