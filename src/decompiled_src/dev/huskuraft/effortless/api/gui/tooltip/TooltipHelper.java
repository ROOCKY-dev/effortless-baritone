/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.tooltip;

import dev.huskuraft.effortless.api.gui.Typeface;
import dev.huskuraft.effortless.api.input.Keys;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Style;
import dev.huskuraft.effortless.api.text.Text;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TooltipHelper {
    public static final int MAX_WIDTH_PER_LINE = 200;

    public static String makeProgressBar(int length, int filledLength) {
        return " " + "\u2588".repeat(Math.max(0, filledLength)) + "\u2592".repeat(Math.max(0, length - filledLength)) + " ";
    }

    private static ChatFormatting getLastTextStyle(String string) {
        int index = string.lastIndexOf(167);
        if (index != -1 && index + 1 != string.length()) {
            return ChatFormatting.getByCode(string.charAt(index + 1));
        }
        return null;
    }

    public static List<Text> wrapLines(Typeface typeface, Text text) {
        return TooltipHelper.wrapLines(typeface, text, 200);
    }

    public static List<Text> wrapLines(Typeface typeface, Text text, int lineWidth) {
        StringBuilder letters = new StringBuilder();
        LinkedList styles = new LinkedList();
        text.decompose((index, chr, style) -> {
            for (int i = 0; i < chr.length(); ++i) {
                letters.append(chr.charAt(i));
                styles.add(style);
            }
            return true;
        });
        BreakIterator iterator = BreakIterator.getLineInstance();
        iterator.setText(letters.toString());
        int start = iterator.first();
        LinkedList<Text> words = new LinkedList<Text>();
        int end = iterator.next();
        while (end != -1) {
            String word = letters.substring(start, end);
            List worldStyle = styles.subList(start, end);
            boolean wrapLine = false;
            if (word.endsWith("\n")) {
                word = word.substring(0, word.length() - 1);
                wrapLine = true;
            }
            Text result = Text.empty();
            for (int i = 0; i < word.length(); ++i) {
                result = result.append(Text.text(word.substring(i, i + 1)).withStyle((Style)worldStyle.get(i)));
            }
            words.add(result);
            if (wrapLine) {
                words.add(Text.text("\n"));
            }
            start = end;
            end = iterator.next();
        }
        LinkedList<Text> lines = new LinkedList<Text>();
        Text lastLine = Text.empty();
        int width = 0;
        for (Text word : words) {
            if (word.getString().equals("\n")) {
                lines.add(lastLine);
                lastLine = Text.empty();
                width = 0;
                continue;
            }
            int newWidth = typeface.measureWidth(word);
            if (width + newWidth > lineWidth) {
                lines.add(lastLine);
                lastLine = Text.empty();
                width = 0;
            }
            lastLine = lastLine.append(word);
            width += newWidth;
        }
        if (width > 0) {
            lines.add(lastLine);
        }
        return lines;
    }

    public static Text holdShiftForSummary() {
        if (TooltipHelper.isSummaryButtonDown()) {
            return Text.translate("effortless.tooltip.hold_for_summary", Text.translate("key.effortless.shift").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY);
        }
        return Text.translate("effortless.tooltip.hold_for_summary", Text.translate("key.effortless.shift").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.DARK_GRAY);
    }

    public static boolean isSummaryButtonDown() {
        return Keys.KEY_LEFT_SHIFT.isDown() || Keys.KEY_RIGHT_SHIFT.isDown();
    }

    public static List<Text> makeSummary(Typeface typeface, Text name, Text summary) {
        ArrayList<Text> tooltips = new ArrayList<Text>();
        tooltips.add(name);
        tooltips.add(TooltipHelper.holdShiftForSummary());
        if (TooltipHelper.isSummaryButtonDown()) {
            tooltips.add(Text.empty());
            tooltips.addAll(TooltipHelper.wrapLines(typeface, summary.withStyle(ChatFormatting.GRAY)));
        }
        return Collections.unmodifiableList(tooltips);
    }
}
