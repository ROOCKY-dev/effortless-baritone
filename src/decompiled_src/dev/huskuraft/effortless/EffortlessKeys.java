/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless;

import dev.huskuraft.effortless.api.input.KeyBinding;
import dev.huskuraft.effortless.api.input.KeyBindingOwner;
import dev.huskuraft.effortless.api.input.KeyCodes;

public enum EffortlessKeys implements KeyBindingOwner
{
    SETTINGS("settings", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    BUILD_MODE_RADIAL("build_mode_radial", Category.DEFAULT, KeyCodes.KEY_LEFT_ALT),
    PASSIVE_BUILD_MODIFIER("passive_build_modifier", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    UNDO("undo", Category.DEFAULT, KeyCodes.KEY_LEFT_BRACKET),
    REDO("redo", Category.DEFAULT, KeyCodes.KEY_RIGHT_BRACKET),
    TOGGLE_CLIPBOARD("toggle_clipboard", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    TOGGLE_PATTERN("toggle_pattern", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    TOGGLE_REPLACE("toggle_replace", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    EDIT_CLIPBOARD("edit_clipboard", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    EDIT_PATTERN("edit_pattern", Category.DEFAULT, KeyCodes.KEY_UNKNOWN),
    MOVE_LEFT("move_left", Category.CLIPBOARD, KeyCodes.KEY_LEFT),
    MOVE_RIGHT("move_right", Category.CLIPBOARD, KeyCodes.KEY_RIGHT),
    MOVE_FORWARD("move_forward", Category.CLIPBOARD, KeyCodes.KEY_UP),
    MOVE_BACKWARD("move_backward", Category.CLIPBOARD, KeyCodes.KEY_DOWN),
    MOVE_UP("move_up", Category.CLIPBOARD, KeyCodes.KEY_PAGE_UP),
    MOVE_DOWN("move_down", Category.CLIPBOARD, KeyCodes.KEY_PAGE_DOWN),
    ROTATE_X("rotate_x", Category.CLIPBOARD, KeyCodes.KEY_UNKNOWN),
    ROTATE_Y("rotate_y", Category.CLIPBOARD, KeyCodes.KEY_UNKNOWN),
    ROTATE_Z("rotate_z", Category.CLIPBOARD, KeyCodes.KEY_UNKNOWN),
    MIRROR_X("mirror_x", Category.CLIPBOARD, KeyCodes.KEY_UNKNOWN),
    MIRROR_Y("mirror_y", Category.CLIPBOARD, KeyCodes.KEY_UNKNOWN),
    MIRROR_Z("mirror_z", Category.CLIPBOARD, KeyCodes.KEY_UNKNOWN);

    private final KeyBinding keyBinding;

    private EffortlessKeys(String description, Category category, KeyCodes defaultKey) {
        this.keyBinding = switch (category.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> KeyBinding.of("key.effortless.%s.desc".formatted(description), "key.effortless.category.default", defaultKey.value());
            case 1 -> KeyBinding.of("key.effortless.%s.desc".formatted(description), "key.effortless.category.clipboard", defaultKey.value());
        };
    }

    @Override
    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public static enum Category {
        DEFAULT,
        CLIPBOARD;

    }
}
