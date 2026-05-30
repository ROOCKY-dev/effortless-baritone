/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.text.Text;

public interface Typeface
extends PlatformReference {
    public int measureHeight(Text var1);

    public int measureWidth(Text var1);

    public int measureHeight(String var1);

    public int measureWidth(String var1);

    public int getLineHeight();

    public String subtractByWidth(String var1, int var2, boolean var3);
}
