/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.platform.PlatformUtils;

public interface Window
extends PlatformReference {
    public int getWidth();

    public int getHeight();

    public int getGuiScaledWidth();

    public int getGuiScaledHeight();

    public double getGuiScaledFactor();

    public boolean isKeyDown(int var1);

    public boolean isMouseButtonDown(int var1);

    default public boolean isControlDown() {
        if (PlatformUtils.isMacOS()) {
            return this.isKeyDown(343) || this.isKeyDown(347);
        }
        return this.isKeyDown(341) || this.isKeyDown(345);
    }

    default public boolean isShiftDown() {
        return this.isKeyDown(340) || this.isKeyDown(344);
    }

    default public boolean isAltDown() {
        return this.isKeyDown(342) || this.isKeyDown(346);
    }

    default public boolean isCut(int key) {
        return key == 88 && this.isControlDown() && !this.isShiftDown() && !this.isAltDown();
    }

    default public boolean isPaste(int key) {
        return key == 86 && this.isControlDown() && !this.isShiftDown() && !this.isAltDown();
    }

    default public boolean isCopy(int key) {
        return key == 67 && this.isControlDown() && !this.isShiftDown() && !this.isAltDown();
    }

    default public boolean isSelectAll(int key) {
        return key == 65 && this.isControlDown() && !this.isShiftDown() && !this.isAltDown();
    }
}
