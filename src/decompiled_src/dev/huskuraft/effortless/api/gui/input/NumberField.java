/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.input;

import dev.huskuraft.effortless.api.gui.AbstractContainerWidget;
import dev.huskuraft.effortless.api.gui.button.Button;
import dev.huskuraft.effortless.api.gui.input.EditBox;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.text.Text;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.function.Consumer;

public class NumberField
extends AbstractContainerWidget {
    public static final int TYPE_INTEGER = 0;
    public static final int TYPE_DOUBLE = 1;
    private final int buttonWidth = 10;
    private final EditBox textField;
    private final Button minusButton;
    private final Button plusButton;
    private final NumberFormat format;
    private final int type;
    private final int ctrlMultiplier = 5;
    private final int shiftMultiplier = 10;
    private int step = 1;
    private Range range;

    public NumberField(Entrance entrance, int x, int y, int width, int height, int type) {
        super(entrance, x, y, width, height, Text.empty());
        this.focusable = true;
        this.format = new DecimalFormat();
        this.format.setGroupingUsed(false);
        if (type != 1 && type != 0) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        this.type = type;
        this.textField = this.addWidget(new EditBox(entrance, x + 10, y + 1, width - 20, height - 2, Text.empty()));
        this.minusButton = this.addWidget(new Button(entrance, x, y, 10, height, Text.text("-"), button -> {
            int valueChanged = this.step;
            if (this.getEntrance().getClient().getWindow().isControlDown()) {
                valueChanged = 5 * this.step;
            }
            if (this.getEntrance().getClient().getWindow().isShiftDown()) {
                valueChanged = 10 * this.step;
            }
            this.setValue(this.getNumber().doubleValue() - (double)valueChanged);
        }));
        this.plusButton = this.addWidget(new Button(entrance, x + width - 10, y, 10, height, Text.text("+"), button -> {
            int valueChanged = this.step;
            if (this.getEntrance().getClient().getWindow().isControlDown()) {
                valueChanged = 5 * this.step;
            }
            if (this.getEntrance().getClient().getWindow().isShiftDown()) {
                valueChanged = 10 * this.step;
            }
            this.setValue(this.getNumber().doubleValue() + (double)valueChanged);
        }));
        this.textField.setFilter(text -> {
            if (text.isEmpty() || text.equals("-")) {
                return true;
            }
            try {
                Number value = this.format.parse((String)text);
                if (this.range.isBelow(value)) {
                    this.setValue(this.range.min());
                    return false;
                }
                if (this.range.isAbove(value)) {
                    this.setValue(this.range.max());
                    return false;
                }
            }
            catch (ParseException value) {
                // empty catch block
            }
            try {
                return switch (type) {
                    case 1 -> {
                        if (Double.parseDouble(text) == BigDecimal.valueOf(Double.parseDouble(text)).setScale(3, RoundingMode.DOWN).doubleValue()) {
                            yield true;
                        }
                        yield false;
                    }
                    case 0 -> {
                        if (Integer.parseInt(text) == Integer.parseInt(text)) {
                            yield true;
                        }
                        yield false;
                    }
                    default -> false;
                };
            }
            catch (NumberFormatException e) {
                return false;
            }
        });
        this.setTooltipMessage(null);
        this.range = Range.UNBOUNDED;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        this.textField.setActive(active);
        this.minusButton.setActive(active);
        this.plusButton.setActive(active);
    }

    public Number getNumber() {
        if (this.textField.getValue().isEmpty()) {
            return 0;
        }
        try {
            return this.format.parse(this.textField.getValue());
        }
        catch (ParseException e) {
            return 0;
        }
    }

    public void setValue(Number number) {
        this.textField.setValue(number == null ? "" : this.format.format(number.doubleValue()));
    }

    public void setValueChangeListener(Consumer<Number> responder) {
        this.textField.setResponder(text -> {
            if (text.isEmpty() || text.equals("-")) {
                responder.accept(0);
            } else {
                try {
                    responder.accept(this.format.parse((String)text));
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
        });
    }

    public void setValueRange(Number min, Number max) {
        this.range = new Range(min, max);
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setTooltipMessage(Text tooltip) {
    }

    private record Range(Number min, Number max) {
        public static final Range UNBOUNDED = new Range(Integer.MIN_VALUE, Integer.MAX_VALUE);

        public boolean contains(int number) {
            return number >= this.min.intValue() && number <= this.max.intValue();
        }

        public boolean contains(double number) {
            return number >= this.min.doubleValue() && number <= this.max.doubleValue();
        }

        public boolean isBelow(Number number) {
            return number.doubleValue() < this.min.doubleValue();
        }

        public boolean isAbove(Number number) {
            return number.doubleValue() > this.max.doubleValue();
        }
    }
}
