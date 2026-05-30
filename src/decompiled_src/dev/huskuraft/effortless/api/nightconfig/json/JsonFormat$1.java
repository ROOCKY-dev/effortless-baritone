/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.json;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.json.FancyJsonWriter;
import dev.huskuraft.effortless.api.nightconfig.json.JsonFormat;
import dev.huskuraft.effortless.api.nightconfig.json.JsonParser;

class JsonFormat.1
extends JsonFormat<FancyJsonWriter> {
    JsonFormat.1() {
        super(null);
    }

    @Override
    public FancyJsonWriter createWriter() {
        return new FancyJsonWriter();
    }

    @Override
    public ConfigParser<Config> createParser() {
        return new JsonParser(this);
    }
}
