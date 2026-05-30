/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui;

import java.awt.Color;

public class Dimens {
    public static final int SLOT_OFFSET_X = 20;
    public static final int SLOT_OFFSET_Y = 20;
    public static final int ICON_WIDTH = 30;
    public static final int ICON_HEIGHT = 30;
    public static final int SLOT_WIDTH = 18;
    public static final int SLOT_HEIGHT = 18;

    public static class Screen {
        public static final int TITLE_24 = 24;
        public static final int TITLE_36 = 32;
        public static final int BUTTON_ROW_1 = 36;
        public static final int BUTTON_ROW_2 = 60;
        public static final int BUTTON_ROW_3 = 84;
    }

    public static class Buttons {
        public static final int QUARTER_WIDTH = 72;
        public static final int HALF_WIDTH = 150;
        public static final int FULL_WIDTH = 306;
        public static final int TAB_WIDTH = 214;
        public static final int ROW_WIDTH = 282;
        public static final int HEIGHT = 20;
        public static final int VERTICAL_SPACING = 4;
        public static final int HORIZONTAL_SPACING = 6;
        public static final int VERTICAL_PADDING = 8;
        public static final int HORIZONTAL_PADDING = 8;
    }

    public static class Entry {
        public static final int ROW_WIDTH = 282;
        public static final int MAX_SLOT_COUNT = 12;
    }

    public static class CellRing {
        public static final Color RADIAL_COLOR = new Color(0.44f, 0.44f, 0.44f, 1.0f);
        public static final Color HIGHLIGHT_COLOR = new Color(0.84f, 0.84f, 0.84f, 1.0f);
        public static final int RADIAL_SIZE = 12;
        public static final double RING_INNER_EDGE = 6.0;
        public static final double RING_OUTER_EDGE = 15.0;

        private CellRing() {
        }
    }

    public static class Icon {
        public static final int SIZE_14 = 14;
        public static final int SIZE_18 = 18;
        public static final int SIZE_26 = 26;
        public static final int SIZE_34 = 34;
        public static final int SIZE_66 = 66;
    }
}
