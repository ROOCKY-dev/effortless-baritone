/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.json;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.file.FormatDetector;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.utils.WriterSupplier;
import dev.huskuraft.effortless.api.nightconfig.json.FancyJsonWriter;
import dev.huskuraft.effortless.api.nightconfig.json.JsonParser;
import dev.huskuraft.effortless.api.nightconfig.json.MinimalJsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.function.Supplier;

public abstract class JsonFormat<W extends ConfigWriter>
implements ConfigFormat<Config> {
    private static final JsonFormat<FancyJsonWriter> FANCY = new JsonFormat<FancyJsonWriter>(){

        @Override
        public FancyJsonWriter createWriter() {
            return new FancyJsonWriter();
        }

        @Override
        public ConfigParser<Config> createParser() {
            return new JsonParser(this);
        }
    };
    private static final JsonFormat<MinimalJsonWriter> MINIMAL = new JsonFormat<MinimalJsonWriter>(){

        @Override
        public MinimalJsonWriter createWriter() {
            return new MinimalJsonWriter();
        }

        @Override
        public ConfigParser<Config> createParser() {
            return new JsonParser(this);
        }
    };

    public static JsonFormat<FancyJsonWriter> fancyInstance() {
        return FANCY;
    }

    public static JsonFormat<MinimalJsonWriter> minimalInstance() {
        return MINIMAL;
    }

    public static JsonFormat<FancyJsonWriter> emptyTolerantInstance() {
        return new JsonFormat<FancyJsonWriter>(){

            @Override
            public FancyJsonWriter createWriter() {
                return new FancyJsonWriter();
            }

            @Override
            public ConfigParser<Config> createParser() {
                return new JsonParser(this).setEmptyDataAccepted(true);
            }
        };
    }

    public static JsonFormat<MinimalJsonWriter> minimalEmptyTolerantInstance() {
        return new JsonFormat<MinimalJsonWriter>(){

            @Override
            public MinimalJsonWriter createWriter() {
                return new MinimalJsonWriter();
            }

            @Override
            public ConfigParser<Config> createParser() {
                return new JsonParser(this).setEmptyDataAccepted(true);
            }
        };
    }

    public static Config newConfig() {
        return FANCY.createConfig();
    }

    public static Config newConfig(Supplier<Map<String, Object>> s) {
        return FANCY.createConfig(s);
    }

    public static Config newConcurrentConfig() {
        return FANCY.createConcurrentConfig();
    }

    private JsonFormat() {
    }

    public abstract W createWriter();

    @Override
    public abstract ConfigParser<Config> createParser();

    @Override
    public Config createConfig(Supplier<Map<String, Object>> mapCreator) {
        return Config.of(mapCreator, this);
    }

    @Override
    public boolean supportsComments() {
        return false;
    }

    @Override
    public void initEmptyFile(WriterSupplier ws) throws IOException {
        try (Writer writer = ws.get();){
            writer.write("{}");
        }
    }

    static {
        FormatDetector.registerExtension("json", FANCY);
    }
}
