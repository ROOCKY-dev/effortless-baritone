/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.json;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.json.JsonFormat;
import dev.huskuraft.effortless.api.nightconfig.json.JsonParser;
import dev.huskuraft.effortless.api.nightconfig.json.MinimalJsonWriter;

class JsonFormat.2
extends JsonFormat<MinimalJsonWriter> {
    JsonFormat.2() {
        super(null);
    }

    @Override
    public MinimalJsonWriter createWriter() {
        return new MinimalJsonWriter();
    }

    @Override
    public ConfigParser<Config> createParser() {
        return new JsonParser(this);
    }
}
