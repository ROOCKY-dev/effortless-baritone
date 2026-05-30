/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.text;

import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Style;
import java.util.Collection;
import java.util.stream.Stream;

public interface Text
extends PlatformReference {
    public static Text empty() {
        return ContentFactory.getInstance().newText();
    }

    public static Text text(String text) {
        return ContentFactory.getInstance().newText(text);
    }

    public static Text translate(String text) {
        return ContentFactory.getInstance().newTranslatableText(text);
    }

    public static Text translate(String text, Object ... args) {
        for (int i = 0; i < args.length; ++i) {
            Object object = args[i];
            if (!(object instanceof Text)) continue;
            Text text1 = (Text)object;
            args[i] = text1.reference();
        }
        return ContentFactory.getInstance().newTranslatableText(text, args);
    }

    public Style getStyle();

    public Text withStyle(Style var1);

    default public Text withStyle(ChatFormatting ... styles) {
        return this.withStyle(this.getStyle().applyFormat(styles));
    }

    default public Text withColor(Integer color) {
        return this.withStyle(this.getStyle().withColor(color));
    }

    public String getString();

    public Collection<Text> getSiblings();

    public Text withSiblings(Collection<Text> var1);

    default public Text append(Text text) {
        return this.withSiblings(Stream.concat(this.getSiblings().stream(), Stream.of(text)).toList());
    }

    default public Text append(String text) {
        return this.append(Text.text(text));
    }

    public Text copy();

    public void decompose(Sink var1);

    public static interface Sink {
        public boolean accept(int var1, String var2, Style var3);
    }
}
