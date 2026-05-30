/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.session;

import dev.huskuraft.effortless.api.platform.LoaderType;
import dev.huskuraft.effortless.api.platform.Mod;
import java.util.List;

public record Session(LoaderType loaderType, String loaderVersion, String gameVersion, List<Mod> mods, int protocolVersion) {
}
