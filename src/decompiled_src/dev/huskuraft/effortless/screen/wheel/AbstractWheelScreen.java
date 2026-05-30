/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.screen.wheel;

import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.api.core.AxisDirection;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.gui.AbstractScreen;
import dev.huskuraft.effortless.api.gui.tooltip.TooltipHelper;
import dev.huskuraft.effortless.api.input.KeyBinding;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.RenderLayers;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.api.utils.ColorUtils;
import dev.huskuraft.effortless.building.Option;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class AbstractWheelScreen<S, B>
extends AbstractScreen {
    public static final int ANIMATION_OFFSET_Y = 12;
    public static final int ANIMATION_TICKS = 4;
    private static final float FADE_SPEED = 0.5f;
    private static final int WATERMARK_TEXT_COLOR = -1921024129;
    private static final int DEFAULT_RADIAL_SLOTS = 12;
    private static final ColorState RADIAL_SLOT_COLOR_STATE;
    private static final ColorState RADIAL_BUTTON_COLOR_STATE;
    private static final int WHITE_TEXT_COLOR = 0xFFFFFF;
    private static final double RING_INNER_EDGE = 32.0;
    private static final double RING_OUTER_EDGE = 67.0;
    private static final double CATEGORY_LINE_OUTER_EDGE = 36.0;
    private static final double TEXT_DISTANCE = 84.0;
    private static final double SECTION_OFFSET_X = 112.0;
    private static final double SECTION_OFFSET_Y = 0.0;
    private static final int BUTTON_WIDTH = 22;
    private static final int BUTTON_HEIGHT = 22;
    private static final double BUTTON_OFFSET_X = 26.0;
    private static final double BUTTON_OFFSET_Y = 26.0;
    private static final double TITLE_HEIGHT = 10.0;
    private static final int MIN_RADIAL_SIZE = 8;
    private static final float MOUSE_SCROLL_THRESHOLD = 2.0f;
    private BiConsumer<Slot<S>, Boolean> radialSelectResponder;
    private BiConsumer<Button<B>, Boolean> radialOptionSelectResponder;
    private List<? extends Slot<S>> radialSlots = List.of();
    private List<? extends ButtonSet<B>> leftButtons = List.of();
    private List<? extends ButtonSet<B>> rightButtons = List.of();
    private Slot<S> hoveredSlot;
    private Button<B> hoveredButton;
    private Collection<? extends Slot<S>> selectedSlot = new HashSet<Slot<S>>();
    private float lastScrollOffset = 0.0f;
    private float visibility = 1.0f;
    private float animationTicks = 0.0f;
    private float animationScaleTicks = 0.0f;
    private boolean detached = false;
    public static final int MAX_ANIMATION_TICKS = 4;

    public AbstractWheelScreen(Entrance entrance, Text text) {
        super(entrance, text);
    }

    protected static <T> Slot<T> slot(final Object id, final Text name, final ResourceLocation icon, final Color tintColor, final T content) {
        return new Slot<T>(){

            @Override
            public Object getId() {
                return id;
            }

            @Override
            public Text getDisplayName() {
                return name;
            }

            @Override
            public Text getDisplayCategory() {
                return null;
            }

            @Override
            public ResourceLocation getIcon() {
                return icon;
            }

            @Override
            public Color getTintColor() {
                return tintColor;
            }

            @Override
            public T getContent() {
                return content;
            }
        };
    }

    protected static <T> Button<T> button(final Object id, final Text name, final Text category, final Text summary, final List<Text> description, final ResourceLocation icon, final T content, final boolean activated) {
        return new Button<T>(){

            @Override
            public Object getId() {
                return id;
            }

            @Override
            public Text getName() {
                return name;
            }

            @Override
            public Text getCategory() {
                return category;
            }

            @Override
            public Text getSummary() {
                return summary;
            }

            @Override
            public List<Text> getDescriptions() {
                return description;
            }

            @Override
            public ResourceLocation getIcon() {
                return icon;
            }

            @Override
            public Color getTintColor() {
                return null;
            }

            @Override
            public T getContent() {
                return content;
            }

            @Override
            public boolean isActivated() {
                return activated;
            }
        };
    }

    @SafeVarargs
    protected static <T> ButtonSet<T> buttonSet(Button<T> ... entries) {
        return AbstractWheelScreen.buttonSet(List.of(entries));
    }

    protected static <T> ButtonSet<T> buttonSet(final List<? extends Button<T>> entries) {
        return new ButtonSet<T>(){

            @Override
            public Text getDisplayName() {
                return null;
            }

            @Override
            public List<? extends Button<T>> getButtons() {
                return entries;
            }
        };
    }

    public static <T extends Option> Button<T> button(T option) {
        return AbstractWheelScreen.button(option, false);
    }

    public static <T extends Option> Button<T> lazyButton(final Supplier<Button<T>> supplier) {
        return new Button<T>(){

            @Override
            public Object getId() {
                return ((Button)supplier.get()).getId();
            }

            @Override
            public Text getName() {
                return ((Button)supplier.get()).getName();
            }

            @Override
            public Text getCategory() {
                return ((Button)supplier.get()).getCategory();
            }

            @Override
            public Text getSummary() {
                return ((Button)supplier.get()).getSummary();
            }

            @Override
            public List<Text> getDescriptions() {
                return ((Button)supplier.get()).getDescriptions();
            }

            @Override
            public ResourceLocation getIcon() {
                return ((Button)supplier.get()).getIcon();
            }

            @Override
            public Color getTintColor() {
                return ((Button)supplier.get()).getTintColor();
            }

            @Override
            public T getContent() {
                return (Option)((Button)supplier.get()).getContent();
            }

            @Override
            public boolean isActivated() {
                return ((Button)supplier.get()).isActivated();
            }
        };
    }

    public static <T extends Option> Button<T> button(T option, boolean activated) {
        return AbstractWheelScreen.button(option, option.getNameText(), option.getCategoryText(), option.getTooltipText(), List.of(), option.getIcon(), option, activated);
    }

    public static <T extends Option> Button<T> button(T option, Text name, List<Text> description, boolean activated) {
        return AbstractWheelScreen.button(option, name, option.getCategoryText(), option.getTooltipText(), description, option.getIcon(), option, activated);
    }

    protected abstract KeyBinding getAssignedKeyBinds();

    @Override
    public void init(int width, int height) {
        super.init(width, height);
        if (this.detached) {
            super.detach();
        }
    }

    @Override
    public boolean isPauseGame() {
        return false;
    }

    @Override
    public void onAnimateTick(float partialTick) {
        this.detached = !this.getAssignedKeyBinds().isKeyDown();
        this.animationTicks = Math.min(Math.max(this.animationTicks + (float)(this.detached ? -1 : 1) * partialTick, 0.0f), 4.0f);
        this.animationScaleTicks = Math.min(Math.max(this.animationScaleTicks + (float)(this.detached ? -1 : 1) * partialTick, 0.0f), 4.0f);
        if (this.detached && this.animationTicks == 0.0f) {
            this.detach();
        }
    }

    @Override
    public void onCreate() {
    }

    private static int pack(int r, int g, int b, int alpha) {
        return alpha << 24 | r << 16 | g << 8 | b;
    }

    @Override
    public void renderWidgetBackground(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        if (this.isTransparentBackground() && this.getEntrance().getClient().isLoaded()) {
            renderer.renderGradientRect(0, 0, super.getWidth(), super.getHeight(), ColorUtils.setAlpha(0x101010, (int)(192.0f * this.getAnimationFactor())), ColorUtils.setAlpha(0x101010, (int)(208.0f * this.getAnimationFactor())));
        } else {
            renderer.setRsShaderColor(0.25f, 0.25f, 0.25f, this.getAnimationFactor());
            renderer.renderPanelBackgroundTexture(0, 0, 0.0f, 0.0f, this.getWidth(), this.getHeight());
            renderer.setRsShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        renderer.pushPose();
        renderer.translate(0.0f, 0.0f * this.getAnimationScaleFactor() * 12.0f, 0.0f);
        renderer.translate((float)this.getX() + (float)this.getWidth() / 2.0f, (float)this.getY() + (float)this.getHeight() / 2.0f, 0.0f);
        renderer.scale(MathUtils.lerp((double)this.getAnimationScaleFactor(), 0.92, 1.0));
        renderer.translate((float)(-this.getX()) - (float)this.getWidth() / 2.0f, (float)(-this.getY()) - (float)this.getHeight() / 2.0f, 0.0f);
        renderer.setRsShaderColor(1.0f, 1.0f, 1.0f, this.getAnimationScaleFactor());
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        this.hoveredSlot = null;
        this.hoveredButton = null;
        this.renderRadialButtonSets(renderer, mouseX, mouseY, this.leftButtons, AxisDirection.NEGATIVE);
        this.renderRadialButtonSets(renderer, mouseX, mouseY, this.rightButtons, AxisDirection.POSITIVE);
        this.renderRadialSlots(renderer, mouseX, mouseY, this.radialSlots);
        renderer.popPose();
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        boolean result = false;
        if (this.isActive() && this.isVisible()) {
            if (this.radialSelectResponder != null && this.hoveredSlot != null) {
                this.radialSelectResponder.accept(this.hoveredSlot, button == 0);
                this.playRadialMenuSound();
                result = true;
            }
            if (this.radialOptionSelectResponder != null && this.hoveredButton != null) {
                this.radialOptionSelectResponder.accept(this.hoveredButton, button == 0);
                this.playRadialMenuSound();
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean onMouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        double sign = (double)this.lastScrollOffset * amountY;
        if (sign < 0.0) {
            this.lastScrollOffset = 0.0f;
        }
        this.lastScrollOffset = (float)((double)this.lastScrollOffset + amountY);
        if (this.lastScrollOffset > 2.0f) {
            this.playRadialMenuSound();
            this.getEntrance().getStructureBuilder().setStructure(this.getPlayer(), this.getEntrance().getConfigStorage().getStructure(this.getEntrance().getStructureBuilder().getContext(this.getPlayer()).buildMode().previous()));
            this.lastScrollOffset = 0.0f;
        } else if (this.lastScrollOffset < -2.0f) {
            this.playRadialMenuSound();
            this.getEntrance().getStructureBuilder().setStructure(this.getPlayer(), this.getEntrance().getConfigStorage().getStructure(this.getEntrance().getStructureBuilder().getContext(this.getPlayer()).buildMode().next()));
            this.lastScrollOffset = 0.0f;
        }
        return true;
    }

    public final void setRadialSelectResponder(BiConsumer<Slot<S>, Boolean> consumer) {
        this.radialSelectResponder = consumer;
    }

    public final void setRadialOptionSelectResponder(BiConsumer<Button<B>, Boolean> consumer) {
        this.radialOptionSelectResponder = consumer;
    }

    public final void setRadialSlots(List<Slot<S>> slots) {
        this.radialSlots = slots;
    }

    @SafeVarargs
    public final void setLeftButtons(ButtonSet<B> ... options) {
        this.setLeftButtons(List.of(options));
    }

    public final void setLeftButtons(List<? extends ButtonSet<B>> options) {
        this.leftButtons = options;
    }

    @SafeVarargs
    public final void setRightButtons(ButtonSet<B> ... options) {
        this.setRightButtons(List.of(options));
    }

    public final void setRightButtons(List<? extends ButtonSet<B>> options) {
        this.rightButtons = options;
    }

    @SafeVarargs
    public final void setSelectedSlots(Slot<S> ... slots) {
        this.setSelectedSlots(Set.of(slots));
    }

    public final void setSelectedSlots(Collection<? extends Slot<S>> slots) {
        this.selectedSlot = slots;
    }

    public void resetScaleAnimation() {
        this.animationScaleTicks = 1.0f;
    }

    private void renderRadialSlots(Renderer renderer, int mouseX, int mouseY, List<? extends Slot<S>> slots) {
        double middleX = (double)this.getWidth() / 2.0;
        double middleY = (double)this.getHeight() / 2.0;
        double mouseCenterX = (double)mouseX - middleX;
        double mouseCenterY = (double)mouseY - middleY;
        double mouseRad = (MathUtils.atan2(mouseCenterY, mouseCenterX) + Math.PI * 2) % (Math.PI * 2);
        double ringInnerEdge = (double)23.04f + 32.0 * (double)this.visibility * (double)0.28f;
        double ringOuterEdge = 48.240001916885376 + 67.0 * (double)this.visibility * (double)0.28f;
        double categoryOuterEdge = 25.92000102996826 + 36.0 * (double)this.visibility * (double)0.28f;
        double innerGap = 0.02199114857512855;
        double outerGap = innerGap * ringInnerEdge / ringOuterEdge;
        double rad = Math.PI * 2 / (double)MathUtils.max(8, slots.size());
        for (int i = 0; i < slots.size(); ++i) {
            Slot slot = slots.get(i);
            double lRad = ((double)i - 0.5) * rad - 1.5707963267948966;
            double rRad = ((double)i + 0.5) * rad - 1.5707963267948966;
            double x1 = MathUtils.cos(lRad);
            double x2 = MathUtils.cos(rRad);
            double y1 = MathUtils.sin(lRad);
            double y2 = MathUtils.sin(rRad);
            double x1m1 = MathUtils.cos(lRad + innerGap) * ringInnerEdge;
            double x2m1 = MathUtils.cos(rRad - innerGap) * ringInnerEdge;
            double y1m1 = MathUtils.sin(lRad + innerGap) * ringInnerEdge;
            double y2m1 = MathUtils.sin(rRad - innerGap) * ringInnerEdge;
            double x1m2 = MathUtils.cos(lRad + outerGap) * ringOuterEdge;
            double x2m2 = MathUtils.cos(rRad - outerGap) * ringOuterEdge;
            double y1m2 = MathUtils.sin(lRad + outerGap) * ringOuterEdge;
            double y2m2 = MathUtils.sin(rRad - outerGap) * ringOuterEdge;
            boolean isActivated = this.selectedSlot.stream().anyMatch(obj -> Objects.equals(obj.getId(), slot.getId()));
            boolean isMouseInQuad = this.inTriangle(x1m1, y1m1, x2m2, y2m2, x2m1, y2m1, mouseCenterX, mouseCenterY) || this.inTriangle(x1m1, y1m1, x1m2, y1m2, x2m2, y2m2, mouseCenterX, mouseCenterY);
            boolean isHovered = (lRad <= mouseRad && mouseRad <= rRad || lRad <= mouseRad - Math.PI * 2 && mouseRad - Math.PI * 2 <= rRad) && isMouseInQuad;
            Color color = RADIAL_SLOT_COLOR_STATE.defaultColor();
            if (isActivated) {
                color = RADIAL_SLOT_COLOR_STATE.activedColor();
            }
            if (isHovered) {
                color = RADIAL_SLOT_COLOR_STATE.hoveredColor();
            }
            if (isActivated && isHovered) {
                color = RADIAL_SLOT_COLOR_STATE.activedHoveredColor();
            }
            if (isHovered) {
                this.hoveredSlot = slot;
                double x = (x1 + x2) * 0.5;
                double y = (y1 + y2) * 0.5;
                int textX = (int)(x * 84.0);
                int textY = (int)(y * 84.0) - this.getTypeface().getLineHeight() / 2;
                Text text = slot.getDisplayName();
                if (x <= -0.2) {
                    textX -= this.getTypeface().measureWidth(text);
                } else if (-0.2 <= x && x <= 0.2) {
                    textX -= this.getTypeface().measureWidth(text) / 2;
                }
                renderer.renderTextFromStart(this.getTypeface(), text, (int)middleX + textX, (int)middleY + textY, 0xFFFFFF, true);
            }
            renderer.renderQuad((int)(middleX + x1m1), (int)(middleY + y1m1), (int)(middleX + x2m1), (int)(middleY + y2m1), (int)(middleX + x2m2), (int)(middleY + y2m2), (int)(middleX + x1m2), (int)(middleY + y1m2), 100, color.getRGB());
            color = slot.getTintColor();
            double x1m3 = MathUtils.cos(lRad + innerGap) * categoryOuterEdge;
            double x2m3 = MathUtils.cos(rRad - innerGap) * categoryOuterEdge;
            double y1m3 = MathUtils.sin(lRad + innerGap) * categoryOuterEdge;
            double y2m3 = MathUtils.sin(rRad - innerGap) * categoryOuterEdge;
            renderer.renderQuad((int)(middleX + x1m1), (int)(middleY + y1m1), (int)(middleX + x2m1), (int)(middleY + y2m1), (int)(middleX + x2m3), (int)(middleY + y2m3), (int)(middleX + x1m3), (int)(middleY + y1m3), 200, color.getRGB());
            if (slot.getIcon() == null) continue;
            double iconX = (x1 + x2) * 0.5 * (ringOuterEdge * 0.55 + 0.45 * ringInnerEdge);
            double iconY = (y1 + y2) * 0.5 * (ringOuterEdge * 0.55 + 0.45 * ringInnerEdge);
            renderer.pushPose();
            renderer.translate(0.0f, 0.0f, 300.0f);
            renderer.renderTexture(slot.getIcon(), (int)MathUtils.round(middleX + iconX - 8.0), (int)MathUtils.round(middleY + iconY - 8.0), 16, 16, 0.0f, 0.0f, 18, 18, 18, 18);
            renderer.popPose();
        }
    }

    private void renderRadialButtonSets(Renderer renderer, int mouseX, int mouseY, List<? extends ButtonSet<B>> buttonSets, AxisDirection direction) {
        double middleX = (double)this.getWidth() / 2.0;
        double middleY = (double)this.getHeight() / 2.0;
        double mouseCenterX = (double)mouseX - middleX;
        double mouseCenterY = (double)mouseY - middleY;
        for (int row = 0; row < buttonSets.size(); ++row) {
            ButtonSet<B> buttonSet = buttonSets.get(row);
            List<Button<B>> buttons = buttonSet.getButtons();
            for (int col = 0; col < buttons.size(); ++col) {
                Button<B> button = buttons.get(col);
                double x = (112.0 + 26.0 * (double)col) * (double)direction.getStep();
                double y = (0.0 + 26.0 * (double)((float)row - (float)(buttonSets.size() - 1) / 2.0f)) * 1.0;
                double x1 = x - 11.0;
                double y1 = y - 11.0;
                double x2 = x + 11.0;
                double y2 = y + 11.0;
                boolean isActivated = button.isActivated();
                boolean isHovered = x1 <= mouseCenterX && x2 >= mouseCenterX && y1 <= mouseCenterY && y2 >= mouseCenterY;
                Color color = RADIAL_BUTTON_COLOR_STATE.defaultColor();
                if (isActivated) {
                    color = RADIAL_BUTTON_COLOR_STATE.activedColor();
                }
                if (isHovered) {
                    color = RADIAL_BUTTON_COLOR_STATE.hoveredColor();
                }
                if (isActivated && isHovered) {
                    color = RADIAL_BUTTON_COLOR_STATE.activedHoveredColor();
                }
                if (isHovered) {
                    this.hoveredButton = button;
                }
                renderer.renderRect(RenderLayers.GUI, (int)(middleX + x1), (int)(middleY + y1), (int)(middleX + x2), (int)(middleY + y2), color.getRGB(), 0);
                if (button.getIcon() == null) continue;
                renderer.pushPose();
                double iconX = x;
                double iconY = y;
                renderer.translate((int)MathUtils.round(middleX + iconX - 8.0), (int)MathUtils.round(middleY + iconY - 8.0), 0.0f);
                renderer.renderTexture(button.getIcon(), 0, 0, 16, 16, 0.0f, 0.0f, 18, 18, 18, 18);
                renderer.popPose();
            }
        }
    }

    @Override
    public void renderWidgetOverlay(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidgetOverlay(renderer, mouseX, mouseY, deltaTick);
        if (this.hoveredButton != null) {
            ArrayList<Text> tooltip = new ArrayList<Text>();
            tooltip.add(this.hoveredButton.getCategory().withStyle(ChatFormatting.WHITE));
            tooltip.add(this.hoveredButton.getName().withStyle(ChatFormatting.GOLD));
            if (!this.hoveredButton.getDescriptions().isEmpty()) {
                tooltip.addAll(this.hoveredButton.getDescriptions());
            }
            if (!this.hoveredButton.getSummary().getString().isEmpty()) {
                tooltip.add(Text.empty());
                tooltip.add(TooltipHelper.holdShiftForSummary());
                if (TooltipHelper.isSummaryButtonDown()) {
                    tooltip.add(Text.empty());
                    tooltip.addAll(TooltipHelper.wrapLines(this.getTypeface(), this.hoveredButton.getSummary().withStyle(ChatFormatting.GRAY)));
                }
            }
            renderer.renderTooltip(this.getTypeface(), tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected EffortlessClient getEntrance() {
        return (EffortlessClient)super.getEntrance();
    }

    private Player getPlayer() {
        return this.getEntrance().getClient().getPlayer();
    }

    private float getAnimationFactor() {
        float fac = 1.0f - Math.min(this.animationTicks, 4.0f) / 4.0f;
        if (this.detached) {
            return 1.0f - MathUtils.lerp((1.0f - fac) * (1.0f - fac), 1.0f, 0.0f);
        }
        return 1.0f - MathUtils.lerp(fac * fac, 0.0f, 1.0f);
    }

    private float getAnimationScaleFactor() {
        float fac = 1.0f - Math.min(this.animationScaleTicks, 4.0f) / 4.0f;
        if (this.detached) {
            return 1.0f - MathUtils.lerp((1.0f - fac) * (1.0f - fac), 1.0f, 0.0f);
        }
        return 1.0f - MathUtils.lerp(fac * fac, 0.0f, 1.0f);
    }

    private boolean inTriangle(double x1, double y1, double x2, double y2, double x3, double y3, double x, double y) {
        double ab = (x1 - x) * (y2 - y) - (x2 - x) * (y1 - y);
        double bc = (x2 - x) * (y3 - y) - (x3 - x) * (y2 - y);
        double ca = (x3 - x) * (y1 - y) - (x1 - x) * (y3 - y);
        return MathUtils.sign(ab) == MathUtils.sign(bc) && MathUtils.sign(bc) == MathUtils.sign(ca);
    }

    private void playRadialMenuSound() {
        this.getEntrance().getClient().getSoundManager().playButtonClickSound();
    }

    static {
        RADIAL_BUTTON_COLOR_STATE = RADIAL_SLOT_COLOR_STATE = new ColorState(new Color(0.0f, 0.0f, 0.0f, 0.42f), new Color(0.0f, 0.0f, 0.0f, 0.42f), new Color(0.24f, 0.24f, 0.24f, 0.5f), new Color(0.36f, 0.36f, 0.36f, 0.64f), new Color(0.42f, 0.42f, 0.42f, 0.64f));
    }

    public static interface ButtonSet<T> {
        public Text getDisplayName();

        public List<? extends Button<T>> getButtons();
    }

    public static interface Button<T> {
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

    public static interface Slot<T> {
        public Object getId();

        public Text getDisplayName();

        public Text getDisplayCategory();

        public ResourceLocation getIcon();

        public Color getTintColor();

        public T getContent();
    }

    record ColorState(Color disabledColor, Color defaultColor, Color hoveredColor, Color activedColor, Color activedHoveredColor) {
    }
}
