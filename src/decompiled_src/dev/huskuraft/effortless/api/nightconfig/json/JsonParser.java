/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.json;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingMode;
import dev.huskuraft.effortless.api.nightconfig.core.io.ReaderInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import dev.huskuraft.effortless.api.nightconfig.core.utils.FastStringReader;
import dev.huskuraft.effortless.api.nightconfig.json.JsonFormat;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JsonParser
implements ConfigParser<Config> {
    private static final char[] SPACES = new char[]{' ', '\t', '\n', '\r'};
    private static final char[] TRUE_LAST = new char[]{'r', 'u', 'e'};
    private static final char[] FALSE_LAST = new char[]{'a', 'l', 's', 'e'};
    private static final char[] NULL_LAST = new char[]{'u', 'l', 'l'};
    private static final char[] NUMBER_END = new char[]{',', '}', ']', ' ', '\t', '\n', '\r'};
    private final ConfigFormat<Config> configFormat;
    private boolean emptyDataAccepted = false;

    public JsonParser() {
        this(JsonFormat.fancyInstance());
    }

    JsonParser(ConfigFormat<Config> configFormat) {
        this.configFormat = configFormat;
    }

    @Override
    public ConfigFormat<Config> getFormat() {
        return this.configFormat;
    }

    public boolean isEmptyDataAccepted() {
        return this.emptyDataAccepted;
    }

    public JsonParser setEmptyDataAccepted(boolean emptyDataAccepted) {
        this.emptyDataAccepted = emptyDataAccepted;
        return this;
    }

    public Object parseDocument(String json) {
        return this.parseDocument(new FastStringReader(json));
    }

    public Object parseDocument(Reader reader) {
        ReaderInput input = new ReaderInput(reader);
        if (input.peek() == -1) {
            if (this.emptyDataAccepted) {
                return this.configFormat.createConfig();
            }
            throw new ParsingException("No json data: input is empty");
        }
        char firstChar = input.readCharAndSkip(SPACES);
        if (firstChar == '{') {
            return this.parseObject(input, this.configFormat.createConfig(), ParsingMode.MERGE);
        }
        if (firstChar == '[') {
            return this.parseArray(input, new ArrayList(), ParsingMode.MERGE);
        }
        throw new ParsingException("Invalid first character for a json document: " + firstChar);
    }

    @Override
    public Config parse(Reader reader) {
        Object config = JsonFormat.minimalInstance().createConfig();
        this.parse(reader, (Config)config, ParsingMode.MERGE);
        return config;
    }

    @Override
    public void parse(Reader reader, Config destination, ParsingMode parsingMode) {
        ReaderInput input = new ReaderInput(reader);
        if (input.peek() == -1) {
            if (this.emptyDataAccepted) {
                return;
            }
            throw new ParsingException("No json data: input is empty");
        }
        char firstChar = input.readCharAndSkip(SPACES);
        if (firstChar != '{') {
            throw new ParsingException("Invalid first character for a json object: " + firstChar);
        }
        parsingMode.prepareParsing(destination);
        this.parseObject(input, destination, parsingMode);
    }

    public <T> List<T> parseList(String json) {
        return this.parseList(new FastStringReader(json));
    }

    public <T> List<T> parseList(Reader reader) {
        ArrayList list = new ArrayList();
        this.parseList(reader, list, ParsingMode.MERGE);
        return list;
    }

    public void parseList(Reader reader, List<?> destination, ParsingMode parsingMode) {
        ReaderInput input = new ReaderInput(reader);
        if (input.peek() == -1) {
            if (this.emptyDataAccepted) {
                return;
            }
            throw new ParsingException("No json data: input is empty");
        }
        char firstChar = input.readCharAndSkip(SPACES);
        if (firstChar != '[') {
            throw new ParsingException("Invalid first character for a json array: " + firstChar);
        }
        this.parseArray(input, destination, parsingMode);
    }

    private <T extends Config> T parseObject(CharacterInput input, T config, ParsingMode parsingMode) {
        char kfirst = input.readCharAndSkip(SPACES);
        if (kfirst == '}') {
            return config;
        }
        if (kfirst != '\"') {
            throw new ParsingException("Invalid beginning of a key: " + kfirst);
        }
        this.parseKVPair(input, config, parsingMode);
        char vsep;
        while ((vsep = input.readCharAndSkip(SPACES)) != '}') {
            if (vsep != ',') {
                throw new ParsingException("Invalid value separator: " + vsep);
            }
            kfirst = input.readCharAndSkip(SPACES);
            if (kfirst != '\"') {
                throw new ParsingException("Invalid beginning of a key: " + kfirst);
            }
            this.parseKVPair(input, config, parsingMode);
        }
        return config;
    }

    private void parseKVPair(CharacterInput input, Config config, ParsingMode parsingMode) {
        List<String> key = Collections.singletonList(this.parseString(input));
        char sep = input.readCharAndSkip(SPACES);
        if (sep != ':') {
            throw new ParsingException("Invalid key-value separator: " + sep);
        }
        char vfirst = input.readCharAndSkip(SPACES);
        Object value = this.parseValue(input, vfirst, parsingMode);
        parsingMode.put(config, key, value);
    }

    private <T> List<T> parseArray(CharacterInput input, List<T> list, ParsingMode parsingMode) {
        char valueFirst;
        char next;
        boolean first = true;
        do {
            valueFirst = input.readCharAndSkip(SPACES);
            if (first && valueFirst == ']') {
                return list;
            }
            first = false;
            Object value = this.parseValue(input, valueFirst, parsingMode);
            list.add(value);
            next = input.readCharAndSkip(SPACES);
            if (next != ']') continue;
            return list;
        } while (next == ',');
        throw new ParsingException("Invalid value separator: " + valueFirst);
    }

    private Object parseValue(CharacterInput input, char firstChar, ParsingMode parsingMode) {
        switch (firstChar) {
            case '\"': {
                return this.parseString(input);
            }
            case '{': {
                return this.parseObject(input, this.configFormat.createConfig(), parsingMode);
            }
            case '[': {
                return this.parseArray(input, new ArrayList(), parsingMode);
            }
            case 't': {
                return this.parseTrue(input);
            }
            case 'f': {
                return this.parseFalse(input);
            }
            case 'n': {
                return this.parseNull(input);
            }
        }
        input.pushBack(firstChar);
        return this.parseNumber(input);
    }

    private Number parseNumber(CharacterInput input) {
        int small;
        CharsWrapper chars = input.readCharsUntil(NUMBER_END);
        if (chars.contains('.') || chars.contains('e') || chars.contains('E')) {
            return Utils.parseDouble(chars);
        }
        long l = Utils.parseLong(chars, 10);
        if (l == (long)(small = (int)l)) {
            return small;
        }
        return l;
    }

    private boolean parseTrue(CharacterInput input) {
        CharsWrapper chars = input.readChars(3);
        if (!chars.contentEquals(TRUE_LAST)) {
            throw new ParsingException("Invalid value: t" + chars + " - expected boolean true");
        }
        return true;
    }

    private boolean parseFalse(CharacterInput input) {
        CharsWrapper chars = input.readChars(4);
        if (!chars.contentEquals(FALSE_LAST)) {
            throw new ParsingException("Invalid value: f" + chars + " - expected boolean false");
        }
        return false;
    }

    private Object parseNull(CharacterInput input) {
        CharsWrapper chars = input.readChars(3);
        if (!chars.contentEquals(NULL_LAST)) {
            throw new ParsingException("Invaid value: n" + chars + " - expected null");
        }
        return null;
    }

    private String parseString(CharacterInput input) {
        char c;
        StringBuilder builder = new StringBuilder();
        boolean escape = false;
        while ((c = input.readChar()) != '\"' || escape) {
            if (escape) {
                builder.append(this.escape(c, input));
                escape = false;
                continue;
            }
            if (c == '\\') {
                escape = true;
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private char escape(char c, CharacterInput input) {
        switch (c) {
            case '\"': 
            case '/': 
            case '\\': {
                return c;
            }
            case 'b': {
                return '\b';
            }
            case 'f': {
                return '\f';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 't': {
                return '\t';
            }
            case 'u': {
                CharsWrapper chars = input.readChars(4);
                return (char)Utils.parseInt(chars, 16);
            }
        }
        throw new ParsingException("Invalid escapement: \\" + c);
    }
}
