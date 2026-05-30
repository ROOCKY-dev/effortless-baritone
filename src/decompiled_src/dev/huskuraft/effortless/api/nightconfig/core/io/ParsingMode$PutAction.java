/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import java.util.List;

@FunctionalInterface
private static interface ParsingMode.PutAction {
    public Object put(Config var1, List<String> var2, Object var3);

    default public Object put(Config config, String key, Object value) {
        return this.put(config, StringUtils.split(key, '.'), value);
    }
}
