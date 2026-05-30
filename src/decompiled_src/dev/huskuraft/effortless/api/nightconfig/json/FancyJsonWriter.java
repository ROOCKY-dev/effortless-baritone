/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.json;

import dev.huskuraft.effortless.api.nightconfig.core.NullObject;
import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.io.IndentStyle;
import dev.huskuraft.effortless.api.nightconfig.core.io.NewlineStyle;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import dev.huskuraft.effortless.api.nightconfig.core.io.WriterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import dev.huskuraft.effortless.api.nightconfig.json.MinimalJsonWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

public final class FancyJsonWriter
implements ConfigWriter {
    private static final char[] ENTRY_SEPARATOR = new char[]{':', ' '};
    private static final char[] VALUE_SEPARATOR = new char[]{',', ' '};
    private Predicate<UnmodifiableConfig> indentObjectElementsPredicate = c -> true;
    private Predicate<Collection<?>> indentArrayElementsPredicate = c -> true;
    private boolean newlineAfterObjectStart;
    private char[] newline;
    private char[] indent;
    private int currentIndentLevel;

    public FancyJsonWriter() {
        this.newline = NewlineStyle.system().chars;
        this.indent = IndentStyle.TABS.chars;
    }

    @Override
    public void write(UnmodifiableConfig config, Writer writer) {
        this.currentIndentLevel = 0;
        this.writeObject(config, new WriterOutput(writer));
    }

    private void writeObject(UnmodifiableConfig config, CharacterOutput output) {
        boolean indentElements;
        if (config.isEmpty()) {
            output.write(MinimalJsonWriter.EMPTY_OBJECT);
            return;
        }
        Iterator<Map.Entry<String, Object>> it = config.valueMap().entrySet().iterator();
        output.write('{');
        if (this.newlineAfterObjectStart) {
            output.write(this.newline);
        }
        if (indentElements = this.indentObjectElementsPredicate.test(config)) {
            output.write(this.newline);
            this.increaseIndentLevel();
        }
        while (true) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (indentElements) {
                this.writeIndent(output);
            }
            this.writeString(key, output);
            output.write(ENTRY_SEPARATOR);
            this.writeValue(value, output);
            if (!it.hasNext()) break;
            output.write(',');
            if (!indentElements) continue;
            output.write(this.newline);
        }
        if (indentElements) {
            output.write(this.newline);
        }
        if (indentElements) {
            this.decreaseIndentLevel();
            this.writeIndent(output);
        }
        output.write('}');
    }

    private void writeValue(Object v, CharacterOutput output) {
        if (v == null || v == NullObject.NULL_OBJECT) {
            output.write(MinimalJsonWriter.NULL_CHARS);
        } else if (v instanceof CharSequence) {
            this.writeString((CharSequence)v, output);
        } else if (v instanceof Enum) {
            this.writeString(((Enum)v).name(), output);
        } else if (v instanceof Number) {
            output.write(v.toString());
        } else if (v instanceof UnmodifiableConfig) {
            this.writeObject((UnmodifiableConfig)v, output);
        } else if (v instanceof Collection) {
            this.writeArray((Collection)v, output);
        } else if (v instanceof Boolean) {
            this.writeBoolean((Boolean)v, output);
        } else if (v instanceof Object[]) {
            this.writeArray(Arrays.asList((Object[])v), output);
        } else if (v.getClass().isArray()) {
            this.writeArray(v, output);
        } else {
            throw new WritingException("Unsupported value type: " + v.getClass());
        }
    }

    private void writeArray(Collection<?> collection, CharacterOutput output) {
        boolean indentElements;
        if (collection.isEmpty()) {
            output.write(MinimalJsonWriter.EMPTY_ARRAY);
            return;
        }
        Iterator<?> it = collection.iterator();
        output.write('[');
        if (this.newlineAfterObjectStart) {
            output.write(this.newline);
        }
        if (indentElements = this.indentArrayElementsPredicate.test(collection)) {
            output.write(this.newline);
            this.increaseIndentLevel();
        }
        while (true) {
            Object value = it.next();
            if (indentElements) {
                this.writeIndent(output);
            }
            this.writeValue(value, output);
            if (!it.hasNext()) break;
            output.write(VALUE_SEPARATOR);
            if (!indentElements) continue;
            output.write(this.newline);
        }
        if (indentElements) {
            output.write(this.newline);
        }
        if (indentElements) {
            this.decreaseIndentLevel();
            this.writeIndent(output);
        }
        output.write(']');
    }

    private void writeArray(Object array, CharacterOutput output) {
        int length = Array.getLength(array);
        ArrayList<Object> list = new ArrayList<Object>(length);
        for (int i = 0; i < length; ++i) {
            list.add(Array.get(array, i));
        }
        this.writeArray(list, output);
    }

    private void writeBoolean(boolean b, CharacterOutput output) {
        if (b) {
            output.write(MinimalJsonWriter.TRUE_CHARS);
        } else {
            output.write(MinimalJsonWriter.FALSE_CHARS);
        }
    }

    private void writeString(CharSequence s, CharacterOutput output) {
        output.write('\"');
        int length = s.length();
        for (int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            int escapeIndex = Utils.arrayIndexOf(MinimalJsonWriter.TO_ESCAPE, c);
            if (escapeIndex == -1) {
                output.write(c);
                continue;
            }
            char escaped = MinimalJsonWriter.ESCAPED[escapeIndex];
            output.write('\\');
            output.write(escaped);
        }
        output.write('\"');
    }

    private void increaseIndentLevel() {
        ++this.currentIndentLevel;
    }

    private void decreaseIndentLevel() {
        --this.currentIndentLevel;
    }

    private void writeIndent(CharacterOutput output) {
        for (int i = 0; i < this.currentIndentLevel; ++i) {
            output.write(this.indent);
        }
    }

    public FancyJsonWriter setIndentObjectElementsPredicate(Predicate<UnmodifiableConfig> indentObjectElementsPredicate) {
        this.indentObjectElementsPredicate = indentObjectElementsPredicate;
        return this;
    }

    public FancyJsonWriter setIndentArrayElementsPredicate(Predicate<Collection<?>> indentArrayElementsPredicate) {
        this.indentArrayElementsPredicate = indentArrayElementsPredicate;
        return this;
    }

    public FancyJsonWriter setNewlineAfterObjectStart(boolean newlineAfterObjectStart) {
        this.newlineAfterObjectStart = newlineAfterObjectStart;
        return this;
    }

    public FancyJsonWriter setIndent(IndentStyle indentStyle) {
        this.indent = indentStyle.chars;
        return this;
    }

    public FancyJsonWriter setIndent(String indent) {
        this.indent = indent.toCharArray();
        return this;
    }

    public FancyJsonWriter setNewline(NewlineStyle newlineStyle) {
        this.newline = newlineStyle.chars;
        return this;
    }

    public FancyJsonWriter setNewline(String newlineString) {
        this.newline = newlineString.toCharArray();
        return this;
    }
}
