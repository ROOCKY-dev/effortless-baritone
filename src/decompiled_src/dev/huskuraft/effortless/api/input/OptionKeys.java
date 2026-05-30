/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.input;

import dev.huskuraft.effortless.api.input.KeyBinding;
import dev.huskuraft.effortless.api.input.KeyBindingOwner;
import dev.huskuraft.effortless.api.platform.ClientEntrance;
import dev.huskuraft.effortless.api.platform.Options;

public enum OptionKeys implements KeyBindingOwner
{
    KEY_UP,
    KEY_LEFT,
    KEY_DOWN,
    KEY_RIGHT,
    KEY_JUMP,
    KEY_SHIFT,
    KEY_SPRINT,
    KEY_INVENTORY,
    KEY_SWAP_OFFHAND,
    KEY_DROP,
    KEY_USE,
    KEY_ATTACK,
    KEY_PICK_ITEM,
    KEY_CHAT,
    KEY_PLAYER_LIST,
    KEY_COMMAND,
    KEY_SOCIAL_INTERACTIONS,
    KEY_SCREENSHOT,
    KEY_TOGGLE_PERSPECTIVE,
    KEY_SMOOTH_CAMERA,
    KEY_FULLSCREEN,
    KEY_SPECTATOR_OUTLINES,
    KEY_ADVANCEMENTS,
    KEY_HOTBAR_SLOTS_1,
    KEY_HOTBAR_SLOTS_2,
    KEY_HOTBAR_SLOTS_3,
    KEY_HOTBAR_SLOTS_4,
    KEY_HOTBAR_SLOTS_5,
    KEY_HOTBAR_SLOTS_6,
    KEY_HOTBAR_SLOTS_7,
    KEY_HOTBAR_SLOTS_8,
    KEY_HOTBAR_SLOTS_9,
    KEY_SAVE_HOTBAR_ACTIVATOR,
    KEY_LOAD_HOTBAR_ACTIVATOR;


    @Override
    public KeyBinding getKeyBinding() {
        Options options = ClientEntrance.getInstance().getClient().getOptions();
        return switch (this.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> options.keyUp();
            case 1 -> options.keyLeft();
            case 2 -> options.keyDown();
            case 3 -> options.keyRight();
            case 4 -> options.keyJump();
            case 5 -> options.keyShift();
            case 6 -> options.keySprint();
            case 7 -> options.keyInventory();
            case 8 -> options.keySwapOffhand();
            case 9 -> options.keyDrop();
            case 10 -> options.keyUse();
            case 11 -> options.keyAttack();
            case 12 -> options.keyPickItem();
            case 13 -> options.keyChat();
            case 14 -> options.keyPlayerList();
            case 15 -> options.keyCommand();
            case 16 -> options.keySocialInteractions();
            case 17 -> options.keyScreenshot();
            case 18 -> options.keyTogglePerspective();
            case 19 -> options.keySmoothCamera();
            case 20 -> options.keyFullscreen();
            case 21 -> options.keySpectatorOutlines();
            case 22 -> options.keyAdvancements();
            case 23 -> options.keyHotbarSlots()[0];
            case 24 -> options.keyHotbarSlots()[1];
            case 25 -> options.keyHotbarSlots()[2];
            case 26 -> options.keyHotbarSlots()[3];
            case 27 -> options.keyHotbarSlots()[4];
            case 28 -> options.keyHotbarSlots()[5];
            case 29 -> options.keyHotbarSlots()[6];
            case 30 -> options.keyHotbarSlots()[7];
            case 31 -> options.keyHotbarSlots()[8];
            case 32 -> options.keySaveHotbarActivator();
            case 33 -> options.keyLoadHotbarActivator();
        };
    }
}
