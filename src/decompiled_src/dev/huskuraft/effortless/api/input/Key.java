/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.input;

import dev.huskuraft.effortless.api.platform.ClientEntrance;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.text.Text;

public interface Key
extends PlatformReference {
    public String getName();

    public int getValue();

    default public boolean isDown() {
        return ClientEntrance.getInstance().getClient().getWindow().isKeyDown(this.getValue());
    }

    default public Text getNameText() {
        return Text.translate(this.getName());
    }
}
