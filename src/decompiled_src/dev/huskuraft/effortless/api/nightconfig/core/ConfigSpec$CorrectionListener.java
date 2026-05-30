/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigSpec;
import java.util.List;

@FunctionalInterface
public static interface ConfigSpec.CorrectionListener {
    public void onCorrect(ConfigSpec.CorrectionAction var1, List<String> var2, Object var3, Object var4);
}
