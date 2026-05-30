/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.config;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigSpec;

public interface ConfigSerializer<T> {
    public ConfigSpec getSpec(Config var1);

    public T getDefault();

    public T deserialize(Config var1);

    public Config serialize(T var1);

    default public void validate(Config config) {
        this.getSpec(config).correct(config);
    }

    default public boolean isCorrect(Object config) {
        Config config1;
        return config instanceof Config && this.getSpec(config1 = (Config)config).isCorrect(config1);
    }
}
