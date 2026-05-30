/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.io.ConfigWriter;
import dev.huskuraft.effortless.api.nightconfig.core.io.IndentStyle;
import dev.huskuraft.effortless.api.nightconfig.core.io.NewlineStyle;
import dev.huskuraft.effortless.api.nightconfig.core.io.WriterOutput;
import dev.huskuraft.effortless.api.nightconfig.core.utils.StringUtils;
import dev.huskuraft.effortless.api.nightconfig.toml.StringWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.TableWriter;
import dev.huskuraft.effortless.api.nightconfig.toml.Toml;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class TomlWriter
implements ConfigWriter {
    private boolean omitIntermediateLevels = false;
    private boolean lenientBareKeys = false;
    private Predicate<UnmodifiableConfig> writeTableInlinePredicate = UnmodifiableConfig::isEmpty;
    private Predicate<String> writeStringLiteralPredicate = c -> false;
    private Predicate<List<?>> indentArrayElementsPredicate = c -> false;
    private char[] indent;
    private char[] newline;
    private int currentIndentLevel;

    public TomlWriter() {
        this.indent = IndentStyle.TABS.chars;
        this.newline = NewlineStyle.system().chars;
    }

    @Override
    public void write(UnmodifiableConfig config, Writer writer) {
        this.currentIndentLevel = -1;
        WriterOutput output = new WriterOutput(writer);
        TableWriter.writeNormal(config, new ArrayList<String>(), (CharacterOutput)output, this);
    }

    public boolean isLenientWithBareKeys() {
        return this.lenientBareKeys;
    }

    public void setLenientWithBareKeys(boolean lenientBareKeys) {
        this.lenientBareKeys = lenientBareKeys;
    }

    public boolean isOmitIntermediateLevels() {
        return this.omitIntermediateLevels;
    }

    public void setOmitIntermediateLevels(boolean omitIntermediateLevels) {
        this.omitIntermediateLevels = omitIntermediateLevels;
    }

    public void setWriteTableInlinePredicate(Predicate<UnmodifiableConfig> writeTableInlinePredicate) {
        this.writeTableInlinePredicate = writeTableInlinePredicate;
    }

    public void setWriteStringLiteralPredicate(Predicate<String> writeStringLiteralPredicate) {
        this.writeStringLiteralPredicate = writeStringLiteralPredicate;
    }

    public void setIndentArrayElementsPredicate(Predicate<List<?>> indentArrayElementsPredicate) {
        this.indentArrayElementsPredicate = indentArrayElementsPredicate;
    }

    public void setIndent(IndentStyle indentStyle) {
        this.indent = indentStyle.chars;
    }

    public void setIndent(String indentString) {
        this.indent = indentString.toCharArray();
    }

    public void setNewline(NewlineStyle newlineStyle) {
        this.newline = newlineStyle.chars;
    }

    public void setNewline(String newlineString) {
        this.newline = newlineString.toCharArray();
    }

    void increaseIndentLevel() {
        ++this.currentIndentLevel;
    }

    void decreaseIndentLevel() {
        --this.currentIndentLevel;
    }

    void writeIndent(CharacterOutput output) {
        for (int i = 0; i < this.currentIndentLevel; ++i) {
            output.write(this.indent);
        }
    }

    void writeNewline(CharacterOutput output) {
        output.write(this.newline);
    }

    void writeComment(String commentString, CharacterOutput output) {
        List<String> comments = StringUtils.splitLines(commentString);
        for (String comment : comments) {
            this.writeIndent(output);
            output.write('#');
            output.write(comment);
            output.write(this.newline);
        }
    }

    void writeKey(String key, CharacterOutput output) {
        if (Toml.isValidBareKey(key, this.lenientBareKeys)) {
            output.write(key);
        } else if (this.writeStringLiteralPredicate.test(key)) {
            StringWriter.writeLiteral(key, output);
        } else {
            StringWriter.writeBasic(key, output);
        }
    }

    boolean writesInline(UnmodifiableConfig config) {
        return this.writeTableInlinePredicate.test(config);
    }

    boolean writesLiteral(String string) {
        return this.writeStringLiteralPredicate.test(string);
    }

    boolean writesIndented(List<?> list) {
        return this.indentArrayElementsPredicate.test(list);
    }
}
