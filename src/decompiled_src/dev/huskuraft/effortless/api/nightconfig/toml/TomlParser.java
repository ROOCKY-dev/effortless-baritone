/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.CommentedConfig;
import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterInput;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigParser;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingMode;
import dev.huskuraft.effortless.api.nightconfig.core.io.ReaderInput;
import dev.huskuraft.effortless.api.nightconfig.toml.TableParser;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlFormat;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TomlParser
implements ConfigParser<CommentedConfig> {
    private int initialStringBuilderCapacity = 16;
    private int initialListCapacity = 10;
    private boolean lenientBareKeys = false;
    private boolean lenientSeparators = false;
    private boolean configWasEmpty = false;
    private ParsingMode parsingMode;
    private final Set<Config> inlineTables = Collections.newSetFromMap(new IdentityHashMap());
    private String currentComment;

    void registerInlineTable(Config table) {
        this.inlineTables.add(table);
    }

    boolean isInlineTable(Config table) {
        return this.inlineTables.contains(table);
    }

    private void clearParsingState() {
        this.inlineTables.clear();
    }

    @Override
    public CommentedConfig parse(Reader reader) {
        this.configWasEmpty = true;
        return this.parse(new ReaderInput(reader), (CommentedConfig)TomlFormat.instance().createConfig(), ParsingMode.MERGE);
    }

    @Override
    public void parse(Reader reader, Config destination, ParsingMode parsingMode) {
        if (parsingMode == ParsingMode.REPLACE) {
            this.configWasEmpty = true;
        }
        this.parse(new ReaderInput(reader), destination, parsingMode);
    }

    private <T extends Config> T parse(CharacterInput input, T destination, ParsingMode parsingMode) {
        int next;
        this.parsingMode = parsingMode;
        parsingMode.prepareParsing(destination);
        CommentedConfig commentedConfig = CommentedConfig.fake(destination);
        CommentedConfig rootTable = TableParser.parseNormal(input, this, commentedConfig);
        while ((next = input.peek()) != -1) {
            Config table;
            Map<String, Object> parentMap;
            boolean isArray;
            boolean bl = isArray = next == 91;
            if (isArray) {
                input.skipPeeks();
            }
            List<String> path = TableParser.parseTableName(input, this, isArray);
            int lastIndex = path.size() - 1;
            String lastKey = path.get(lastIndex);
            List<String> parentPath = path.subList(0, lastIndex);
            Config parentConfig = this.getSubTable(rootTable, parentPath);
            Map<String, Object> map = parentMap = parentConfig != null ? parentConfig.valueMap() : null;
            if (this.hasPendingComment()) {
                String comment = this.consumeComment();
                if (parentConfig instanceof CommentedConfig) {
                    List<String> lastPath = Collections.singletonList(lastKey);
                    ((CommentedConfig)parentConfig).setComment(lastPath, comment);
                }
            }
            if (isArray) {
                if (parentMap == null) {
                    throw new ParsingException("Cannot create entry " + path + " because of an invalid parent that isn't a table.");
                }
                CommentedConfig table2 = TableParser.parseNormal(input, this);
                List<T> arrayOfTables = (List<T>)parentMap.get(lastKey);
                if (arrayOfTables == null) {
                    arrayOfTables = this.createList();
                    parentMap.put(lastKey, arrayOfTables);
                }
                arrayOfTables.add(table2);
                continue;
            }
            if (parentMap == null) {
                throw new ParsingException("Cannot create entry " + path + " because of an invalid parent that isn't a table.");
            }
            Object alreadyDeclared = parentMap.get(lastKey);
            if (alreadyDeclared == null) {
                table = TableParser.parseNormal(input, this);
                parentMap.put(lastKey, table);
                continue;
            }
            if (alreadyDeclared instanceof Config) {
                table = (Config)alreadyDeclared;
                this.checkContainsOnlySubtables(table, path);
                CommentedConfig commentedTable = CommentedConfig.fake(table);
                TableParser.parseNormal(input, this, commentedTable);
                continue;
            }
            if (!this.configWasEmpty) continue;
            throw new ParsingException("Entry " + path + " has been defined twice.");
        }
        this.clearParsingState();
        return destination;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Config getSubTable(Config parentTable, List<String> path) {
        void var3_4;
        if (path.isEmpty()) {
            return parentTable;
        }
        Config config = parentTable;
        for (String key : path) {
            void var3_8;
            Object value = var3_4.valueMap().get(key);
            if (value == null) {
                Object sub = TomlFormat.instance().createConfig();
                var3_4.valueMap().put(key, sub);
                Object c = sub;
            } else if (value instanceof Config) {
                Config config2 = (Config)value;
            } else {
                if (!(value instanceof List)) return null;
                List list = (List)value;
                if (list.isEmpty()) return null;
                if (!list.stream().allMatch(Config.class::isInstance)) return null;
                int lastIndex = list.size() - 1;
                Config config3 = (Config)list.get(lastIndex);
            }
            if (!this.isInlineTable((Config)var3_8)) continue;
            throw new ParsingException("Cannot modify an inline table after its creation. Key path: " + path);
        }
        return var3_4;
    }

    private void checkContainsOnlySubtables(Config table, List<String> path) {
        for (Object value : table.valueMap().values()) {
            if (value instanceof Config) continue;
            throw new ParsingException("Table with path " + path + " has been declared twice.");
        }
    }

    public boolean isLenientWithSeparators() {
        return this.lenientSeparators;
    }

    public TomlParser setLenientWithSeparators(boolean lenientSeparators) {
        this.lenientSeparators = lenientSeparators;
        return this;
    }

    public boolean isLenientWithBareKeys() {
        return this.lenientBareKeys;
    }

    public TomlParser setLenientWithBareKeys(boolean lenientBareKeys) {
        this.lenientBareKeys = lenientBareKeys;
        return this;
    }

    public TomlParser setInitialStringBuilderCapacity(int initialStringBuilderCapacity) {
        this.initialStringBuilderCapacity = initialStringBuilderCapacity;
        return this;
    }

    public TomlParser setInitialListCapacity(int initialListCapacity) {
        this.initialListCapacity = initialListCapacity;
        return this;
    }

    @Override
    public ConfigFormat<CommentedConfig> getFormat() {
        return TomlFormat.instance();
    }

    boolean configWasEmpty() {
        return this.configWasEmpty;
    }

    ParsingMode getParsingMode() {
        return this.parsingMode;
    }

    <T> List<T> createList() {
        return new ArrayList(this.initialListCapacity);
    }

    CharsWrapper.Builder createBuilder() {
        return new CharsWrapper.Builder(this.initialStringBuilderCapacity);
    }

    boolean hasPendingComment() {
        return this.currentComment != null;
    }

    String consumeComment() {
        String comment = this.currentComment;
        this.currentComment = null;
        return comment;
    }

    void setComment(CharsWrapper comment) {
        if (comment != null) {
            this.currentComment = this.currentComment == null ? comment.toString() : this.currentComment + '\n' + comment.toString();
        }
    }

    void setComment(List<CharsWrapper> commentsList) {
        CharsWrapper.Builder builder = new CharsWrapper.Builder(32);
        if (!commentsList.isEmpty()) {
            Iterator<CharsWrapper> it = commentsList.iterator();
            builder.append(it.next());
            while (it.hasNext()) {
                builder.append('\n');
                builder.append(it.next());
            }
            this.setComment(builder.build());
        }
    }
}
