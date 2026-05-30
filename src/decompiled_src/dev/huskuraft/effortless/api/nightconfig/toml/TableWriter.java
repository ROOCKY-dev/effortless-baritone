/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableCommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import dev.huskuraft.effortless.api.nightconfig.toml.ArrayWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.ValueWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class TableWriter {
    private static final char[] KEY_VALUE_SEPARATOR = new char[]{' ', '=', ' '};
    private static final char[] INLINE_ENTRY_SEPARATOR = ArrayWriter.ELEMENT_SEPARATOR;
    private static final char[] ARRAY_OF_TABLES_NAME_BEGIN = new char[]{'[', '['};
    private static final char[] ARRAY_OF_TABLES_NAME_END = new char[]{']', ']'};
    private static final char[] TABLE_NAME_BEGIN = new char[]{'['};
    private static final char[] TABLE_NAME_END = new char[]{']'};

    static void writeInline(UnmodifiableConfig config, CharacterOutput output, TomlWriter writer) {
        output.write('{');
        Iterator<Map.Entry<String, Object>> iterator = config.valueMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            writer.writeKey(key, output);
            output.write(KEY_VALUE_SEPARATOR);
            ValueWriter.write(value, output, writer);
            if (!iterator.hasNext()) continue;
            output.write(INLINE_ENTRY_SEPARATOR);
        }
        output.write('}');
    }

    static void writeNormal(UnmodifiableConfig config, List<String> configPath, CharacterOutput output, TomlWriter writer) {
        UnmodifiableCommentedConfig commentedConfig = UnmodifiableCommentedConfig.fake(config);
        TableWriter.writeNormal(commentedConfig, configPath, output, writer);
    }

    private static void writeNormal(UnmodifiableCommentedConfig config, List<String> configPath, CharacterOutput output, TomlWriter writer) {
        ArrayList<UnmodifiableCommentedConfig.Entry> tablesEntries = new ArrayList<UnmodifiableCommentedConfig.Entry>();
        ArrayList<UnmodifiableCommentedConfig.Entry> tableArraysEntries = new ArrayList<UnmodifiableCommentedConfig.Entry>();
        writer.increaseIndentLevel();
        for (UnmodifiableCommentedConfig.Entry entry : config.entrySet()) {
            List list;
            String key = entry.getKey();
            Object value = entry.getValue();
            String comment = entry.getComment();
            if (value instanceof UnmodifiableConfig && !writer.writesInline((UnmodifiableConfig)value)) {
                tablesEntries.add(entry);
                continue;
            }
            if (value instanceof List && !(list = (List)value).isEmpty()) {
                if (list.stream().allMatch(UnmodifiableConfig.class::isInstance)) {
                    tableArraysEntries.add(entry);
                    continue;
                }
            }
            writer.writeComment(comment, output);
            writer.writeIndent(output);
            writer.writeKey(key, output);
            output.write(KEY_VALUE_SEPARATOR);
            ValueWriter.write(value, output, writer);
            writer.writeNewline(output);
        }
        int nonSimpleValuesCount = tablesEntries.size() + tableArraysEntries.size();
        int n = config.size() - nonSimpleValuesCount;
        if (n > 0 && nonSimpleValuesCount > 0) {
            writer.writeNewline(output);
        }
        for (UnmodifiableCommentedConfig.Entry entry : tablesEntries) {
            writer.writeComment(entry.getComment(), output);
            configPath.add(entry.getKey());
            TableWriter.writeTableName(configPath, output, writer);
            writer.writeNewline(output);
            TableWriter.writeNormal((UnmodifiableConfig)entry.getValue(), configPath, output, writer);
            configPath.remove(configPath.size() - 1);
        }
        for (UnmodifiableCommentedConfig.Entry entry : tableArraysEntries) {
            writer.writeComment(entry.getComment(), output);
            configPath.add(entry.getKey());
            List tableArray = (List)entry.getValue();
            for (UnmodifiableConfig table : tableArray) {
                TableWriter.writeTableArrayName(configPath, output, writer);
                writer.writeNewline(output);
                TableWriter.writeNormal(table, configPath, output, writer);
            }
            configPath.remove(configPath.size() - 1);
        }
        writer.decreaseIndentLevel();
    }

    private static void writeTableArrayName(List<String> name, CharacterOutput output, TomlWriter writer) {
        TableWriter.writeTableName(name, output, writer, ARRAY_OF_TABLES_NAME_BEGIN, ARRAY_OF_TABLES_NAME_END);
    }

    private static void writeTableName(List<String> name, CharacterOutput output, TomlWriter writer) {
        TableWriter.writeTableName(name, output, writer, TABLE_NAME_BEGIN, TABLE_NAME_END);
    }

    private static void writeTableName(List<String> name, CharacterOutput output, TomlWriter writer, char[] begin, char[] end) {
        if (name.isEmpty()) {
            throw new WritingException("Invalid empty table name.");
        }
        writer.writeIndent(output);
        output.write(begin);
        Iterator<String> it = name.iterator();
        writer.writeKey(it.next(), output);
        while (it.hasNext()) {
            output.write('.');
            writer.writeKey(it.next(), output);
        }
        output.write(end);
    }

    private TableWriter() {
    }
}
