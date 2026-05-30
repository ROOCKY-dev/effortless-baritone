/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigSpec;

public class ConcurrentConfigSpec
extends ConfigSpec {
    public ConcurrentConfigSpec() {
        super(Config.inMemoryUniversalConcurrent());
    }
}
