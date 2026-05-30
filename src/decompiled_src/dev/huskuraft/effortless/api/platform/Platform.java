/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.Environment;
import dev.huskuraft.effortless.api.platform.LoaderType;
import dev.huskuraft.effortless.api.platform.Mod;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface Platform {
    public static Platform getInstance() {
        return PlatformLoader.getSingleton(new Platform[0]);
    }

    public LoaderType getLoaderType();

    public String getLoaderVersion();

    public String getGameVersion();

    public List<Mod> getRunningMods();

    default public Optional<Mod> findMod(String modId) {
        return this.getRunningMods().stream().filter(mod -> mod.getId().equals(modId)).findFirst();
    }

    public Path getGameDir();

    default public Path getConfigDir() {
        return this.getGameDir().resolve("config");
    }

    public Environment getEnvironment();

    public boolean isDevelopment();
}
