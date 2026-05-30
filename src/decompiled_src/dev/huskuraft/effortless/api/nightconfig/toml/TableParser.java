/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.toml.StringParser;
import dev.huskuraft.effortless.api.nightconfig.toml.Toml;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlFormat;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlParser;
import dev.huskuraft.effortless.api.nightconfig.toml.ValueParser;
import java.util.ArrayList;
import java.util.List;

final class TableParser {
    private static final char[] KEY_END = new char[]{'\t', ' ', '=', '.', '\n', '\r', ']', ':'};

    static CommentedConfig parseInline(CharacterInput input, TomlParser parser) {
        char after;
        CommentedConfig config = (CommentedConfig)TomlFormat.instance().createConfig();
        parser.registerInlineTable(config);
        do {
            char keyFirst;
            if ((keyFirst = Toml.readNonSpaceChar(input, false)) == '}') {
                return config;
            }
            String key = TableParser.parseKey(input, keyFirst, parser);
            char sep = Toml.readNonSpaceChar(input, false);
            TableParser.checkInvalidSeparator(sep, key, parser);
            Object value = ValueParser.parse(input, parser);
            Object previous = parser.getParsingMode().put(config.valueMap(), key, value);
            TableParser.checkDuplicateKey(key, previous, true);
            after = Toml.readNonSpaceChar(input, false);
            if (after != '}') continue;
            return config;
        } while (after == ',');
        throw new ParsingException("Invalid entry separator '" + after + "' in inline table.");
    }

    static <T extends CommentedConfig> T parseNormal(CharacterInput input, TomlParser parser, T config) {
        while (true) {
            ArrayList<CharsWrapper> commentsList;
            int keyFirst;
            if ((keyFirst = Toml.readUseful(input, commentsList = new ArrayList<CharsWrapper>(2))) == -1 || keyFirst == 91) {
                parser.setComment(commentsList);
                return config;
            }
            List<String> key = TableParser.parseDottedKey(input, (char)keyFirst, parser);
            Object value = ValueParser.parse(input, parser);
            Object previous = parser.getParsingMode().put(config, key, value);
            TableParser.checkDuplicateKey(key, previous, parser.configWasEmpty());
            int after = Toml.readNonSpace(input, false);
            if (after == -1) {
                return config;
            }
            if (after == 35) {
                CharsWrapper comment = Toml.readLine(input);
                commentsList.add(comment);
            } else if (after != 10 && after != 13) {
                throw new ParsingException("Invalid character '" + (char)after + "' after table entry \"" + key + "\" = " + value);
            }
            parser.setComment(commentsList);
            config.setComment(key, parser.consumeComment());
        }
    }

    private static void checkDuplicateKey(Object key, Object previousValue, boolean emptyConfig) {
        if (previousValue != null && emptyConfig) {
            throw new ParsingException("Invalid TOML data: entry \"" + key + "\" defined twice in its table.");
        }
    }

    private static void checkInvalidSeparator(char sep, String key, TomlParser parser) {
        if (!Toml.isKeyValueSeparator(sep, parser.isLenientWithSeparators())) {
            throw new ParsingException("Invalid separator '" + sep + "'after key \"" + key + "\" in some table.");
        }
    }

    static CommentedConfig parseNormal(CharacterInput input, TomlParser parser) {
        return TableParser.parseNormal(input, parser, (CommentedConfig)TomlFormat.instance().createConfig());
    }

    static List<String> parseTableName(CharacterInput input, TomlParser parser, boolean array) {
        char separator;
        List<String> list = parser.createList();
        do {
            char after;
            char firstChar;
            if ((firstChar = Toml.readNonSpaceChar(input, false)) == ']') {
                throw new ParsingException("Tables names must not be empty.");
            }
            String key = TableParser.parseKey(input, firstChar, parser);
            list.add(key);
            separator = Toml.readNonSpaceChar(input, false);
            if (separator != ']') continue;
            if (array && (after = input.readChar()) != ']') {
                throw new ParsingException("Invalid declaration of an element of an array of tables: it ends by ]" + after + " but should end by ]]");
            }
            after = Toml.readNonSpaceChar(input, false);
            if (after == '#') {
                CharsWrapper comment = Toml.readLine(input);
                parser.setComment(comment);
            } else if (after != '\n' && after != '\r') {
                throw new ParsingException("Invalid character '" + after + "' after a table declaration.");
            }
            return list;
        } while (separator == '.');
        throw new ParsingException("Invalid separator '" + separator + "' in table name.");
    }

    static List<String> parseDottedKey(CharacterInput input, char firstChar, TomlParser parser) {
        List<String> list = parser.createList();
        char first = firstChar;
        while (true) {
            String part = TableParser.parseKey(input, first, parser);
            list.add(part);
            char sep = Toml.readNonSpaceChar(input, false);
            if (Toml.isKeyValueSeparator(sep, parser.isLenientWithSeparators())) {
                return list;
            }
            if (sep != '.') {
                throw new ParsingException("Invalid character '" + sep + "' after key " + list);
            }
            first = Toml.readNonSpaceChar(input, false);
        }
    }

    static String parseKey(CharacterInput input, char firstChar, TomlParser parser) {
        if (firstChar == '\"') {
            return StringParser.parseBasic(input, parser);
        }
        if (firstChar == '\'') {
            return StringParser.parseLiteral(input, parser);
        }
        CharsWrapper restOfKey = input.readCharsUntil(KEY_END);
        String bareKey = new CharsWrapper.Builder(restOfKey.length() + 1).append(firstChar).append(restOfKey).toString();
        if (bareKey.isEmpty()) {
            throw new ParsingException("Empty bare keys aren't allowed.");
        }
        if (!Toml.isValidBareKey(bareKey, parser.isLenientWithBareKeys())) {
            throw new ParsingException("Invalid bare key: " + bareKey);
        }
        return bareKey;
    }

    private TableParser() {
    }
}
