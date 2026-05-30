/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.input.KeyBinding;
import dev.huskuraft.effortless.api.platform.PlatformReference;

public interface Options
extends PlatformReference {
    public KeyBinding keyUp();

    public KeyBinding keyLeft();

    public KeyBinding keyDown();

    public KeyBinding keyRight();

    public KeyBinding keyJump();

    public KeyBinding keyShift();

    public KeyBinding keySprint();

    public KeyBinding keyInventory();

    public KeyBinding keySwapOffhand();

    public KeyBinding keyDrop();

    public KeyBinding keyUse();

    public KeyBinding keyAttack();

    public KeyBinding keyPickItem();

    public KeyBinding keyChat();

    public KeyBinding keyPlayerList();

    public KeyBinding keyCommand();

    public KeyBinding keySocialInteractions();

    public KeyBinding keyScreenshot();

    public KeyBinding keyTogglePerspective();

    public KeyBinding keySmoothCamera();

    public KeyBinding keyFullscreen();

    public KeyBinding keySpectatorOutlines();

    public KeyBinding keyAdvancements();

    public KeyBinding keySaveHotbarActivator();

    public KeyBinding keyLoadHotbarActivator();

    public KeyBinding[] keyHotbarSlots();

    public int renderDistance();
}
