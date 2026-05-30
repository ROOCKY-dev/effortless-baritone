/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.toml.Toml;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlParser;
import dev.huskuraft.effortless.api.nightconfig.toml.ValueParser;
import java.util.List;

final class ArrayParser {
    static List<?> parse(CharacterInput input, TomlParser parser) {
        char after;
        List list = parser.createList();
        do {
            char firstChar;
            if ((firstChar = Toml.readUsefulChar(input)) == ']') {
                return list;
            }
            if (firstChar == ',') {
                char nextChar = Toml.readUsefulChar(input);
                if (nextChar == ']') {
                    return list;
                }
                throw new ParsingException("Unexpected character in array: '" + nextChar + "' - Expected end of array because of the leading comma.");
            }
            Object value = ValueParser.parse(input, firstChar, parser);
            list.add(value);
            after = Toml.readUsefulChar(input);
            if (after != ']') continue;
            return list;
        } while (after == ',');
        throw new ParsingException("Invalid separator '" + after + "' in array.");
    }

    private ArrayParser() {
    }
}
