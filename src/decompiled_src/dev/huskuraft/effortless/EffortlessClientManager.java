/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.Effortless;
import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.EffortlessKeys;
import dev.huskuraft.effortless.api.core.Direction;
import dev.huskuraft.effortless.api.core.Interaction;
import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.core.InteractionType;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.events.EventResult;
import dev.huskuraft.effortless.api.events.input.KeyRegistry;
import dev.huskuraft.effortless.api.events.lifecycle.ClientTick;
import dev.huskuraft.effortless.api.events.render.RegisterShader;
import dev.huskuraft.effortless.api.gui.Screen;
import dev.huskuraft.effortless.api.input.InputKey;
import dev.huskuraft.effortless.api.input.Keys;
import dev.huskuraft.effortless.api.input.OptionKeys;
import dev.huskuraft.effortless.api.platform.Client;
import dev.huskuraft.effortless.api.platform.ClientManager;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.platform.Platform;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.renderer.Shaders;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.building.clipboard.SnapshotTransform;
import dev.huskuraft.effortless.renderer.BlockShaders;
import dev.huskuraft.effortless.renderer.opertaion.OperationsRenderer;
import dev.huskuraft.effortless.renderer.outliner.OutlineRenderer;
import dev.huskuraft.effortless.renderer.pattern.PatternRenderer;
import dev.huskuraft.effortless.renderer.tooltip.TooltipRenderer;
import dev.huskuraft.effortless.screen.clipboard.EffortlessClipboardScreen;
import dev.huskuraft.effortless.screen.pattern.EffortlessPatternScreen;
import dev.huskuraft.effortless.screen.settings.EffortlessSettingsScreen;
import dev.huskuraft.effortless.screen.structure.EffortlessStructureScreen;
import dev.huskuraft.effortless.screen.test.EffortlessTestScreen;
import java.util.Stack;

public final class EffortlessClientManager
implements ClientManager {
    private final Stack<Screen> screenStack = new Stack();
    private final EffortlessClient entrance;
    private final TooltipRenderer tooltipRenderer;
    private final OperationsRenderer operationsRenderer;
    private final OutlineRenderer outlineRenderer;
    private final PatternRenderer patternRenderer;
    private Client client;
    private int interactionCooldown = 0;

    public EffortlessClientManager(EffortlessClient entrance) {
        this.entrance = entrance;
        this.tooltipRenderer = new TooltipRenderer(entrance);
        this.operationsRenderer = new OperationsRenderer(entrance);
        this.outlineRenderer = new OutlineRenderer();
        this.patternRenderer = new PatternRenderer(entrance);
        this.getEntrance().getEventRegistry().getRegisterKeysEvent().register(this::onRegisterKeys);
        this.getEntrance().getEventRegistry().getKeyInputEvent().register(this::onKeyInput);
        this.getEntrance().getEventRegistry().getInteractionInputEvent().register(this::onInteractionInput);
        this.getEntrance().getEventRegistry().getClientStartEvent().register(this::onClientStart);
        this.getEntrance().getEventRegistry().getClientTickEvent().register(this::onClientTick);
        this.getEntrance().getEventRegistry().getRenderGuiEvent().register(this::onRenderGui);
        this.getEntrance().getEventRegistry().getRenderWorldEvent().register(this::onRenderEnd);
        this.getEntrance().getEventRegistry().getRegisterShaderEvent().register(this::onRegisterShader);
    }

    private EffortlessClient getEntrance() {
        return this.entrance;
    }

    private Player getPlayer() {
        return this.getEntrance().getClient().getPlayer();
    }

    @Override
    public Client getRunningClient() {
        return this.client;
    }

    @Override
    public void setRunningClient(Client client) {
        this.client = client;
    }

    public OperationsRenderer getOperationsRenderer() {
        return this.operationsRenderer;
    }

    public OutlineRenderer getOutlineRenderer() {
        return this.outlineRenderer;
    }

    public PatternRenderer getPatternRenderer() {
        return this.patternRenderer;
    }

    @Override
    public void pushScreen(Screen screen) {
        if (screen == null) {
            this.screenStack.clear();
        } else {
            this.screenStack.push(this.getRunningClient().getPanel());
        }
        this.getRunningClient().setPanel(screen);
    }

    @Override
    public void popScreen(Screen screen) {
        if (this.getRunningClient().getPanel() != screen) {
            return;
        }
        if (this.screenStack.isEmpty()) {
            this.getRunningClient().setPanel(null);
        } else {
            this.getRunningClient().setPanel(this.screenStack.pop());
        }
    }

    public TooltipRenderer getTooltipRenderer() {
        return this.tooltipRenderer;
    }

    private void tickCooldown() {
        if (OptionKeys.KEY_ATTACK.getKeyBinding().isDown() || OptionKeys.KEY_USE.getKeyBinding().isDown() || OptionKeys.KEY_PICK_ITEM.getKeyBinding().isDown()) {
            return;
        }
        this.interactionCooldown = Math.max(0, this.interactionCooldown - 1);
    }

    private boolean isInteractionCooldown() {
        return this.interactionCooldown == 0;
    }

    private void setInteractionCooldown(int tick) {
        this.interactionCooldown = tick;
    }

    private void resetInteractionCooldown() {
        this.setInteractionCooldown(1);
    }

    public void onRegisterKeys(KeyRegistry keyRegistry) {
        for (EffortlessKeys key : EffortlessKeys.values()) {
            keyRegistry.register(key);
        }
    }

    public void onKeyInput(InputKey key) {
        if (this.getRunningClient() == null) {
            return;
        }
        if (this.getRunningClient().getPlayer() == null) {
            return;
        }
        if (Keys.KEY_ESCAPE.isDown()) {
            this.getEntrance().getStructureBuilder().resetInteractions(this.getRunningClient().getPlayer());
        }
        if (EffortlessKeys.BUILD_MODE_RADIAL.getKeyBinding().isDown() && !(this.getRunningClient().getPanel() instanceof EffortlessStructureScreen)) {
            new EffortlessStructureScreen((Entrance)this.getEntrance(), EffortlessKeys.BUILD_MODE_RADIAL.getKeyBinding()).attach();
        }
        if (EffortlessKeys.UNDO.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().undo(this.getRunningClient().getPlayer());
        }
        if (EffortlessKeys.REDO.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().redo(this.getRunningClient().getPlayer());
        }
        if (EffortlessKeys.SETTINGS.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            new EffortlessSettingsScreen(this.getEntrance()).attach();
        }
        if (EffortlessKeys.TOGGLE_CLIPBOARD.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().setClipboard(this.getRunningClient().getPlayer(), this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).clipboard().toggled());
            this.getEntrance().getClient().getPlayer().sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.toggle_clipboard", this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).clipboard().getNameText().withStyle(ChatFormatting.GOLD))));
        }
        if (EffortlessKeys.TOGGLE_PATTERN.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().setPattern(this.getRunningClient().getPlayer(), this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).pattern().toggled());
            this.getEntrance().getClient().getPlayer().sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.toggle_pattern", this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).pattern().getNameText().withStyle(ChatFormatting.GOLD))));
        }
        if (EffortlessKeys.TOGGLE_REPLACE.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().setReplace(this.getRunningClient().getPlayer(), this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).replace().next());
            this.getEntrance().getClient().getPlayer().sendMessage(Effortless.getSystemMessage(Text.translate("effortless.message.building.server.toggle_replace", this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).replace().getNameText().withStyle(ChatFormatting.GOLD))));
        }
        if (EffortlessKeys.ROTATE_X.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.ROTATE_X);
        }
        if (EffortlessKeys.ROTATE_Y.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.ROTATE_Y);
        }
        if (EffortlessKeys.ROTATE_Z.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.ROTATE_Z);
        }
        if (EffortlessKeys.MOVE_BACKWARD.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            switch (Direction.fromYRot(this.getPlayer().getYRot())) {
                case NORTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_Z);
                    break;
                }
                case SOUTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_Z);
                    break;
                }
                case WEST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_X);
                    break;
                }
                case EAST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_X);
                }
            }
        }
        if (EffortlessKeys.MOVE_FORWARD.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            switch (Direction.fromYRot(this.getPlayer().getYRot())) {
                case NORTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_Z);
                    break;
                }
                case SOUTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_Z);
                    break;
                }
                case WEST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_X);
                    break;
                }
                case EAST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_X);
                }
            }
        }
        if (EffortlessKeys.MOVE_LEFT.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            switch (Direction.fromYRot(this.getPlayer().getYRot())) {
                case NORTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_X);
                    break;
                }
                case SOUTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_X);
                    break;
                }
                case WEST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_Z);
                    break;
                }
                case EAST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_Z);
                }
            }
        }
        if (EffortlessKeys.MOVE_RIGHT.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            switch (Direction.fromYRot(this.getPlayer().getYRot())) {
                case NORTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_X);
                    break;
                }
                case SOUTH: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_X);
                    break;
                }
                case WEST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_Z);
                    break;
                }
                case EAST: {
                    this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_Z);
                }
            }
        }
        if (EffortlessKeys.MOVE_UP.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.INCREASE_Y);
        }
        if (EffortlessKeys.MOVE_DOWN.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.DECREASE_Y);
        }
        if (EffortlessKeys.MIRROR_X.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.MIRROR_X);
        }
        if (EffortlessKeys.MIRROR_Y.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.MIRROR_Y);
        }
        if (EffortlessKeys.MIRROR_Z.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            this.getEntrance().getStructureBuilder().updateClipboard(this.getPlayer(), SnapshotTransform.MIRROR_Z);
        }
        if (EffortlessKeys.EDIT_CLIPBOARD.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            new EffortlessClipboardScreen(this.getEntrance()).attach();
        }
        if (EffortlessKeys.EDIT_PATTERN.getKeyBinding().consumeClick()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            new EffortlessPatternScreen(this.getEntrance()).attach();
        }
        if (Platform.getInstance().isDevelopment() && Keys.KEY_LEFT_CONTROL.isDown() && Keys.KEY_ENTER.isDown()) {
            this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            new EffortlessTestScreen(this.getEntrance()).attach();
        }
    }

    public EventResult onInteractionInput(InteractionType type, InteractionHand hand) {
        if (this.getEntrance().getStructureBuilder().getContext(this.getRunningClient().getPlayer()).isDisabled()) {
            return EventResult.pass();
        }
        if (!this.isInteractionCooldown()) {
            return EventResult.interruptFalse();
        }
        this.resetInteractionCooldown();
        Interaction interaction = this.getEntrance().getClient().getLastInteraction();
        if (interaction != null && interaction.getTarget() == Interaction.Target.ENTITY) {
            return EventResult.interruptFalse();
        }
        return switch (type) {
            default -> throw new MatchException(null, null);
            case InteractionType.ATTACK, InteractionType.USE_ITEM -> this.getEntrance().getStructureBuilder().onPlayerInteract(this.getRunningClient().getPlayer(), type, hand);
            case InteractionType.UNKNOWN -> EventResult.pass();
        };
    }

    public synchronized void onClientStart(Client client) {
        this.setRunningClient(client);
    }

    public void onClientTick(Client client, ClientTick.Phase phase) {
        switch (phase) {
            case START: {
                this.tickCooldown();
                this.tooltipRenderer.tick();
                this.operationsRenderer.tick();
                this.outlineRenderer.tick();
                this.patternRenderer.tick();
                break;
            }
        }
    }

    public void onRenderGui(Renderer renderer, float deltaTick) {
        if (this.getRunningClient().getPanel() != null && !(this.getRunningClient().getPanel() instanceof EffortlessStructureScreen)) {
            return;
        }
        this.getTooltipRenderer().renderGuiOverlay(renderer, deltaTick);
    }

    public void onRenderEnd(Renderer renderer, float deltaTick) {
        this.patternRenderer.render(renderer, deltaTick);
        this.outlineRenderer.render(renderer, deltaTick);
        this.operationsRenderer.render(renderer, deltaTick);
    }

    public void onRegisterShader(RegisterShader.ShadersSink sink) {
        BlockShaders.TINTED_OUTLINE.register(sink);
        for (Shaders value : Shaders.values()) {
            value.register(sink);
        }
    }
}
