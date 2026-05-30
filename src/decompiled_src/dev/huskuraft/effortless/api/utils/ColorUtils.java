/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.utils;

import java.awt.Color;

public class ColorUtils {
    public static Color setAlpha(Color color, int alpha) {
        return new Color(ColorUtils.pack(color.getRed(), color.getGreen(), color.getBlue(), alpha), true);
    }

    public static Color setAlpha(Color color, double alpha) {
        return ColorUtils.setAlpha(color, (int)(alpha * 255.0));
    }

    public static int setAlpha(int color, int alpha) {
        return ColorUtils.setAlpha(new Color(color), alpha).getRGB();
    }

    public static int setAlpha(int color, double alpha) {
        return ColorUtils.setAlpha(new Color(color), (int)(alpha * 255.0)).getRGB();
    }

    public static int pack(int r, int g, int b) {
        return r << 16 | g << 8 | b;
    }

    public static int pack(double r, double g, double b) {
        return (int)(r * 255.0) << 16 | (int)(g * 255.0) << 8 | (int)(b * 255.0);
    }

    public static int pack(int r, int g, int b, int alpha) {
        return alpha << 24 | r << 16 | g << 8 | b;
    }

    public static int pack(double r, double g, double b, double alpha) {
        return (int)(alpha * 255.0) << 24 | (int)(r * 255.0) << 16 | (int)(g * 255.0) << 8 | (int)(b * 255.0);
    }

    public static double getDifference(Color colorA, Color colorB) {
        long rmean = ((long)colorA.getRed() + (long)colorB.getRed()) / 2L;
        long r = (long)colorA.getRed() - (long)colorB.getRed();
        long g = (long)colorA.getGreen() - (long)colorB.getGreen();
        long b = (long)colorA.getBlue() - (long)colorB.getBlue();
        return Math.sqrt(((512L + rmean) * r * r >> 8) + 4L * g * g + ((767L - rmean) * b * b >> 8));
    }

    public static String getHex(Color color) {
        return ColorUtils.getHex(color.getRGB());
    }

    public static String getHex(int rgbColor) {
        return "#" + Integer.toHexString(rgbColor).toUpperCase();
    }

    public static Color fromHex(String hexColorCode) throws NumberFormatException {
        return new Color(Integer.decode(hexColorCode));
    }
}
