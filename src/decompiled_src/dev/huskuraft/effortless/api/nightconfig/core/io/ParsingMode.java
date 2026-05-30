/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum ParsingMode {
    REPLACE(Config::clear, Config::set, Map::put),
    MERGE(c -> {}, Config::set, Map::put),
    ADD(c -> {}, (cfg, path, value) -> {
        cfg.add(path, value);
        return null;
    }, Map::putIfAbsent);

    private final Consumer<? super Config> preparationAction;
    private final PutAction putAction;
    private final MapPutAction mapPutAction;

    private ParsingMode(Consumer<? super Config> preparationAction, PutAction putAction, MapPutAction mapPutAction) {
        this.preparationAction = preparationAction;
        this.putAction = putAction;
        this.mapPutAction = mapPutAction;
    }

    public void prepareParsing(Config config) {
        this.preparationAction.accept(config);
    }

    public Object put(Config config, List<String> key, Object value) {
        return this.putAction.put(config, key, value);
    }

    public Object put(Config config, String key, Object value) {
        return this.putAction.put(config, key, value);
    }

    public Object put(Map<String, Object> map, String key, Object value) {
        return this.mapPutAction.put(map, key, value);
    }

    @FunctionalInterface
    private static interface PutAction {
        public Object put(Config var1, List<String> var2, Object var3);

        default public Object put(Config config, String key, Object value) {
            return this.put(config, StringUtils.split(key, '.'), value);
        }
    }

    @FunctionalInterface
    private static interface MapPutAction {
        public Object put(Map<String, Object> var1, String var2, Object var3);
    }
}
