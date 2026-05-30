/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.gui.input;

import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.math.MathUtils;
import dev.huskuraft.effortless.api.platform.Client;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.RenderLayers;
import dev.huskuraft.effortless.api.renderer.Renderer;
import dev.huskuraft.effortless.api.text.Text;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class EditBox
extends AbstractWidget {
    private static final int BACKWARDS = -1;
    private static final int FORWARDS = 1;
    private static final int DEFAULT_TEXT_COLOR = 0xE0E0E0;
    private static final int CURSOR_INSERT_WIDTH = 1;
    private static final int CURSOR_INSERT_COLOR = -3092272;
    private static final String CURSOR_APPEND_CHARACTER = "_";
    private static final int BORDER_COLOR_FOCUSED = -1;
    private static final int BORDER_COLOR_INACTIVE = -11579569;
    private static final int BORDER_COLOR = -6250336;
    private static final int BACKGROUND_COLOR = -16777216;
    private String value;
    private int maxLength;
    private int frame;
    private boolean bordered;
    private boolean canLoseFocus;
    private boolean isEditable;
    private boolean shiftPressed;
    private int displayPos;
    private int cursorPos;
    private int highlightPos;
    private int textColor;
    private int textColorUneditable;
    @Nullable
    private String suggestion;
    @Nullable
    private Consumer<String> responder;
    private Predicate<String> filter;
    private BiFunction<String, Integer, String> formatter;
    @Nullable
    private Text hint;

    public EditBox(Entrance entrance, int x, int y, int width, int height, Text message) {
        this(entrance, x, y, width, height, null, message);
    }

    public EditBox(Entrance entrance, int x, int y, int width, int height, @Nullable EditBox editBox, Text message) {
        super(entrance, x, y, width, height, message);
        this.focusable = true;
        this.value = "";
        this.maxLength = Integer.MAX_VALUE;
        this.bordered = true;
        this.canLoseFocus = true;
        this.isEditable = true;
        this.textColor = 0xE0E0E0;
        this.textColorUneditable = 0x707070;
        this.filter = Objects::nonNull;
        this.formatter = (string, integer) -> string;
        if (editBox != null) {
            this.setValue(editBox.getValue());
        }
    }

    public static String filterText(String string) {
        return EditBox.filterText(string, false);
    }

    public static String filterText(String string, boolean wrapLines) {
        StringBuilder builder = new StringBuilder();
        for (char chr : string.toCharArray()) {
            if (EditBox.isAllowedChatCharacter(chr)) {
                builder.append(chr);
                continue;
            }
            if (!wrapLines || chr != '\n') continue;
            builder.append(chr);
        }
        return builder.toString();
    }

    public static boolean isAllowedChatCharacter(char c) {
        return c != '\u00a7' && c >= ' ' && c != '\u007f';
    }

    public static int offsetByCodepoints(String string, int i, int j) {
        int length = string.length();
        if (j >= 0) {
            for (int l = 0; i < length && l < j; ++l) {
                if (!Character.isHighSurrogate(string.charAt(i++)) || i >= length || !Character.isLowSurrogate(string.charAt(i))) continue;
                ++i;
            }
        } else {
            for (int l = j; i > 0 && l < 0; ++l) {
                if (!Character.isLowSurrogate(string.charAt(--i)) || i <= 0 || !Character.isHighSurrogate(string.charAt(i - 1))) continue;
                --i;
            }
        }
        return i;
    }

    public void setResponder(Consumer<String> consumer) {
        this.responder = consumer;
    }

    public void setFormatter(BiFunction<String, Integer, String> biFunction) {
        this.formatter = biFunction;
    }

    @Override
    public void onTick() {
        ++this.frame;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String string) {
        if (this.filter.test(string)) {
            this.value = string.length() > this.maxLength ? string.substring(0, this.maxLength) : string;
            this.moveCursorToEnd();
            this.setHighlightPos(this.cursorPos);
            this.onValueChange(string);
        }
    }

    public String getHighlighted() {
        int i = MathUtils.min(this.cursorPos, this.highlightPos);
        int j = MathUtils.max(this.cursorPos, this.highlightPos);
        return this.value.substring(i, j);
    }

    public void setFilter(Predicate<String> predicate) {
        this.filter = predicate;
    }

    private void onValueChange(String string) {
        if (this.responder != null) {
            this.responder.accept(string);
        }
    }

    private void deleteText(int i) {
        if (this.getEntrance().getClient().getWindow().isControlDown()) {
            this.deleteWords(i);
        } else {
            this.deleteChars(i);
        }
    }

    public void deleteWords(int i) {
        if (!this.value.isEmpty()) {
            if (this.highlightPos != this.cursorPos) {
                this.insertText("");
            } else {
                this.deleteChars(this.getWordPosition(i) - this.cursorPos);
            }
        }
    }

    public void deleteChars(int i) {
        if (!this.value.isEmpty()) {
            if (this.highlightPos != this.cursorPos) {
                this.insertText("");
            } else {
                String string;
                int l;
                int j = this.getCursorPos(i);
                int k = MathUtils.min(j, this.cursorPos);
                if (k != (l = MathUtils.max(j, this.cursorPos)) && this.filter.test(string = new StringBuilder(this.value).delete(k, l).toString())) {
                    this.value = string;
                    this.moveCursorTo(k);
                }
            }
        }
    }

    public int getWordPosition(int i) {
        return this.getWordPosition(i, this.getCursorPosition());
    }

    private int getWordPosition(int start, int ebd) {
        return this.getWordPosition(start, ebd, true);
    }

    private int getWordPosition(int start, int end, boolean bl) {
        int k = end;
        boolean bl2 = start < 0;
        int l = MathUtils.abs(start);
        for (int m = 0; m < l; ++m) {
            if (!bl2) {
                int n = this.value.length();
                if ((k = this.value.indexOf(32, k)) == -1) {
                    k = n;
                    continue;
                }
                while (bl && k < n && this.value.charAt(k) == ' ') {
                    ++k;
                }
                continue;
            }
            while (bl && k > 0 && this.value.charAt(k - 1) == ' ') {
                --k;
            }
            while (k > 0 && this.value.charAt(k - 1) != ' ') {
                --k;
            }
        }
        return k;
    }

    public void moveCursor(int i) {
        this.moveCursorTo(this.getCursorPos(i));
    }

    public void moveCursorTo(int i) {
        this.setCursorPosition(i);
        if (!this.shiftPressed) {
            this.setHighlightPos(this.cursorPos);
        }
        this.onValueChange(this.value);
    }

    public void moveCursorToStart() {
        this.moveCursorTo(0);
    }

    public void moveCursorToEnd() {
        this.moveCursorTo(this.value.length());
    }

    @Override
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        Client client = this.getEntrance().getClient();
        if (!this.canConsumeInput()) {
            return false;
        }
        this.shiftPressed = client.getWindow().isShiftDown();
        if (client.getWindow().isSelectAll(keyCode)) {
            this.moveCursorToEnd();
            this.setHighlightPos(0);
            return true;
        }
        if (client.getWindow().isCopy(keyCode)) {
            client.setClipboard(this.getHighlighted());
            return true;
        }
        if (client.getWindow().isPaste(keyCode)) {
            if (this.isEditable) {
                this.insertText(client.getClipboard());
            }
            return true;
        }
        if (client.getWindow().isCut(keyCode)) {
            client.setClipboard(this.getHighlighted());
            if (this.isEditable) {
                this.insertText("");
            }
            return true;
        }
        return switch (keyCode) {
            case 259 -> {
                if (this.isEditable) {
                    this.shiftPressed = false;
                    this.deleteText(-1);
                    this.shiftPressed = client.getWindow().isShiftDown();
                }
                yield true;
            }
            case 261 -> {
                if (this.isEditable) {
                    this.shiftPressed = false;
                    this.deleteText(1);
                    this.shiftPressed = client.getWindow().isShiftDown();
                }
                yield true;
            }
            case 262 -> {
                if (client.getWindow().isControlDown()) {
                    this.moveCursorTo(this.getWordPosition(1));
                } else {
                    this.moveCursor(1);
                }
                yield true;
            }
            case 263 -> {
                if (client.getWindow().isControlDown()) {
                    this.moveCursorTo(this.getWordPosition(-1));
                } else {
                    this.moveCursor(-1);
                }
                yield true;
            }
            case 268 -> {
                this.moveCursorToStart();
                yield true;
            }
            case 269 -> {
                this.moveCursorToEnd();
                yield true;
            }
            default -> false;
        };
    }

    public boolean canConsumeInput() {
        return this.isVisible() && this.isFocused() && this.isEditable() && this.isActive();
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isVisible() || !this.isActive()) {
            this.setFocused(false);
            return false;
        }
        boolean isMouseOver = this.isMouseOver(mouseX, mouseY);
        if (this.canLoseFocus) {
            if (isMouseOver && !this.isFocused()) {
                this.getEntrance().getClient().getSoundManager().playButtonClickSound();
            }
            this.setFocused(isMouseOver);
        }
        if (this.isFocused() && isMouseOver && button == 0) {
            int j = (int)MathUtils.floor(mouseX) - this.getX();
            if (this.bordered) {
                j -= 4;
            }
            String string = this.getTypeface().subtractByWidth(this.value.substring(this.displayPos), this.getInnerWidth(), false);
            this.moveCursorTo(this.getTypeface().subtractByWidth(string, j, false).length() + this.displayPos);
            return true;
        }
        return false;
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        int var10004;
        int var10003;
        int var10002;
        int k;
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        if (this.isBordered()) {
            k = this.isActive() ? (this.isFocused() ? -1 : -6250336) : -11579569;
            renderer.renderRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), k);
            renderer.renderRect(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, -16777216);
        }
        k = this.isActive() && this.isEditable ? this.textColor : this.textColorUneditable;
        int l = this.cursorPos - this.displayPos;
        int m = this.highlightPos - this.displayPos;
        String string = this.getTypeface().subtractByWidth(this.value.substring(this.displayPos), this.getInnerWidth(), false);
        boolean bl = l >= 0 && l <= string.length();
        boolean bl2 = this.isFocused() && this.frame / 6 % 2 == 0 && bl;
        int n = this.bordered ? this.getX() + 4 : this.getX();
        int o = this.bordered ? this.getY() + (this.getHeight() - 8) / 2 : this.getY();
        int p = n;
        if (m > string.length()) {
            m = string.length();
        }
        if (!string.isEmpty()) {
            String string2 = bl ? string.substring(0, l) : string;
            p = renderer.renderTextFromStart(this.getTypeface(), this.formatter.apply(string2, this.displayPos), n, o, k, true);
        }
        boolean bl3 = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
        int q = p;
        if (!bl) {
            q = l > 0 ? n + this.getWidth() : n;
        } else if (bl3) {
            q = p - 1;
            --p;
        }
        if (!string.isEmpty() && bl && l < string.length()) {
            renderer.renderTextFromStart(this.getTypeface(), this.formatter.apply(string.substring(l), this.cursorPos), p, o, k, true);
        }
        if (this.hint != null && string.isEmpty() && !this.isFocused()) {
            renderer.renderTextFromStart(this.getTypeface(), this.hint, p, o, k, true);
        }
        if (!bl3 && this.suggestion != null) {
            renderer.renderTextFromStart(this.getTypeface(), this.suggestion, q - 1, o, -8355712, true);
        }
        if (bl2) {
            if (bl3) {
                var10002 = o - 1;
                var10003 = q + 1;
                var10004 = o + 1;
                renderer.renderRect(q, var10002, var10003, var10004 + 9, -3092272);
            } else {
                renderer.renderTextFromStart(this.getTypeface(), CURSOR_APPEND_CHARACTER, q, o, k, true);
            }
        }
        if (m != l) {
            int r = n + this.getTypeface().measureWidth(string.substring(0, m));
            var10002 = o - 1;
            var10003 = r - 1;
            var10004 = o + 1;
            Objects.requireNonNull(this.getTypeface());
            this.renderHighlight(renderer, q, var10002, var10003, var10004 + 9);
        }
    }

    private void renderHighlight(Renderer renderer, int i, int j, int k, int l) {
        int m;
        if (i < k) {
            m = i;
            i = k;
            k = m;
        }
        if (j < l) {
            m = j;
            j = l;
            l = m;
        }
        if (k > this.getX() + this.getWidth()) {
            k = this.getX() + this.getWidth();
        }
        if (i > this.getX() + this.getWidth()) {
            i = this.getX() + this.getWidth();
        }
        renderer.renderRect(RenderLayers.GUI_TEXT_HIGHLIGHT, i, j, k, l, -16776961);
    }

    private int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int i) {
        this.maxLength = i;
        if (this.value.length() > i) {
            this.value = this.value.substring(0, i);
            this.onValueChange(this.value);
        }
    }

    public int getCursorPosition() {
        return this.cursorPos;
    }

    public void setCursorPosition(int i) {
        this.cursorPos = MathUtils.clamp(i, 0, this.value.length());
    }

    private boolean isBordered() {
        return this.bordered;
    }

    public void setBordered(boolean bl) {
        this.bordered = bl;
    }

    public void setTextColor(int i) {
        this.textColor = i;
    }

    public void setTextColorUneditable(int i) {
        this.textColorUneditable = i;
    }

    @Override
    public boolean onFocusMove(boolean forwards) {
        return super.isVisible() && this.isEditable && super.onFocusMove(forwards);
    }

    protected void onFocusedChanged(boolean bl) {
        if (bl) {
            this.frame = 0;
        }
    }

    private boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean bl) {
        this.isEditable = bl;
    }

    public int getInnerWidth() {
        return this.isBordered() ? this.getWidth() - 8 : this.getWidth();
    }

    public void setHighlightPos(int i) {
        int j = this.value.length();
        this.highlightPos = MathUtils.clamp(i, 0, j);
        if (this.getTypeface() != null) {
            if (this.displayPos > j) {
                this.displayPos = j;
            }
            int k = this.getInnerWidth();
            String string = this.getTypeface().subtractByWidth(this.value.substring(this.displayPos), k, false);
            int l = string.length() + this.displayPos;
            if (this.highlightPos == this.displayPos) {
                this.displayPos -= this.getTypeface().subtractByWidth(this.value, k, true).length();
            }
            if (this.highlightPos > l) {
                this.displayPos += this.highlightPos - l;
            } else if (this.highlightPos <= this.displayPos) {
                this.displayPos -= this.displayPos - this.highlightPos;
            }
            this.displayPos = MathUtils.clamp(this.displayPos, 0, j);
        }
    }

    public void setCanLoseFocus(boolean bl) {
        this.canLoseFocus = bl;
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
    }

    public void setSuggestion(@Nullable String string) {
        this.suggestion = string;
    }

    public int getScreenX(int i) {
        return i > this.value.length() ? this.getX() : this.getX() + this.getTypeface().measureWidth(this.value.substring(0, i));
    }

    public void setHint(Text text) {
        this.hint = text;
    }

    public void insertText(String string) {
        String string3;
        String string2;
        int l;
        int i = MathUtils.min(this.cursorPos, this.highlightPos);
        int j = MathUtils.max(this.cursorPos, this.highlightPos);
        int k = this.maxLength - this.value.length() - (i - j);
        if (k < (l = (string2 = EditBox.filterText(string)).length())) {
            string2 = string2.substring(0, k);
            l = k;
        }
        if (this.filter.test(string3 = new StringBuilder(this.value).replace(i, j, string2).toString())) {
            this.value = string3;
            this.setCursorPosition(i + l);
            this.setHighlightPos(this.cursorPos);
            this.onValueChange(this.value);
        }
    }

    private int getCursorPos(int i) {
        return EditBox.offsetByCodepoints(this.value, this.cursorPos, i);
    }

    @Override
    public boolean onCharTyped(char character, int modifiers) {
        if (!this.canConsumeInput()) {
            return false;
        }
        if (EditBox.isAllowedChatCharacter(character)) {
            if (this.isEditable) {
                this.insertText(Character.toString(character));
            }
            return true;
        }
        return false;
    }
}
