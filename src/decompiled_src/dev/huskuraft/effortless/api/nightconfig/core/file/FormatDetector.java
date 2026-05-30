/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class FormatDetector {
    private static final Map<String, Supplier<ConfigFormat<?>>> registry = new ConcurrentHashMap();

    public static void registerExtension(String fileExtension, ConfigFormat<?> format) {
        registry.put(fileExtension, () -> format);
    }

    public static void registerExtension(String fileExtension, Supplier<ConfigFormat<?>> formatSupplier) {
        registry.put(fileExtension, formatSupplier);
    }

    public static ConfigFormat<?> detect(File file) {
        return FormatDetector.detectByName(file.getName());
    }

    public static ConfigFormat<?> detect(Path file) {
        return FormatDetector.detectByName(file.getFileName().toString());
    }

    public static ConfigFormat<?> detectByName(String fileName) {
        List<String> splitted = StringUtils.split(fileName, '.');
        String fileExtension = splitted.get(splitted.size() - 1);
        Supplier<ConfigFormat<?>> supplier = registry.get(fileExtension);
        return supplier == null ? null : supplier.get();
    }

    private static void tryLoad(String className) {
        try {
            Class.forName(className);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }

    private FormatDetector() {
    }

    static {
        FormatDetector.tryLoad("dev.huskuraft.effortless.api.nightconfig.toml.TomlFormat");
        FormatDetector.tryLoad("dev.huskuraft.effortless.api.nightconfig.hocon.HoconFormat");
        FormatDetector.tryLoad("dev.huskuraft.effortless.api.nightconfig.json.JsonFormat");
        FormatDetector.tryLoad("dev.huskuraft.effortless.api.nightconfig.yaml.YamlFormat");
    }
}
