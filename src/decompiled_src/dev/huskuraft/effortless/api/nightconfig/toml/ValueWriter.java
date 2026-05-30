/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.Config;
import dev.huskuraft.effortless.api.nightconfig.core.NullObject;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import dev.huskuraft.effortless.api.nightconfig.toml.ArrayWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.StringWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.TableWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.TemporalWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.TomlWriter;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.List;

final class ValueWriter {
    private static void writeString(String string, CharacterOutput output, TomlWriter writer) {
        if (writer.writesLiteral(string)) {
            StringWriter.writeLiteral(string, output);
        } else {
            StringWriter.writeBasic(string, output);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static void write(Object value, CharacterOutput output, TomlWriter writer) {
        if (value instanceof Config) {
            TableWriter.writeInline((Config)value, output, writer);
            return;
        }
        if (value instanceof List) {
            List list = (List)value;
            if (!list.isEmpty()) {
                if (list.stream().allMatch(Config.class::isInstance)) {
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Object table = iterator.next();
                        TableWriter.writeInline((Config)table, output, writer);
                        if (!iterator.hasNext()) continue;
                        output.write(ArrayWriter.ELEMENT_SEPARATOR);
                    }
                    return;
                }
            }
            ArrayWriter.write((List)value, output, writer);
            return;
        }
        if (value instanceof CharSequence) {
            ValueWriter.writeString(value.toString(), output, writer);
            return;
        }
        if (value instanceof Enum) {
            ValueWriter.writeString(((Enum)value).name(), output, writer);
            return;
        }
        if (value instanceof Temporal) {
            TemporalWriter.write((Temporal)value, output);
            return;
        }
        if (value instanceof Float || value instanceof Double) {
            double d = ((Number)value).doubleValue();
            if (Double.isNaN(d)) {
                output.write("nan");
                return;
            }
            if (d == Double.POSITIVE_INFINITY) {
                output.write("+inf");
                return;
            }
            if (d == Double.NEGATIVE_INFINITY) {
                output.write("-inf");
                return;
            }
            output.write(value.toString());
            return;
        }
        if (!(value instanceof Number) && !(value instanceof Boolean)) {
            if (value == null) throw new WritingException("TOML doesn't support null values");
            if (value != NullObject.NULL_OBJECT) throw new WritingException("Unsupported value type: " + value.getClass());
            throw new WritingException("TOML doesn't support null values");
        }
        output.write(value.toString());
    }

    private ValueWriter() {
    }
}
